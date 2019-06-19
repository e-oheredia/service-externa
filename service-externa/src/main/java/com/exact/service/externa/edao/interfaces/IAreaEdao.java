package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

public interface IAreaEdao {
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarByIds(List<Long> ids) throws IOException, JSONException;
}
