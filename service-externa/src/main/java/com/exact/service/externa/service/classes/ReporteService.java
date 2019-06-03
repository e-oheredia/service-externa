package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.dao.IReporteDao;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IReporteService;

@Service
public class ReporteService implements IReporteService {

	
	@Autowired
	IReporteDao reportedao;
	
	@Autowired
	IProveedorDao proveedordao;
	
	@Override
	public Map<String, Float> volumenbycurier() throws IOException, JSONException {
		
		return null;
	}

}
