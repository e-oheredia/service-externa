package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IBuzonService {
	public Map<String, Object> listarById(Long id) throws IOException, JSONException;
}
