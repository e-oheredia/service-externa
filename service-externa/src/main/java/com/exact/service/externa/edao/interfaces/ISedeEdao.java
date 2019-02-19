package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface ISedeEdao {
	
	public Map<String, Object> findSedeByMatricula(String matricula) throws IOException, JSONException;
	public Iterable<Map<String, Object>> listarSedesDespacho() throws IOException, JSONException;
	
}
