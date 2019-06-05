package com.exact.service.externa.service.interfaces;

import java.util.Map;

import org.json.JSONException;

import io.jsonwebtoken.io.IOException;

public interface IReporteEficienciaService {

	Map<Long, Map<String, Integer>> eficienciaPorCourier(String fechaIni,String fechaFin) throws IOException, JSONException;
	
	Map<Long, Map<String, Map<Long, Integer>>> eficienciaPorPlazoPorCourier(String fechaIni,String fechaFin) throws IOException, JSONException;
	
}
