package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@Override
	public DocumentoGuia desvalidarDocumento(Long documentoId) {
		
		DocumentoGuia documentoGuia = documentoGuiaDao.findByDocumentoId(documentoId);
		if (documentoGuia == null) {
			return null;
		}
		documentoGuia.setValidado(false);
		
		return documentoGuiaDao.save(documentoGuia);
	}


	
	

	
}
