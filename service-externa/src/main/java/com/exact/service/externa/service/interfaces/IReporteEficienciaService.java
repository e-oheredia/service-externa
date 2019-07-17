package com.exact.service.externa.service.interfaces;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import io.jsonwebtoken.io.IOException;

public interface IReporteEficienciaService {

	Map<Long, Map<String, Integer>> eficienciaPorCourier(String fechaIni,String fechaFin) throws IOException, JSONException;
	
	Map<Long, Object> eficienciaPorPlazoPorCourier(String fechaIni,String fechaFin) throws IOException, JSONException, java.io.IOException;
	
	Map<Long, Map<String, Integer>> detalleEficienciaPorCourier(String fechaIni,String fechaFin,Long proveedorId) throws IOException, JSONException, ClientProtocolException, java.io.IOException, URISyntaxException, ParseException ;
	
	Map<Integer, Object> detalleeficienciaporCourier(String fechaIni,String fechaFin) throws IOException, JSONException, ClientProtocolException, java.io.IOException, URISyntaxException, ParseException;

}
