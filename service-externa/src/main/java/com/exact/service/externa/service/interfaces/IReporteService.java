package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;

public interface IReporteService {
	
	public Map<String, Float> volumenbycurier() throws IOException, JSONException;

}
