package com.exact.service.externa.service.classes;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IDocumentoGuiaDao;
import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IGuiaDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.service.interfaces.IGuiaService;
import com.exact.service.externa.service.interfaces.IReporteEficienciaService;

import io.jsonwebtoken.io.IOException;

import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.DENTRO_PLAZO;
import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.FUERA_PLAZO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;

@Service
public class ReporteEficienciaService implements IReporteEficienciaService {

	@Autowired
	IDocumentoReporteDao documentoReporteDao;
	
	@Autowired
	IProveedorDao proveedorDao;
	
	@Autowired
	IPlazoDistribucionDao plazoDao;
	
	@Autowired
	IDocumentoGuiaDao documentoguiaDao;
	
	@Autowired
	IGuiaService guiaService;
	
	@Autowired
	IGuiaDao guiaDao;
	
	@Autowired
	IDocumentoDao documentoDao;
	
	@Override
	public Map<Long, Map<String, Integer>> eficienciaPorCourier(String fechaIni, String fechaFin) throws IOException, JSONException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		Map<Long, Map<String, Integer>> total = new HashMap<>();
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Iterable<DocumentoReporte> documentosPlazo = documentoReporteDao.findDocumentosByDentroFueraPlazo(dateI,dateF);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentosPlazo.spliterator(), false).collect(Collectors.toList());
		Iterable<Proveedor> proveedores =  proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedores.spliterator(), false).collect(Collectors.toList());
		for(Proveedor proveedor: proveedoreslst) {
			Map<String, Integer> cantidades = new HashMap<>();
			int cantDentroPlazo = 0;
			int cantFueraPlazo = 0;
			for(DocumentoReporte documentoreporte : documentoslst) {
				if(proveedor.getId()==documentoreporte.getProveedorId()) {
					if(documentoreporte.getTiempoEntrega()==DENTRO_PLAZO) {
						cantDentroPlazo++;
					}else{
						cantFueraPlazo++;
					}
				}
			}
			cantidades.put("dentroplazo", cantDentroPlazo);
			cantidades.put("fueraplazo", cantFueraPlazo);
			total.put(proveedor.getId(), cantidades);
		}
		return total;
	}

	@Override
	public Map<Long, Map<Long, Map<String, Integer>>> eficienciaPorPlazoPorCourier(String fechaIni, String fechaFin) throws IOException, JSONException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Map<Long, Map<Long, Map<String, Integer>>> proveedorcantidad = new HashMap<>();
		Iterable<DocumentoReporte> documentosPlazo = documentoReporteDao.findDocumentosByDentroFueraPlazo(dateI,dateF);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentosPlazo.spliterator(), false).collect(Collectors.toList());
		Iterable<Proveedor> proveedores =  proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedores.spliterator(), false).collect(Collectors.toList());
		for(Proveedor proveedor: proveedoreslst) {
			Map<Long, Map<String, Integer>> cantidadPlazo = new HashMap<>();
			for(PlazoDistribucion plazo : proveedor.getPlazosDistribucion() ) {
					Map<String, Integer> cantidadDentroFuera = new HashMap<>();
					int cantidaddentroplazo=0;
					int cantidadfueraplazo=0;
					for(DocumentoReporte documentoreporte: documentoslst) {
						if(proveedor.getId()==documentoreporte.getProveedorId()) {
							if(plazo.getId()==documentoreporte.getPlazoId()) {
								if(documentoreporte.getTiempoEntrega()==DENTRO_PLAZO) {
									cantidaddentroplazo++;
									
								}else {
									cantidadfueraplazo++;
								}
							}
						}
					}
					cantidadDentroFuera.put("dentroplazo",cantidaddentroplazo);
					cantidadDentroFuera.put("fueraplazo",cantidadfueraplazo);
					cantidadPlazo.put(plazo.getId(),cantidadDentroFuera);
			}
			proveedorcantidad.put(proveedor.getId(), cantidadPlazo);
		}
		
		return proveedorcantidad;
		
	}

	@Override
	public Map<Long, Map<Long, Map<Long, Integer>>> detalleEficienciaPorCourier(String fechaIni, String fechaFin,Long proveedorId) throws IOException, JSONException, ClientProtocolException, java.io.IOException, URISyntaxException, ParseException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Map<Long, Map<Long, Map<Long, Integer>>> cantidadDetalle = new HashMap<>();
		
		Iterable<DocumentoReporte> documentos = documentoReporteDao.findDocumentosByProveedorId(dateI, dateF,proveedorId);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		Proveedor proveedor = proveedorDao.findById(proveedorId).orElse(null);
		for(PlazoDistribucion plazo : proveedor.getPlazosDistribucion()) {
			Map<Long, Integer> cantidadPorHoras = new HashMap<>();
			Map<Long, Map<Long, Integer>> cantidadPlazos = new HashMap<>();
			int cantidadPlazo =0;
			for(DocumentoReporte documentoreporte : documentoslst ) {
				Documento documento = documentoDao.findById(documentoreporte.getDocumentoId()).orElse(null);
				SeguimientoDocumento seguimientoDocumento = documento.getSeguimientoDocumentoByEstadoId(ENTREGADO); 
				if(plazo.getId() == documentoreporte.getPlazoId()) {
					DocumentoGuia dc = documentoguiaDao.findByDocumentoId(documentoreporte.getDocumentoId());
					Guia guia = guiaDao.findById(dc.getGuia().getId()).orElse(null);
					Date fechaLimite = guiaService.getFechaLimite(guia);
					Calendar fechaEntrega = Calendar.getInstance();
					fechaEntrega.setTime(seguimientoDocumento.getFecha());
				}
				//faltaprobar
			}
		}
		return cantidadDetalle;
	}

}
