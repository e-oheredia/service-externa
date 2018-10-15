package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.PENDIENTE_ENTREGA;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.REZAGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.DEVUELTO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.EXTRAVIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.DENEGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.RETIRADO;

import java.io.IOException;
import java.util.ArrayList;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.ISeguimientoDocumentoDao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.SeguimientoGuia;
import com.exact.service.externa.service.interfaces.IDocumentoService;
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
	public Iterable<Documento> listarDocumentosGuiaPorCrear(Guia guia) throws ClientProtocolException, IOException, JSONException {
		Iterable<Documento> documentosCreados = documentoDao.findByPlazoDistribucionAndTipoServicioAndTipoSeguridad(guia.getPlazoDistribucion().getId(), guia.getTipoServicio().getId(), guia.getTipoSeguridad().getId());
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
			tipoDocumentoIds.add(documento.getEnvio().getTipoDocumentoId());
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
				if (documento.getEnvio().getTipoDocumentoId() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
					documento.getEnvio().setTipoDocumento(tiposDocumento.get(j));
					break;
				}
				j++;
			}
		
		}
		return documentosCustodiadosList;
	}

	@Override
	public Iterable<Documento> listarReporteBCP(Date fechaIni, Date fechaFin) throws ClientProtocolException, IOException, JSONException
	{
		Iterable<Documento> documentos = documentoDao.listarReporteBCP(fechaIni, fechaFin);
		List<Documento> documentosUbcp = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		List<Long> distritosIds = new ArrayList();
		
		for (Documento documento : documentosUbcp) {
			distritosIds.add(documento.getDistritoId());
		}
		
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		
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
		}
		return documentosUbcp;
	}

	@Override
	@Transactional
	public Map<Integer,String> cargarResultados(List<Documento> documentosExcelList, Long usuarioId) throws ClientProtocolException, IOException, JSONException {	
		
		Map<Integer,String> map = new HashMap<Integer,String>();
		
		List<String> autogeneradoList = new ArrayList<String>();		
		
		for(Documento documento : documentosExcelList) {
			autogeneradoList.add(documento.getDocumentoAutogenerado());
		}
		
		List<Documento> documentosBDList = StreamSupport.stream(documentoDao.findAllByDocumentoAutogeneradoIn(autogeneradoList).spliterator(), false).collect(Collectors.toList());	 
		

		if (documentosBDList.size()==0) {
			map.put(0, "NO HAY COINCIDENCIAS");
			return map;
		}
		
				
		
		for(Documento documento : documentosExcelList) {					
			
			Optional<Documento> d = documentosBDList.stream().filter(a -> a.getDocumentoAutogenerado().equals(documento.getDocumentoAutogenerado())).findFirst();
			
			
			if (!d.isPresent()) {
				map.put(2, "EL CÓDIGO AUTOGENERADO " + documento.getDocumentoAutogenerado() + " NO EXISTE");
				return map;
			}
			
			Documento documentoBD = d.get();
			
			SeguimientoDocumento seguimientoDocumentoBDUltimo = documentoBD.getUltimoSeguimientoDocumento(); 
			
			
			if (seguimientoDocumentoBDUltimo.getEstadoDocumento().getId() != PENDIENTE_ENTREGA)  {
				map.put(5, "EL DOCUMENTO " + documento.getDocumentoAutogenerado() + " NO SE ENCUENTRA EN ESTADO PENDIENTE DE ENTREGA");
				return map;
			}
						
			
			SeguimientoDocumento seguimientoDocumentoExcel = documento.getUltimoSeguimientoDocumento();
			
			
			if (seguimientoDocumentoExcel.getEstadoDocumento().getId() != ENTREGADO &&
				seguimientoDocumentoExcel.getEstadoDocumento().getId() != REZAGADO &&
				seguimientoDocumentoExcel.getEstadoDocumento().getId() != DEVUELTO &&
				seguimientoDocumentoExcel.getEstadoDocumento().getId() != EXTRAVIADO) {
				map.put(3, "EL DOCUMENTO " + documento.getDocumentoAutogenerado() + " TIENE UN ESTADO NO VÁLIDO PARA ESTE PROCESO");
				return map;
			}
			
			if ( (seguimientoDocumentoExcel.getEstadoDocumento().getId() == ENTREGADO || 
					seguimientoDocumentoExcel.getEstadoDocumento().getId() == REZAGADO) &&
					seguimientoDocumentoExcel.getLinkImagen().isEmpty()) {
				map.put(4, "EL DOCUMENTO " + documento.getDocumentoAutogenerado() + " NO CUENTA CON LINK DE IMAGEN");
				return map;
			}
			
			if ( seguimientoDocumentoExcel.getEstadoDocumento().getId() != ENTREGADO && 
				seguimientoDocumentoExcel.getEstadoDocumento().getId() != REZAGADO) {
				
				seguimientoDocumentoExcel.setLinkImagen("");
			}
			
			SeguimientoDocumento seguimientoDocumentoNuevo = new SeguimientoDocumento();
			seguimientoDocumentoNuevo.setDocumento(documentoBD);
			seguimientoDocumentoNuevo.setObservacion(seguimientoDocumentoExcel.getObservacion());
			seguimientoDocumentoNuevo.setFecha(seguimientoDocumentoExcel.getFecha());
			seguimientoDocumentoNuevo.setEstadoDocumento(seguimientoDocumentoExcel.getEstadoDocumento());
			seguimientoDocumentoNuevo.setLinkImagen(seguimientoDocumentoExcel.getLinkImagen());
			seguimientoDocumentoNuevo.setUsuarioId(usuarioId);
			
			
			documentoBD.addSeguimientoDocumento(seguimientoDocumentoNuevo);
		}
		
				
		
		documentoDao.saveAll(documentosBDList);		
		
		map.put(1, "SE CARGARON LOS RESULTADOS SATISFACTORIAMENTE");
		return map;
	}

	public Iterable<Documento> listarDocumentosEntregados() throws ClientProtocolException, IOException, JSONException {
		Iterable<Documento> documentos = documentoDao.listarDocumentosEntregados();
		List<Documento> documentosEntregados = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());	
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
		
		seguimientodocumento.setFecha(sdMax.getFecha());
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
	public Iterable<Documento> listarDocumentosDevueltos() throws ClientProtocolException, IOException, JSONException {
		Iterable<Documento> documentos = documentoDao.listarDocumentosDevueltos();
		List<Documento> documentosDevueltos = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());	
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
		
		if(sdMax.getEstadoDocumento().getId()!=REZAGADO && sdMax.getEstadoDocumento().getId()!=DEVUELTO) {
			return null;
		}
		documento.setRecepcionado(true);
		SeguimientoDocumento seguimientodocumento = new SeguimientoDocumento(idUsuario, sdMax.getEstadoDocumento(),"Documento Devuelto");
		
		seguimientodocumento.setFecha(sdMax.getFecha());
		seguimientodocumento.setLinkImagen(sdMax.getLinkImagen());
		seguimientodocumento.setUsuario(sdMax.getUsuario());
		seguimientodocumento.setDocumento(documento);
		
		seguimientosDocumentolst.add(seguimientodocumento);
		seguimientoDocumentodao.saveAll(seguimientosDocumentolst);
		
		Set<SeguimientoDocumento> sd = new HashSet<SeguimientoDocumento>(seguimientosDocumentolst);
		documento.setSeguimientosDocumento(sd);
		
		return documentoDao.save(documento);
	}
	
}
