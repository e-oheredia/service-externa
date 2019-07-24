package com.exact.service.externa.service.classes;

import java.io.IOException;
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
import com.exact.service.externa.edao.interfaces.ISedeEdao;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IPlazoDistribucionService;
import com.exact.service.externa.service.interfaces.IRegionService;
import com.exact.service.externa.service.interfaces.IReporteVolumenService;

@Service
public class ReporteVolumenService implements IReporteVolumenService {

	@Autowired
	IDocumentoReporteDao reportedao;

	@Autowired
	IProveedorDao proveedordao;

	@Autowired	
	ISedeEdao sedeEdao;
	
	
	@Autowired
	IPlazoDistribucionDao plazodao;
	
	
	@Autowired
	IPlazoDistribucionService plazoservice;
	
	@Autowired
	IRegionService regionservice;
	
	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dtmes = new SimpleDateFormat("yyyy-MM");
	
	private static final Log Logger = LogFactory.getLog(ReporteVolumenService.class);

	
	@Override
	public Map<Long,Map<String, Float>> volumenbycurier(String fechaIni,String fechaFin) throws IOException, JSONException {
		Map<Long,Map<String, Float>> multiMap = new HashMap<>();

		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		

		Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);

		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());
		int cantidadtotal = reportes.size();
		if(cantidadtotal==0) {
			Logger.info("ENTRO");
			return null;
		}
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false)
				.collect(Collectors.toList());
		
		for (Proveedor pro : proveedores) {
			int cantidadproveedor=0;
			 		
			Map<String, Float> m = new HashMap<String, Float>();
			
			for (DocumentoReporte entidad : reportes) {
				if (entidad.getProveedorId() == pro.getId()) {
					cantidadproveedor++;	
				}
			}
			float porcentaje=(float)cantidadproveedor/cantidadtotal;
			m.put("cantidad", (float) cantidadproveedor);
			m.put("porcentaje", (float)porcentaje*100);
			multiMap.put(pro.getId(),m );
		}
		return multiMap;
	}

	@Override
	public Map<Integer,Map<String, Float>> volumenbyutd(String fechaIni, String fechaFin)
			throws IOException, JSONException {
		Map<Integer,Map<String, Float>> multiMap = new HashMap<>();

		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		
		Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());
		int cantidadtotal = reportes.size();		
		Iterable<Map<String, Object>> sedes = sedeEdao.listarSedesDespacho();
		
		for (Map<String, Object> sede : sedes) {
			int cantidadsede=0;
			 		
			Map<String, Float> m = new HashMap<String, Float>();
			for (DocumentoReporte entidad : reportes) {
				if (entidad.getSedeId()  == Integer.parseInt(sede.get("id").toString())) {
					cantidadsede++;	
				}
			}
			float porcentaje=(float)cantidadsede/cantidadtotal;
			m.put("cantidad", (float) cantidadsede);
			m.put("porcentaje", (float)porcentaje*100);
			multiMap.put(Integer.parseInt(sede.get("id").toString())  , m );
		}
		
		
		
		return multiMap;
	}

	@Override
	public Map<Integer,Object> volumenbyplazo(String fechaIni, String fechaFin)
			throws IOException, JSONException {
		Map<Integer,Object > multiMap = new HashMap<>();

		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		
		
		Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false)
				.collect(Collectors.toList());
		
		
		for (Proveedor pro : proveedores) {
			
			Iterable<Map<String, Object>> regiones = regionservice.RegionesbyProveedor(pro.getId());
			//Iterable<PlazoDistribucion> pd = plazoservice.listarPlazosByProveedor(pro);

			//////////////
			//Iterable<PlazoDistribucion> pd =plazodao.listarplazosactivos(pro.getId());
			//////////////
			Map<Integer, Map<Integer, Integer>> re = new HashMap<Integer, Map<Integer, Integer>>();

			for(Map<String, Object> region : regiones) {
				Map<Integer, Integer> m = new HashMap<Integer, Integer>();
				Iterable<PlazoDistribucion> pds = plazoservice.listarPlazosByRegionId( Long.valueOf(region.get("id").toString())  );
				for (PlazoDistribucion entidad : pds) {
					int cantidadproveedor=0;
					for (DocumentoReporte dr : reportes) {
						
						if (entidad.getId() == dr.getPlazoId() && pro.getId()==dr.getProveedorId() && dr.getRegionId() == Long.valueOf(region.get("id").toString())   ) {
							cantidadproveedor++;
						}
							
					}
						///poner los plazos segun proveedor
						m.put((int) (long)entidad.getId() , cantidadproveedor);

				}
				
				re.put((int) (long)Long.valueOf(region.get("id").toString()), m);
			}
			multiMap.put((int) (long)pro.getId(), re );			

		}
		
		
		
		return multiMap;
		
	}

	@Override
	public int validardia(String fechaini, String fechainifin,int rango) {
		
		if(fechaini=="" || fechainifin==""){
			return 1;
		}
		
		Date dateI= null;
		Date dateF= null;
		
		if(rango==1) {
			try {
				dateI = dt.parse(fechaini);
				dateF = dt.parse(fechainifin); 
			} catch (Exception e) {
				return 2;
			}
		}else {
			try {
				dateI = dtmes.parse(fechaini);
				dateF = dtmes.parse(fechainifin); 
			} catch (Exception e) {
				return 2;
			}
		}

		if(dateI.compareTo(dateF)>0){
			return 3;
		}	
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateI); 
		calendar.add(Calendar.YEAR, 1);  
		calendar.getTime();
		Date iniciomodificado=calendar.getTime();
		
		if(iniciomodificado.compareTo(dateF)<0){
			return 4;
		}
			
		return 0;
	}

	
}
