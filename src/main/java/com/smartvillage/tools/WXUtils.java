package com.smartvillage.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WXUtils {
	private static final Logger logger = LoggerFactory.getLogger(WXUtils.class);
	
	public static String get(String url) {
		String body = null;
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {		
			logger.info("create httppost:" + url);
			HttpGet get = new HttpGet(url);
			get.addHeader("Accept-Charset","utf-8");
			HttpResponse response = sendRequest(httpClient, get);
			body = parseResponse(response);
		} catch (IOException e) {
			logger.error("send post request failed: {}", e.getMessage());
		}
 
		return body;
	}
	
	@SuppressWarnings("unused")
	private static String paramsToString(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		try{
			for (String key : params.keySet()) {
				sb.append(String.format("&%s=%s", key, URLEncoder.encode(params.get(key),StandardCharsets.UTF_8.toString())));
			}
		}catch(UnsupportedEncodingException e){
			logger.warn("{}: encode url parameters failed", e.getMessage());
		}
		return sb.length() > 0 ? "?".concat(sb.substring(1)) : "";
	}
	
	private static HttpResponse sendRequest(CloseableHttpClient httpclient, HttpUriRequest httpost)
			throws ClientProtocolException, IOException {
		HttpResponse response = null;
		response = httpclient.execute(httpost);
		return response;
	}
	
	private static String parseResponse(HttpResponse response) {
		logger.info("get response from http server..");
		HttpEntity entity = response.getEntity();
 
		logger.info("response status: " + response.getStatusLine());
		Charset charset = ContentType.getOrDefault(entity).getCharset();
		if (charset != null) {
			logger.info(charset.name());
		}
 
		String body = null;
		try {
			body = EntityUtils.toString(entity, "utf-8");
			logger.info("body " + body);
		} catch (IOException e) {
			logger.warn("{}: cannot parse the entity", e.getMessage());
		}
 
		return body;
	}

}
