package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IDistritoEdao {
	public Iterable<Map<String, Object>> listarDistritosByIdProvincia(Long provinciaId) throws ClientProtocolException, IOException, JSONException;
	Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException;
}
