package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.PENDIENTE_ENTREGA;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.REZAGADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_ENVIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.DENEGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ELIMINADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.NO_DISTRIBUIBLE;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_CERRADO;
import static com.exact.service.externa.enumerator.EstadoGuiaEnum.GUIA_COMPLETA;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IDocumentoGuiaDao;
import com.exact.service.externa.dao.IEstadoDocumentoDao;
import com.exact.service.externa.dao.IGuiaDao;
import com.exact.service.externa.dao.ISeguimientoDocumentoDao;
import com.exact.service.externa.dao.ISeguimientoGuiaDao;
import com.exact.service.externa.edao.classes.SedeEdao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.edao.interfaces.IProductoEdao;
import com.exact.service.externa.edao.interfaces.ISedeEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.EstadoGuia;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.SeguimientoGuia;
import com.exact.service.externa.entity.id.DocumentoGuiaId;
import com.exact.service.externa.service.interfaces.IDocumentoService;
import com.exact.service.externa.service.interfaces.IEstadoDocumentoService;
import com.exact.service.externa.utils.IAutogeneradoUtils;

@Service
public class DocumentoService implements IDocumentoService {

	
	@Autowired
	private IDocumentoDao documentoDao;

	@Autowired
	private IAutogeneradoUtils autogeneradoUtils;

	@Autowired
	private ISeguimientoDocumentoDao seguimientoDocumentodao;

	@Autowired
	private IHandleFileEdao handleFileEdao;
	
	@Autowired
	IBuzonEdao buzonEdao;
	
	@Autowired
	ITipoDocumentoEdao tipoDocumentoEdao;
	
	@Autowired
	IDistritoEdao distritoEdao;
	
	@Autowired
	ISedeEdao sedeEdao;
	
	@Autowired
	IDocumentoGuiaDao documentoGuiadao;
	
	@Autowired
	IGuiaDao guiadao;
	
	@Autowired
	ISeguimientoGuiaDao seguimientoGuiadao;
	
	@Autowired
	IProductoEdao productoEdao;

	@Autowired
	IEstadoDocumentoDao estadoDocumentodao;

	
	@Override
	@Transactional
	public int custodiarDocumentos(Iterable<Documento> documentos, Long usuarioId) {
		List<SeguimientoDocumento> seguimientosDocumento = new ArrayList<SeguimientoDocumento>();
		for (Documento documento : documentos) {
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(usuarioId, new EstadoDocumento(CUSTODIADO));
			seguimientoDocumento.setDocumento(documento);
			seguimientosDocumento.add(seguimientoDocumento);
		}
		seguimientoDocumentodao.saveAll(seguimientosDocumento);
		return 1;
	}

	@Override
	public Iterable<Documento> listarDocumentosGuiaPorCrear(Guia guia, String matricula) throws ClientProtocolException, IOException, JSONException {
		
		Map<String, Object> sede = sedeEdao.findSedeByMatricula(matricula);
		
		Iterable<Documento> documentosCreados = documentoDao.findByPlazoDistribucionAndTipoServicioAndTipoSeguridadAndProductoAndClasificacion(guia.getPlazoDistribucion().getId(), guia.getTipoServicio().getId(),  guia.getProductoId(), guia.getTipoClasificacionId(),guia.getTipoSeguridad().getId(), Long.valueOf(sede.get("id").toString()));
		List<Documento> documentosCreadosList = StreamSupport.stream(documentosCreados.spliterator(), false).collect(Collectors.toList());	
		
		return documentosCreadosList;
	}

	@Override
	public Iterable<Documento> listarDocumentosPorEstado() throws ClientProtocolException, IOException, JSONException{

				
		Iterable<Documento> documentosCustodiados = documentoDao.listarDocumentosPorEstado(CUSTODIADO);
		List<Documento> documentosCustodiadosList = StreamSupport.stream(documentosCustodiados.spliterator(), false).collect(Collectors.toList());		
		
		List<Long> buzonIds = new ArrayList();
		List<Long> tipoDocumentoIds = new ArrayList();
		
		for (Documento documento : documentosCustodiadosList) {
			buzonIds.add(documento.getEnvio().getBuzonId());
			tipoDocumentoIds.add(documento.getEnvio().getTipoClasificacionId());
		}
		
		
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
		List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarByIds(tipoDocumentoIds);
		
		for (Documento documento : documentosCustodiadosList) {
			
			int i = 0; 
			while(i < buzones.size()) {
				if (documento.getEnvio().getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
					documento.getEnvio().setBuzon(buzones.get(i));
					break;
				}
				i++;
			}
			int j = 0;
			while(j < tiposDocumento.size()) {
				if (documento.getEnvio().getTipoClasificacionId() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
					documento.getEnvio().setClasificacion(tiposDocumento.get(j));
					break;
				}
				j++;
			}
		
		}
		return documentosCustodiadosList;
	}

	
	
