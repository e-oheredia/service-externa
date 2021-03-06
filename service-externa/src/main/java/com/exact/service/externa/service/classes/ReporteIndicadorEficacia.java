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
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IReporteIndicadorEficacia;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.NO_DISTRIBUIBLE;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.REZAGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.PENDIENTE_ENTREGA;


@Service
public class ReporteIndicadorEficacia implements IReporteIndicadorEficacia {

	@Autowired
	IDocumentoReporteDao reportedao;
	
	@Autowired
	IProveedorDao proveedordao;
	
	@Autowired
	IPlazoDistribucionDao plazodao;
	
	private static final Log Logger = LogFactory.getLog(DiaService.class);

	SimpleDateFormat dtcompleta = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dtaño = new SimpleDateFormat("yyyy");				

	@Override
	public Map<Integer, Map<Integer, Float>> indicadorgrafico(String fechaIni, String fechaFin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		SimpleDateFormat dtmeses = new SimpleDateFormat("MM");		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		Date dateI= null;
		Date dateF= null;

		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		
		
		int ultimodia=obtenerUltimoDiaMes(Integer.parseInt(dtmeses.format(dateI)),Integer.parseInt(dtaño.format(dateI)));
		Date newdatef = dtcompleta.parse(dt.format(dateF)+"-"+ultimodia);
		
		Map<Integer, Map<Integer, Float>>  multiMap = new HashMap<>();
		
		
		
		//Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		
		Iterable<DocumentoReporte> entidades = null;
		
		if( dt.format(dateI).equals(dt.format(dateF)) ) {
			entidades = reportedao.buscarvolumenporfechas2( Integer.parseInt(dtmeses.format(dateI)) ,Integer.parseInt(dtaño.format(dateI)));
		}else {
			entidades = reportedao.buscarvolumenporfechas(dateI,newdatef);

		}
		
		
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());		
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = this.getListaEntreFechas2(dateI,dateF);
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}

		int cantidadtotal = reportes.size();
		if(cantidadtotal==0) {
			return null;
		}
	    int i=0;
		for(String mesaño : listademeses) {
			int total=0;
			int entregados=0;
			float porcentaje=0;

			Map<Integer, Float> m = new HashMap<Integer, Float>();
			for (DocumentoReporte entidad : reportes) {
				if (dt.format(entidad.getFecha()).equals(mesaño)) {
					if(entidad.getEstadoDocumento()==ENTREGADO) {
					entregados++;	
					}
					total++;	
				}
			}
			
			if(total !=0) {
				Logger.info("entro");
				porcentaje=(float)entregados/total;
			}
			Logger.info(porcentaje);
			m.put( Integer.parseInt(dtmeses.format(dt.parse(mesaño))), (float)porcentaje);
			i++;
			multiMap.put(i, m);
		}
		 return multiMap;		
	}

	public int obtenerUltimoDiaMes(int anio, int mes) {
		Calendar calendario=Calendar.getInstance();
		calendario.set(anio, mes, 1);
		return calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
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
	public Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>> indicadortabla2(String fechaIni, String fechaFin)
			throws IOException, JSONException, NumberFormatException, ParseException {

		//Map<Integer,Map<Integer,  Map<Integer, Integer>>> remultiMap = new HashMap<>();
		Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>> remultiMap = new HashMap<>();

		SimpleDateFormat dtmeses = new SimpleDateFormat("MM");		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		List<String> listademeses = new ArrayList<>();
		
		List<Date> meses = this.getListaEntreFechas2(dateI,dateF);
		
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}
		
		
		List<Long> ids = new ArrayList<>();
		ids.add(PENDIENTE_ENTREGA);
		ids.add(ENTREGADO);
		ids.add(REZAGADO);
		ids.add(NO_DISTRIBUIBLE);
		
		int i= 0;
		Iterable<DocumentoReporte> entidades = null;
		
		int ultimodia=obtenerUltimoDiaMes(Integer.parseInt(dtmeses.format(dateI)),Integer.parseInt(dtaño.format(dateI)));
		Date newdatef = dtcompleta.parse(dt.format(dateF)+"-"+ultimodia);
		
		if( dt.format(dateI).equals(dt.format(dateF)) ) {
			entidades = reportedao.buscarvolumenporfechas2( Integer.parseInt(dtmeses.format(dateI)) ,Integer.parseInt(dtaño.format(dateI)));
		}else {
			entidades = reportedao.buscarvolumenporfechas(dateI,newdatef);

		}

		
		List<DocumentoReporte> reportes = new ArrayList<>();
		
		
		
		
		
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());
		
		if(reportes.size()==0) {
			return null;
		}
		
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false)
				.collect(Collectors.toList());
		for (Proveedor pro : proveedores) {
			Map<Integer,Map<Integer,Map<Integer, Integer>>> multiMap = new HashMap<>();
			
			for (Long entidad : ids ) {
				Map<Integer, Map<Integer, Integer>> mis = new HashMap<>();
				i= 0;
				for(String mesaño : listademeses) {
					Map<Integer, Integer> min = new HashMap<Integer, Integer>();
					int cantidadsede=0;
					for (DocumentoReporte documentoreporte : reportes) {

						if (dt.format(documentoreporte.getFecha()).equals(mesaño) && documentoreporte.getEstadoDocumento()==entidad && documentoreporte.getProveedorId()==pro.getId()) {
							cantidadsede++;	
						}
					}
					min.put( Integer.parseInt(dtmeses.format(dt.parse(mesaño))), cantidadsede);
					i++;
					mis.put(i, min);
				}
				
				multiMap.put((int) (long) entidad, mis);	
			}
			
			remultiMap.put((int) (long)pro.getId(), multiMap );
			
		}
		return remultiMap;
	}



	@Override
	public Map<Integer, Map<Integer, Map<Integer, Float>>> indicadortabla2cabecera(String fechaIni, String fechaFin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		SimpleDateFormat dtmeses = new SimpleDateFormat("MM");		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		Date dateI= null;
		Date dateF= null;

		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Map<Integer, Map<Integer, Map<Integer, Float>>>  remultiMap = new HashMap<>();
		int ultimodia=obtenerUltimoDiaMes(Integer.parseInt(dtmeses.format(dateI)),Integer.parseInt(dtaño.format(dateI)));
		Date newdatef = dtcompleta.parse(dt.format(dateF)+"-"+ultimodia);
		
		
		Iterable<DocumentoReporte> entidades = null;
		//Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		
		if( dt.format(dateI).equals(dt.format(dateF)) ) {
			entidades = reportedao.buscarvolumenporfechas2( Integer.parseInt(dtmeses.format(dateI)) ,Integer.parseInt(dtaño.format(dateI)));
		}else {
			entidades = reportedao.buscarvolumenporfechas(dateI,newdatef);

		}
		
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());		
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = this.getListaEntreFechas2(dateI,dateF);
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}

		int cantidadtotal = reportes.size();		
	    if(cantidadtotal==0) {
	    	return null;
	    }
		int i=0;
	    	    	
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false)
				.collect(Collectors.toList());
	    
	    
	    
