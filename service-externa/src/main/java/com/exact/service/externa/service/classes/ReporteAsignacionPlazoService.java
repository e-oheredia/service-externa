package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAreaPlazoDistribucionDao;
import com.exact.service.externa.dao.IBuzonPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IAreaEdao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.entity.AreaPlazoDistribucion;
import com.exact.service.externa.entity.BuzonPlazoDistribucion;
import com.exact.service.externa.entity.ReporteAsignacionPlazo;
import com.exact.service.externa.service.interfaces.IReporteAsignacionPlazoService;

import static com.exact.service.externa.enumerator.TipoAsignacionEnum.AREA;
import static com.exact.service.externa.enumerator.TipoAsignacionEnum.BUZON;

@Service
public class ReporteAsignacionPlazoService implements IReporteAsignacionPlazoService{

	@Autowired
	IBuzonPlazoDistribucionDao buzonPlazoDao;
	
	@Autowired
	IAreaPlazoDistribucionDao areaPlazoDao;
	
	@Autowired
	IBuzonEdao buzonEdao;
	
	@Autowired
	IAreaEdao areaEdao;
	
	
	@Override
	public Iterable<ReporteAsignacionPlazo> listarReportes(String fechaIni, String fechaFin) throws IOException, Exception{
		List<ReporteAsignacionPlazo> reportes = new ArrayList<>();
		ReporteAsignacionPlazo repor = new ReporteAsignacionPlazo();
		int id=1;
	//	Map<String,Object> prueba = new HashMap<>();
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Iterable<AreaPlazoDistribucion> areaplazos = areaPlazoDao.listarPorFechasAreaPlazo(dateI, dateF);
		Iterable<BuzonPlazoDistribucion> buzonplazos = buzonPlazoDao.listarPorFechasBuzonPlazo(dateI, dateF);
//		List<AreaPlazoDistribucion> areaslst = StreamSupport.stream(areaplazos.spliterator(), false).collect(Collectors.toList());
//		List<BuzonPlazoDistribucion> buzoneslst = StreamSupport.stream(buzonplazos.spliterator(), false).collect(Collectors.toList());
//		List<Long> buzonIds = new ArrayList();
//		List<Long> areaIds = new ArrayList();
//		for (int i = 0; i < buzoneslst.size(); i++) {
//			buzonIds.add(Long.valueOf(buzoneslst.get(i).getBuzonId()));
//		}
//		for (int i = 0; i < areaslst.size(); i++) {
//			areaIds.add(Long.valueOf(areaslst.get(i).getAreaId()));
//		}
//		List<Map<String, Object>> buzonesls = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
//		List<Map<String, Object>> areas = (List<Map<String, Object>>) areaEdao.listarByIds(areaIds);
//		for(AreaPlazoDistribucion area: areaplazos) {
//			int i =0;
//			while(i<areas.size()) {
//				if(area.getAreaId()==Long.valueOf(areas.get(i).get("id").toString())) {
//					area.setArea(areas.get(i));
//					break;
//				}
//				i++;
//			}
//		}
//		
//		for(BuzonPlazoDistribucion buzon : buzonplazos) {
//			int k =0;
//			while(k<buzonesls.size()) {
//				if(buzon.getBuzonId()==Long.valueOf(buzonesls.get(k).get("id").toString())) {
//					buzon.setBuzon(buzonesls.get(k));
//					break;
//				}
//				k++;
//		}
//	}
//			prueba.put("area", areaplazos);
//			prueba.put("buzon",buzonplazos);
			
		if(areaplazos!=null) {
			List<AreaPlazoDistribucion> areaslst = StreamSupport.stream(areaplazos.spliterator(), false)
					.collect(Collectors.toList());
			List<Long> areaIds = new ArrayList();
			for (int i = 0; i < areaslst.size(); i++) {
				areaIds.add(Long.valueOf(areaslst.get(i).getAreaId()));
			}
			List<Map<String, Object>> areas = (List<Map<String, Object>>) areaEdao.listarByIds(areaIds);
			for(AreaPlazoDistribucion area: areaplazos) {
				int i =0;
				while(i<areas.size()) {
					if(area.getAreaId()==Long.valueOf(areas.get(i).get("id").toString())) {
						area.setArea(areas.get(i));
						break;
					}
					i++;
				}
				ReporteAsignacionPlazo reporte = new ReporteAsignacionPlazo();
				reporte.setId(id);
				reporte.setTipoAsignacion("Área");
				reporte.setAreaPlazoDistribucion(area.getAreaId());
				reporte.setFecha(area.getFechaAsociacion());
				reporte.setAreaBuzon(area.getArea().get("nombre").toString());
				reporte.setPlazoActual(area.getPlazoDistribucion().getNombre());
				reporte.setRutaAutorizacion(area.getRutaAutorizacion());
				reportes.add(reporte);
				id++;
			}
		}
		
		if(buzonplazos!=null) {
			List<BuzonPlazoDistribucion> buzoneslst = StreamSupport.stream(buzonplazos.spliterator(), false).collect(Collectors.toList());
			List<Long> buzonIds = new ArrayList();
			for (int i = 0; i < buzoneslst.size(); i++) {
				buzonIds.add(Long.valueOf(buzoneslst.get(i).getBuzonId()));
			}
			List<Map<String, Object>> buzonesls = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
			for(BuzonPlazoDistribucion buzon : buzonplazos) {
				int k =0;
				while(k<buzonesls.size()) {
					if(buzon.getBuzonId()==Long.valueOf(buzonesls.get(k).get("id").toString())) {
						buzon.setBuzon(buzonesls.get(k));
						break;
					}
					k++;
				}
				ReporteAsignacionPlazo reporte = new ReporteAsignacionPlazo();
				reporte.setId(id);
				reporte.setTipoAsignacion("Buzon");
				reporte.setBuzonPlazoDistribucion(buzon.getBuzonId());
				reporte.setFecha(buzon.getFechaAsociacion());
				reporte.setAreaBuzon(buzon.getBuzon().get("nombre").toString());
				reporte.setPlazoActual(buzon.getPlazoDistribucion().getNombre());
				reporte.setRutaAutorizacion(buzon.getRutaAutorizacion());
				reportes.add(reporte);
				id++;
			}
		}
		
//		for(int j=1;j<reportes.size();j++) {
//			repor.setId(j);
//			reportes.add
//		}
		
		return reportes;
	}

	
}
	
