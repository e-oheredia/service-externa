package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IAmbitoDiasEdao;
import com.exact.service.externa.edao.interfaces.IFeriadoEdao;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.service.interfaces.IFeriadoService;

@Service
public class FeriadoService  implements IFeriadoService{
	
	@Autowired
	IFeriadoEdao feriadoedao;
	
	@Autowired
	IAmbitoDiasEdao ambitoedao;
	
	@Override
	public Map<String, Object> eliminar(Long id) throws io.jsonwebtoken.io.IOException, Exception {
		return feriadoedao.Eliminar(id);
	}
	@Override
	public Map<String, Object> guardarferiado(Long id, String feriado) throws IOException, JSONException {
		return ambitoedao.guardarferiado(id, feriado);
	}
	
	
	@Override
	public Iterable<Map<String, Object>> listarferiados() throws IOException, JSONException {
		return feriadoedao.listarAll();
	}
	@Override
	public Map<String, Object> guardarferiados(String feriado) throws io.jsonwebtoken.io.IOException, Exception {
		return feriadoedao.guardar(feriado) ;
	}

	
}
