package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.json.JSONException;

public interface IReporteIndicadorEficacia {

	public Map<Integer, Map<Integer, Float>> indicadorgrafico(String fechaini,String fechainifin) throws IOException, JSONException, NumberFormatException, ParseException;

	
	public Map<Integer, Map<Integer, Map<Integer, Integer>>> indicadortabla2(String fechaini,String fechainifin) throws IOException, JSONException, NumberFormatException, ParseException;
	
	
	public Map<Integer, Map<Integer, Float>> indicadortabla2cabecera(String fechaini,String fechainifin) throws IOException, JSONException, NumberFormatException, ParseException;
	
	
}