	@Override
	public Iterable<Documento> listarReporteBCP(Date fechaIni, Date fechaFin, Long idbuzon) throws IOException, Exception
	{
		Iterable<Documento> documentos = documentoDao.listarReporteBCP(fechaIni, fechaFin,idbuzon);
		if(documentos==null) {
			return null;
		}
		List<Documento> documentosUbcp = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		List<Long> distritosIds = new ArrayList();
		List<Long> buzonIds = new ArrayList();
		List<Long> tipoDocumentoIds = new ArrayList();
		
		for (Documento documento : documentosUbcp) {
			distritosIds.add(documento.getDistritoId());
			buzonIds.add(documento.getEnvio().getBuzonId());
			tipoDocumentoIds.add(documento.getEnvio().getTipoClasificacionId());
		}
		
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
		List<Map<String, Object>> sedes = (List<Map<String, Object>>) sedeEdao.listarSedesDespacho();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarByIds(tipoDocumentoIds);
		
		for (Documento documento : documentosUbcp) {
			
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
			while(j < buzones.size()) {
				if (documento.getEnvio().getBuzonId() == Long.valueOf(buzones.get(j).get("id").toString())) {
					documento.getEnvio().setBuzon(buzones.get(j));
					break;
				}
				j++;
			}
			
			int k = 0; 
			while(k < sedes.size()) {
				if (documento.getEnvio().getSedeId() == Long.valueOf(sedes.get(k).get("id").toString())) {
					documento.getEnvio().setSede(sedes.get(k));
					break;
				}
				k++;
			}
			
			int m = 0; 
			while(m < productos.size()) {
				if (documento.getEnvio().getProductoId() == Long.valueOf(productos.get(m).get("id").toString())) {
					documento.getEnvio().setProducto(productos.get(m));
					break;
				}
				m++;
			}
			
			int n = 0;
			while(n < tiposDocumento.size()) {
				if (documento.getEnvio().getTipoClasificacionId() == Long.valueOf(tiposDocumento.get(n).get("id").toString())) {
					documento.getEnvio().setClasificacion(tiposDocumento.get(n));
					break;
				}
				n++;
			}
		}
		return documentosUbcp;
	}

