package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.SeguimientoDocumento;
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
	@Transactional
	public int cargarResultados(List<Documento> documentosPorCargarResuladoList) throws ClientProtocolException, IOException, JSONException {	
		
		List<Long> documentosId = new ArrayList();		
		
		for(Documento documento : documentosPorCargarResuladoList) {
			documentosId.add(documento.getEnvio().getBuzonId());
		}
		
		List<Documento> documentosPorCompararList = StreamSupport.stream(documentoDao.findAllById(documentosId).spliterator(), false).collect(Collectors.toList());	 
		
		if (documentosPorCompararList.size()==0) {
			return 0;
		}
		
		if (documentosPorCompararList.size() != documentosPorCargarResuladoList.size()) {
			return 2;
		}
		
				
		//List<Documento> documentosCorrectosList = documentosPorCompararList.removeIf(x => x.id = 0);
				
		
		return 1;
	}
	
}
