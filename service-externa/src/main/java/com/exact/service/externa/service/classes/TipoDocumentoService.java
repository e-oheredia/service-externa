package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.service.interfaces.ITipoDocumentoService;

@Service
public class TipoDocumentoService implements ITipoDocumentoService {
	
	@Autowired
	ITipoDocumentoEdao tipoDocumentoEdao;
	
	@Override
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException {
		return tipoDocumentoEdao.listarAll();
	}

}
