package com.exact.service.externa.edao.classes;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

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
	
	private final String path2 = "/subambitos";
	
	private final String subpath = "/diaslaborales";
	
	private final String subpath2 = "/horaslaborales";
	
	private final String subpath3 = "/feriados";
	
	private final String subpath4 = "/diaslaboralesporrango";



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

	@Override
	public Iterable<Map<String, Object>> listarhoraslaborales(Long ambitoid)
			throws ClientProtocolException, java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + path + "/" + ambitoid.toString() + subpath2);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

	@Override
	public Iterable<Map<String, Object>> listarambitosdiaslabores()
			throws ClientProtocolException, java.io.IOException, JSONException {
		return null;
	}	
	
	@Override
	public Map<String, Object> modificarAmbito(Long id, String ambito) throws IOException, JSONException, ClientProtocolException, java.io.IOException {
		HttpPut httput = new HttpPut(ambitosPath+"/"+path+"/"+ id);
		StringEntity entity = new StringEntity(ambito);
		httput.setEntity(entity);
		httput.setHeader("Accept", "application/json");
		httput.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPut(httput);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		 	
		return CommonUtils.jsonToMap(responseJson);
	}

	@Override
	public Map<String, Object> guardarferiado(Long id,String feriado) throws IOException, JSONException, java.io.IOException {
		HttpPost httpost = new HttpPost(ambitosPath +path+"/" +id+subpath3);
		StringEntity entity = new StringEntity(feriado);
		httpost.setEntity(entity);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPost(httpost);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		
		return CommonUtils.jsonToMap(responseJson);
	}

	@Override
	public Map<String, Object> listardias(Long ambitoid, String fecha1, String fecha2)
			throws ClientProtocolException, java.io.IOException, JSONException, URISyntaxException {
		URI uri = new URIBuilder(ambitosPath+"/"+path+"/"+ ambitoid+subpath4) 
			    .addParameter("fecha1", fecha1) 
			    .addParameter("fecha2", fecha2) 
			    .build();
		HttpGet httpGet = new HttpGet(uri);		
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		
		return CommonUtils.jsonToMap(responseJson);
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitos() throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + path2);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitosActivosByAmbitoId(Long id)
			throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + path +"/"+ id +path2);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

	@Override
	public Iterable<Map<String, Object>> listarSubAmbitosActivos() throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + path2 +"/activos");
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

	@Override
	public Map<String, Object> guardarSubAmbito(String subambito)
			throws ClientProtocolException, java.io.IOException, JSONException {
		HttpPost httpost = new HttpPost(ambitosPath + path2);
		StringEntity entity = new StringEntity(subambito);
		httpost.setEntity(entity);
		httpost.setHeader("Accept", "application/json");
		httpost.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPost(httpost);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		
		return CommonUtils.jsonToMap(responseJson);
	}

	@Override
	public Map<String, Object> modificarSubAmbito(Long id, String subambito) throws java.io.IOException, JSONException {
		HttpPut httput = new HttpPut(ambitosPath + path2+ "/"+ id);
		StringEntity entity = new StringEntity(subambito);
		httput.setEntity(entity);
		httput.setHeader("Accept", "application/json");
		httput.setHeader("Content-type", "application/json");
		CloseableHttpResponse httpResponse = requester.requestPut(httput);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONObject responseJson = new JSONObject(response);		 	
		return CommonUtils.jsonToMap(responseJson);
	}
	
	
	
	
	
	
	
	
	
}
