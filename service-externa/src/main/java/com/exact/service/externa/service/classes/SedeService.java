package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.ISedeEdao;
import com.exact.service.externa.service.interfaces.ISedeService;

@Service
public class SedeService implements ISedeService{

	@Autowired
	ISedeEdao sedeEdao;
	
	@Override
	public Map<String, Object> findSedeByMatricula(String matricula) throws IOException, JSONException {
		return sedeEdao.findSedeByMatricula(matricula);
	}

	@Override
	public Iterable<Map<String, Object>> listarSedesDespacho() throws IOException, JSONException {
		return sedeEdao.listarSedesDespacho();
	}

}
