package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IBuzonPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.entity.BuzonPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IBuzonService;

@Service
public class BuzonService implements IBuzonService {

	@Autowired
	IBuzonEdao buzonEdao;

	@Autowired
	IBuzonPlazoDistribucionDao buzonPlazoDao;

	@Override
	public Map<String, Object> listarById(Long id) throws IOException, JSONException {
		return buzonEdao.listarById(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException {
		Iterable<Map<String, Object>> buzonesBD = buzonEdao.listarAll();
		List<Map<String, Object>> buzones = StreamSupport.stream(buzonesBD.spliterator(), false)
				.collect(Collectors.toList());
		List<Long> buzonIds = new ArrayList();
		for (int i = 0; i < buzones.size(); i++) {
			buzonIds.add(Long.valueOf(buzones.get(i).get("id").toString()));
		}
		Iterable<BuzonPlazoDistribucion> buzonPlazoDistribucion = buzonPlazoDao.findAllByBuzonIdIn(buzonIds);
		List<BuzonPlazoDistribucion> buzonPlazolst = StreamSupport.stream(buzonPlazoDistribucion.spliterator(), false)
				.collect(Collectors.toList());
		buzones.forEach(buzon -> {
			List<BuzonPlazoDistribucion> buzonesEncontrados =  buzonPlazolst.stream()
					.filter(x -> x.getBuzonId().longValue() == Long.valueOf(buzon.get("id").toString())).collect(Collectors.toList());
			if (!buzonesEncontrados.isEmpty()) {
				buzon.put("plazoDistribucionPermitido", buzonesEncontrados.get(0).getPlazoDistribucion());
			}
		});
		int j=0;
		while(j<buzones.size()) {
			if(buzones.get(j).get("plazoDistribucionPermitido")==null) {
				buzones.remove(j);
			}else {
				j++;
			}
		}
		return buzones;
	}

}
