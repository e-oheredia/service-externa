package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;

import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.dao.IReporteDao;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IReporteService;

public class ReporteService implements IReporteService {

	
	@Autowired
	IReporteDao reportedao;
	
	@Autowired
	IProveedorDao proveedordao;
	
	@Override
	public Map<String, Float> volumenbycurier() throws IOException, JSONException {
		
		Entidad entidades=reportedao.findAll(); 
		Map<String, Float > m = new HashMap<String,Float>();
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false).collect(Collectors.toList());
		for(Entidad entidad : entidades ) {
			for(Proveedor pro : proveedores) {
				if(entidad.getproveedor()==pro.getId()) {
					m.put(pro.getId(),entidades.length());
				}
			}
		}		
		return m;
	}

}
