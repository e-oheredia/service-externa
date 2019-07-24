package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IPlazoDistribucionService;
import com.exact.service.externa.service.interfaces.IReporteIndicadorEficienciaService;

import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.DENTRO_PLAZO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;

@Service
public class ReporteIndicadorEficienciaService implements IReporteIndicadorEficienciaService {

	@Autowired
	IDocumentoReporteDao documentoreporteDao;
	
	@Autowired
	IProveedorDao proveedorDao;
	
	@Autowired
	IPlazoDistribucionService plazoservice;
	

	
	@Override
	public Map<Integer, Map<Integer, Float>> graficoTablaPorcentaje(String fechaIni, String fechaFin)
			throws IOException, NumberFormatException, ParseException {
		SimpleDateFormat dtmeses = new SimpleDateFormat("MM");		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dtdia = new SimpleDateFormat("yyyy-MM-dd");

		Date dateI= null;
		Date dateF= null;

		try {
			dateI = dtdia.parse(fechaIni);
			dateF = dtdia.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		List<DocumentoReporte> reportes = new ArrayList<>();
		Iterable<DocumentoReporte> documentos = documentoreporteDao.buscarvolumenporfechas3(dateI,dateF);
		reportes = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(reportes.size()==0) {
			return null;

		}
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = getListaEntreFechas2(dateI,dateF);
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}
		int i=0;
		Map<Integer, Map<Integer, Float>> indicesMeses = new HashMap<>();
		for(String mesaño : listademeses) {
			int cantidadTotal =0;
			int totalmes = 0;
			float porcentaje =(float)0.0;
			Map<Integer,Float> mesesTotal = new HashMap<>();
			for(DocumentoReporte documentoreporte : documentos) {
				if(dt.format(documentoreporte.getFecha()).equals(mesaño)) {
					if(documentoreporte.getEstadoDocumento()==ENTREGADO) {
						if(documentoreporte.getTiempoEntrega()==DENTRO_PLAZO) {
							cantidadTotal++;
						}
					}
					totalmes++;
				}
			}
			i++;
			if(totalmes!=0) {
				porcentaje = ((float) cantidadTotal / totalmes )* 100;
				
			}else {
				porcentaje = 0;
			}
			mesesTotal.put(Integer.parseInt(dtmeses.format(dt.parse(mesaño))), porcentaje);
			indicesMeses.put(i, mesesTotal);
			
		}
		return indicesMeses;
	}
	
	public List<Date> getListaEntreFechas2(Date fechaInicio, Date fechaFin) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicio);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);
		java.util.List<Date> listaFechas = new java.util.ArrayList<Date>();

		while (!c1.after(c2)) {
			listaFechas.add(c1.getTime());
			c1.add(Calendar.MONTH , 1);
		}
		return listaFechas;
	}

	@Override
	public Map<Long, Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>>> proveedorPlazoDentroFuera(String fechaini, String fechafin) throws IOException, NumberFormatException, ParseException, JSONException {
		SimpleDateFormat dtmeses = new SimpleDateFormat("MM");		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dtdia = new SimpleDateFormat("yyyy-MM-dd");

		Date dateI= null;
		Date dateF= null;

		try {
			dateI = dtdia.parse(fechaini);
			dateF = dtdia.parse(fechafin); 
		} catch (Exception e) {
			return null;
		}
		Iterable<Proveedor> proveedores = proveedorDao.findAll();
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = getListaEntreFechas2(dateI,dateF);
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}
		List<DocumentoReporte> reportes = new ArrayList<>();
		Iterable<DocumentoReporte> documentos = documentoreporteDao.buscarvolumenporfechas3(dateI,dateF);
		reportes = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(reportes.size()==0) {
			return null;
		}
		Map<Long, Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>>> cantidades = new HashMap<>();
		
		for(Proveedor proveedor : proveedores ) {

			Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>> plazoCantidad = new HashMap<>();
			for(PlazoDistribucion plazo : plazoservice.listarPlazosByProveedor(proveedor) ) {
				Map<Integer, Map<Integer, Map<String, Integer>>> mesesIndex = new HashMap<>();
				int i=0;
				for(String mesaño : listademeses) {
					int cantidadDentroPlazo = 0;
					int cantidadFueraPlazo = 0;
					Map<String, Integer> cantidadDentroFuera = new HashMap<>();
					Map<Integer, Map<String, Integer>> cantidadPorMes = new HashMap<>();
					for(DocumentoReporte documentoreporte : reportes) {
						if(proveedor.getId()==documentoreporte.getProveedorId() && plazo.getId()==documentoreporte.getPlazoId()) {
								if(dt.format(documentoreporte.getFecha()).equals(mesaño)) {
									//if(documentoreporte.getEstadoDocumento() ==ENTREGADO) {
											if(documentoreporte.getTiempoEntrega()==DENTRO_PLAZO) {
												cantidadDentroPlazo++;
											}else {
												cantidadFueraPlazo++;
											}
										//}
								}
						}
					}
					i++;
					cantidadDentroFuera.put("dentroplazo", cantidadDentroPlazo);
					cantidadDentroFuera.put("fueraplazo", cantidadFueraPlazo);
					cantidadPorMes.put(Integer.parseInt(dtmeses.format(dt.parse(mesaño))), cantidadDentroFuera);
					mesesIndex.put(i, cantidadPorMes);
				}
				plazoCantidad.put(plazo.getId(), mesesIndex);
				cantidades.put(proveedor.getId(), plazoCantidad);
			}
			
		}
		return cantidades;
	}

}
