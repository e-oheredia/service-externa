package com.exact.service.externa.edao.classes;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
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

import com.exact.service.externa.edao.interfaces.IRegionEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

import io.jsonwebtoken.io.IOException;

@Repository
public class RegionEdao implements IRegionEdao {

	@Value("${service.dias-laborales}")
	private String regionesPath;
	
	
	@Autowired
	private IRequester requester;
	

	private final String path = "/regiones";
	
	private final String path2 = "/ambitos";
	
	private final String subpath = "/diaslaborales";
	
	private final String subpath2 = "/horaslaborales";
	
	private final String subpath3 = "/feriados";
	
	private final String subpath4 = "/diaslaboralesporrango";



	@Override
	public Iterable<Map<String, Object>> listarAmbitos() throws Exception{
		HttpGet httpGet = new HttpGet(regionesPath + path);
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
	public Iterable<Map<String, Object>> listardiaslaborales(Long ambitoid)
			throws ClientProtocolException, java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path + "/" + ambitoid.toString() + subpath);
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
	public Iterable<Map<String, Object>> listarhoraslaborales(Long ambitoid)
			throws ClientProtocolException, java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path + "/" + ambitoid.toString() + subpath2);
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
	public Iterable<Map<String, Object>> listarambitosdiaslabores()
			throws ClientProtocolException, java.io.IOException, JSONException {
		return null;
	}	
	
	@Override
	public Map<String, Object> modificarAmbito(Long id, String ambito) throws IOException, JSONException, ClientProtocolException, java.io.IOException {
		HttpPut httput = new HttpPut(regionesPath+"/"+path+"/"+ id);
		StringEntity entity = new StringEntity(ambito);
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
	public Map<String, Object> guardarferiado(Long id,String feriado) throws IOException, JSONException, java.io.IOException {
		HttpPost httpost = new HttpPost(regionesPath +path+"/" +id+subpath3);
		StringEntity entity = new StringEntity(feriado);
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
	public Map<String, Object> listardias(Long ambitoid, String fecha1, String fecha2)
			throws ClientProtocolException, java.io.IOException, JSONException, URISyntaxException {
		URI uri = new URIBuilder(regionesPath+"/"+path+"/"+ ambitoid+subpath4) 
			    .addParameter("fecha1", fecha1) 
			    .addParameter("fecha2", fecha2) 
			    .build();
		HttpGet httpGet = new HttpGet(uri);		
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
	public Iterable<Map<String, Object>> listarSubAmbitos() throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path2);
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
	public Iterable<Map<String, Object>> listarSubAmbitosActivosByAmbitoId(Long id)
			throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path +"/"+ id +path2);
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
	public Iterable<Map<String, Object>> listarSubAmbitosActivos() throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path2 +"/activos");
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
	public Map<String, Object> guardarSubAmbito(String subambito)
			throws ClientProtocolException, java.io.IOException, JSONException {
		HttpPost httpost = new HttpPost(regionesPath + path2);
		StringEntity entity = new StringEntity(subambito);
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
	public Map<String, Object> modificarSubAmbito(Long id, String subambito) throws java.io.IOException, JSONException {
		HttpPut httput = new HttpPut(regionesPath + path2+ "/"+ id);
		StringEntity entity = new StringEntity(subambito);
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
	public Map<String, Object> listarFechaLimite(Long id, String fecha, double hora, int tipo) throws IOException, JSONException, URISyntaxException, ClientProtocolException, java.io.IOException {
		String horas = String.valueOf(hora);
		String tipoplazo = Integer.toString(tipo);
		URI uri = new URIBuilder(regionesPath + path +"/"+ id +"/fechalimite") 
			    .addParameter("fecha", fecha) 
			    .addParameter("hora", horas)
			    .addParameter("tipo", tipoplazo)
			    .build();
		HttpGet httpGet = new HttpGet(uri);
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
	public Iterable<Map<String, Object>> listarAmbitosByIds(List<Long> ids) throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path2 + "?ids=" + String.join(",", ids.stream().map(id -> id.toString()).collect(Collectors.toList())));
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
	public Iterable<Map<String, Object>> listarAmbitosByRegion(Long id) throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path2 + "/" + id + "/region" );
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
	public String listarAmbitosByNombre(List<String> nombres) throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path2 + "?nombres=" + String.join(",", nombres.stream().map(id -> id.toString()).collect(Collectors.toList())));
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				return response;
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
	}
	
	@Override
	public Map<String,Object> listarRegionByDistrito(Long id) throws java.io.IOException, JSONException {
		HttpGet httpGet = new HttpGet(regionesPath + path + "/ambito" + "/" + id);
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
	
	
	
}
