package com.exact.service.externa.request;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;

public interface IRequester {
	
	CloseableHttpResponse request(HttpUriRequest httpGet) throws ClientProtocolException, IOException;
	CloseableHttpResponse requestPost(HttpPost httpPost) throws ClientProtocolException, IOException;
	CloseableHttpResponse requestPut(HttpPut httpPut) throws ClientProtocolException, IOException;
}
