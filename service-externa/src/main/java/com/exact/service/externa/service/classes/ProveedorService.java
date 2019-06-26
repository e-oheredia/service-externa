																																																											package com.exact.service.externa.service.classes;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAmbitoPlazoDistribucionDao;
import com.exact.service.externa.dao.IAmbitoProveedorDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.edao.interfaces.IRegionEdao;
import com.exact.service.externa.entity.AmbitoPlazoDistribucion;
import com.exact.service.externa.entity.AmbitoProveedor;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.entity.id.AmbitoPlazoDistribucionId;
import com.exact.service.externa.entity.id.AmbitoProveedorId;
import com.exact.service.externa.service.interfaces.IProveedorService;

@Service
public class ProveedorService implements IProveedorService{


	@Autowired
	IProveedorDao proveedorDao;
	
	@Autowired
	IAmbitoProveedorDao ambitoproveedorDao;
	
	@Autowired
	IAmbitoPlazoDistribucionDao ambitoPlazoDao;
	
	@Autowired
	IRegionEdao ambitodiasdao;
	
	@Autowired
	IPlazoDistribucionDao plazodao;
	
	@Override
	public Iterable<Proveedor> listarProveedores() throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Map<String,Object>> ambitos = ambitodiasdao.listarSubAmbitos();
		Iterable<Proveedor> proveedoresCreados = proveedorDao.findAll();
		List<Proveedor> proveedoresCreadosList = StreamSupport.stream(proveedoresCreados.spliterator(), false).collect(Collectors.toList());
		Iterable<PlazoDistribucion> plazos = plazodao.findAll();
		Iterable<AmbitoPlazoDistribucion> ambitosplazo= ambitoPlazoDao.findAll();

		for(Proveedor provee : proveedoresCreadosList) {
			Iterable<AmbitoProveedor> ambitosId= ambitoproveedorDao.listarAmbitosIds(provee.getId());
			Set<Map<String,Object>> ambitprovee = new HashSet<>();
			List<Long> ambitoProveedor = new ArrayList<>();
			List<Long> ambitoPlazos = new ArrayList<>();
			
			for(AmbitoProveedor ambitoprovee : ambitosId) {
				ambitoProveedor.add(ambitoprovee.getId().getAmbitoId());
			}
			
			for(Long ambitoss : ambitoProveedor) {
					for(AmbitoPlazoDistribucion ambitoplazo : ambitosplazo) {
					if(ambitoplazo.getId().getAmbitoId()==ambitoss) {
						ambitoPlazos.add(ambitoplazo.getPlazoDistribucion().getId());
						}
					}

				}	
			
			for(Long ambitoId : ambitoProveedor) {
				for(Map<String,Object> ambito: ambitos) {
					if(ambitoId==Long.valueOf(ambito.get("id").toString())) {
						ambitprovee.add(ambito);
						Set<PlazoDistribucion> plazosdistri = new HashSet<>();
						//Iterable<AmbitoPlazoDistribucion> ambitosplazo= ambitoPlazoDao.listarplazosIds(Long.valueOf(ambito.get("id").toString()));
							/*
							for(AmbitoPlazoDistribucion ambitoplazos : ambitosplazo) {
								if(ambitoplazos.getPlazoDistribucion().getId()==Long.valueOf(ambito.get("id").toString())) {
									ambitoPlazos.add(ambitoplazos.getPlazoDistribucion().getId());
								}
							}*/
							for(Long plazosid : ambitoPlazos) {
								for(PlazoDistribucion plazo: plazos) {
									if(plazosid==plazo.getId() ) {
										plazosdistri.add(plazo);
									}
								}
							}
							
							provee.setPlazosDistribucion(plazosdistri);
						
						
						
					}
				}
			}
			provee.setAmbitos(ambitprovee) ;
		}
		
		return proveedoresCreadosList;
	}

	@Override
	public Proveedor guardar(Proveedor proveedor) {
		if(proveedor.getNombre()==null) {
			return null;
		}
		
		Proveedor provee = proveedorDao.save(proveedor);
		List<AmbitoProveedor> ambitosplazos = new ArrayList<>();
		for(Map<String,Object> ambito : provee.getAmbitos()) {
			AmbitoProveedor ambitoprovee = new AmbitoProveedor();
			AmbitoProveedorId ambitoProveedorId = new AmbitoProveedorId();
			ambitoProveedorId.setAmbitoId(Long.valueOf(ambito.get("id").toString()));
			ambitoProveedorId.setProveedorId(provee.getId());
			ambitoprovee.setId(ambitoProveedorId);
			ambitoprovee.setAmbitoId(Long.valueOf(ambito.get("id").toString()));
			ambitoprovee.setProveedores(provee);
			ambitosplazos.add(ambitoprovee);
		}
		ambitoproveedorDao.saveAll(ambitosplazos);
		return provee;		
	}

	@Override
	public Proveedor modificar(Proveedor proveedor) {
		if(proveedorDao.existsById(proveedor.getId())) {
			return guardar(proveedor);
		}else
			return null;
	}

	@Override
	public Iterable<Proveedor> listarProveedoresActivos() throws ClientProtocolException, IOException, JSONException {
		Iterable<Proveedor> proveedoresBD = proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedoresBD.spliterator(), false).collect(Collectors.toList());
		proveedoreslst.removeIf(proveedor -> !proveedor.isActivo());
		return proveedoreslst;
	}



}
