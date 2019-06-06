package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IReporteEficaciaService {
	
	public Map<Integer, Map<Integer, Integer>> eficaciaporproveedor(String fechaini,String fechainifin) throws IOException, JSONException;


}
