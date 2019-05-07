package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IAmbitoDiasEdao {
	
	public Iterable<Map<String, Object>> listarAmbitos() throws  Exception;

	
	public Iterable<Map<String, Object>> listardiaslaborales(Long ambitoid)
			throws ClientProtocolException, IOException, JSONException;
	
	public Iterable<Map<String, Object>> listarhoraslaborales(Long ambitoid)
			throws ClientProtocolException, IOException, JSONException;

	public Iterable<Map<String, Object>> listarambitosdiaslabores()
			throws ClientProtocolException, IOException, JSONException;


	public Map<String, Object> modificarAmbito(Long id, String subambito) 
			throws io.jsonwebtoken.io.IOException, JSONException, UnsupportedEncodingException, ClientProtocolException, IOException;
	

	public Map<String, Object> guardarferiado(Long id, String feriado) 
			throws io.jsonwebtoken.io.IOException, JSONException, UnsupportedEncodingException, ClientProtocolException, IOException;	
	
	public Map<String, Object> listardias(Long ambitoid,String fecha1,String fecha2)
			throws ClientProtocolException, IOException, JSONException, URISyntaxException;
}
