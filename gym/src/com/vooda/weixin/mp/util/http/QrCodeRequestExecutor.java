package com.vooda.weixin.mp.util.http;

import com.vooda.weixin.common.bean.result.WxError;
import com.vooda.weixin.common.exception.WxErrorException;
import com.vooda.weixin.common.util.fs.FileUtils;
import com.vooda.weixin.common.util.http.InputStreamResponseHandler;
import com.vooda.weixin.common.util.http.RequestExecutor;
import com.vooda.weixin.common.util.http.Utf8ResponseHandler;
import com.vooda.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * 获得QrCode图片 请求执行器
 * @author
 */
public class QrCodeRequestExecutor implements RequestExecutor<File, WxMpQrCodeTicket> {

	public File execute(CloseableHttpClient httpclient, HttpHost httpProxy, String uri, WxMpQrCodeTicket ticket) throws WxErrorException, ClientProtocolException, IOException {
		if (ticket != null) {
			if (uri.indexOf('?') == -1) {
				uri += '?';
			}
			uri += uri.endsWith("?") ? "ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8") : "&ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8");
		}

		HttpGet httpGet = new HttpGet(uri);
		if (httpProxy != null) {
			RequestConfig config = RequestConfig.custom().setProxy(httpProxy).build();
			httpGet.setConfig(config);
		}

		CloseableHttpResponse response = httpclient.execute(httpGet);

		Header[] contentTypeHeader = response.getHeaders("Content-Type");
		if (contentTypeHeader != null && contentTypeHeader.length > 0) {
			// 出错
			if (ContentType.TEXT_PLAIN.getMimeType().equals(contentTypeHeader[0].getValue())) {
				String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
				throw new WxErrorException(WxError.fromJson(responseContent));
			}
		}
		InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);

		File localFile = FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
		return localFile;
	}

}
