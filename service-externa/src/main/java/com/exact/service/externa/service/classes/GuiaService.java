package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.PENDIENTE_ENTREGA;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_CREADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_ENVIADO;

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

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IDocumentoGuiaDao;
import com.exact.service.externa.dao.IGuiaDao;
import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.EstadoGuia;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.Proveedor;
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
	
	@Autowired
	IDocumentoDao documentoDao;
	
	@Autowired
	ITipoDocumentoEdao tipoDocumentoEdao;
	
	@Autowired
	IDistritoEdao distritoEdao;
	
	
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
	public int quitarDocumentosGuia(Long guiaId) throws ClientProtocolException, IOException, JSONException {

		Guia guia = guiaDao.findById(guiaId).orElse(null);
		
		if (guia ==null) {
			return 0;
		}
		
		Iterable<DocumentoGuia> noValidadosList = documentoGuiaDao.listarNoValidados(guiaId);
		
		List<DocumentoGuia> dgvalid = StreamSupport.stream(noValidadosList.spliterator(), false).collect(Collectors.toList());
		
		if(dgvalid.size()>0) {
			
			documentoGuiaDao.deleteAll(noValidadosList);
			
			if (guia.getDocumentosGuia().size() == 0) {
				guiaDao.delete(guia);
				return 1;			
			}
			else {
				 return 2;
			}
		}
		else {
			return 3;
		}
	}

	
	@Override
	@Transactional
	public int enviarGuia(Long guiaId, Long usuarioId) throws ClientProtocolException, IOException, JSONException {
		
		Guia guiaEnviada = guiaDao.findById(guiaId).orElse(null);
		
		if(guiaEnviada==null) {
			return 0;
		}
		
		Iterable<DocumentoGuia> noValidadosList = documentoGuiaDao.listarNoValidados(guiaEnviada.getId());
		List<DocumentoGuia> dgvalid = StreamSupport.stream(noValidadosList.spliterator(), false).collect(Collectors.toList());
		
		if(dgvalid.size()>0) {
			return 2;
		}
		
		if(guiaEnviada.getUltimoSeguimientoGuia().getId()!= GUIA_CREADO) {
			return 3;
		}
		
		
		List<SeguimientoGuia> seguimientoGuiaList = new ArrayList<SeguimientoGuia>();
		SeguimientoGuia seguimientoGuia = new SeguimientoGuia();		
		EstadoGuia estadoGuia = new EstadoGuia();		
		
		estadoGuia.setId(GUIA_ENVIADO);
		seguimientoGuia.setGuia(guiaEnviada);
		seguimientoGuia.setEstadoGuia(estadoGuia);		
		seguimientoGuia.setUsuarioId(usuarioId);
		seguimientoGuiaList.add(seguimientoGuia);
		
		Set<SeguimientoGuia> sg = new HashSet<SeguimientoGuia>(seguimientoGuiaList);
		guiaEnviada.setSeguimientosGuia(sg);
		
		Set<DocumentoGuia> lista = guiaEnviada.getDocumentosGuia();
		List<DocumentoGuia> listDG = new ArrayList<>(lista);
		for (DocumentoGuia dg : listDG) {
			List<SeguimientoDocumento> seguimientosDocumento = new ArrayList<SeguimientoDocumento>();
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(usuarioId, new EstadoDocumento(PENDIENTE_ENTREGA));
			Documento doc = dg.getDocumento();
			seguimientoDocumento.setDocumento(doc);
			seguimientosDocumento.add(seguimientoDocumento);
			Set<SeguimientoDocumento> sd = new HashSet<>(seguimientosDocumento);
			dg.getDocumento().setSeguimientosDocumento(sd);
		}
		
		 guiaDao.save(guiaEnviada);
		 
		 return 1;
		
		
	}

	@Override
	@Transactional
	public int modificarGuia(Guia guia) throws ClientProtocolException, IOException, JSONException {

		Guia guiaSeleccionada = guiaDao.findById(guia.getId()).orElse(null);
		
		if (guiaSeleccionada == null) {
			return 0;
		}				
		
		List<SeguimientoGuia> sgList = StreamSupport.stream(guiaSeleccionada.getSeguimientosGuia().spliterator(), false).collect(Collectors.toList());			
		
		boolean esCreado = true;		
		for (SeguimientoGuia sg : sgList) {
			if (sg.getEstadoGuia().getId() != GUIA_CREADO) {
				esCreado = false;
				break;
			}
		}
		
		if (esCreado == false) {
			return 2;
		}			
		
		guiaSeleccionada.setProveedor(guia.getProveedor());
		guiaSeleccionada.setNumeroGuia(guia.getNumeroGuia());
		
		guiaDao.save(guiaSeleccionada);
		
		return 1;
	
	}

	@Override
	public int eliminarGuia(Long guiaId) throws ClientProtocolException, IOException, JSONException {

		Guia guiaSeleccionada = guiaDao.findById(guiaId).orElse(null);
		
		if (guiaSeleccionada == null) {
			return 0;
		}
		
		List<SeguimientoGuia> sgList = StreamSupport.stream(guiaSeleccionada.getSeguimientosGuia().spliterator(), false).collect(Collectors.toList());			
		
		boolean esCreado = true;		
		for (SeguimientoGuia sg : sgList) {
			if (sg.getEstadoGuia().getId() != GUIA_CREADO) {
				esCreado = false;
				break;
			}
		}
		
		if (esCreado == false) {
			return 2;
		}	
		
		guiaDao.delete(guiaSeleccionada);
		
		return 1;
	}

	@Override
	public Iterable<Guia> listarGuiasParaProveedor() throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Guia> guiasParaProveedor = guiaDao.findByGuiasParaProveedor();
		List<Guia> guiasParaProveedorList = StreamSupport.stream(guiasParaProveedor.spliterator(), false).collect(Collectors.toList());	
		
		List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		
		for(Guia guia : guiasParaProveedorList) {
			List<DocumentoGuia> documentoGuiaList = StreamSupport.stream(guia.getDocumentosGuia().spliterator(), false).collect(Collectors.toList());	
			
			for(DocumentoGuia documentoGuia : documentoGuiaList) {
				
				int i = 0;
				while(i < distritos.size()) {
					if (documentoGuia.getDocumento().getDistritoId() == Long.valueOf(distritos.get(i).get("id").toString())) {
						documentoGuia.getDocumento().setDistrito(distritos.get(i));
						break;
					}
					i++;
				}				
				
				int j = 0;
				while(j < tiposDocumento.size()) {
					if (documentoGuia.getDocumento().getEnvio().getTipoDocumentoId() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						documentoGuia.getDocumento().getEnvio().setTipoDocumento(tiposDocumento.get(j));
						break;
					}
					j++;
				}
				
			}			
			
		}
		
		return guiasParaProveedorList;	
	}

	@Override
	public Iterable<Guia> listarGuiasSinCerrar() throws ClientProtocolException, IOException, JSONException {
		Iterable<Guia> guiasSinCerrar = guiaDao.findByGuiasSinCerrar();
		List<Guia> guiasSinCerrarList = StreamSupport.stream(guiasSinCerrar.spliterator(), false).collect(Collectors.toList());	
		
		List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		
		for(Guia guia : guiasSinCerrarList) {
			List<DocumentoGuia> documentoGuiaList = StreamSupport.stream(guia.getDocumentosGuia().spliterator(), false).collect(Collectors.toList());	
			
			for(DocumentoGuia documentoGuia : documentoGuiaList) {
				
				int i = 0;
				while(i < tiposDocumento.size()) {
					if (documentoGuia.getDocumento().getDistritoId() == Long.valueOf(distritos.get(i).get("id").toString())) {
						documentoGuia.getDocumento().setDistrito(distritos.get(i));
						break;
					}
					i++;
				}				
				
				int j = 0;
				while(j < tiposDocumento.size()) {
					if (documentoGuia.getDocumento().getEnvio().getTipoDocumentoId() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						documentoGuia.getDocumento().getEnvio().setTipoDocumento(tiposDocumento.get(j));
						break;
					}
					j++;
				}
				
			}			
			
		}
		
		return guiasSinCerrarList;	
	}
	
}
