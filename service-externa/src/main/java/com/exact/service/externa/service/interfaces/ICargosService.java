package com.exact.service.externa.service.interfaces;

import java.util.Map;

import org.json.JSONException;

import io.jsonwebtoken.io.IOException;

public interface ICargosService {
	
	Map<Long, Map<Long, Map<Long, Integer>>> devolucionPorTipo(String fechaIni,String fechaFin) throws IOException, JSONException;

}
