package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IEmpleadoService {
	public Map<String, Object> listarByMatricula(String matricula) throws ClientProtocolException, IOException, JSONException;
	
	public Long findSede(String matricula) throws ClientProtocolException, IOException, JSONException;
}
