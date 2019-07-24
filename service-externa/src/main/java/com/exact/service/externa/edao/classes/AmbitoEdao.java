package com.exact.service.externa.edao.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.IAmbitoEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

@Repository
public class AmbitoEdao implements IAmbitoEdao {

	@Value("${service.ambitos}")
	private String ambitosPath;
	
	@Autowired
	private IRequester requester;
	
	private final String path = "/ambitos";
	private final String pathsub = "/subambitos";
	
	@Override
	public Iterable<Map<String, Object>> listarAmbitos() throws IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + path);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				JSONArray responseJson = new JSONArray(response);		
				return CommonUtils.jsonArrayToMap(responseJson);
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitos() throws IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + pathsub);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				JSONArray responseJson = new JSONArray(response);		
				return CommonUtils.jsonArrayToMap(responseJson);
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitosActivosByAmbitoId(Long id) throws IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + path +"/"+ id +pathsub);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				JSONArray responseJson = new JSONArray(response);		
				return CommonUtils.jsonArrayToMap(responseJson);
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}

	}

	@Override
	public Map<String, Object> guardarSubAmbito(String subambito) throws ClientProtocolException, IOException, JSONException {
		HttpPost httpost = new HttpPost(ambitosPath + pathsub);
		StringEntity entity = new StringEntity(subambito);
		httpost.setEntity(entity);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPost(httpost);
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				JSONObject responseJson = new JSONObject(response);		
				return CommonUtils.jsonToMap(responseJson);
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
	}

	@Override
	public Map<String, Object> modificarSubAmbito(Long id, String subambito) throws IOException, JSONException {
		HttpPut httput = new HttpPut(ambitosPath + pathsub+ "/"+ id);
		StringEntity entity = new StringEntity(subambito);
		httput.setEntity(entity);
		httput.setHeader("Accept", "application/json");
		httput.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPut(httput);
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				JSONObject responseJson = new JSONObject(response);		 	
				return CommonUtils.jsonToMap(responseJson);
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitosActivos() throws IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + pathsub +"/activos");
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				JSONArray responseJson = new JSONArray(response);		
				return CommonUtils.jsonArrayToMap(responseJson);
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}

	}

}
