package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_CREADO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.dao.IDocumentoGuiaDao;
import com.exact.service.externa.dao.IGuiaDao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.EstadoGuia;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.SeguimientoGuia;
import com.exact.service.externa.entity.id.DocumentoGuiaId;
import com.exact.service.externa.service.interfaces.IDocumentoGuiaService;
import com.exact.service.externa.service.interfaces.IDocumentoService;
import com.exact.service.externa.service.interfaces.IGuiaService;

@Service
public class GuiaService implements IGuiaService{

	@Autowired
	IGuiaDao guiaDao;

	@Autowired
	IDocumentoGuiaDao documentoGuiaDao;
	
	@Autowired
	IDocumentoService documentoService;
	
	@Autowired
	IDocumentoGuiaService documentoGuiaService;
	
	@Override
	public Iterable<Guia> listarGuiasCreadas() throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Guia> guiasCreadas = guiaDao.findByUltimoEstadoId(CREADO);
		List<Guia> guiasCreadasList = StreamSupport.stream(guiasCreadas.spliterator(), false).collect(Collectors.toList());	
		
		return guiasCreadasList;		
	}

	@Override
	@Transactional
	public Guia crearGuia(Guia guia, Long usuarioId) throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Documento> documentos = documentoService.listarDocumentosGuiaPorCrear(guia);
		
		if (documentos == null) {
			return null;		
		}		
		
		List<Documento> documentosList = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
			
		if (documentosList.size() == 0) {
			return null;
		}
		
		List<DocumentoGuia> documentosGuiaList = new ArrayList<DocumentoGuia>();
		
		for (Documento documento : documentosList) {			
			
			DocumentoGuiaId documentoGuiaId = new DocumentoGuiaId();
			documentoGuiaId.setGuiaId(guia.getId());
			documentoGuiaId.setDocumentoId(documento.getId());
			
			DocumentoGuia documentoGuia = new DocumentoGuia();
			documentoGuia.setDocumento(documento);
			documentoGuia.setGuia(guia);
			documentoGuia.setValidado(false);
			documentoGuia.setId(documentoGuiaId);
			documentosGuiaList.add(documentoGuia);
		}
		
		Set<DocumentoGuia> dg = new HashSet<DocumentoGuia>(documentosGuiaList);
		guia.setDocumentosGuia(dg);
		
		
		List<SeguimientoGuia> seguimientoGuiaList = new ArrayList<SeguimientoGuia>();
		SeguimientoGuia seguimientoGuia = new SeguimientoGuia();		
		EstadoGuia estadoGuia = new EstadoGuia();		
		
		estadoGuia.setId(GUIA_CREADO);
		
		seguimientoGuia.setGuia(guia);
		seguimientoGuia.setEstadoGuia(estadoGuia);		
		seguimientoGuia.setUsuarioId(usuarioId);
				
		seguimientoGuiaList.add(seguimientoGuia);
		
		Set<SeguimientoGuia> sg = new HashSet<SeguimientoGuia>(seguimientoGuiaList);
		guia.setSeguimientosGuia(sg);
		
		guiaDao.save(guia);
		
		return guia;
	}

	@Override
	public Guia quitarDocumentosGuia(Long guiaId) throws ClientProtocolException, IOException, JSONException {

		Guia guia = guiaDao.findById(guiaId).orElse(null);
		
		if (guia ==null) {
			return null;
		}
		
		Iterable<DocumentoGuia> noValidadosList = documentoGuiaDao.listarNoValidados(guiaId);
		
		documentoGuiaDao.deleteAll(noValidadosList);
				
		if (guia.getDocumentosGuia().size() == 0) {
			guiaDao.delete(guia);
			return null;			
		}
		else {
			 return guia;
		}
		
		
	}
		

}