//		for(String mesaño : listademeses) {
//
//			int total=0;
//			int entregados=0;
//			float porcentaje=0;
//
//			Map<Integer, Float> m = new HashMap<Integer, Float>();
//			for (DocumentoReporte entidad : reportes) {
//				
//				if (dt.format(entidad.getFecha()).equals(mesaño)) {
//					if(entidad.getEstadoDocumento()==ENTREGADO) {
//					entregados++;	
//					}
//					total++;	
//				}
//				
//			}
//			
//			if(total !=0) {
//				Logger.info("entro");
//				porcentaje=(float)entregados/total;
//			}
//			Logger.info(porcentaje);
//			m.put( Integer.parseInt(dtmeses.format(dt.parse(mesaño))), (float)porcentaje);
//			i++;
//			multiMap.put(i, m);
//		}
		
		for(Proveedor pro : proveedores) {
			Map<Integer, Map<Integer, Float>>  multiMap = new HashMap<>();
			for(String mesaño : listademeses) {

				int total=0;
				int entregados=0;
				float porcentaje=0;

				Map<Integer, Float> m = new HashMap<Integer, Float>();
				for (DocumentoReporte entidad : reportes) {
					
					if (dt.format(entidad.getFecha()).equals(mesaño) && entidad.getProveedorId()==pro.getId()) {
						if(entidad.getEstadoDocumento()==ENTREGADO) {
						entregados++;	
						}
						total++;	
					}
					
				}
				
				if(total !=0) {
					Logger.info("entro");
					porcentaje=(float)entregados/total;
				}
				Logger.info(porcentaje);
				m.put( Integer.parseInt(dtmeses.format(dt.parse(mesaño))), (float)porcentaje);
				i++;
				multiMap.put(i, m);
			}
			
			
			remultiMap.put((int)(long)pro.getId(), multiMap);
		
		}
		
		
		
		 return remultiMap;		
	
	}


}
