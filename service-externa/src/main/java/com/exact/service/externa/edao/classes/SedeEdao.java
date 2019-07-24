package com.exact.service.externa.edao.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.ISedeEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

@Repository
public class SedeEdao implements ISedeEdao{

	@Value("${service.empleados}")
	private String empleadosPath;
	
	@Autowired
	private IRequester requester;
	
	private final String path = "/sedes";
	
	@Override
	public Map<String, Object> findSedeByMatricula(String matricula) throws IOException, JSONException {
		HttpGet httpGet = new HttpGet(empleadosPath + path + "/despacho" +"/" + matricula);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
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
	public Iterable<Map<String, Object>> listarSedesDespacho() throws IOException, JSONException {
		HttpGet httpGet = new HttpGet(empleadosPath + path + "/sedesdespacho");
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
