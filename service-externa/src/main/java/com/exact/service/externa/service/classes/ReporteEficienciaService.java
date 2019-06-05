package com.exact.service.externa.service.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IReporteEficienciaService;

import io.jsonwebtoken.io.IOException;

import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.DENTRO_PLAZO;
import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.FUERA_PLAZO;

@Service
public class ReporteEficienciaService implements IReporteEficienciaService {

	@Autowired
	IDocumentoReporteDao documentoReporteDao;
	
	@Autowired
	IProveedorDao proveedorDao;
	
	@Autowired
	IPlazoDistribucionDao plazoDao;
	
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
	public Map<Long, Map<String, Map<Long, Integer>>> eficienciaPorPlazoPorCourier(String fechaIni, String fechaFin) throws IOException, JSONException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		Map<Long, Map<String, Map<Long, Integer>>> proveedorcantidad = new HashMap<>();
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
			Map<Long, Integer> plazofuera = new HashMap<>();
			Map<Long, Integer> plazodentro = new HashMap<>();
			Map<String, Map<Long, Integer>> dentrofueraplazo = new HashMap<>();
			
			for(PlazoDistribucion plazo : proveedor.getPlazosDistribucion() ) {
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
					plazodentro.put(plazo.getId(), cantidaddentroplazo);
					plazofuera.put(plazo.getId(), cantidadfueraplazo);
					dentrofueraplazo.put("dentroplazo", plazodentro);
					dentrofueraplazo.put("fueraplazo", plazofuera);
				
			}
			
			
			
			proveedorcantidad.put(proveedor.getId(), dentrofueraplazo);
		}
		
		return proveedorcantidad;
		
	}

}
