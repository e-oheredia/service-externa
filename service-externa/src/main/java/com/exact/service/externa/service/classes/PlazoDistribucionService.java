package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.dao.IAmbitoDistritoDao;
import com.exact.service.externa.dao.IAmbitoPlazoDistribucionDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IRegionEdao;
import com.exact.service.externa.entity.AmbitoDistrito;
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
	
	@Autowired
	IRegionEdao ambitodiasdao;
	
	@Autowired
	IAmbitoDistritoDao ambitodistritoDao;
	
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
		
		PlazoDistribucion plaz =plazoDistribucionDao.buscarpornombre(plazodistribucion.getNombre());
		if(plaz!=null) {
			return null;
		}
		
		PlazoDistribucion plazito = plazoDistribucionDao.save(plazodistribucion);
		return plazito;
		
		/*
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
		*/		
	}

	@Override
	public Iterable<PlazoDistribucion> listarAll() throws Exception {
		
		//Iterable<Map<String,Object>> ambitos = ambitodiasdao.listarSubAmbitos();
		Iterable<PlazoDistribucion> plazos =  plazoDistribucionDao.findAll();
		List<PlazoDistribucion> plazoslst = StreamSupport.stream(plazos.spliterator(), false).collect(Collectors.toList());
		Iterable<Map<String,Object>> regiones = ambitodiasdao.listarAmbitos();
		
		//List<Map<String,Object>> regioneslistt = StreamSupport.stream(regiones.spliterator(), false).collect(Collectors.toList());
		for(PlazoDistribucion plazito : plazoslst) {
			for(Map<String,Object> region : regiones) {
				Iterable<Map<String,Object>> ambitos =ambitodiasdao.listarAmbitosByRegion(Long.valueOf(region.get("id").toString()));
				Set<Map<String,Object>> ambitoslst = new HashSet<>();
				for(Map<String,Object> ambitoEncontrado:ambitos ) {
					ambitoslst.add(ambitoEncontrado);
				}
				if(plazito.getRegionId()==Long.valueOf(region.get("id").toString())) {
					plazito.setRegion(region);
					plazito.setAmbitos(ambitoslst);
				}
			}

		}
		
		/*
		Iterable<AmbitoPlazoDistribucion> ambitosId= ambitoPlazoDao.listarAmbitosIds(plazito.getId());
		Set<Map<String,Object>> ambitplazos = new HashSet<>();
		List<Long> ambitoPlazos = new ArrayList<>();
		for(AmbitoPlazoDistribucion ambitoplazos : ambitosId) {
			ambitoPlazos.add(ambitoplazos.getId().getAmbitoId());
		}
		for(Long ambitoId : ambitoPlazos) {
			for(Map<String,Object> ambito: ambitos) {
				if(ambitoId==Long.valueOf(ambito.get("id").toString())) {
					ambitplazos.add(ambito);
				}
			}
		}
		plazito.setAmbitos(ambitplazos);
		*/
		return plazoslst;
	}

	@Override
	public PlazoDistribucion modificar(PlazoDistribucion plazodistribucion) {
		if(plazodistribucion.getNombre()==null) {
			return null;
		}
		PlazoDistribucion plazoactualizado = plazoDistribucionDao.save(plazodistribucion);
		
		/*
		ambitoPlazoDao.eliminarbyproveedorid(plazodistribucion.getId());
		List<AmbitoPlazoDistribucion> ambitosplazos = new ArrayList<>();
		plazoactualizado.setAmbitos(plazodistribucion.getAmbitos());
		for(Map<String,Object> ambito : plazoactualizado.getAmbitos()) {
			AmbitoPlazoDistribucion ambitoplazito = new AmbitoPlazoDistribucion();
			AmbitoPlazoDistribucionId ambitoPlazoDistribucionId = new AmbitoPlazoDistribucionId();
			ambitoPlazoDistribucionId.setAmbitoId(Long.valueOf(ambito.get("id").toString()));
			ambitoPlazoDistribucionId.setPlazoDistribucionID(plazoactualizado.getId());
			ambitoplazito.setId(ambitoPlazoDistribucionId);
			ambitoplazito.setAmbitoId(Long.valueOf(ambito.get("id").toString()));
			ambitoplazito.setPlazoDistribucion(plazoactualizado);
			ambitosplazos.add(ambitoplazito);
		}
		ambitoPlazoDao.saveAll(ambitosplazos);
		*/
		return plazoactualizado;
	}

	@Override
	public Iterable<PlazoDistribucion> listarPlazosByRegionId(Long regionId) {
		return plazoDistribucionDao.findPlazosByRegionId(regionId);
	}

	@Override
	public Iterable<PlazoDistribucion> listarPlazosByDistritoId(Long distritoId) throws IOException, JSONException {
		Map<String,Object> region = ambitodiasdao.listarRegionByDistrito(distritoId);
		return plazoDistribucionDao.findPlazosByRegionId(Long.valueOf(region.get("id").toString()));
	}

}
