package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAreaPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IAreaEdao;
import com.exact.service.externa.entity.AreaPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IAreaService;

@Service
public class AreaService implements IAreaService{

	@Autowired
	IAreaEdao areaEdao;
	
	@Autowired
	IAreaPlazoDistribucionDao areaPlazoDao;
	
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException{
		Iterable<Map<String, Object>> areasBD = areaEdao.listarAll();
		List<Map<String, Object>> areas = StreamSupport.stream(areasBD.spliterator(), false)
				.collect(Collectors.toList());
		List<Long> areaIds = new ArrayList<>();
		for (int i = 0; i < areas.size(); i++) {
			areaIds.add(Long.valueOf(areas.get(i).get("id").toString()));
		}
		
		Iterable<AreaPlazoDistribucion> areaPlazoDistribucion = areaPlazoDao.findAllByAreaIdIn(areaIds);
		List<AreaPlazoDistribucion> areaPlazolst = StreamSupport.stream(areaPlazoDistribucion.spliterator(), false)
				.collect(Collectors.toList());
		areas.forEach(area -> {
			List<AreaPlazoDistribucion> areasEncontradas =  areaPlazolst.stream()
					.filter(x -> x.getAreaId().longValue() == Long.valueOf(area.get("id").toString())).collect(Collectors.toList());
			if (!areasEncontradas.isEmpty()) {
				area.put("plazoDistribucionPermitido", areasEncontradas.get(0).getPlazoDistribucion());
			}
		});
		int j=0;
		while(j<areas.size()) {
			if(areas.get(j).get("plazoDistribucionPermitido")==null) {
				areas.remove(j);
			}else {
				j++;
			}
		}
		return areas;
	}
}
