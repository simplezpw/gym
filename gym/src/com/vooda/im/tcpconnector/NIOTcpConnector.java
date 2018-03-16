/*
 *Copyright 2014 DDPush
 *Author: AndyKwok(in English) GuoZhengzhu(in Chinese)
 *Email: ddpush@126.com
 *

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

 */
package com.vooda.im.tcpconnector;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.vooda.im.util.PropertyUtil;
import com.vooda.im.util.StringUtil;

/**
 * newIO TCP服务端
 */
public class NIOTcpConnector implements Runnable {
	/** 超时时间 */
	private static int sockTimout = 1000 * PropertyUtil.getPropertyInt("CLIENT_TCP_SOCKET_TIMEOUT");
	/** 端口号 */
	private static int port = PropertyUtil.getPropertyInt("CLIENT_TCP_PORT");
	/** 停止服务状态 */
	private boolean stoped = false;
	/** 同package内可以使用的服务通道 */
	ServerSocketChannel channel = null;
	/** 通道维护者 */
	private Selector selector = null;
	/** 服务线程池 */
	private ExecutorService executor;
	/** 服务最小线程数 */
	private int minThreads = PropertyUtil.getPropertyInt("CLIENT_TCP_MIN_THREAD");
	/** 服务最大线程数 */
	private int maxThreads = PropertyUtil.getPropertyInt("CLIENT_TCP_MAX_THREAD");
	/** 事件队列 */
	protected ConcurrentLinkedQueue<Runnable> events = new ConcurrentLinkedQueue<Runnable>();

	/**
	 * 初始化
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		initExecutor();
		initChannel();
	}

	/**
	 * 注册服务通道
	 * 
	 * @throws Exception
	 */
	public void initChannel() throws Exception {
		channel = ServerSocketChannel.open();
		channel.socket().bind(new InetSocketAddress(port));
		channel.configureBlocking(false);
		System.out.println("nio tcp connector port:" + port);
		selector = Selector.open();
		channel.register(selector, SelectionKey.OP_ACCEPT);
	}

