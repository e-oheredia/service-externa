package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface IBuzonEdao {	
	public Map<String, Object> listarById(Long id) throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarByIds(List<Long> buzonIds) throws ClientProtocolException, IOException, JSONException;	
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException;
}
