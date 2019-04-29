package com.exact.service.externa.edao.classes;

import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.IAmbitoDiasEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

import io.jsonwebtoken.io.IOException;

@Repository
public class AmbitoDiaEdao implements IAmbitoDiasEdao {

	@Value("${service.dias-laborales}")
	private String ambitosPath;
	
	
	@Autowired
	private IRequester requester;
	

	private final String path = "/ambitos";
	
	
	private final String subpath = "/diaslaborales";
	@Override
	public Iterable<Map<String, Object>> listarAmbitos() throws Exception{
		HttpGet httpGet = new HttpGet(ambitosPath + path);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

	@Override
	public Iterable<Map<String, Object>> listardiaslaborales(Long ambitoid)
			throws ClientProtocolException, java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + path + "/" + ambitoid.toString() + subpath);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

	
	
	
}
