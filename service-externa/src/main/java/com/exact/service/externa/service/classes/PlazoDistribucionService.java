package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.exact.service.externa.dao.IAmbitoDistritoDao;
import com.exact.service.externa.dao.IAmbitoProveedorDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.dao.IRegionPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IRegionEdao;
import com.exact.service.externa.entity.AmbitoDistrito;
import com.exact.service.externa.entity.AmbitoProveedor;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.entity.RegionPlazoDistribucion;
import com.exact.service.externa.entity.id.AmbitoProveedorId;
import com.exact.service.externa.entity.id.RegionPlazoDistribucionId;
import com.exact.service.externa.service.interfaces.IPlazoDistribucionService;

@Service
public class PlazoDistribucionService implements IPlazoDistribucionService {
	
	@Autowired
	IPlazoDistribucionDao plazoDistribucionDao;
	
	
	
	@Autowired
	IRegionEdao ambitodiasdao;
	
	@Autowired
	IAmbitoDistritoDao ambitodistritoDao;
	
	@Autowired
	IAmbitoProveedorDao ambitoproveedorDao;
	
	@Autowired
	IRegionPlazoDistribucionDao regionplazoDao;
	
	
	
	private static final Log Logger = LogFactory.getLog(PlazoDistribucionService.class);

	
	@Override
	public Iterable<PlazoDistribucion> listarPlazosActivos() {
		Iterable<PlazoDistribucion> plazosBD = plazoDistribucionDao.findAll();
		List<PlazoDistribucion> plazoslst = StreamSupport.stream(plazosBD.spliterator(), false).collect(Collectors.toList());
		plazoslst.removeIf(plazo -> !plazo.isActivo());
		return plazoslst;
	}

	@Override
	public Iterable<PlazoDistribucion> listarByProveedorId(Long proveedorId) throws IOException, JSONException {
		//return plazoDistribucionDao.findByProveedorId(proveedorId);
		List<Long> idsregion = new ArrayList<>();
		Iterable<AmbitoProveedor> ambitosId= ambitoproveedorDao.listarAmbitosIds(proveedorId);
		Iterable<Map<String,Object>> ambitos = ambitodiasdao.listarSubAmbitos();
		Set<Map<String,Object>> ambitprovee = new HashSet<>();
		List<Long> ambitoProveedor = new ArrayList<>();
		List<Long> regionesid = new ArrayList<>();
		//Iterable<AmbitoPlazoDistribucion> ambitosplazo= ambitoPlazoDao.findAll();
		List<Map<String,Object>> listaambitos = new ArrayList<>();
		List<PlazoDistribucion> listaplazos = new ArrayList<>();
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
			idsregion.add(Long.valueOf(region.get("id").toString()));			
		}
		idsregion = idsregion.stream().distinct().collect(Collectors.toList());
		