	/**
	 * 注册线程池
	 * 
	 * @throws Exception
	 */
	public void initExecutor() throws Exception {
		if (executor == null) {
			executor = new ThreadPoolExecutor(minThreads, maxThreads, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		}
	}

	/**
	 * 唤醒通道维护者
	 */
	public void wakeupSelector() {
		if (this.selector == null)
			return;
		try {
			selector.wakeup();
		} catch (Exception e) {
		}
	}

	/**
	 * 处理消息事件
	 * 
	 * @param event
	 */
	public void addEvent(Runnable event) {
		if (selector == null) {
			return;
		}

		events.add(event);

		if (stoped == false && selector != null) {
			selector.wakeup();
		}

	}

	@Override
	public void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		synchronized (this) {
			this.notifyAll();
		}

		while (!stoped && selector != null) {
			try {
				handleEvent();
				handleTimeout();
				handleChannel();
			} catch (java.nio.channels.ClosedSelectorException cse) {
				//
			} catch (java.nio.channels.CancelledKeyException cke) {
				//
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		closeSelector();
		stopExecutor();

	}

	/**
	 * 设定运行停止位
	 */
	public void stop() {
		this.stoped = true;
		if (this.selector != null) {
			try {
				selector.wakeup();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 关闭任务线程池
	 */
	private void stopExecutor() {
		try {
			if (executor != null)
				executor.shutdownNow();
		} catch (Exception e) {
			e.printStackTrace();
		}
		executor = null;
	}

	/**
	 * 关闭通道维护者
	 */
	private void closeSelector() {
		if (selector != null) {
			try {
				selector.wakeup();
				selector.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				selector = null;
			}
		}
	}

	/**
	 * 处理消息事件
	 */
	private void handleEvent() {
		Runnable r = null;
		while (true) {
			r = events.poll();
			if (r == null) {
				// no events
				return;
			}
			try {
				r.run();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 处理timeout
	 */
	private void handleTimeout() {
		Selector tmpsel = selector;
		Set<SelectionKey> keys = (stoped == false && tmpsel != null) ? tmpsel.keys() : null;
		if (keys == null) {
			return;
		}
		Iterator<SelectionKey> it = keys.iterator();
		long now = System.currentTimeMillis();
		// cancel timeout and no interestOps keys,close socket and channel
		while (it.hasNext()) {
			SelectionKey key = it.next();
			// 通道是服务用通道
			if (key.channel() instanceof ServerSocketChannel) {
				continue;
			}
			// 该通道已经被关闭
			if (key.isValid() == false) {
				continue;
			}
			try {
				// if(key.interestOps() != 0){
				// continue;
				// }
				// 通道键上没有附加对象
				MessengerTask task = (MessengerTask) key.attachment();
				if (task == null) {
					cancelKey(key);
					continue;
				}
				// 对象空闲并且超时
				if (task.isWritePending() == false && now - task.getLastActive() > sockTimout) {
					cancelKey(key);
				}
			} catch (CancelledKeyException e) {
				cancelKey(key);
			}
		}
	}

	/**
	 * 注册服务通道
	 * @throws Exception
	 */
	private void handleChannel() throws Exception {
		if (selector.select() == 0) {
			try {
				Thread.sleep(1);
			} catch (Exception e) {

			}
			return;
		}

		Iterator<SelectionKey> it = selector.selectedKeys().iterator();
		while (it.hasNext()) {
			SelectionKey key = it.next();
			// 清除旧维护key
			it.remove();
			// Is a new connection coming in?
			// 测试此键的通道是否已准备好接受新的套接字连接
			if (key.isAcceptable()) {
				try {
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel channel = server.accept();
					channel.configureBlocking(false);
					channel.socket().setSoTimeout(sockTimout);
					// channel.socket().setReceiveBufferSize(1024);
					// channel.socket().setSendBufferSize(1024);
					// 注册读取服务对象
					MessengerTask task = new MessengerTask(this, channel);
					channel.register(selector, SelectionKey.OP_READ, task);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// 通道读取或写入已经准备好
			if (key.isReadable() || key.isWritable()) {
				try {
					// 取得注册的对象
					MessengerTask task = (MessengerTask) key.attachment();
					// 对象注册不成功
					if (task == null) {// this should never happen
						cancelKey(key);
						continue;
					}
					// 开始执行服务线程
					task.setKey(key);
					executor.execute(task);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * 关闭指定选择键的通道和套接字
	 * @param key
	 */
	public void cancelKey(SelectionKey key) {
		if (key == null)
			return;

		key.cancel();
		try {
			((SocketChannel) key.channel()).socket().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			key.channel().close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		class test implements Runnable {
			AtomicInteger cnt;

			public test(AtomicInteger cnt) {
				this.cnt = cnt;
			}

			public void run() {
				try {
					Socket s = new Socket("127.0.0.1", PropertyUtil.getPropertyInt("CLIENT_TCP_PORT"));
					s.setSoTimeout(0);
					InputStream in = s.getInputStream();
					OutputStream out = s.getOutputStream();

					// for(int i = 600000; i< 700000; i++){
					while (true) {
						int key = cnt.addAndGet(1);
						if (key > 14999) {
							break;
						}
						out.write(1);
						out.write(1);
						out.write(0);
						out.write(StringUtil.md5Byte("" + key));
						out.write(0);
						out.write(0);
						out.flush();

						long lastA = System.currentTimeMillis();

						byte[] b = new byte[1];
						int read = -1;
						while ((read = in.read(b)) >= 0) {
							if (read == 0) {
								try {
									Thread.sleep(1);
								} catch (Exception e) {
								}
								continue;
							}
							if (System.currentTimeMillis() - lastA > 60 * 1000) {
								out.write(1);
								out.write(1);
								out.write(0);
								out.write(StringUtil.md5Byte("" + key));
								out.write(0);
								out.write(0);
								out.flush();
							}

							System.out.print(StringUtil.convert(b));
						}

					}
					// while(true){
					// int read = in.read(b);
					// System.out.println(b[0]);
					// if(read < 0){
					// break;
					// }
					// }
					s.close();
					System.out.println("bye~~");
					// long time = din.readLong();
					// System.out.println("time:"+time);
					// din.readLong();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		Thread[] worker = new Thread[100];
		AtomicInteger cnt = new AtomicInteger(4999);
		for (int i = 0; i < worker.length; i++) {
			Thread t = new Thread(new test(cnt));
			worker[i] = t;
		}
		for (int i = 0; i < worker.length; i++) {
			worker[i].start();
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}
		}

		for (int i = 0; i < worker.length; i++) {
			try {
				worker[i].join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("done~~~~~~~~~~~~~");
	}
}