	@Override
	@Transactional()
	public Map<Integer,String> cargarResultados(List<Documento> documentosExcelList, Long usuarioId) throws ClientProtocolException, IOException, JSONException {	
		
		
		Map<Integer,String> map = new HashMap<Integer,String>();
		
		List<String> autogeneradoList = new ArrayList<String>();		
		
		for(Documento documento : documentosExcelList) {
			autogeneradoList.add(documento.getDocumentoAutogenerado());
		}
		
		Guia guia = guiadao.findGuiabyAutogenerado(documentosExcelList.get(0).getDocumentoAutogenerado());
		
		List<Documento> documentosBDList = StreamSupport.stream(documentoDao.findAllByDocumentoAutogeneradoIn(autogeneradoList).spliterator(), false).collect(Collectors.toList());	 
		

		if (documentosBDList.size()==0) {
			map.put(0, "NO HAY COINCIDENCIAS");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return map;
		}
		
		for(Documento documento : documentosExcelList) {					
			
			Optional<Documento> d = documentosBDList.stream().filter(a -> a.getDocumentoAutogenerado().equals(documento.getDocumentoAutogenerado())).findFirst();
			
			
			if (!d.isPresent()) {				
				map.put(2, "EL CÓDIGO AUTOGENERADO " + documento.getDocumentoAutogenerado() + " NO EXISTE");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return map;
			}
			
			Documento documentoBD = d.get();
			
			SeguimientoDocumento seguimientoDocumentoBDUltimo = documentoBD.getUltimoSeguimientoDocumento(); 
			
			
			if (seguimientoDocumentoBDUltimo.getEstadoDocumento().getId() != PENDIENTE_ENTREGA && 
				seguimientoDocumentoBDUltimo.getEstadoDocumento().getId() != REZAGADO)  {
				map.put(5, "EL DOCUMENTO " + documento.getDocumentoAutogenerado() + " NO SE ENCUENTRA EN ESTADO PENDIENTE DE ENTREGA O REZAGADO PARA CARGAR RESULTADO");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return map;
			}
			SeguimientoDocumento seguimientoDocumentoExcel = documento.getUltimoSeguimientoDocumento();
			
			if (seguimientoDocumentoExcel.getEstadoDocumento().getId() != ENTREGADO &&
				seguimientoDocumentoExcel.getEstadoDocumento().getId() != REZAGADO &&
				seguimientoDocumentoExcel.getEstadoDocumento().getId() != NO_DISTRIBUIBLE ) {
				map.put(3, "EL DOCUMENTO " + documento.getDocumentoAutogenerado() + " TIENE UN ESTADO NO VÁLIDO PARA ESTE PROCESO");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return map;
			}
			
			if ( (seguimientoDocumentoExcel.getEstadoDocumento().getId() == ENTREGADO || 
					seguimientoDocumentoExcel.getEstadoDocumento().getId() == REZAGADO) &&
					seguimientoDocumentoExcel.getLinkImagen().isEmpty()) {
				map.put(4, "EL DOCUMENTO " + documento.getDocumentoAutogenerado() + " NO CUENTA CON LINK DE IMAGEN");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return map;
			}
			
			if ( seguimientoDocumentoExcel.getEstadoDocumento().getId() != ENTREGADO && 
				seguimientoDocumentoExcel.getEstadoDocumento().getId() != REZAGADO) {
				
				seguimientoDocumentoExcel.setLinkImagen("");
			}
			
			if (seguimientoDocumentoBDUltimo.getFecha().compareTo(seguimientoDocumentoExcel.getFecha())>=0) {
				map.put(6, "LA FECHA Y HORA DEL DOCUMENTO " + documento.getDocumentoAutogenerado() + " DEBE SER MAYOR A LA FECHA Y HORA DEL ÚLTIMO ESTADO");
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return map;
			}
			
			EstadoDocumento estadoDocumentoBD = estadoDocumentodao.findById(seguimientoDocumentoExcel.getEstadoDocumento().getId()).orElse(null);
			if(estadoDocumentoBD!=null) {
				boolean rpta = estadoDocumentoBD.getMotivos().stream().anyMatch(motivo -> motivo.getId().longValue()==seguimientoDocumentoExcel.getMotivoEstado().getId().longValue());
				if(!rpta) {
					map.put(7, "EL MOTIVO NO CORRESPONDE A EL ESTADO INGRESADO");
				}
			}
			SeguimientoDocumento seguimientoDocumentoNuevo = new SeguimientoDocumento();
			seguimientoDocumentoNuevo.setDocumento(documentoBD);
			seguimientoDocumentoNuevo.setObservacion(seguimientoDocumentoExcel.getObservacion());
			seguimientoDocumentoNuevo.setFecha(seguimientoDocumentoExcel.getFecha());
			seguimientoDocumentoNuevo.setEstadoDocumento(seguimientoDocumentoExcel.getEstadoDocumento());
			seguimientoDocumentoNuevo.setLinkImagen(seguimientoDocumentoExcel.getLinkImagen());
			seguimientoDocumentoNuevo.setMotivoEstado(seguimientoDocumentoExcel.getMotivoEstado());
			seguimientoDocumentoNuevo.setUsuarioId(usuarioId);
			
			
			documentoBD.addSeguimientoDocumento(seguimientoDocumentoNuevo);
		}
		
		documentoDao.saveAll(documentosBDList);		
		
		boolean rpta = guiadao.existeDocumentosPendientes(guia.getId());
		if(!rpta) {
			List<SeguimientoGuia> seguimientoGuiaList = new ArrayList<SeguimientoGuia>();
			SeguimientoGuia seguimientoGuia = new SeguimientoGuia();		
			EstadoGuia estadoGuia = new EstadoGuia();		
			
			estadoGuia.setId(GUIA_COMPLETA);
			seguimientoGuia.setGuia(guia);
			seguimientoGuia.setEstadoGuia(estadoGuia);		
			seguimientoGuia.setUsuarioId(usuarioId);
			seguimientoGuiaList.add(seguimientoGuia);
			
			Set<SeguimientoGuia> sg = new HashSet<SeguimientoGuia>(seguimientoGuiaList);
			guia.setSeguimientosGuia(sg);
			guiadao.save(guia);
		}
		
		map.put(1, "SE CARGARON LOS RESULTADOS SATISFACTORIAMENTE");
		return map;
	}

	
	public Iterable<Documento> listarDocumentosEntregados(String matricula) throws ClientProtocolException, IOException, JSONException {
		
		Map<String, Object> sede = sedeEdao.findSedeByMatricula(matricula);
		
		Iterable<Documento> documentos = documentoDao.listarDocumentosEntregados(Long.valueOf(sede.get("id").toString()));
		List<Documento> documentosEntregados = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());	
		List<Long> buzonIds = new ArrayList();
		
