package com.vooda.im.udpconnector;

import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import com.vooda.im.Constant;
import com.vooda.im.ServerMessage;
import com.vooda.im.util.PropertyUtil;

/**
 * 服务器端发送服务
 */
public class Sender implements Runnable {

	/** UDP通道 */
	protected DatagramChannel channel;
	/** 接收消息数量 */
	protected AtomicLong queueIn = new AtomicLong(0);
	/** 发送消息数量 */
	protected AtomicLong queueOut = new AtomicLong(0);
	/** 消息体最大长度 */
	protected int bufferSize = Constant.PUSH_MSG_HEADER_LEN + PropertyUtil.getPropertyInt("PUSH_MSG_MAX_CONTENT_LEN");
	/** 服务停止状态位 */
	protected boolean stoped = false;
	/** 通道缓存流 */
	protected ByteBuffer buffer;

	protected Object enQueSignal = new Object();

	protected ConcurrentLinkedQueue<ServerMessage> mq = new ConcurrentLinkedQueue<ServerMessage>();

	public Sender(DatagramChannel channel) {
		this.channel = channel;
	}

	public void init() {
		buffer = ByteBuffer.allocate(bufferSize);
	}

	public void stop() {
		this.stoped = true;
	}

	public void run() {
		while (!this.stoped) {
			try {
				synchronized (enQueSignal) {
					while (mq.isEmpty() == true && stoped == false) {
						try {
							enQueSignal.wait(1);
						} catch (InterruptedException e) {

						}
						// System.out.println("sender wake up");
					}
					processMessage();

				}
			} catch (Exception e) {
				e.printStackTrace();
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	protected void processMessage() throws Exception {
		buffer.clear();
		ServerMessage pendingMessage = dequeue();
		if (pendingMessage == null) {
			// Thread.yield();
			return;
		}
		buffer.put(pendingMessage.getData());
		buffer.flip();
		channel.send(buffer, pendingMessage.getSocketAddress());
		// System.out.println(DateTimeUtil.getCurDateTime()+" s:"+StringUtil.convert(pendingMessage.getData())+" to  :"+pendingMessage.getSocketAddress().toString());
	}

	protected boolean enqueue(ServerMessage message) {
		boolean result = mq.add(message);
		if (result == true) {
			queueIn.addAndGet(1);
		}
		return result;
	}

	protected ServerMessage dequeue() {
		ServerMessage m = mq.poll();
		if (m != null) {
			queueOut.addAndGet(1);
		}
		return m;
	}

	public boolean send(ServerMessage message) {
		return enqueue(message);
	}
}