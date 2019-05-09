package com.exact.service.externa.edao.classes;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
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

import com.exact.service.externa.controller.FeriadoController;
import com.exact.service.externa.edao.interfaces.IFeriadoEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

import io.jsonwebtoken.io.IOException;

@Repository
public class FeriadoEdao implements IFeriadoEdao{
	
	@Value("${service.dias-laborales}")
	private String feriadosPath;
	
	@Autowired
	private IRequester requester;
	
	private final String path = "/feriados";	
	private static final Log Logger = LogFactory.getLog(FeriadoEdao.class);


	@Override
	public Map<String, Object> Eliminar(Long id ) throws IOException, JSONException, Exception {
		HashMap<String,Object> message = new HashMap<>();
		HttpDelete httdel = new HttpDelete(feriadosPath+"/"+path+"/"+ id);
		httdel.setHeader("Accept", "application/json");
		httdel.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.request(httdel);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		 	
		return CommonUtils.jsonToMap(responseJson);
	}

	@Override
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException, java.io.IOException {
		HttpGet httpGet = new HttpGet(feriadosPath + path);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

	@Override
	public Map<String, Object> guardar(String feriado) throws IOException, JSONException, Exception {
		HttpPost httpost = new HttpPost(feriadosPath + path);
		StringEntity entity = new StringEntity(feriado);
		httpost.setEntity(entity);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPost(httpost);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		
		return CommonUtils.jsonToMap(responseJson);
	}
}
