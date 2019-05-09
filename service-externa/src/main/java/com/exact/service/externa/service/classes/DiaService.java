package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IAmbitoDiasEdao;
import com.exact.service.externa.service.interfaces.IDiaService;

@Service
public class DiaService implements IDiaService {

	@Autowired
	IAmbitoDiasEdao ambitodias;

	@Override
	public Map<String, Object>  listarferiados(Long id, String fecha1, String fecha2)
			throws IOException, JSONException, URISyntaxException {
		return ambitodias.listardias(id, fecha1, fecha2) ;
	}
	
	
	
	
}
