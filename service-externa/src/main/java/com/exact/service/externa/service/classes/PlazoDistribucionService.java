package com.exact.service.externa.service.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAmbitoPlazoDistribucionDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.entity.AmbitoPlazoDistribucion;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.id.AmbitoPlazoDistribucionId;
import com.exact.service.externa.service.interfaces.IPlazoDistribucionService;

@Service
public class PlazoDistribucionService implements IPlazoDistribucionService {
	
	@Autowired
	IPlazoDistribucionDao plazoDistribucionDao;
	
	@Autowired
	IAmbitoPlazoDistribucionDao ambitoPlazoDao;
	
	@Override
	public Iterable<PlazoDistribucion> listarPlazosActivos() {
		Iterable<PlazoDistribucion> plazosBD = plazoDistribucionDao.findAll();
		List<PlazoDistribucion> plazoslst = StreamSupport.stream(plazosBD.spliterator(), false).collect(Collectors.toList());
		plazoslst.removeIf(plazo -> !plazo.isActivo());
		return plazoslst;
	}

	@Override
	public Iterable<PlazoDistribucion> listarByProveedorId(Long proveedorId) {
		return plazoDistribucionDao.findByProveedorId(proveedorId);
	}

	@Override
	public PlazoDistribucion guardar(PlazoDistribucion plazodistribucion) {
		if(plazodistribucion.getNombre()==null) {
			return null;
		}
		
		PlazoDistribucion plazito = plazoDistribucionDao.save(plazodistribucion);
		List<AmbitoPlazoDistribucion> ambitosplazos = new ArrayList<>();
		for(Map<String,Object> ambito : plazito.getAmbitos()) {
			AmbitoPlazoDistribucion ambitoplazito = new AmbitoPlazoDistribucion();
			AmbitoPlazoDistribucionId ambitoPlazoDistribucionId = new AmbitoPlazoDistribucionId();
			ambitoPlazoDistribucionId.setAmbitoId(Long.valueOf(ambito.get("id").toString()));
			ambitoPlazoDistribucionId.setPlazoDistribucionID(plazito.getId());
			ambitoplazito.setId(ambitoPlazoDistribucionId);
			ambitoplazito.setAmbitoId(Long.valueOf(ambito.get("id").toString()));
			ambitoplazito.setPlazoDistribucion(plazito);
			ambitosplazos.add(ambitoplazito);
		}
		ambitoPlazoDao.saveAll(ambitosplazos);
		return plazito;		
	}

	@Override
	public Iterable<PlazoDistribucion> listarAll() {
		return plazoDistribucionDao.findAll();
	}

	@Override
	public PlazoDistribucion modificar(PlazoDistribucion plazodistribucion, Long ambitoId) {
		if(plazodistribucion.getNombre()==null) {
			return null;
		}
		AmbitoPlazoDistribucion ambitoplazo = ambitoPlazoDao.findAmbitoPlazo(plazodistribucion.getId());
		
		ambitoplazo.setPlazoDistribucion(plazodistribucion);
		ambitoPlazoDao.save(ambitoplazo);
		return plazoDistribucionDao.save(plazodistribucion);
	}

}
