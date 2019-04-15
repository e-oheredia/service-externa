package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface ITipoDocumentoService {
	
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarActivos() throws IOException, JSONException;
	Map<String, Object> guardar(String tipoDocumento) throws ClientProtocolException, IOException, JSONException;
	Map<String, Object> modificar(Long id, String tipoDocumento) throws ClientProtocolException, IOException, JSONException;
}
