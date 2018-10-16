package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IAreaService {
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException;
}
