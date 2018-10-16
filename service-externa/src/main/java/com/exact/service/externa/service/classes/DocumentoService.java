package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.REZAGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.DEVUELTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
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
	public Iterable<Documento> listarReporteBCP(Date fechaIni, Date fechaFin, Long idbuzon) throws ClientProtocolException, IOException, JSONException
	{
		Iterable<Documento> documentos = documentoDao.listarReporteBCP(fechaIni, fechaFin,idbuzon);
		List<Documento> documentosUbcp = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		
		if(documentosUbcp.size()==0) {
			return null;
		}
		
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
	public Iterable<Documento> listarDocumentosEntregados() throws ClientProtocolException, IOException, JSONException {
		Iterable<Documento> documentos = documentoDao.listarDocumentosEntregados();
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

	@Override
	public Iterable<Documento> listarReporteUTD(Date fechaIni, Date fechaFin)
			throws ClientProtocolException, IOException, JSONException {
		

		Iterable<Documento> documentos = documentoDao.listarReporteUTD(fechaIni, fechaFin);
		List<Documento> documentosUTD = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		List<Long> distritosIds = new ArrayList();
		List<Long> buzonIds = new ArrayList();
		
		for (Documento documento : documentosUTD) {
			distritosIds.add(documento.getDistritoId());
			buzonIds.add(documento.getEnvio().getBuzonId());
		}
		
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
		
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
		}
		return documentosUTD;
	}

	@Override
	public Documento listarDocumentoUTD(Long id) throws ClientProtocolException, IOException, JSONException {
		
		Documento documento = documentoDao.findById(id).orElse(null);
		
		if(documento==null) {
			return null;
		}
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarAll();
		Map<String, Object> buzones = buzonEdao.listarById(documento.getEnvio().getBuzonId().longValue());
		documento.getEnvio().setBuzon(buzones);
		for(int i=0;i < distritos.size();i++) {	
			Long distritoId= Long.valueOf(distritos.get(i).get("id").toString());
			if (documento.getDistritoId().longValue() == distritoId.longValue()) {
				documento.setDistrito(distritos.get(i));
				break;
			}
		}
		return documento;
	}
}
