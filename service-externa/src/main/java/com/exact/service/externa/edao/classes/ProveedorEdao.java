package com.exact.service.externa.edao.classes;

import java.io.IOException;
import java.util.Map;

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

import com.exact.service.externa.edao.interfaces.IProveedorEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

@Repository
public class ProveedorEdao implements IProveedorEdao{
	
	@Value("${service.proveedor}")
	private String proveedorPath;
	
	@Autowired
	private IRequester requester;
	
	private final String path = "/proveedores";

	@Override
	public Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(proveedorPath + path);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

	@Override
	public Map<String, Object> guardar(String proveedor) throws ClientProtocolException, IOException, JSONException {
		HttpPost httpost = new HttpPost(proveedorPath + path);
		StringEntity entity = new StringEntity(proveedor);
		httpost.setEntity(entity);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPost(httpost);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		
		return CommonUtils.jsonToMap(responseJson);
	}

	@Override
	public Map<String, Object> modificar(Long id, String proveedor)throws ClientProtocolException, IOException, JSONException {
		HttpPut httput = new HttpPut(proveedorPath + path+ "/"+ id);
		StringEntity entity = new StringEntity(proveedor);
		httput.setEntity(entity);
		httput.setHeader("Accept", "application/json");
		httput.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPut(httput);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		
		return CommonUtils.jsonToMap(responseJson);
	}

}
