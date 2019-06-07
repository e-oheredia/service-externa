package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

public interface IReporteIndicadorVolumenService {
	
	public Map<Integer, Map<Integer, Integer>> IndicadorVolumenGrafico(String fechaini,String fechainifin) throws IOException, JSONException, NumberFormatException, ParseException;


}
