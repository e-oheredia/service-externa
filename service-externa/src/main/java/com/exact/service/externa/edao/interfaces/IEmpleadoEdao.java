package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IEmpleadoEdao {
	public Map<String, Object> listarByMatricula(String matricula) throws IOException, JSONException;
	
	public Long findSede(String matricula) throws IOException, JSONException;
	
	Iterable<Map<String, Object>> listarAll() throws IOException, JSONException;
}
