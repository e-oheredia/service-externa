package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IMenuService {
	Iterable<Map<String, Object>> listarMenuByPermisoIds(List<Long> permisoIds) throws ClientProtocolException, IOException, JSONException;
}
