package com.exact.service.externa.edao.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.IEmpleadoEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

@Repository
public class EmpleadoEdao implements IEmpleadoEdao{
	
	@Value("${service.empleados}")
	private String empleadosPath;
	
	@Autowired
	private IRequester requester;
	
	private final String path = "/empleados";	

	@Override
	public Map<String, Object> listarByMatricula(String matricula) throws ClientProtocolException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(empleadosPath + path + "?matricula=" + matricula);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		
		return CommonUtils.jsonToMap(responseJson);
	}

	@Override
	public Long findSede(String matricula) throws ClientProtocolException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(empleadosPath + path + "?matricula=" + matricula);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		
		return Long.valueOf(response);
	}

	@Override
	public Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(empleadosPath + path + "/empleados");
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}
	
	

}
