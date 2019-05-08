package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IPeriodoService {

	public Iterable<Map<String, Object>> listar() throws IOException, JSONException;

	
}
