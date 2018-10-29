package com.exact.service.externa.service.classes;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IEstadoDocumentoDao;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.service.interfaces.IEstadoDocumentoService;

@Service
public class EstadoDocumentoService implements IEstadoDocumentoService {

	@Autowired
	IEstadoDocumentoDao estadoDocumentoDao;

	@Override
	public Iterable<EstadoDocumento> listarPorTipoEstadoDocumentoId(Long tipoEstadoDocumentoId) throws ClientProtocolException, IOException, JSONException {
		return estadoDocumentoDao.findByTipoEstadoDocumentoId(tipoEstadoDocumentoId);
	}

	@Override
	public Iterable<EstadoDocumento> listarAll() {
		return estadoDocumentoDao.findAll();
	}
	
	
	
}
