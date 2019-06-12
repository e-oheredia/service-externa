package com.exact.service.externa.service.interfaces;

import java.text.ParseException;
import java.util.Map;

import org.json.JSONException;

import io.jsonwebtoken.io.IOException;

public interface ICargosService {
	
	Map<Long, Map<Long, Map<String, Integer>>> devolucionPorTipo(String fechaIni,String fechaFin) throws IOException, JSONException, Exception;
	
	Map<Long,Integer> pendientesCargosPorArea (String fechaIni,String fechaFin) throws IOException, JSONException, Exception;
	
	Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>> controlCargos(String fechaIni,String fechaFin, Long TipoDevolucionId) throws IOException, JSONException, NumberFormatException, ParseException;
	
	Map<Long, Map<Integer, Map<Integer, Integer>>> controlCargosPorAreas(String fechaIni,String fechaFin,Long TipoDevolucionId) throws IOException, JSONException, Exception;
	
	
}
