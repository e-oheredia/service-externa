package com.exact.service.externa.edao.classes;


import java.util.Map;

import org.apache.http.HttpStatus;
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

import com.exact.service.externa.edao.interfaces.IProductoEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

import io.jsonwebtoken.io.IOException;

@Repository
public class ProductoEdao implements IProductoEdao{

	@Value("${service.productos}")
	private String productosPath;
	
	@Autowired
	private IRequester requester;
	
	private final String path = "/productos";
	
	@Override
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException, Exception{
		HttpGet httpGet = new HttpGet(productosPath + path);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
	public Map<String, Object> guardar(String producto) throws IOException, JSONException, Exception {
		HttpPost httpost = new HttpPost(productosPath + path);
		StringEntity entity = new StringEntity(producto);
		httpost.setEntity(entity);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPost(httpost);
		try {
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
	public Map<String, Object> modificar(Long id, String producto) throws IOException, JSONException, Exception{
		HttpPut httput = new HttpPut(productosPath + path+ "/"+ id);
		StringEntity entity = new StringEntity(producto);
		httput.setEntity(entity);
		httput.setHeader("Accept", "application/json");
		httput.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPut(httput);
		try {
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
	public Iterable<Map<String, Object>> listarActivos() throws IOException, JSONException, Exception {
		HttpGet httpGet = new HttpGet(productosPath + path +"/activos");
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
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
