package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IReporteVolumenService {
	
	public Map<Long,Map<String, Float>> volumenbycurier(String fechaini,String fechainifin) throws IOException, JSONException;
	
	
	public Map<Integer, Map<String, Float>> volumenbyutd(String fechaini,String fechainifin) throws IOException, JSONException;
	
	public Map<Integer, Map<Integer, Integer>> volumenbyplazo(String fechaini,String fechainifin) throws IOException, JSONException;
	
	

}
