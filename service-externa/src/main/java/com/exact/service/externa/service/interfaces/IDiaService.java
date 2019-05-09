package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.json.JSONException;

public interface IDiaService {
	
	
	public Map<String, Object>  listarferiados(Long id,String fecha1,String fecha2) throws IOException, JSONException, URISyntaxException;

	
}
