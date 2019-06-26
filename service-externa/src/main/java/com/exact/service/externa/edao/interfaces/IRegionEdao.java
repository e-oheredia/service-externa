package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IRegionEdao {
	
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

	public Iterable<Map<String, Object>> listarSubAmbitos() throws IOException, JSONException;
	
	public Iterable<Map<String, Object>> listarSubAmbitosActivosByAmbitoId(Long id) throws IOException, JSONException;
	
	public Iterable<Map<String, Object>> listarSubAmbitosActivos() throws IOException, JSONException;
	
	public Map<String, Object> guardarSubAmbito(String ambito) throws ClientProtocolException, IOException, JSONException;
	
	public Map<String, Object> modificarSubAmbito(Long id, String ambito) throws IOException, JSONException;	
	
	public Map<String, Object> listarFechaLimite (Long ambitoId, String fecha, double dia )  throws IOException, JSONException, URISyntaxException;	
	
	public Iterable<Map<String,Object>> listarAmbitosByIds(List<Long> ids) throws java.io.IOException, JSONException;
	
}
