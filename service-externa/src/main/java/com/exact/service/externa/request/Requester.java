package com.exact.service.externa.request;

import java.io.IOException;
import java.net.URI;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

@Component
public class Requester implements IRequester {
	
	private CloseableHttpClient httpClient;
	private CloseableHttpResponse response;
	
	public Requester() {
		httpClient = HttpClients.createDefault();
	}

	@Override
	public CloseableHttpResponse request(HttpUriRequest httpGet) throws ClientProtocolException, IOException{
		response = httpClient.execute(httpGet);
		return response;
	}

	@Override
	public CloseableHttpResponse requestPost(HttpPost httpPost) throws ClientProtocolException, IOException {
		response = httpClient.execute(httpPost);
		return response;
	}

	@Override
	public CloseableHttpResponse requestPut(HttpPut httpPut) throws ClientProtocolException, IOException {
		response = httpClient.execute(httpPut);
		return response;
	}




	
	
	
}
