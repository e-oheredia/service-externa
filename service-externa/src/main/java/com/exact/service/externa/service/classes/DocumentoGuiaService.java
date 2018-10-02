package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoGuiaDao;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.service.interfaces.IDocumentoGuiaService;

@Service
public class DocumentoGuiaService implements IDocumentoGuiaService{

	@Autowired
	IDocumentoGuiaDao documentoGuiaDao;
	
	@Override
	public DocumentoGuia validarDocumentoGuia(Long guiaId, Long documentoId, Long usuarioId) {
			
		DocumentoGuia documentoGuia = documentoGuiaDao.findByGuiaIdAndDocumentoId(guiaId, documentoId);
		
		if(documentoGuia==null) {
			
			return null;
			
		}
		documentoGuia.setValidado(true);		
		return documentoGuiaDao.save(documentoGuia);
		
	}

	
}
