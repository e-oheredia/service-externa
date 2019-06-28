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
	@SuppressWarnings("unchecked")
	public Proveedor guardar(Proveedor proveedor) {
		
		if(proveedor.getNombre()==null) {
			return null;
		}
		
		List<Long> idsregion = new ArrayList<>();

		
		for(Map<String,Object> ambito : proveedor.getAmbitos()) {	
			Map<String,Object> region = (Map<String, Object>) ambito.get("region");
			idsregion.add(Long.valueOf(region.get("id").toString()));
		}
		
		idsregion = idsregion.stream().distinct().collect(Collectors.toList());
		
		Set<PlazoDistribucion> variosplazos = new HashSet<PlazoDistribucion>();

				
		for(Long id : idsregion) {
			    for(PlazoDistribucion pd : plazodao.plazosByRegion(id)) {
			    	variosplazos.add(pd);
			    }
		}
		//Iterable<AmbitoPlazoDistribucion> ambitosplazos2 = ambitoPlazoDao.findAll();
		//Iterable<PlazoDistribucion> varios = plazodao.findAll();
		//List<Long> idsambitos = new ArrayList<>();
		//List<Long> plazos = new ArrayList<>();
		/*
		for(Map<String,Object> ambito : proveedor.getAmbitos()) {
			idsambitos.add(Long.valueOf(ambito.get("id").toString()));
		}
		for(AmbitoPlazoDistribucion ambitoproveedor : ambitosplazos2) {
			for(Long ids : idsambitos) {
				if(ids== ambitoproveedor.getId().getAmbitoId()) {
					plazos.add(ambitoproveedor.getPlazoDistribucion().getId());
				}
			}
		}
		for(PlazoDistribucion plazosss : varios ) {
			for(Long idsplazos : plazos  ) {
				if(idsplazos == plazosss.getId()) {
					variosplazos.add(plazosss);
				}
			}
		}
		*/
		proveedor.setPlazosDistribucion(variosplazos);
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
	@SuppressWarnings("unchecked")
	public Proveedor modificar(Proveedor proveedor) {
		
		if(proveedor.getNombre()==null) {
			return null;
		}
		plazodao.eliminarbyproveedorid(proveedor.getId());
		
		List<Long> idsregion = new ArrayList<>();
		for(Map<String,Object> ambito : proveedor.getAmbitos()) {	
			Map<String,Object> region = (Map<String, Object>) ambito.get("region");
			idsregion.add(Long.valueOf(region.get("id").toString()));
		}
		
		idsregion = idsregion.stream().distinct().collect(Collectors.toList());
		
		Set<PlazoDistribucion> variosplazos = new HashSet<PlazoDistribucion>();
		
		for(Long id : idsregion) {
				Iterable<PlazoDistribucion> pds =  plazodao.plazosByRegion(id);
				if(pds==null) {
					return null;
				}
			    for(PlazoDistribucion pd : plazodao.plazosByRegion(id)) {
			    	variosplazos.add(pd);
			    }
		}
		proveedor.setPlazosDistribucion(variosplazos);
		Proveedor provee = proveedorDao.save(proveedor);
		provee.setAmbitos(proveedor.getAmbitos());
		ambitoproveedorDao.eliminarbyproveedorid(proveedor.getId());
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
	public Iterable<Proveedor> listarProveedoresActivos() throws ClientProtocolException, IOException, JSONException {
		Iterable<Proveedor> proveedoresBD = proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedoresBD.spliterator(), false).collect(Collectors.toList());
		proveedoreslst.removeIf(proveedor -> !proveedor.isActivo());
		return proveedoreslst;
	}



}
