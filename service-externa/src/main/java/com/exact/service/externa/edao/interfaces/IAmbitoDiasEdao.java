package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IAmbitoDiasEdao {
	
	public Iterable<Map<String, Object>> listarAmbitos() throws  Exception;

	
	public Iterable<Map<String, Object>> listardiaslaborales(Long ambitoid)
			throws ClientProtocolException, IOException, JSONException;
	
}
