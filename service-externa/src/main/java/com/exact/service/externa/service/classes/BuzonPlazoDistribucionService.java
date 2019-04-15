package com.exact.service.externa.service.classes;

import java.io.IOException;
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
import com.exact.service.externa.service.interfaces.IBuzonPlazoDistribucionService;

@Service
public class BuzonPlazoDistribucionService implements IBuzonPlazoDistribucionService {
	
	@Autowired
	IBuzonPlazoDistribucionDao buzonPlazoDistribucionDao;
	
	@Autowired
	IBuzonEdao buzonEdao;
	
	@Override
	public BuzonPlazoDistribucion listarById(Long id) {
		return buzonPlazoDistribucionDao.getPlazoDistribucionIdByBuzonId(id);
	}
	
	@Override
	public BuzonPlazoDistribucion actualizar(BuzonPlazoDistribucion buzonPlazoDistribucion) {
		if (buzonPlazoDistribucionDao.existsById(buzonPlazoDistribucion.getBuzonId())) {
			return buzonPlazoDistribucionDao.save(buzonPlazoDistribucion);
		}
		return null;
		
	}

	@Override
	public Iterable<BuzonPlazoDistribucion> listarBuzonPlazo() throws IOException, JSONException {
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarAll();
		Iterable<BuzonPlazoDistribucion> buzonplazoBD = buzonPlazoDistribucionDao.findAll();
		List<BuzonPlazoDistribucion> buzonesplazo = StreamSupport.stream(buzonplazoBD.spliterator(), false).collect(Collectors.toList());
		
		for(BuzonPlazoDistribucion buzonplazo : buzonesplazo) {
			int i = 0; 
			while(i < buzones.size()) {
				if (buzonplazo.getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
					buzonplazo.setBuzon(buzones.get(i));
					break;
				}
				i++;
			}
		}
		
		return buzonesplazo;
	}

}
