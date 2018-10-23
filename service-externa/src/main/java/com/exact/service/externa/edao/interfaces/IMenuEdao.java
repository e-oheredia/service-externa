package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IMenuEdao {
	Iterable<Map<String, Object>> listarMenuByPermisoIds(List<Long> permisoIds) throws ClientProtocolException, IOException, JSONException;
}
