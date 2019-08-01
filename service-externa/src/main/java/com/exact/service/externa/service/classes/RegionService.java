package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAmbitoProveedorDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.edao.interfaces.IRegionEdao;
import com.exact.service.externa.entity.AmbitoProveedor;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.service.interfaces.IRegionService;

@Service
public class RegionService implements IRegionService{

	@Autowired
	IRegionEdao RegionEdao;
	
	@Autowired
	IPlazoDistribucionDao plazoDistribucionDao;
	
	@Autowired
	IProveedorDao proveedorDao;
	
	@Autowired
	IAmbitoProveedorDao ambitoproveedorDao;
	

	
	@Override
	public Iterable<Map<String, Object>> listarRegiones() throws Exception {
		return RegionEdao.listarAmbitos();
	}

	@Override
	public Iterable<Map<String, Object>> listardiaslaborales(Long ambitoid) throws IOException, JSONException, Exception {
		return RegionEdao.listardiaslaborales(ambitoid);
	}

	@Override
	public Iterable<Map<String, Object>> listarhoraslaborales(Long ambitoid) throws ClientProtocolException, IOException, JSONException {
		return RegionEdao.listarhoraslaborales(ambitoid);

	}

	@Override
	public Iterable<Map<String, Object>> listarAmbitos() throws IOException, JSONException {
		Iterable<Map<String,Object>> ambitos =  RegionEdao.listarSubAmbitos();
		/*
		for(Map<String,Object> ambito : ambitos) {
			//IMPACTO//////////////////////////////////
			
				Iterable<PlazoDistribucion> plazoAmbito = ambitoPlazoDao.listarPlazosByAmbitoId(Long.valueOf(ambito.get("id").toString()));
			

			//////////////////////////////////////////
			List<PlazoDistribucion> plazolst = new ArrayList<>();
			for(PlazoDistribucion plazo : plazoAmbito) {
				for(PlazoDistribucion plazito : plazos) {
					if(plazo.getId()==plazito.getId()) {
						plazolst.add(plazito);
					}
				}
			}
			ambito.put("plazos", plazolst);
		}
		*/
		return ambitos;
	}

	@Override
	public Iterable<Map<String, Object>> listarAmbitosByAmbitoId(Long id) throws IOException, JSONException {
		return RegionEdao.listarSubAmbitosActivosByAmbitoId(id);

	}

	@Override
	public Iterable<Map<String, Object>> listarAmbitosActivos() throws IOException, JSONException {
		return RegionEdao.listarSubAmbitosActivos();
	}

	@Override
	public Map<String, Object> guardarAmbito(String ambito) throws IOException, JSONException {
		return RegionEdao.guardarSubAmbito(ambito);

	}

	@Override
	public Map<String, Object> modificarAmbito(Long id, String ambito) throws IOException, JSONException {
		JSONObject jsonobj = new JSONObject(ambito);
		jsonobj.remove("plazos");
		return RegionEdao.modificarSubAmbito(id, jsonobj.toString());

	}

	@Override
	public Iterable<Map<String, Object>> listardiaslaborables() throws IOException, JSONException, Exception {
		return RegionEdao.listarAmbitos();

	}

	@Override
	public Map<String, Object> modificarRegion(Long id, String ambito) throws IOException, JSONException {
		return RegionEdao.modificarAmbito(id, ambito);

	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<Map<String, Object>> RegionesbyProveedor(Long proveedorId) throws IOException, JSONException {
		List<Map<String,Object>> regiones = new ArrayList<>();
		
		Iterable<AmbitoProveedor> ambitosId= ambitoproveedorDao.listarAmbitosIds(proveedorId);
		Iterable<Map<String,Object>> ambitos = RegionEdao.listarSubAmbitos();
		//Set<Map<String,Object>> ambitprovee = new HashSet<>();
		List<Long> ambitoProveedor = new ArrayList<>();
		//List<Long> regionesid = new ArrayList<>();
		//Iterable<AmbitoPlazoDistribucion> ambitosplazo= ambitoPlazoDao.findAll();
		List<Map<String,Object>> listaambitos = new ArrayList<>();
		//List<PlazoDistribucion> listaplazos = new ArrayList<>();
		for(AmbitoProveedor ambitoprovee : ambitosId) {
			ambitoProveedor.add(ambitoprovee.getId().getAmbitoId());
		}
		
		
		for(Long ambitoss : ambitoProveedor) {
				for(Map<String,Object> ambito : ambitos) {
					if(Long.valueOf(  ambito.get("id").toString()     )==ambitoss) {
						listaambitos.add(ambito);
					}
					
				}

		}
		
		for(Map<String,Object> ambito : listaambitos) {
			Map<String,Object> region = (Map<String, Object>) ambito.get("region");
			regiones.add(region);			
		}
		
		return regiones;
	}

}
