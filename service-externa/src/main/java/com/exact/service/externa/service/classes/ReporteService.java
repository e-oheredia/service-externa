package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IReporteService;

@Service
public class ReporteService implements IReporteService {

	@Autowired
	IDocumentoReporteDao reportedao;

	@Autowired
	IProveedorDao proveedordao;

	@Override
	public Map<String, Float> volumenbycurier(String fechaIni,String fechaFin) throws IOException, JSONException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());
		int cantidadtotal = reportes.size();
		float porcentaje = 0;
		Map<String, Float> m = new HashMap<String, Float>();
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false)
				.collect(Collectors.toList());
		for (Proveedor pro : proveedores) {
			int cantidadproveedor=0;
			for (DocumentoReporte entidad : reportes) {
				if (entidad.getProveedorId() == pro.getId()) {
					cantidadproveedor++;	
				}
			}
			porcentaje=(cantidadproveedor/cantidadtotal)*100;
			m.put(pro.getNombre(),porcentaje );
		}

		return m;
	}

}
