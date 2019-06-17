package com.exact.service.externa.service.classes;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAreaPlazoDistribucionDao;
import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.dao.ITipoDevolucionDao;
import com.exact.service.externa.edao.interfaces.IAreaEdao;
import com.exact.service.externa.entity.AreaPlazoDistribucion;
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
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.REZAGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.NO_DISTRIBUIBLE;

import io.jsonwebtoken.io.IOException;

@Service
public class CargosService implements ICargosService {

	@Autowired
	IDocumentoReporteDao documentoReporteDao;

	@Autowired
	IProveedorDao proveedorDao;

	@Autowired
	IDocumentoDao documentoDao;

	@Autowired
	ITipoDevolucionDao tipodevolucionDao;

	@Autowired
	IAreaEdao areaDao;
	
	@Autowired
	IAreaPlazoDistribucionDao areaplazodao;

	@Override
	public Map<Long, Map<Long, Map<String, Integer>>> devolucionPorTipo(String fechaIni, String fechaFin)
			throws IOException, JSONException, Exception {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI = null;
		Date dateF = null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin);
		} catch (Exception e) {
			return null;
		}
		Iterable<DocumentoReporte> documentoreportes = documentoReporteDao.findDocumentosByEstadoCargo(dateI, dateF);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentoreportes.spliterator(), false)
				.collect(Collectors.toList());
		
		if(documentoslst.size()==0) {
			return null;

		}
		
		Iterable<Proveedor> proveedores = proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedores.spliterator(), false)
				.collect(Collectors.toList());
		Iterable<TipoDevolucion> tiposDevolucion = tipodevolucionDao.findAll();
		Iterable<Map<String, Object>> areasBD = areaDao.listarAll();
		Map<Long, Map<Long, Map<String, Integer>>> cantidadesProveedor = new HashMap<>();
		for (Proveedor proveedor : proveedoreslst) {
			Map<Long, Map<String, Integer>> cantidadTipo = new HashMap<>();
			for (TipoDevolucion tipoDevolucion : tiposDevolucion) {
				int cantProveedorPendiente = 0;
				int cantProveedorDevuelto = 0;
				Map<String, Integer> cantidadPendienteDevuelto = new HashMap<>();
				for (DocumentoReporte documentoreporte : documentoslst) {
					Documento documento = documentoDao.findById(documentoreporte.getDocumentoId()).orElse(null);
					SeguimientoDocumento sd = documento.getUltimoSeguimientoDocumento();
					if (proveedor.getId() == documentoreporte.getProveedorId()) {
						if (documentoreporte.getEstadoCargo() == PENDIENTE) {
							Iterable<TipoDevolucion> tiposdefecto = sd.getEstadoDocumento().getTiposDevolucion();
							List<TipoDevolucion> tiposdefectolst = StreamSupport
									.stream(tiposdefecto.spliterator(), false).collect(Collectors.toList());
							if (tiposdefectolst.contains(tipoDevolucion)) {
								cantProveedorPendiente++;
							}
						} else {
							Iterable<TipoDevolucion> tiposdevolucionDocumento = documento.getTiposDevolucion();
							List<TipoDevolucion> tiposdevolucionDocumentolst = StreamSupport
									.stream(tiposdevolucionDocumento.spliterator(), false).collect(Collectors.toList());
							if (tiposdevolucionDocumentolst.contains(tipoDevolucion)) {
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

	@Override
	public Map<Long, Integer> pendientesCargosPorArea(String fechaIni, String fechaFin)
			throws JSONException, java.io.IOException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI = null;
		Date dateF = null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin);
		} catch (Exception e) {
			return null;
		}
		
		
		Iterable<DocumentoReporte> documentos = documentoReporteDao.buscarvolumenporfechas(dateI, dateF);
		List<DocumentoReporte> documentolst = StreamSupport.stream(documentos.spliterator(), false)
				.collect(Collectors.toList());
		if(documentolst.size()==0) {
			return null;

		}
		Iterable<AreaPlazoDistribucion> areasBD = areaplazodao.findAll();
		Map<Long, Integer> cantidades = new HashMap<>();
		for (AreaPlazoDistribucion area : areasBD) {

			int cantidadPorArea = 0;
			for (DocumentoReporte documentoreporte : documentolst) {
				if (documentoreporte.getArea() == area.getAreaId()) {
					if (documentoreporte.getEstadoCargo() == PENDIENTE) {
						cantidadPorArea++;
					}
				}
			}
			cantidades.put(area.getAreaId(), cantidadPorArea);
		}
		return cantidades;
	}

	@Override
	public Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>> controlCargos(String fechaIni, String fechaFin,
			Long tipoDevolucionId) throws IOException, JSONException, NumberFormatException, ParseException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dtmeses = new SimpleDateFormat("MM");
		Date dateI = null;
		Date dateF = null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin);
		} catch (Exception e) {
			return null;
		}
		Iterable<DocumentoReporte> documentos = null;
		if (tipoDevolucionId == CARGO) {
			documentos = documentoReporteDao.findDocumentosByEstadoDevolucion(dateI, dateF, ENTREGADO, REZAGADO);
		} else if (tipoDevolucionId == REZAGO) {
			documentos = documentoReporteDao.findDocumentosByEstadoDevolucion(dateI, dateF, REZAGADO, NO_DISTRIBUIBLE);
		} else {
			documentos = documentoReporteDao.findDocumentosByEstadoDevolucionDenuncia(dateI, dateF, NO_DISTRIBUIBLE);
		}
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = getListaEntreFechas2(dateI,dateF);
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}
		
		List<DocumentoReporte> reportes = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(reportes.size()==0) {
			return null;

		}
		
		Iterable<Proveedor> proveedores = proveedorDao.findAll();
		Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>> cantidades = new HashMap<>();
		
		for (Proveedor proveedor : proveedores) {
			Map<Integer, Map<Integer, Map<String, Integer>>> keyMeses = new HashMap<>();
			int i=0;
			for(String mesaño : listademeses) {
				int cantidadPendiente = 0;
				int cantidadDevuelto = 0;
				Map<Integer, Map<String, Integer>> mesesCantidad = new HashMap<>();
				Map<String, Integer> cantDevueltoPendiendte = new HashMap<>();
				for (DocumentoReporte documentoreporte : documentos) {
					if(proveedor.getId()==documentoreporte.getProveedorId()) {
						if(dt.format(documentoreporte.getFecha()).equals(mesaño)){
							 
							 if(documentoreporte.getEstadoCargo()==PENDIENTE) {
									cantidadPendiente++;
								}else {
									cantidadDevuelto++;
								}
					}
				}
			}
				i++;
				cantDevueltoPendiendte.put("devuelto", cantidadDevuelto);
				cantDevueltoPendiendte.put("pendiente", cantidadPendiente);
				mesesCantidad.put( Integer.parseInt(dtmeses.format(dt.parse(mesaño))), cantDevueltoPendiendte);
				keyMeses.put(i, mesesCantidad);
			}
			cantidades.put(proveedor.getId(), keyMeses);
		}

		return cantidades;

	}
	
	public List<Date> getListaEntreFechas2(Date fechaInicio, Date fechaFin) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicio);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);

		java.util.List<Date> listaFechas = new java.util.ArrayList<Date>();

		while (!c1.after(c2)) {
			listaFechas.add(c1.getTime());
			c1.add(Calendar.MONTH , 1);
		}
		return listaFechas;
	}

	@Override
	public Map<Long, Map<Integer, Map<Integer, Integer>>> controlCargosPorAreas(String fechaIni, String fechaFin, Long tipoDevolucionId) throws IOException, JSONException, Exception {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		SimpleDateFormat dtmeses = new SimpleDateFormat("MM");
		Date dateI = null;
		Date dateF = null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin);
		} catch (Exception e) {
			return null;
		}
		Iterable<DocumentoReporte> documentos = null;
		if (tipoDevolucionId == CARGO) {
			documentos = documentoReporteDao.findDocumentosByEstadoDevolucion(dateI, dateF, ENTREGADO, REZAGADO);
		} else if (tipoDevolucionId == REZAGO) {
			documentos = documentoReporteDao.findDocumentosByEstadoDevolucion(dateI, dateF, REZAGADO, NO_DISTRIBUIBLE);
		} else {
			documentos = documentoReporteDao.findDocumentosByEstadoDevolucionDenuncia(dateI, dateF, NO_DISTRIBUIBLE);
		}
		
		List<DocumentoReporte> reportes = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(reportes.size()==0) {
			return null;

		}
		
		
		Iterable<AreaPlazoDistribucion> areasBD = areaplazodao.findAll();
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = getListaEntreFechas2(dateI,dateF);
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}
		Map<Long, Map<Integer, Map<Integer, Integer>>> cantidades = new HashMap<>();
		for (AreaPlazoDistribucion area : areasBD) {
			int i=0;
			Map<Integer, Map<Integer, Integer>> mesesArea = new HashMap<>();
			for(String mesaño : listademeses) {
				int cantidadArea =0;
				Map<Integer, Integer> cantArea = new HashMap<>();
				for (DocumentoReporte documentoreporte : documentos) {
					if(area.getAreaId()==documentoreporte.getArea()) {
						if(dt.format(documentoreporte.getFecha()).equals(mesaño)){
							cantidadArea++;
					}
				}
			 }
				i++;
				cantArea.put(Integer.parseInt(dtmeses.format(dt.parse(mesaño))), cantidadArea);
				mesesArea.put(i, cantArea);
			}
			cantidades.put(area.getAreaId(), mesesArea);
		}
		
		return cantidades;
				
	}

}
