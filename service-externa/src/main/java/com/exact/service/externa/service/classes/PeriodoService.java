package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IPeriodoEdao;
import com.exact.service.externa.service.interfaces.IPeriodoService;

@Service
public class PeriodoService implements IPeriodoService{

	@Autowired
	IPeriodoEdao periodoedao;

	@Override
	public Iterable<Map<String, Object>> listar() throws IOException, JSONException {
		return periodoedao.listarAll();
	}
	
	
	
}