		for(Long regionid  :  idsregion) {
			
			for(PlazoDistribucion pd : this.listarPlazosByRegionId(regionid)) {
				listaplazos.add(pd);
			}
			//listaplazos.add(this.listarPlazosByRegionId(regionid));
		}
		
		
		return listaplazos;
	}

	@Override
	public PlazoDistribucion guardar(PlazoDistribucion plazodistribucion) {
		List<Long> idsregion = new ArrayList<>();

		if(plazodistribucion.getNombre()==null) {
			return null;
		}
		
		PlazoDistribucion plaz =plazoDistribucionDao.buscarpornombre(plazodistribucion.getNombre());
		if(plaz!=null) {
			return null;
		}

		
		PlazoDistribucion plazito = plazoDistribucionDao.save(plazodistribucion);
			for(Map<String,Object> region : plazodistribucion.getRegiones()) {	
			idsregion.add(Long.valueOf(region.get("id").toString()));
		}
		
		idsregion = idsregion.stream().distinct().collect(Collectors.toList());
		
		List<RegionPlazoDistribucion> ambitosplazos = new ArrayList<>();
		for(Map<String,Object> region : plazodistribucion.getRegiones()) {
			RegionPlazoDistribucion regionplazo = new RegionPlazoDistribucion();
			RegionPlazoDistribucionId regionplazoId = new RegionPlazoDistribucionId();
			regionplazoId.setRegionId(Long.valueOf(region.get("id").toString()));
			regionplazoId.setPlazoDistribucionId(plazito.getId());
			regionplazo.setId(regionplazoId);
			regionplazo.setRegionId(Long.valueOf(region.get("id").toString()));
			regionplazo.setPlazoDistribucion(plazito);
			ambitosplazos.add(regionplazo);
		}
		regionplazoDao.saveAll(ambitosplazos);
		
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
		
		Iterable<Map<String,Object>> regiones = ambitodiasdao.listarAmbitos();
		Iterable<PlazoDistribucion> plazos =  plazoDistribucionDao.findAll();
		List<PlazoDistribucion> plazoslst = StreamSupport.stream(plazos.spliterator(), false).collect(Collectors.toList());
		Iterable<RegionPlazoDistribucion> regisplazo= regionplazoDao.findAll();
		

		for(PlazoDistribucion pd : plazoslst) {
			Set<Map<String,Object>> regionesplaz = new HashSet<>();

			Iterable<RegionPlazoDistribucion> regionesId= regionplazoDao.listarRegionIds(pd.getId());
			Set<Map<String,Object>> regionprovee = new HashSet<>();
			List<Long> regionPlazo = new ArrayList<>();
			List<Long> regionPlazos = new ArrayList<>();
			
			/*for(RegionPlazoDistribucion regionpro : regionesId) {
				regionPlazo.add(regionpro.getId().getRegionId());
			}*/
			
			
			for(RegionPlazoDistribucion regionpro : regionesId) {
				for(Map<String,Object> region: regiones) {
					if(regionpro.getId().getRegionId()==Long.valueOf(region.get("id").toString())) {
						regionesplaz.add(region);
						Logger.info("PZ :" +pd.getNombre());
						Logger.info("RG :" +region.get("nombre").toString() );
						}
					
				}
				
			}
			pd.setRegiones(regionesplaz);


			
			
			/*
			for(Long regionees : regionPlazo) {
					for(RegionPlazoDistribucion replazo : regisplazo ) {
					if(replazo.getId().getRegionId()==regionees) {
						regionPlazos.add(replazo.getPlazoDistribucion().getId());
						}
					}
				}
			for(Long regionId : regionPlazo) {
				for(Map<String,Object> ambito: regiones) {
					if(regionId==Long.valueOf(ambito.get("id").toString())) {
						regionesplaz.add(ambito);}}
				
			}
			
			
			
			*/
			
			
		}
		
		//Iterable<Map<String,Object>> regiones = ambitodiasdao.listarAmbitos();
		
		//List<Map<String,Object>> regioneslistt = StreamSupport.stream(regiones.spliterator(), false).collect(Collectors.toList());
		
		
		/*for(PlazoDistribucion plazito : plazoslst) {
			for(Map<String,Object> region : regiones) {
				Iterable<Map<String,Object>> ambitos =ambitodiasdao.listarAmbitosByRegion(Long.valueOf(region.get("id").toString()));
				Set<Map<String,Object>> ambitoslst = new HashSet<>();
				for(Map<String,Object> ambitoEncontrado:ambitos ) {
					ambitoslst.add(ambitoEncontrado);
				}
				
				if(plazito.getRegionId() == Long.valueOf(region.get("id").toString())) {
					plazito.setRegion(region);
					plazito.setAmbitos(ambitoslst);
				}
			
			
			
			}

		}*/
		
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
		List<Long> idsregion = new ArrayList<>();

		
		/*if(plazodistribucion.getNombre()==null) {
			return null;
		}
		PlazoDistribucion plazoactualizado = plazoDistribucionDao.save(plazodistribucion);
		*/
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
		
		if(plazodistribucion.getNombre()==null) {
			return null;
		}
		
		PlazoDistribucion plaz =plazoDistribucionDao.buscarpornombre(plazodistribucion.getNombre());
		if(plaz!=null) {
			return null;
		}

		
		PlazoDistribucion plazito = plazoDistribucionDao.save(plazodistribucion);
		
		
			for(Map<String,Object> region : plazodistribucion.getRegiones()) {	
			idsregion.add(Long.valueOf(region.get("id").toString()));
		}
		
		idsregion = idsregion.stream().distinct().collect(Collectors.toList());
		regionplazoDao.eliminarbyplazoid(plazito.getId());

		List<RegionPlazoDistribucion> ambitosplazos = new ArrayList<>();
		for(Map<String,Object> region : plazodistribucion.getRegiones()) {
			RegionPlazoDistribucion regionplazo = new RegionPlazoDistribucion();
			RegionPlazoDistribucionId regionplazoId = new RegionPlazoDistribucionId();
			regionplazoId.setRegionId(Long.valueOf(region.get("id").toString()));
			regionplazoId.setPlazoDistribucionId(plazito.getId());
			regionplazo.setId(regionplazoId);
			regionplazo.setRegionId(Long.valueOf(region.get("id").toString()));
			regionplazo.setPlazoDistribucion(plazito);
			ambitosplazos.add(regionplazo);
		}
		regionplazoDao.saveAll(ambitosplazos);
		
		return plazito;
		
		
		
		
		
		
		//return plazoactualizado;
	}
	
	
	//IMPACTO
	@Override
	public Iterable<PlazoDistribucion> listarPlazosByRegionId(Long regionId) {
		//return plazoDistribucionDao.findPlazosByRegionId(regionId);
		//return null;
		List<PlazoDistribucion> listarplazos = new ArrayList<>();
		 Iterable<RegionPlazoDistribucion> regionplazoss = regionplazoDao.getPlazosDistribucionByRegion(regionId);
		 for(RegionPlazoDistribucion RP : regionplazoss) {
			 for(PlazoDistribucion pd : plazoDistribucionDao.findAll()) {
				 if(pd.getId() == RP.getId().getPlazoDistribucionId()) {
					 listarplazos.add(pd);
				 }
			 }
			 
		 }
		return listarplazos;

	}
	
	
	//IMPACTO
	@Override
	public Iterable<PlazoDistribucion> listarPlazosByDistritoId(Long distritoId) throws IOException, JSONException {
		AmbitoDistrito ambitodistrito = ambitodistritoDao.findById(distritoId).orElse(null);
		Map<String,Object> region = ambitodiasdao.listarRegionByDistrito(ambitodistrito.getAmbitoId());
		List<PlazoDistribucion> listarplazos = new ArrayList<>();
		 Iterable<RegionPlazoDistribucion> regionplazoss = regionplazoDao.getPlazosDistribucionByRegion(Long.valueOf(region.get("id").toString()));
		//return plazoDistribucionDao.findPlazosByRegionId(Long.valueOf(region.get("id").toString()));
		 for(RegionPlazoDistribucion RP : regionplazoss) {
			 for(PlazoDistribucion pd : plazoDistribucionDao.findAll()) {
				 if(pd.getId() == RP.getId().getPlazoDistribucionId()) {
					 listarplazos.add(pd);
				 }
			 }
			 
		 }
		return listarplazos;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<PlazoDistribucion> listarPlazosByProveedor(Proveedor proveedor) {
		List<Long> idsregion = new ArrayList<>();
		List<PlazoDistribucion> plazos = new ArrayList<>();
		//Iterable<AmbitoPlazoDistribucion> ambitosplazo= ambitoPlazoDao.findAll();
		Iterable<AmbitoProveedor> ambitosId= ambitoproveedorDao.listarAmbitosIds(proveedor.getId());
		Set<Map<String,Object>> ambitprovee = new HashSet<>();
		List<Long> ambitoProveedor = new ArrayList<>();
		List<Long> ambitoPlazos = new ArrayList<>();
		for(AmbitoProveedor ambitoprovee : ambitosId) {
			ambitoProveedor.add(ambitoprovee.getId().getAmbitoId());
		}
		/*for(Long ambitoss : ambitoProveedor) {
				for(AmbitoPlazoDistribucion ambitoplazo : ambitosplazo) {
				if(ambitoplazo.getId().getAmbitoId()==ambitoss) {
					ambitoPlazos.add(ambitoplazo.getPlazoDistribucion().getId());
					}
				}

			}*/
		proveedor.setAmbitos(ambitprovee); 
		for(Map<String,Object> ambito : proveedor.getAmbitos()) {	
			Map<String,Object> region = (Map<String, Object>) ambito.get("region");
			idsregion.add(Long.valueOf(region.get("id").toString()));
		}
		idsregion = idsregion.stream().distinct().collect(Collectors.toList());
		for(Long ids : idsregion) {
			for(PlazoDistribucion pd : listarPlazosByRegionId(ids)) {
				plazos.add(pd);
			}
		}
		return plazos;
	}

}
