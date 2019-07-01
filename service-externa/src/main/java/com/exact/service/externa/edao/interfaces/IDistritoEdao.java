package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IDistritoEdao {
	Iterable<Map<String, Object>> listarDistritosByIdProvincia(Long provinciaId) throws ClientProtocolException, IOException, JSONException;
	Iterable<Map<String, Object>> listarAll() throws ClientProtocolException, IOException, JSONException;
	Iterable<Map<String, Object>> listarByIds(List<Long> ids) throws ClientProtocolException, IOException, JSONException;
	Iterable<Map<String, Object>> listarDistritoIdsByUbigeos(List<String> ubigeos) throws ClientProtocolException, IOException, JSONException;
}
