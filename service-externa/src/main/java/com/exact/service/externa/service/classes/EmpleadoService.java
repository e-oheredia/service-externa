package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IEmpleadoEdao;
import com.exact.service.externa.service.interfaces.IEmpleadoService;

@Service	
public class EmpleadoService implements IEmpleadoService {
	
	@Autowired
	IEmpleadoEdao empleadoEdao;
	
	@Override
	public Map<String, Object> listarByMatricula(String matricula) throws ClientProtocolException, IOException, JSONException {
		
		return empleadoEdao.listarByMatricula(matricula);
	}

	@Override
	public Long findSede(String matricula) throws ClientProtocolException, IOException, JSONException {
		return empleadoEdao.findSede(matricula);
	}

	@Override
	public Iterable<Map<String, Object>> listarEmpleados() throws ClientProtocolException, IOException, JSONException {
		return empleadoEdao.listarAll();
	}

}