		for (Documento documento : documentosEntregados) {
			buzonIds.add(documento.getEnvio().getBuzonId());
		}
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
		for (Documento documento : documentosEntregados) {
			int i = 0; 
			while(i < buzones.size()) {
				if (documento.getEnvio().getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
					documento.getEnvio().setBuzon(buzones.get(i));
					break;
				}
				i++;
			}
		}
		return documentosEntregados;
	}

	@Override
	public Documento recepcionarDocumentoEntregado(Long id, Long idUsuario){
		Documento documento = documentoDao.findById(id).orElse(null);
		if(documento==null) {
			return null;
		}
		if (documento.isRecepcionado()) {
			return null;
		} 
		List<SeguimientoDocumento> seguimientosDocumentolst = new ArrayList<SeguimientoDocumento>(documento.getSeguimientosDocumento());
		SeguimientoDocumento sdMax = Collections.max(seguimientosDocumentolst, Comparator.comparingLong(s -> s.getId()));
		
		if(sdMax.getEstadoDocumento().getId()!=ENTREGADO) {
			return null;
		}
		documento.setRecepcionado(true);
		SeguimientoDocumento seguimientodocumento = new SeguimientoDocumento(idUsuario, sdMax.getEstadoDocumento(),"Cargo Recibido");
		
		
		seguimientodocumento.setLinkImagen(sdMax.getLinkImagen());
		seguimientodocumento.setUsuario(sdMax.getUsuario());
		seguimientodocumento.setDocumento(documento);
		
		seguimientosDocumentolst.add(seguimientodocumento);
		seguimientoDocumentodao.saveAll(seguimientosDocumentolst);
		
		Set<SeguimientoDocumento> sd = new HashSet<SeguimientoDocumento>(seguimientosDocumentolst);
		documento.setSeguimientosDocumento(sd);
		
		return documentoDao.save(documento);
	}

	@Override
	public Iterable<Documento> listarDocumentosDevueltos(String matricula) throws ClientProtocolException, IOException, JSONException {
		Map<String, Object> sede = sedeEdao.findSedeByMatricula(matricula);
		Iterable<Documento> documentos = documentoDao.listarDocumentosDevueltos(Long.valueOf(sede.get("id").toString()));
		List<Documento> documentosDevueltos = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		List<Long> buzonIds = new ArrayList();
		
		for (Documento documento : documentosDevueltos) {
			buzonIds.add(documento.getEnvio().getBuzonId());
		}
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
		for (Documento documento : documentosDevueltos) {
			int i = 0; 
			while(i < buzones.size()) {
				if (documento.getEnvio().getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
					documento.getEnvio().setBuzon(buzones.get(i));
					break;
				}
				i++;
			}
		}
		return documentosDevueltos;
	}
	
	
	@Override
	public Documento recepcionarDocumentoDevuelto(Long id, Long idUsuario) throws ClientProtocolException, IOException, JSONException {
		Documento documento = documentoDao.findById(id).orElse(null);
		if(documento==null) {
			return null;
		}
		if (documento.isRecepcionado()) {
			return null;
		} 
		List<SeguimientoDocumento> seguimientosDocumentolst = new ArrayList<SeguimientoDocumento>(documento.getSeguimientosDocumento());
		SeguimientoDocumento sdMax = Collections.max(seguimientosDocumentolst, Comparator.comparingLong(s -> s.getId()));
		
		if(sdMax.getEstadoDocumento().getId()!=REZAGADO && sdMax.getEstadoDocumento().getId()!=NO_DISTRIBUIBLE) {
			return null;
		}
		documento.setRecepcionado(true);
		SeguimientoDocumento seguimientodocumento = new SeguimientoDocumento(idUsuario, sdMax.getEstadoDocumento(),"Documento Devuelto");
		
		
		seguimientodocumento.setLinkImagen(sdMax.getLinkImagen());
		seguimientodocumento.setUsuario(sdMax.getUsuario());
		seguimientodocumento.setDocumento(documento);
		
		seguimientosDocumentolst.add(seguimientodocumento);
		seguimientoDocumentodao.saveAll(seguimientosDocumentolst);
		
		Set<SeguimientoDocumento> sd = new HashSet<SeguimientoDocumento>(seguimientosDocumentolst);
		documento.setSeguimientosDocumento(sd);
		
		return documentoDao.save(documento);
	}


	@Override
	public Iterable<Documento> listarReporteUTD(Date fechaIni, Date fechaFin)
			throws IOException, Exception {
		
		Iterable<Documento> documentos = documentoDao.listarReporteUTD(fechaIni, fechaFin);
		List<Documento> documentosUTD = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		List<Long> distritosIds = new ArrayList();
		List<Long> buzonIds = new ArrayList();
		List<Long> tipoDocumentoIds = new ArrayList();
		
		for (Documento documento : documentosUTD) {
			distritosIds.add(documento.getDistritoId());
			buzonIds.add(documento.getEnvio().getBuzonId());
			tipoDocumentoIds.add(documento.getEnvio().getTipoClasificacionId());
		}
		
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
		List<Map<String, Object>> sedes = (List<Map<String, Object>>) sedeEdao.listarSedesDespacho();
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		List<Map<String, Object>> tipoDocumentos = (List<Map<String, Object>>) tipoDocumentoEdao.listarByIds(tipoDocumentoIds);
		
		for (Documento documento : documentosUTD) {
			
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
			while(j < buzones.size()) {
				if (documento.getEnvio().getBuzonId() == Long.valueOf(buzones.get(j).get("id").toString())) {
					documento.getEnvio().setBuzon(buzones.get(j));
					break;
				}
				j++;
			}
			
			int k = 0; 
			while(k < sedes.size()) {
				if (documento.getEnvio().getSedeId() == Long.valueOf(sedes.get(k).get("id").toString())) {
					documento.getEnvio().setSede(sedes.get(k));
					break;
				}
				k++;
			}
			
			int m = 0; 
			while(m < productos.size()) {
				if (documento.getEnvio().getTipoClasificacionId()== Long.valueOf(productos.get(m).get("id").toString())) {
					documento.getEnvio().setProducto(productos.get(m));
					break;
				}
				m++;
			}
			
			int n = 0; 
			while(n < tipoDocumentos.size()) {
				if (documento.getEnvio().getProductoId() == Long.valueOf(tipoDocumentos.get(n).get("id").toString())) {
					documento.getEnvio().setClasificacion(tipoDocumentos.get(n));
					break;
				}
				n++;
			}
		}
		return documentosUTD;
	}

	@Override
	public Documento listarDocumentoUTD(String autogenerado) throws ClientProtocolException, IOException, JSONException, Exception {
		
		Documento documento = documentoDao.listarDocumento(autogenerado);
		
		if(documento==null) {
			return null;
		}
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		Map<String, Object> buzones = buzonEdao.listarById(documento.getEnvio().getBuzonId().longValue());
		List<Map<String, Object>> sedes = (List<Map<String, Object>>) sedeEdao.listarSedesDespacho();
		List<Map<String, Object>> productosBD = (List<Map<String, Object>>) productoEdao.listarAll();
		List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarAll();
		
		documento.getEnvio().setBuzon(buzones);
		for(int i=0;i < distritos.size();i++) {	
			Long distritoId= Long.valueOf(distritos.get(i).get("id").toString());
			if (documento.getDistritoId().longValue() == distritoId.longValue()) {
				documento.setDistrito(distritos.get(i));
				break;
			}
		}
		
		for(int j=0;j < sedes.size();j++) {	
			Long sedeId= Long.valueOf(sedes.get(j).get("id").toString());
			if (documento.getEnvio().getSedeId() == sedeId.longValue()) {
				documento.getEnvio().setSede(sedes.get(j));
				break;
			}
		}
		
		for(int k=0;k < productosBD.size();k++) {	
			Long productoId= Long.valueOf(productosBD.get(k).get("id").toString());
			if (documento.getEnvio().getProductoId() == productoId.longValue()) {
				documento.getEnvio().setProducto(productosBD.get(k));
				break;
			}
		}
		
		for(int m=0;m < tiposDocumento.size();m++) {	
			Long tipoDocumentoId= Long.valueOf(tiposDocumento.get(m).get("id").toString());
			if (documento.getEnvio().getTipoClasificacionId() == tipoDocumentoId.longValue()) {
				documento.getEnvio().setClasificacion(tiposDocumento.get(m));
				break;
			}
		}
		
		return documento;
	}

	@Override
	public Iterable<Documento> listarDocumentosParaVolumen(Date fechaIni, Date fechaFin,Long estadoDocumentoId) throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Documento> documentos = documentoDao.listarDocumentosParaVolumen(fechaIni, fechaFin,estadoDocumentoId);
		List<Documento> documentosVolu = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());

		List<Map<String, Object>> sedes = (List<Map<String, Object>>) sedeEdao.listarSedesDespacho();
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarAll();
		
		
		for (Documento documento : documentosVolu) {
			
			int i = 0; 
			while(i < sedes.size()) {
				
				if (documento.getEnvio().getSedeId().longValue() == Long.valueOf(sedes.get(i).get("id").toString())) {
					documento.getEnvio().setSede(sedes.get(i));
					break;
				}
				i++;
			}
			
			int j = 0; 
			while(j < buzones.size()) {
				if (documento.getEnvio().getBuzonId() == Long.valueOf(buzones.get(j).get("id").toString())) {
					documento.getEnvio().setBuzon(buzones.get(j));
					break;
				}
				j++;
			}
		}
	
		return documentosVolu;
		
	}

	@Override
	public Iterable<Documento> listarCargos(Date fechaIni, Date fechaFin) throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Documento> documentos = documentoDao.listarCargos(fechaIni,fechaFin);
		List<Documento> documentosCargos = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());	
		List<Long> buzonIds = new ArrayList();
		
		for (Documento documento : documentosCargos) {
			buzonIds.add(documento.getEnvio().getBuzonId());
		}
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
		for (Documento documento : documentosCargos) {
			int i = 0; 
			while(i < buzones.size()) {
				if (documento.getEnvio().getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
					documento.getEnvio().setBuzon(buzones.get(i));
					break;
				}
				i++;
			}
		}
		return documentosCargos;
	}

	
	
	@Override
	public Documento cambiarEstadoDocumento(Long id, SeguimientoDocumento sd , Long idUsuario)
			throws ClientProtocolException, IOException, JSONException {
	
		Optional<Documento> d = documentoDao.findById(id);
		if(!d.isPresent()) {
			return null;
		}
		Documento documento = d.get();
		DocumentoGuia dg = documentoGuiadao.findByDocumentoId(id);
		SeguimientoDocumento seguimientoDocumento= null;
		Iterable<EstadoDocumento> estadosDocumento = documento.getUltimoSeguimientoDocumento().getEstadoDocumento().getEstadosDocumentoPermitidos();
		List<EstadoDocumento> lstEstadosDocumento = StreamSupport.stream(estadosDocumento.spliterator(), false).collect(Collectors.toList());	
		
		for(EstadoDocumento ed : lstEstadosDocumento){
			int i = 0; 
			while(i < lstEstadosDocumento.size()) {
				if (sd.getEstadoDocumento().getId() == lstEstadosDocumento.get(i).getId()) {
					seguimientoDocumento = new SeguimientoDocumento(idUsuario, sd.getEstadoDocumento(), sd.getObservacion());
					break;
				}
				i++;
			}
		}
		documentoGuiadao.retirarDocumento(documento.getId());		
		if(dg!=null && dg.getGuia().getDocumentosGuia().isEmpty()) {
				seguimientoGuiadao.retirarSeguimiento(dg.getGuia().getId());
				guiadao.retirarGuia(dg.getGuia().getId());
		}	
		documento.addSeguimientoDocumento(seguimientoDocumento);
		seguimientoDocumento.setDocumento(documento);
		documento.setDocumentosGuia(null);
		return documentoDao.save(documento);
	}

	@Override
	public Documento guardarCodigoDevolucion(Long documentoId, String codigoDev) throws ClientProtocolException, IOException, JSONException {
		if(codigoDev==null) {
			return null;
		}
		Optional<Documento> documentoBD = documentoDao.findById(documentoId);
		if(!documentoBD.isPresent()) {
			return null;
		}
		Documento documento = documentoBD.get();
		documento.setCodigoDevolucion(codigoDev);
		return documentoDao.save(documento);
	}



}
