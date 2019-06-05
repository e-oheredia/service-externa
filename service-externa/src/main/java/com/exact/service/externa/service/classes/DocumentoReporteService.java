package com.exact.service.externa.service.classes;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoGuiaDao;
import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IEnvioDao;
import com.exact.service.externa.dao.IGuiaDao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.service.interfaces.IDocumentoReporteService;
import com.exact.service.externa.service.interfaces.IGuiaService;

import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.FALTA_ENTREGA;
import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.DENTRO_PLAZO;
import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.FUERA_PLAZO;
import static com.exact.service.externa.enumerator.EstadoCargoEnum.PENDIENTE;
import static com.exact.service.externa.enumerator.EstadoCargoEnum.DEVUELTO;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.exact.service.externa.enumerator.EstadoCargoEnum.NO_GENERADO;


@Service
public class DocumentoReporteService implements IDocumentoReporteService{

	@Autowired
	IDocumentoReporteDao documentoreporteDao;
	
	@Autowired
	IGuiaService guiaservice;
	
	@Autowired
	IGuiaDao guiadao;
	
	@Autowired
	IDocumentoGuiaDao documentoGuiadao;
	
	@Autowired
	IBuzonEdao buzonEdao;
	
	@Override
	@SuppressWarnings("unchecked")
	public void insertarDocumentosReporte(Guia guia) throws IOException, JSONException {
		
		for (DocumentoGuia dg : guia.getDocumentosGuia()) {
		
			Documento documentoBD = dg.getDocumento() ;
			DocumentoReporte documentoReporte = new DocumentoReporte();
			Map<String, Object> buzon = buzonEdao.listarById(documentoBD.getEnvio().getBuzonId());
			Map<String, Object> area = (Map<String, Object>) buzon.get("area");
			documentoReporte.setProveedorId(guia.getProveedor().getId());
			documentoReporte.setEstadoDocumento(documentoBD.getUltimoSeguimientoDocumento().getEstadoDocumento().getId());
			documentoReporte.setPlazoId(guia.getPlazoDistribucion().getId());
			documentoReporte.setFecha(guia.getUltimoSeguimientoGuia().getFecha());
			documentoReporte.setDocumentoId(documentoBD.getId());
			documentoReporte.setTiempoEntrega(FALTA_ENTREGA);
			documentoReporte.setEstadoCargo(NO_GENERADO);
			documentoReporte.setSedeId(guia.getSedeId());
			documentoReporte.setArea(area.get("nombre").toString());
			documentoreporteDao.save(documentoReporte);
		}
	}

	@Override
	public void actualizarDocumentosPorResultado(List<Documento> lstdocumento, List<Long> guiaIds) throws ClientProtocolException, IOException, JSONException, URISyntaxException, ParseException {
		
		Map<Long, Date> fechaGuias = new HashMap<Long, Date>();
		for(int i=0;i<guiaIds.size();i++) {
			Guia guia = guiadao.findById(guiaIds.get(i)).get();
			Date fechaLimite = guiaservice.getFechaLimite(guia);
			fechaGuias.put(guiaIds.get(i), fechaLimite);
		}
		for(Documento documento: lstdocumento) {
			DocumentoGuia dg = documentoGuiadao.findByDocumentoId(documento.getId());
			Date fechalimitereporte = fechaGuias.get(dg.getGuia().getId());
			DocumentoReporte documentoreporte = documentoreporteDao.findByDocumentoId(documento.getId());
			SeguimientoDocumento sg = documento.getUltimoSeguimientoDocumento();
			if(sg.getFecha().compareTo(fechalimitereporte)>0) {
				documentoreporte.setTiempoEntrega(FUERA_PLAZO);
			}else {
				documentoreporte.setTiempoEntrega(DENTRO_PLAZO);
			}
			documentoreporte.setEstadoCargo(PENDIENTE);
			documentoreporte.setEstadoDocumento(sg.getEstadoDocumento().getId());
			documentoreporteDao.save(documentoreporte);
		}
	}

	@Override
	public void actualizarDocumentosRecepcionados(Long documentoId) {
		DocumentoReporte documentoreporte = documentoreporteDao.findByDocumentoId(documentoId);
		documentoreporte.setEstadoCargo(DEVUELTO);
		documentoreporteDao.save(documentoreporte);
	}

}
