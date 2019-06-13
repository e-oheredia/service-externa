package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

public interface IReporteIndicadorEficienciaService {

	public Map<Integer,Map<Integer,Float>> graficoTablaPorcentaje(String fechaini,String fechafin)throws IOException, NumberFormatException, ParseException;
	
	public Map<Long, Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>>> proveedorPlazoDentroFuera (String fechaini,String fechafin) throws IOException, NumberFormatException, ParseException;
	
}
