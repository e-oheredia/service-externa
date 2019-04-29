package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IAmbitoDiasEdao;
import com.exact.service.externa.service.interfaces.IAmbitoDiaService;

@Service
public class AmbitoDiaService implements IAmbitoDiaService{

	@Autowired
	IAmbitoDiasEdao ambitodias;
	
	@Override
	public Iterable<Map<String, Object>> listarAmbitos() throws Exception {
		return ambitodias.listarAmbitos();
	}

	@Override
	public Iterable<Map<String, Object>> listardiaslaborales(Long ambitoid) throws IOException, JSONException, Exception {
		return ambitodias.listardiaslaborales(ambitoid);
	}

}
