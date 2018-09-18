package com.exact.service.externa.request;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;

public interface IRequester {
	
	CloseableHttpResponse request(HttpUriRequest httpGet) throws ClientProtocolException, IOException;
}
