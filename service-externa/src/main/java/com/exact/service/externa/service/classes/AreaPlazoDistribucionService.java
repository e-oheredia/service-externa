package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAreaPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IAreaEdao;
import com.exact.service.externa.entity.AreaPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IAreaPlazoDistribucionService;

@Service
public class AreaPlazoDistribucionService implements IAreaPlazoDistribucionService {
	@Autowired
	IAreaPlazoDistribucionDao areaPlazoDistribucionDao;
	
	@Autowired
	IAreaEdao areaEdao;
	
	@Override
	public AreaPlazoDistribucion listarById(Long id) {
		return areaPlazoDistribucionDao.getPlazoDistribucionIdByAreaId(id);
	}
	
	@Override
	public AreaPlazoDistribucion actualizar(AreaPlazoDistribucion areaPlazoDistribucion) {
		if (areaPlazoDistribucionDao.existsById(areaPlazoDistribucion.getAreaId())) {
			return areaPlazoDistribucionDao.save(areaPlazoDistribucion);
		}
		return null;
	}

	@Override
	public Iterable<AreaPlazoDistribucion> listarAreaPlazos() throws IOException, JSONException {
		List<Map<String, Object>> areas = (List<Map<String, Object>>) areaEdao.listarAll();
		Iterable<AreaPlazoDistribucion> areasplazoBD = areaPlazoDistribucionDao.findAll();
		List<AreaPlazoDistribucion> areasplazo = StreamSupport.stream(areasplazoBD.spliterator(), false).collect(Collectors.toList());
		for(AreaPlazoDistribucion areaplazo : areasplazo) {
			int i = 0; 
			while(i < areas.size()) {
				if (areaplazo.getAreaId() == Long.valueOf(areas.get(i).get("id").toString())) {
					areaplazo.setArea(areas.get(i));
					break;
				}
				i++;
			}
		}
		return areasplazo;
	}

}
