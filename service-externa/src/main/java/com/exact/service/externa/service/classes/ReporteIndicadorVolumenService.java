package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;

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
import com.exact.service.externa.service.interfaces.IReporteIndicadorVolumenService;

@Service
public class ReporteIndicadorVolumenService implements IReporteIndicadorVolumenService{

	@Autowired
	IDocumentoReporteDao reportedao;
	
	@Autowired
	IProveedorDao proveedordao;
	
	@Autowired
	IPlazoDistribucionDao plazodao;
	
	
	@Override
	public Map<Integer,Map<Integer, Integer>>  IndicadorVolumenGrafico(String fechaIni, String fechaFin)
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
		
		Map<Integer,Map<Integer, Integer>> multiMap = new HashMap<>();
		
		
		if( dtmeses.format(dateI).equals(dtmeses.format(dateF)) ) {
			
		}
		
		Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		
		
		
		
		
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());		
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = this.getListaEntreFechas2(dateI,dateF);
		

		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}

		Map<Integer, Integer> ms = new HashMap<Integer, Integer>();
		int cantidadtotal = reportes.size();		
	    int i=0;
		for(String mesaño : listademeses) {
			int cantidadsede=0;
			Map<Integer, Integer> m = new HashMap<Integer, Integer>();
			for (DocumentoReporte entidad : reportes) {
				if (dt.format(entidad.getFecha()).equals(mesaño)) {
					cantidadsede++;	
				}
			}
			m.put( Integer.parseInt(dtmeses.format(dt.parse(mesaño))), cantidadsede);
			i++;
			multiMap.put(i, m);
		}
	
		 return multiMap;
	}
//	map<long,map<string, float>> multimap = new hashmap<>();
//	iterable<documentoreporte> entidades = reportedao.buscarvolumenporfechas(datei,datef);
//	list<documentoreporte> reportes = new arraylist<>();
//	reportes = streamsupport.stream(entidades.spliterator(), false).collect(collectors.tolist());
//	int cantidadtotal = reportes.size();		
//	iterable<proveedor> iterableproveedores = proveedordao.findall();
//	iterable<map<string, object>> sedes = sedeedao.listarsedesdespacho();
//	
//	for (map<string, object> sede : sedes) {
//		int cantidadsede=0;
//		 		
//		map<string, float> m = new hashmap<string, float>();
//		for (documentoreporte entidad : reportes) {
//			if (entidad.getsedeid()  == sede.get("id") ) {
//				cantidadsede++;	
//			}
//		}
//		float porcentaje=(float)cantidadsede/cantidadtotal;
//		m.put("cantidad", (float) cantidadsede);
//		m.put("porcentaje", (float)porcentaje*100);
//		multimap.put((long) sede.get("id") , m );
//	}
//	
//	
	
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
	public Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>>  IndicadorVolumenTabla2(String fechaIni, String fechaFin)
		throws IOException, JSONException, NumberFormatException, ParseException {
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
		int i= 0;

		
		Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false)
				.collect(Collectors.toList());
		//Iterable<PlazoDistribucion> pd =plazodao.listarplazosactivos(pro.getId());
		Iterable<PlazoDistribucion> pd =plazodao.findAll();
		
		
		for (Proveedor pro : proveedores) {
			Map<Integer,Map<Integer,Map<Integer, Integer>>> multiMap = new HashMap<>();
			for (PlazoDistribucion entidad : pd ) {
				i= 0;
				Map<Integer, Map<Integer, Integer>> mis = new HashMap<>();
				for(String mesaño : listademeses) {
					Map<Integer, Integer> min = new HashMap<Integer, Integer>();

					int cantidadsede=0;
					for (DocumentoReporte documentoreporte : reportes) {
						if (dt.format(documentoreporte.getFecha()).equals(mesaño) && documentoreporte.getPlazoId()==entidad.getId() && documentoreporte.getProveedorId()==pro.getId()) {
							cantidadsede++;	
						}
					}
					min.put( Integer.parseInt(dtmeses.format(dt.parse(mesaño))), cantidadsede);
					i++;
					mis.put(i, min);

				}
				
				multiMap.put((int) (long) entidad.getId(), mis);	
			}
			
			remultiMap.put((int) (long)pro.getId(), multiMap );
			
		}
		
		
		return remultiMap;
	}
	private static final Log Logger = LogFactory.getLog(DiaService.class);

	@Override
	public Map<Integer, Map<Integer, Map<Integer, Integer>>> indicadortabla2cabeceravolumen(String fechaIni,
			String fechaFin) throws IOException, JSONException, NumberFormatException, ParseException {

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
		int i=0;
		Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());		
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = this.getListaEntreFechas2(dateI,dateF);
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false)
				.collect(Collectors.toList());
		Map<Integer, Map<Integer, Map<Integer, Integer>>>  remultiMap = new HashMap<>();
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}
		for(Proveedor pro : proveedores) {
			Map<Integer, Map<Integer, Integer>>  multiMap = new HashMap<>();
			
			for(String mesaño : listademeses) {
				int entregados=0;

				Map<Integer, Integer> m = new HashMap<Integer, Integer>();
				for (DocumentoReporte entidad : reportes) {
					if (dt.format(entidad.getFecha()).equals(mesaño) && entidad.getProveedorId()==pro.getId()) {
						entregados++;	
					}
				}
				Logger.info(entregados);
				m.put( Integer.parseInt(dtmeses.format(dt.parse(mesaño))), entregados);
				i++;
				multiMap.put(i, m);
			}
			
			
			remultiMap.put((int)(long)pro.getId(), multiMap);
		
		}
	
		 return remultiMap;
		
	}

}
