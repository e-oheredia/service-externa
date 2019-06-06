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
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.TipoDevolucion;
import com.exact.service.externa.service.interfaces.ICargosService;

import static com.exact.service.externa.enumerator.TipoDevolucionEnum.CARGO;
import static com.exact.service.externa.enumerator.TipoDevolucionEnum.DENUNCIA;
import static com.exact.service.externa.enumerator.TipoDevolucionEnum.REZAGO;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
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
	public Map<Long, Map<Long, Map<String, Integer>>> devolucionPorTipo(String fechaIni, String fechaFin) throws IOException, JSONException {
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
		Map<Long, Map<Long, Map<String, Integer>>> cantidadesProveedor = new HashMap<>();
		for(Proveedor proveedor : proveedoreslst) {
			Map<Long, Map<String, Integer>> cantidadTipo = new HashMap<>();
			for(TipoDevolucion tipoDevolucion: tiposDevolucion) {
				int cantProveedorPendiente =0;
				int cantProveedorDevuelto= 0;
				Map<String, Integer> cantidadPendienteDevuelto = new HashMap<>();
				for(DocumentoReporte documentoreporte : documentoslst) {
					Documento documento = documentoDao.findById(documentoreporte.getDocumentoId()).orElse(null);
					SeguimientoDocumento sd = documento.getUltimoSeguimientoDocumento();
					if(proveedor.getId()==documentoreporte.getProveedorId()) {
							if(documentoreporte.getEstadoCargo()==PENDIENTE) {
								Iterable<TipoDevolucion> tiposdefecto = sd.getEstadoDocumento().getTiposDevolucion();
								List<TipoDevolucion> tiposdefectolst = StreamSupport.stream(tiposdefecto.spliterator(), false).collect(Collectors.toList());
								if(tiposdefectolst.contains(tipoDevolucion)){
									cantProveedorPendiente++;
								}
								}else {
									Iterable<TipoDevolucion> tiposdevolucionDocumento = documento.getTiposDevolucion();
									List<TipoDevolucion> tiposdevolucionDocumentolst = StreamSupport.stream(tiposdevolucionDocumento.spliterator(), false).collect(Collectors.toList());
									if(tiposdevolucionDocumentolst.contains(tipoDevolucion)){
										cantProveedorDevuelto++;
									}
								}
					}
				}
				cantidadPendienteDevuelto.put("pendiente", cantProveedorPendiente);
				cantidadPendienteDevuelto.put("devuelto", cantProveedorDevuelto);
				cantidadTipo.put(tipoDevolucion.getId(), cantidadPendienteDevuelto);
			}
			cantidadesProveedor.put(proveedor.getId(), cantidadTipo);
		}
		
		
		return cantidadesProveedor;
	}

}
