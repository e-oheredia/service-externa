package com.exact.service.externa.service.classes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.dao.ITipoDevolucionDao;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.entity.TipoDevolucion;
import com.exact.service.externa.service.interfaces.ICargosService;

import static com.exact.service.externa.enumerator.TipoDevolucionEnum.CARGO;
import static com.exact.service.externa.enumerator.TipoDevolucionEnum.DENUNCIA;
import static com.exact.service.externa.enumerator.TipoDevolucionEnum.REZAGO;
import static com.exact.service.externa.enumerator.EstadoCargoEnum.DEVUELTO;
import static com.exact.service.externa.enumerator.EstadoCargoEnum.PENDIENTE;


import io.jsonwebtoken.io.IOException;

@Service
public class CargosService implements ICargosService{

	@Autowired
	IDocumentoReporteDao documentoReporteDao;
	
	@Autowired
	IProveedorDao proveedorDao;
	
	@Autowired
	IDocumentoDao documentoDao;
	
	@Autowired
	ITipoDevolucionDao tipodevolucionDao;
	
	@Override
	public Map<Long, Map<Long, Map<Long, Integer>>> devolucionPorTipo(String fechaIni, String fechaFin) throws IOException, JSONException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Iterable<DocumentoReporte> documentoreportes = documentoReporteDao.findDocumentosByEstadoCargo(dateI, dateF);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentoreportes.spliterator(), false).collect(Collectors.toList());
		Iterable<Proveedor> proveedores =  proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedores.spliterator(), false).collect(Collectors.toList());
		Iterable<TipoDevolucion> tiposDevolucion = tipodevolucionDao.findAll();
		Map<Long, Map<Long, Map<Long, Integer>>> cantidades = new HashMap<>();
		for(TipoDevolucion tipoDevolucion: tiposDevolucion) {
			Map<Long, Map<Long, Integer>> cantidadPendiente = new HashMap<>();
			Map<Long, Map<Long, Integer>> cantidadDevueltos = new HashMap<>();
			for(Proveedor proveedor : proveedoreslst) {
				int cantProveedorPendiente =0;
				int cantProveedorDevuelto= 0;
				Map<Long, Integer> proveedorPendiente = new HashMap<>();
				Map<Long, Integer> proveedorDevuelto = new HashMap<>();
				for(DocumentoReporte documentoreporte : documentoslst) {
					if(proveedor.getId()==documentoreporte.getProveedorId()) {
						if(documentoreporte.getEstadoCargo()==PENDIENTE) {
							cantProveedorPendiente++;
						}else {
							cantProveedorDevuelto++;
						}
					}
				}
				proveedorPendiente.put(proveedor.getId(), cantProveedorPendiente);
				proveedorDevuelto.put(proveedor.getId(), cantProveedorDevuelto);
				cantidadPendiente.put(PENDIENTE, proveedorPendiente);
				cantidadDevueltos.put(DEVUELTO, proveedorDevuelto);
			}
			cantidades.put(tipoDevolucion.getId(), cantidadPendiente);
			cantidades.put(tipoDevolucion.getId(), cantidadDevueltos);
		}
		
		//falta probar
		return cantidades;
	}

}
