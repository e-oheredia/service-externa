package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.PENDIENTE_ENTREGA;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CERRADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_CERRADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_CREADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_ENVIADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_DESCARGADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_CERRADO;
import static com.exact.service.externa.enumerator.EstadoTipoGuia.GUIA_BLOQUE;
import static com.exact.service.externa.enumerator.EstadoTipoGuia.GUIA_REGULAR;
import static com.exact.service.externa.enumerator.AmbitoEnum.LIMA;



import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IDocumentoGuiaDao;
import com.exact.service.externa.dao.IEstadoDocumentoDao;
import com.exact.service.externa.dao.IGuiaDao;
import com.exact.service.externa.dao.ISeguimientoDocumentoDao;
import com.exact.service.externa.dao.ISubambitoPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IAmbitoDiasEdao;
import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.edao.interfaces.IGestionUsuariosEdao;
import com.exact.service.externa.edao.interfaces.IProductoEdao;
import com.exact.service.externa.edao.interfaces.ISedeEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.SubambitoPlazoDistribucion;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.EstadoGuia;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.SeguimientoGuia;
import com.exact.service.externa.entity.TipoGuia;
import com.exact.service.externa.entity.id.DocumentoGuiaId;
import com.exact.service.externa.service.interfaces.IDocumentoGuiaService;
import com.exact.service.externa.service.interfaces.IDocumentoService;
import com.exact.service.externa.service.interfaces.IGuiaService;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.REZAGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.NO_DISTRIBUIBLE;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.PENDIENTE_ENTREGA;


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
	
	@Autowired
	ISedeEdao sedeEdao;
	
	@Autowired
	IProductoEdao productoEdao;
	
	@Autowired
	IGestionUsuariosEdao gestionUsuarioEdao;
	
	@Autowired
	IEstadoDocumentoDao estadodocumentodao;
	
	@Autowired
	ISeguimientoDocumentoDao seguimientoDocumentodao;
	
	@Autowired
	ISubambitoPlazoDistribucionDao subambitoplazodao;

	@Autowired
	IAmbitoDiasEdao ambitodiasdao;
	
	private static final Log Logger = LogFactory.getLog(GuiaService.class);

	
	@Override
	public Iterable<Guia> listarGuiasCreadas(String matricula) throws ClientProtocolException, IOException, JSONException , Exception{
		
		Map<String, Object> sede = sedeEdao.findSedeByMatricula(matricula);
		
		Iterable<Guia> guiasCreadas = guiaDao.findByUltimoEstadoId(CREADO, Long.valueOf(sede.get("id").toString()));
		List<Guia> guiasCreadasList = StreamSupport.stream(guiasCreadas.spliterator(), false).collect(Collectors.toList());	
		
		
		List<Map<String, Object>> tipodocumento =(List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		
		
		for(Guia guia : guiasCreadasList) {
			int cont=0;
			int k = 0;
			int j =0;
			while (k < productos.size()) {
				if (guia.getProductoId().longValue() == Long.valueOf(productos.get(k).get("id").toString())) {
					guia.setProducto(productos.get(k));
					break;
				}
				k++;
			}

			while (j < tipodocumento.size()) {
				if (guia.getTipoClasificacionId().longValue() == Long.valueOf(tipodocumento.get(j).get("id").toString())) {
					guia.setClasificacion(tipodocumento.get(j));
					break;
				}
				j++;
			}
			guia.setSede(sede);
			
			
			List<DocumentoGuia> documentoGuiaList = StreamSupport.stream(guia.getDocumentosGuia().spliterator(), false).collect(Collectors.toList());	
			
			int validados=0;
			for(DocumentoGuia documentoGuia : documentoGuiaList) {
				if(documentoGuia.isValidado()) {
					validados++;
				}
				cont++;
			}
			guia.setCantidadDocumentos(cont);
			guia.setCantidadValidados(validados);
			
		}
		
		return guiasCreadasList;		
	}

	@Override
	@Transactional
	public Guia crearGuiaRegular(Guia guia, Long usuarioId, String matricula) throws ClientProtocolException, IOException, JSONException {
		
		Map<String, Object> sede = sedeEdao.findSedeByMatricula(matricula);
		guia.setSede(sede);
		TipoGuia tipoGuia = new TipoGuia();
		tipoGuia.setId(GUIA_REGULAR);
		guia.setTipoGuia(tipoGuia);
		Iterable<Documento> documentos = documentoService.listarDocumentosGuiaPorCrear(guia, matricula);
		List<Documento> documentosList = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(documentosList.isEmpty()) {
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
	public int enviarGuiaRegular(Long guiaId, Long usuarioId) throws ClientProtocolException, IOException, JSONException {
		
		Guia guiaEnviada = guiaDao.findById(guiaId).orElse(null);
		
		if(guiaEnviada==null) {
			return 0;
		}
		
		Iterable<DocumentoGuia> noValidadosList = documentoGuiaDao.listarNoValidados(guiaEnviada.getId());
		List<DocumentoGuia> dgvalid = StreamSupport.stream(noValidadosList.spliterator(), false).collect(Collectors.toList());
		
		if(dgvalid.size()>0) {
			return 2;
		}
		
		if(guiaEnviada.getUltimoSeguimientoGuia().getEstadoGuia().getId()!= GUIA_CREADO) {
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
		guiaEnviada.setAmbitoId(LIMA);
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

		Optional<Guia> guiaBD = guiaDao.findById(guia.getId());
		if(!guiaBD.isPresent()) {
			return 0;
		}
		Guia guiaSeleccionada = guiaBD.get();
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
		
		if(guiaSeleccionada.getTipoGuia().getId().longValue()==GUIA_REGULAR) {
			guiaSeleccionada.setProveedor(guia.getProveedor());
		}
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
	public Iterable<Guia> listarGuiasParaProveedor() throws ClientProtocolException, IOException, JSONException, Exception {
		
		Iterable<Guia> guiasParaProveedor = guiaDao.findByGuiasSinCerrar();
		List<Guia> guiasParaProveedorList = StreamSupport.stream(guiasParaProveedor.spliterator(), false).collect(Collectors.toList());	
		List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		List<Map<String, Object>> sedes = (List<Map<String, Object>>) sedeEdao.listarSedesDespacho();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		for(Guia guia : guiasParaProveedorList) {
			Date fechaLimite= null;
			int entregados =0;
			int rezagados =0;
			int nodistri =0;
			int pendientes =0;
			int cont=0;
			int validados=0;
			
			List<DocumentoGuia> documentoGuiaList = StreamSupport.stream(guia.getDocumentosGuia().spliterator(), false).collect(Collectors.toList());	
			
			for(DocumentoGuia documentoGuia : documentoGuiaList) {
				Documento documento = documentoGuia.getDocumento();
				EstadoDocumento estadodocumento = estadodocumentodao.buscarpordocumento(documento.getId());
				Logger.info("ESTADO DOCUMENTOS : "+ estadodocumento.getNombre());
				
				if(documentoGuia.isValidado()) {
					validados++;
				}
				
				if(estadodocumento.getId()==ENTREGADO) {
					entregados++;
				}
				
				if(estadodocumento.getId()==REZAGADO) {
					rezagados++;
				}
				if(estadodocumento.getId()==NO_DISTRIBUIBLE) {
					nodistri++;
				}
				if(estadodocumento.getId()==PENDIENTE_ENTREGA) {
					pendientes++;
				}
				
				
				int i = 0;
				while(i < distritos.size()) {
					if (documentoGuia.getDocumento().getDistritoId().longValue() == Long.valueOf(distritos.get(i).get("id").toString())) {
						documentoGuia.getDocumento().setDistrito(distritos.get(i));
						//documentoGuia.getDocumento().						
						break;
					}
					i++;
				}				
				
				int j = 0;
				while(j < tiposDocumento.size()) {
					if (documentoGuia.getDocumento().getEnvio().getTipoClasificacionId().longValue() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						documentoGuia.getDocumento().getEnvio().setClasificacion(tiposDocumento.get(j));
						break;
					}
					j++;
				}
				
				
				
				
				int k=0;
				while(k < sedes.size()) {
					if(documentoGuia.getDocumento().getEnvio().getSedeId().longValue() == Long.valueOf(sedes.get(k).get("id").toString())) {
						documentoGuia.getGuia().setSede(sedes.get(k));
						break;
					}
					k++;
				}
				
				int m = 0; 
				while(m < productos.size()) {
					if (documentoGuia.getDocumento().getEnvio().getProductoId().longValue() == Long.valueOf(productos.get(m).get("id").toString())) {
						documentoGuia.getDocumento().getEnvio().setProducto(productos.get(m));
						break;
					}
					m++;
				}
				cont++;
			}
			
			
			int x = 0;
			int y =0;
			while (x < productos.size()) {
				if (guia.getProductoId().longValue() == Long.valueOf(productos.get(x).get("id").toString())) {
					guia.setProducto(productos.get(x));
					break;
				}
				x++;
			}

			while (y < tiposDocumento.size()) {
				if (guia.getTipoClasificacionId().longValue() == Long.valueOf(tiposDocumento.get(y).get("id").toString())) {
					guia.setClasificacion(tiposDocumento.get(y));
					break;
				}
				y++;
			}
			
			
			guia.setCantidadEntregados(entregados);
			guia.setCantidadNoDistribuibles(nodistri);
			guia.setCantidadPendientes(pendientes);
			guia.setCantidadRezagados(rezagados);
			guia.setCantidadDocumentos(cont);
			guia.setCantidadValidados(validados);
			if(guia.getAmbitoId()!=null) {
				fechaLimite=getFechaLimite(guia);
				guia.setFechaLimite(fechaLimite);
			}
		}

		return guiasParaProveedorList;	
	}

	@Override
	public Iterable<Guia> listarGuiasSinCerrar() throws ClientProtocolException, IOException, JSONException, Exception {
		Iterable<Guia> guiasSinCerrar = guiaDao.findByGuiasSinCerrar();
		List<Guia> guiasSinCerrarList = StreamSupport.stream(guiasSinCerrar.spliterator(), false).collect(Collectors.toList());	
		
		List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		List<Map<String, Object>> tipodocumento =(List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		
		
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
					if (documentoGuia.getDocumento().getEnvio().getTipoClasificacionId() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						documentoGuia.getDocumento().getEnvio().setClasificacion(tiposDocumento.get(j));
						break;
					}
					j++;
				}
			}		
			
			
			
			int k = 0;
			int n =0;
			while (k < productos.size()) {
				if (guia.getProductoId().longValue() == Long.valueOf(productos.get(k).get("id").toString())) {
					guia.setProducto(productos.get(k));
					break;
				}
				k++;
			}

			while (n < tipodocumento.size()) {
				if (guia.getTipoClasificacionId().longValue() == Long.valueOf(tipodocumento.get(n).get("id").toString())) {
					guia.setClasificacion(tipodocumento.get(n));
					break;
				}
				n++;
			}
			
			
		}
		return guiasSinCerrarList;	
	}

	@Override
	public Guia listarPorNumeroGuia(String numeroguia) throws ClientProtocolException, IOException, JSONException, Exception {
		Guia guia = guiaDao.findBynumeroGuia(numeroguia);
		List<Map<String, Object>> tipodocumento =(List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		Date fechaLimite= null;
		if(guia==null) {
			return null;
		}
		List<Map<String, Object>> sedes = (List<Map<String, Object>>) sedeEdao.listarSedesDespacho();
		for(int i=0;i<sedes.size();i++) {
			if(guia.getSedeId()==Long.valueOf(sedes.get(i).get("id").toString())) {
				guia.setSede(sedes.get(i));
				break;
			}
		}
		
		
		int k = 0;
		int n =0;
		while (k < productos.size()) {
			if (guia.getProductoId().longValue() == Long.valueOf(productos.get(k).get("id").toString())) {
				guia.setProducto(productos.get(k));
				break;
			}
			k++;
		}

		while (n < tipodocumento.size()) {
			if (guia.getTipoClasificacionId().longValue() == Long.valueOf(tipodocumento.get(n).get("id").toString())) {
				guia.setClasificacion(tipodocumento.get(n));
				break;
			}
			n++;
		}
		if(guia.getAmbitoId()!=null) {
			fechaLimite=getFechaLimite(guia);
			guia.setFechaLimite(fechaLimite);
		}
		guia.setCantidadDocumentos(guia.getDocumentosGuia().size());
		
		return guia;
	}

	@Override
	public Iterable<Guia> listarGuiasPorFechas(String fechaIni, String fechaFin) throws ClientProtocolException, IOException, JSONException, Exception {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String, Object>> tipodocumento =(List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		Date dateI= null;
		Date dateF= null;
		int cont=0;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		if(dateF.compareTo(dateI)>0 || dateF.equals(dateI)) {
			Iterable<Guia> guiasBD = guiaDao.listarGuiasPorFechas(dateI, dateF);
			if(guiasBD==null) {
				return null;
			}
			List<Guia> guias = StreamSupport.stream(guiasBD.spliterator(), false).collect(Collectors.toList());
			List<Map<String, Object>> sedes = (List<Map<String, Object>>) sedeEdao.listarSedesDespacho();
			for(Guia guia : guias) {
				Date fechaLimite= null;
				int i=0;
				while(i < sedes.size()) {
					if(guia.getSedeId() == Long.valueOf(sedes.get(i).get("id").toString())) {
						guia.setSede(sedes.get(i));
						break;
					}
					i++;
				}
				
				int k = 0;
				int n =0;
				while (k < productos.size()) {
					if (guia.getProductoId().longValue() == Long.valueOf(productos.get(k).get("id").toString())) {
						guia.setProducto(productos.get(k));
						break;
					}
					k++;
				}

				while (n < tipodocumento.size()) {
					if (guia.getTipoClasificacionId().longValue() == Long.valueOf(tipodocumento.get(n).get("id").toString())) {
						guia.setClasificacion(tipodocumento.get(n));
						break;
					}
					n++;
				}
				if(guia.getAmbitoId()!=null) {
					fechaLimite=getFechaLimite(guia);
					guia.setFechaLimite(fechaLimite);
				}
				
				guia.setCantidadDocumentos(guia.getDocumentosGuia().size());
				
			}
			return guias;
		}
		return null;
	}

	@Override
	public Guia fechaDescargaGuia(Long id, Long usuarioId) throws ClientProtocolException, IOException, JSONException {
		Optional<Guia> guiaBD = guiaDao.findById(id);
		if(!guiaBD.isPresent()) {
			return null;
		}
		Guia guia = guiaBD.get();
		List<SeguimientoGuia> seguimientoGuiaList = new ArrayList<SeguimientoGuia>();
		SeguimientoGuia seguimientoGuia = new SeguimientoGuia();		
		EstadoGuia estadoGuia = new EstadoGuia();		
		
		estadoGuia.setId(GUIA_DESCARGADO);
		seguimientoGuia.setGuia(guia);
		seguimientoGuia.setEstadoGuia(estadoGuia);		
		seguimientoGuia.setUsuarioId(usuarioId);
		seguimientoGuiaList.add(seguimientoGuia);
		
		Set<SeguimientoGuia> sg = new HashSet<SeguimientoGuia>(seguimientoGuiaList);
		guia.setSeguimientosGuia(sg);
		return guiaDao.save(guia);
	}

	@Override
	@Transactional
	public Guia crearGuiaBloque(EnvioMasivo envioMasivo, Long usuarioId ,String codigoGuia, Long proveedorId, String matricula)
			throws ClientProtocolException, IOException, JSONException {
		int cont = 0;
		Map<String, Object> sede = sedeEdao.findSedeByMatricula(matricula);
		Guia guia = new Guia();
		Proveedor proveedor = new Proveedor();
		TipoGuia tipoGuia = new TipoGuia();
		tipoGuia.setId(GUIA_BLOQUE);
		proveedor.setId(proveedorId);
		guia.setSede(sede);
		guia.setPlazoDistribucion(envioMasivo.getPlazoDistribucion());
		guia.setTipoSeguridad(envioMasivo.getTipoSeguridad());
		guia.setTipoServicio(envioMasivo.getTipoServicio());
		guia.setProducto(envioMasivo.getProducto());
		guia.setClasificacion(envioMasivo.getClasificacion());
		guia.setNumeroGuia(codigoGuia);
		guia.setProveedor(proveedor);
		guia.setTipoGuia(tipoGuia);
		
		List<DocumentoGuia> documentosGuiaList = new ArrayList<>();
		
		for (Documento documento : envioMasivo.getDocumentos()) {			
			
			DocumentoGuiaId documentoGuiaId = new DocumentoGuiaId();
			documentoGuiaId.setGuiaId(guia.getId());
			documentoGuiaId.setDocumentoId(documento.getId());
			
			DocumentoGuia documentoGuia = new DocumentoGuia();
			documentoGuia.setDocumento(documento);
			documentoGuia.setGuia(guia);
			documentoGuia.setValidado(true);
			documentoGuia.setId(documentoGuiaId);
			documentosGuiaList.add(documentoGuia);
			cont++;
		}
		//guia.setCantidad(cont);
		guia.setCantidadDocumentos(cont);
		Set<DocumentoGuia> dg = new HashSet<>(documentosGuiaList);
		guia.setDocumentosGuia(dg);
		List<SeguimientoGuia> seguimientoGuiaList = new ArrayList<>();
		SeguimientoGuia seguimientoGuia = new SeguimientoGuia();		
		EstadoGuia estadoGuia = new EstadoGuia();		
		
		estadoGuia.setId(GUIA_CREADO);
		seguimientoGuia.setGuia(guia);
		seguimientoGuia.setEstadoGuia(estadoGuia);		
		seguimientoGuia.setUsuarioId(usuarioId);
		seguimientoGuiaList.add(seguimientoGuia);
		Set<SeguimientoGuia> sg = new HashSet<>(seguimientoGuiaList);
		guia.setSeguimientosGuia(sg);
		
		guiaDao.save(guia);
		
		return guia;
		
	}

	@Override
	public Guia enviarGuiaBloque(Long guiaId, Long usuarioId) throws ClientProtocolException, IOException, JSONException {
		Guia guiaBloque = guiaDao.findById(guiaId).orElse(null);
		if(guiaBloque==null) {
			return null;
		}
		Iterable<DocumentoGuia> documentosGuiaBloque = documentoGuiaDao.findByGuiaId(guiaBloque.getId());
		List<DocumentoGuia> lstDocumentosGuiaBloque = StreamSupport.stream(documentosGuiaBloque.spliterator(), false).collect(Collectors.toList());
		
		if(lstDocumentosGuiaBloque.isEmpty()) {
			return null;
		}
		
		List<SeguimientoGuia> seguimientoGuiaList = new ArrayList<>();
		SeguimientoGuia seguimientoGuia = new SeguimientoGuia();		
		EstadoGuia estadoGuia = new EstadoGuia();		
		
		estadoGuia.setId(GUIA_ENVIADO);
		seguimientoGuia.setGuia(guiaBloque);
		seguimientoGuia.setEstadoGuia(estadoGuia);		
		seguimientoGuia.setUsuarioId(usuarioId);
		seguimientoGuiaList.add(seguimientoGuia);
		
		Set<SeguimientoGuia> sg = new HashSet<>(seguimientoGuiaList);
		guiaBloque.setSeguimientosGuia(sg);
		
		Set<DocumentoGuia> lista = guiaBloque.getDocumentosGuia();
		List<DocumentoGuia> listDG = new ArrayList<>(lista);
		
		for (DocumentoGuia dg : listDG) {
			List<SeguimientoDocumento> seguimientosDocumento = new ArrayList<>();
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(usuarioId, new EstadoDocumento(PENDIENTE_ENTREGA));
			Documento doc = dg.getDocumento();
			seguimientoDocumento.setDocumento(doc);
			seguimientosDocumento.add(seguimientoDocumento);
			Set<SeguimientoDocumento> sd = new HashSet<>(seguimientosDocumento);
			dg.getDocumento().setSeguimientosDocumento(sd);
		}
		
		return guiaDao.save(guiaBloque);
		
		
	}


	@Override
	public Iterable<Guia> listarGuiasBloqueCompletadas(Long usuarioId, String matricula) throws ClientProtocolException, IOException, JSONException, Exception {
		
		List<Map<String, Object>> sedes = (List<Map<String, Object>>) sedeEdao.listarSedesDespacho();
		Iterable<Guia> guiasBloqueBD = guiaDao.listarGuiasCompletadas();
		List<Guia> guiasBloque = StreamSupport.stream(guiasBloqueBD.spliterator(), false).collect(Collectors.toList());
		List<Map<String, Object>> tiposDocumento =(List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		
		for(Guia guia : guiasBloque) {
			
				int entregados =0;
				int rezagados =0;
				int nodistri =0;
				int cont=0;
				
				List<DocumentoGuia> documentoGuiaList = StreamSupport.stream(guia.getDocumentosGuia().spliterator(), false).collect(Collectors.toList());	

				for(DocumentoGuia documentoGuia : documentoGuiaList) {
					Documento documento = documentoGuia.getDocumento();
					EstadoDocumento estadodocumento = estadodocumentodao.buscarpordocumento(documento.getId());
					if(estadodocumento.getId()==ENTREGADO) {
						entregados++;
					}
					if(estadodocumento.getId()==REZAGADO) {
						rezagados++;
					}
					if(estadodocumento.getId()==NO_DISTRIBUIBLE) {
						nodistri++;
					}
					cont++;
				}
				
				int j = 0;
				while (j < productos.size()) {
					if (guia.getProductoId().longValue() == Long.valueOf(productos.get(j).get("id").toString())) {
						guia.setProducto(productos.get(j));
						break;
					}
					j++;
				}
				int k =0;
				while (k < tiposDocumento.size()) {
					if (guia.getTipoClasificacionId().longValue() == Long.valueOf(tiposDocumento.get(k).get("id").toString())) {
						guia.setClasificacion(tiposDocumento.get(k));
						break;
					}
					k++;
				}
				int i=0;
				while(i < sedes.size()) {
					if(guia.getSedeId() == Long.valueOf(sedes.get(i).get("id").toString())) {
						guia.setSede(sedes.get(i));
						break;
					}
					i++;
				}
				guia.setCantidadEntregados(entregados);
				guia.setCantidadNoDistribuibles(nodistri);
				guia.setCantidadRezagados(rezagados);
				guia.setCantidadDocumentos(cont);
		}
		return guiasBloqueBD;
	}

	
	
	@Override
	public Iterable<Guia> listarGuiasBloques(Long usuarioId, String Matricula) throws IOException, Exception {
		
		Map<String, Object> sede = sedeEdao.findSedeByMatricula(Matricula);
		Iterable<Guia> guiasBloqueBD = guiaDao.findByGuiasBloques(usuarioId);
		List<Guia> guiasBloque = StreamSupport.stream(guiasBloqueBD.spliterator(), false).collect(Collectors.toList());
		List<Map<String, Object>> tipodocumento =(List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		
		
		for(Guia guia : guiasBloque) {
			
			int k = 0;
			int j =0;
			while (k < productos.size()) {
				if (guia.getProductoId().longValue() == Long.valueOf(productos.get(k).get("id").toString())) {
					guia.setProducto(productos.get(k));
					break;
				}
				k++;
			}

			while (j < tipodocumento.size()) {
				if (guia.getTipoClasificacionId().longValue() == Long.valueOf(tipodocumento.get(j).get("id").toString())) {
					guia.setClasificacion(tipodocumento.get(j));
					break;
				}
				j++;
			}
			guia.setSede(sede);
			guia.setCantidadDocumentos(guia.getDocumentosGuia().size());
		}
		return guiasBloque;
	}

	@Override
	public Iterable<Documento> listarDocumentosPorGuiaId(Long id) throws ClientProtocolException, IOException, JSONException, Exception {
	Iterable<Documento> documentosBD = guiaDao.listarDocumentosByGuiaId(id);
	List<Documento> documentoslst = StreamSupport.stream(documentosBD.spliterator(), false).collect(Collectors.toList());
	
	if(documentoslst.isEmpty()) {
		return null;
	}
	
	List<Long> tipoDocumentoIds = new ArrayList();
	List<Long> productoIds = new ArrayList();
	List<Long> distritosIds = new ArrayList();
	
	for (Documento documento : documentoslst) {
		distritosIds.add(documento.getDistritoId());
		productoIds.add(documento.getEnvio().getProductoId());
		tipoDocumentoIds.add(documento.getEnvio().getTipoClasificacionId());
	}
	
	List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
	List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
	List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarByIds(tipoDocumentoIds);
	
	for (Documento documento : documentoslst) {
		
		int i = 0; 
		while(i < distritos.size()) {
			Long distritoId= Long.valueOf(distritos.get(i).get("id").toString());
			if (documento.getDistritoId().longValue() == distritoId.longValue()) {
				documento.setDistrito(distritos.get(i));
				break;
			}
			i++;
		}
	
		int j = 0; 
		while(j < productos.size()) {
			if (documento.getEnvio().getProductoId() == Long.valueOf(productos.get(j).get("id").toString())) {
				documento.getEnvio().setProducto(productos.get(j));
				break;
			}
			j++;
		}
		
		int k = 0;
		while(k < tiposDocumento.size()) {
			if (documento.getEnvio().getTipoClasificacionId() == Long.valueOf(tiposDocumento.get(k).get("id").toString())) {
				documento.getEnvio().setClasificacion(tiposDocumento.get(k));
				break;
			}
			k++;
		}
	}
			
	return documentoslst;
	
	}

	@Override
	@Transactional
	public Map<Integer,String> cargarResultadosDevolucion(List<Documento> documentoDevueltos, Long usuarioId) throws ClientProtocolException, IOException, JSONException, Exception {
		Map<Integer,String> map = new HashMap<Integer,String>();
		List<String> autogeneradoList = new ArrayList<>();		
		for(Documento documento : documentoDevueltos) {
		autogeneradoList.add(documento.getDocumentoAutogenerado());
		}
		List<Documento> documentosBDList = StreamSupport.stream(documentoDao.findAllByDocumentoAutogeneradoIn(autogeneradoList).spliterator(), false).collect(Collectors.toList());	 
		if(documentosBDList.isEmpty()) {
			map.put(0, "NO HAY COINCIDENCIAS DE DOCUMENTOS");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return map;
		}
		Guia guia = guiaDao.findGuiabyAutogenerado(documentoDevueltos.get(0).getDocumentoAutogenerado());
		int i=0;
		for(Documento documento : documentosBDList) {
			if(documento.getDocumentoAutogenerado().equals(documentoDevueltos.get(i).getDocumentoAutogenerado())) {
				documento.setTiposDevolucion(documentoDevueltos.get(i).getTiposDevolucion());
			}
			List<SeguimientoDocumento> seguimientosDocumentolst = new ArrayList<>(documento.getSeguimientosDocumento());
			SeguimientoDocumento sdMax = Collections.max(seguimientosDocumentolst, Comparator.comparingLong(s -> s.getId()));
			documento.setRecepcionado(true);
			SeguimientoDocumento seguimientodocumento = new SeguimientoDocumento(usuarioId, sdMax.getEstadoDocumento(),"Documento(s) recibido(s)");
			seguimientodocumento.setUsuarioId(usuarioId);
			seguimientodocumento.setDocumento(documento);
			seguimientosDocumentolst.add(seguimientodocumento);
			seguimientoDocumentodao.saveAll(seguimientosDocumentolst);
			Set<SeguimientoDocumento> sd = new HashSet<SeguimientoDocumento>(seguimientosDocumentolst);
			documento.setSeguimientosDocumento(sd);
			i++;
		}
		documentoDao.saveAll(documentosBDList);
		List<SeguimientoGuia> seguimientoGuiaList = new ArrayList<SeguimientoGuia>();
		SeguimientoGuia seguimientoGuia = new SeguimientoGuia();		
		EstadoGuia estadoGuia = new EstadoGuia();		
		
		estadoGuia.setId(GUIA_CERRADO);
		seguimientoGuia.setGuia(guia);
		seguimientoGuia.setEstadoGuia(estadoGuia);		
		seguimientoGuia.setUsuarioId(usuarioId);
		seguimientoGuiaList.add(seguimientoGuia);
		
		Set<SeguimientoGuia> sg = new HashSet<SeguimientoGuia>(seguimientoGuiaList);
		guia.setSeguimientosGuia(sg);
		guiaDao.save(guia);
		map.put(1, "SE CARGARON LOS RESULTADOS SATISFACTORIAMENTE");
		
		return map;

	}

	@Override
	public Date getFechaLimite(Guia guia) throws ClientProtocolException, IOException, JSONException, URISyntaxException, ParseException {
		SubambitoPlazoDistribucion subambito = subambitoplazodao.getPlazoDistribucionBySubambitoId(1L, guia.getPlazoDistribucion().getId());
		SeguimientoGuia sg = guia.getSeguimientoGuiaByEstadoId(GUIA_ENVIADO);
		Calendar calendar = Calendar.getInstance();
		Calendar envio = Calendar.getInstance();
		envio.setTime(sg.getFecha());
		calendar.setTime(sg.getFecha());
		calendar.add(Calendar.HOUR_OF_DAY, subambito.getTiempoEnvio());
		int dias = calendar.get(Calendar.DAY_OF_MONTH) - envio.get(Calendar.DAY_OF_MONTH);
		Map<String, Object> fecha =  ambitodiasdao.listarFechaLimite(1L,envio.getTime().toString(),dias);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000+0000");
		Date fechaLimite = null;
		try {
			fechaLimite = format.parse(fecha.get("fechaLimite").toString());
		} catch (Exception e) {
			return null;
		}
		return fechaLimite;
	}
	
}
