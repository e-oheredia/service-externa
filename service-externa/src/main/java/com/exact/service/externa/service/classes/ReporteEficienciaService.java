package com.exact.service.externa.service.classes;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IDocumentoGuiaDao;
import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IGuiaDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.dao.ISeguimientoDocumentoDao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.service.interfaces.IGuiaService;
import com.exact.service.externa.service.interfaces.IReporteEficienciaService;

import io.jsonwebtoken.io.IOException;

import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.DENTRO_PLAZO;
import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.FUERA_PLAZO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;

@Service
public class ReporteEficienciaService implements IReporteEficienciaService {

	@Autowired
	IDocumentoReporteDao documentoReporteDao;

	@Autowired
	IProveedorDao proveedorDao;

	@Autowired
	IPlazoDistribucionDao plazoDao;

	@Autowired
	IDocumentoGuiaDao documentoguiaDao;

	@Autowired
	IGuiaService guiaService;

	@Autowired
	IGuiaDao guiaDao;

	@Autowired
	IDocumentoDao documentoDao;

	@Autowired
	ISeguimientoDocumentoDao seguimientodocumento;

	private static final Log Logger = LogFactory.getLog(ReporteEficienciaService.class);

	@Override
	public Map<Long, Map<String, Integer>> eficienciaPorCourier(String fechaIni, String fechaFin)
			throws IOException, JSONException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI = null;
		Date dateF = null;
		Map<Long, Map<String, Integer>> total = new HashMap<>();
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin);
		} catch (Exception e) {
			return null;
		}
		Iterable<DocumentoReporte> documentosPlazo = documentoReporteDao.findDocumentosByDentroFueraPlazo(dateI, dateF);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentosPlazo.spliterator(), false)
				.collect(Collectors.toList());
		if (documentoslst.size() == 0) {
			return null;
		}
		Iterable<Proveedor> proveedores = proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedores.spliterator(), false)
				.collect(Collectors.toList());
		for (Proveedor proveedor : proveedoreslst) {
			Map<String, Integer> cantidades = new HashMap<>();
			int cantDentroPlazo = 0;
			int cantFueraPlazo = 0;
			for (DocumentoReporte documentoreporte : documentoslst) {
				if (proveedor.getId() == documentoreporte.getProveedorId()) {
					if (documentoreporte.getTiempoEntrega() == DENTRO_PLAZO) {
						cantDentroPlazo++;
					} else {
						cantFueraPlazo++;
					}
				}
			}
			cantidades.put("dentroplazo", cantDentroPlazo);
			cantidades.put("fueraplazo", cantFueraPlazo);
			total.put(proveedor.getId(), cantidades);
		}
		return total;
	}

	@Override
	public Map<Long, Map<Long, Map<String, Integer>>> eficienciaPorPlazoPorCourier(String fechaIni, String fechaFin)
			throws IOException, JSONException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI = null;
		Date dateF = null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin);
		} catch (Exception e) {
			return null;
		}
		Map<Long, Map<Long, Map<String, Integer>>> proveedorcantidad = new HashMap<>();
		Iterable<DocumentoReporte> documentosPlazo = documentoReporteDao.findDocumentosByDentroFueraPlazo(dateI, dateF);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentosPlazo.spliterator(), false)
				.collect(Collectors.toList());
		Iterable<Proveedor> proveedores = proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedores.spliterator(), false)
				.collect(Collectors.toList());
		for (Proveedor proveedor : proveedoreslst) {
			Map<Long, Map<String, Integer>> cantidadPlazo = new HashMap<>();
			for (PlazoDistribucion plazo : proveedor.getPlazosDistribucion()) {
				Map<String, Integer> cantidadDentroFuera = new HashMap<>();
				int cantidaddentroplazo = 0;
				int cantidadfueraplazo = 0;
				for (DocumentoReporte documentoreporte : documentoslst) {
					if (proveedor.getId() == documentoreporte.getProveedorId()) {
						if (plazo.getId() == documentoreporte.getPlazoId()) {
							if (documentoreporte.getTiempoEntrega() == DENTRO_PLAZO) {
								cantidaddentroplazo++;

							} else {
								cantidadfueraplazo++;
							}
						}
					}
				}
				cantidadDentroFuera.put("dentroplazo", cantidaddentroplazo);
				cantidadDentroFuera.put("fueraplazo", cantidadfueraplazo);
				cantidadPlazo.put(plazo.getId(), cantidadDentroFuera);
			}
			proveedorcantidad.put(proveedor.getId(), cantidadPlazo);
		}

		return proveedorcantidad;

	}

	@Override
	public Map<Long, Map<String, Integer>> detalleEficienciaPorCourier(String fechaIni, String fechaFin,
			Long proveedorId) throws IOException, JSONException, ClientProtocolException, java.io.IOException,
			URISyntaxException, ParseException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date dateI = null;
		Date dateF = null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin);
		} catch (Exception e) {
			return null;
		}

		Iterable<DocumentoReporte> documentos = documentoReporteDao.findDocumentosByProveedorId(dateI, dateF,
				proveedorId);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentos.spliterator(), false)
				.collect(Collectors.toList());
		Proveedor proveedor = proveedorDao.findById(proveedorId).orElse(null);
		Map<Long, Map<String, Integer>> cantidadDetalle = new HashMap<>();

		for (PlazoDistribucion plazo : proveedor.getPlazosDistribucion()) {
			Map<Integer, Integer> cantidadTiempoEnvio = new HashMap<>();
			Map<Long, Map<Integer, Integer>> cantidadPlazo = new HashMap<>();
			int cantidadPlazos = 0;
			for (DocumentoReporte documentoreporte : documentoslst) {
				Map<Long, Map<Integer, Integer>> cantidadPlazso = new HashMap<>();
				if (plazo.getId() == documentoreporte.getPlazoId()) {
					Long valor = calcularHoras(documentoreporte);

					// faltaprobar
				}
			}

		}
		return cantidadDetalle;
	}

	public static LocalDateTime toLocalDateTime(Calendar calendar) {
		if (calendar == null) {
			return null;
		}
		TimeZone tz = calendar.getTimeZone();
		ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
		return LocalDateTime.ofInstant(calendar.toInstant(), zid);
	}

	public Long calcularHoras(DocumentoReporte documentoreporte)
			throws ClientProtocolException, java.io.IOException, JSONException, URISyntaxException, ParseException {
		Documento documento = documentoDao.findById(documentoreporte.getDocumentoId()).orElse(null);
		SeguimientoDocumento seguimientoDocumento = documento.getSeguimientoDocumentoByEstadoId(ENTREGADO);
		DocumentoGuia dc = documentoguiaDao.findByDocumentoId(documentoreporte.getDocumentoId());
		Guia guia = guiaDao.findById(dc.getGuia().getId()).orElse(null);
		Date fechaLimite = guiaService.getFechaLimite(guia);
		Calendar fechalimitecal = Calendar.getInstance();
		Calendar fechaEntrega = Calendar.getInstance();
		fechalimitecal.setTime(fechaLimite);
		fechaEntrega.setTime(seguimientoDocumento.getFecha());
		LocalDateTime v1 = toLocalDateTime(fechalimitecal);
		LocalDateTime v2 = toLocalDateTime(fechaEntrega);
		Duration duration = Duration.between(v1, v2);
		Long hours = duration.toHours();
		return hours;
	}

	
	@Override
	public Map<Integer, Map<Integer, Map<Integer, Integer>>> detalleeficienciaporCourier(String fechaIni,
			String fechaFin) throws IOException, JSONException, ClientProtocolException, java.io.IOException,
			URISyntaxException, ParseException {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

		Date dateI = null;
		Date dateF = null;
		Map<Long, Map<String, Integer>> total = new HashMap<>();
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin);
		} catch (Exception e) {
			return null;
		}

		Map<Long, Map<Long, Map<String, Integer>>> proveedorcantidad = new HashMap<>();
		Iterable<DocumentoReporte> documentosPlazo = documentoReporteDao.buscarvolumenporfechas4(dateI, dateF);
		List<DocumentoReporte> documentoslst = StreamSupport.stream(documentosPlazo.spliterator(), false)
				.collect(Collectors.toList());
		Iterable<Proveedor> proveedores = proveedorDao.findAll();
		List<Proveedor> proveedoreslst = StreamSupport.stream(proveedores.spliterator(), false)
				.collect(Collectors.toList());
		//List<PlazoDistribucion> plazoss= new ArrayList<>();
		Map<Integer, Map<Integer, Map<Integer, Integer>>> cantidadporproveedorplazos = new HashMap<>();
		Map<Long, Long> cantidadTiempoEnvio = new HashMap<>();

		
		/*for(DocumentoReporte dr : documentoslst) {
			cantidadTiempoEnvio.put(dr.getId(), calcularHoras(dr));
		}**/
		
		
		
		
		
		for (Proveedor proveedor : proveedoreslst) {
			Map<Integer, Map<Integer, Integer>> cantidadporplazos = new HashMap<>();
			 List<PlazoDistribucion> plazoss = new ArrayList<PlazoDistribucion>(proveedor.getPlazosDistribucion()); 
			for (PlazoDistribucion plazo : proveedor.getPlazosDistribucion()) {
				int c=0;
				// for (PlazoDistribucion plazod : proveedor.getPlazosDistribucion()) {
				Map<Integer, Integer> cantidadporplazo = new HashMap<>();	
				int[ ] plazos = new int[10000];
				for (DocumentoReporte documentoreporte : documentoslst) {
					if (documentoreporte.getPlazoId() == plazo.getId() && documentoreporte.getProveedorId()==proveedor.getId()) {
						int ab=0;
						int b=0;
						Date fechaentrega = seguimientodocumento.buscarpordocumento(documentoreporte.getDocumentoId());
						/*for(PlazoDistribucion plazod : proveedor.getPlazosDistribucion()) {
							plazoss.add(plazod);
						}*/
						Collections.sort(plazoss,(x,y)->((Integer)x.getTiempoEnvio()).compareTo((Integer)y.getTiempoEnvio()));						
						for (PlazoDistribucion plazod : plazoss) {
							//Modo 1
							Guia guia = guiaDao.findGuiabydocumentoid(documentoreporte.getDocumentoId());
							guia.setPlazoDistribucion(plazod);
							Date fechalimitesub = guiaService.getFechaLimite(guia);
							if (dt.parse(dt.format(fechalimitesub)).compareTo(dt.parse(dt.format(fechaentrega))) >= 0) {
								if(b==0) {
									plazos[(int)(long)plazod.getId()]=plazos[(int)(long)plazod.getId()]+1 ;
									ab=1;
									b=1;
								}
							}
							cantidadporplazo.put((int)(long)plazod.getId(), plazos[(int)(long)plazod.getId()]);
							c=1;
						}
						
						if(ab==0) {
							plazos[0]++;
						}
					}
					cantidadporplazo.put(0,plazos[0]);
				}				
				for (PlazoDistribucion plazod : plazoss) {
				if(c==0) {
					cantidadporplazo.put((int)(long)plazod.getId(), plazos[(int)(long)plazod.getId()]);
				}
				
				}

				
				/*
				for(PlazoDistribucion plazod : proveedor.getPlazosDistribucion()) {
					
					for (DocumentoReporte documentoreporte : documentoslst) {
						
						Guia guia = guiaDao.findGuiabydocumentoid(documentoreporte.getDocumentoId());
						guia.setPlazoDistribucion(plazod);
						Date fechalimitesub = guiaService.getFechaLimite(guia);
						Date fechaentrega = seguimientodocumento.buscarpordocumento(documentoreporte.getDocumentoId());

						if (fechalimitesub.compareTo(fechaentrega) >= 0) {
							cantidaddedocumentos++;
						}
						cantidadporplazo.put((int) (long) plazod.getId(), cantidaddedocumentos);
						
						
					}
				}*/	
				cantidadporplazos.put((int) (long) plazo.getId(), cantidadporplazo);

				// }
			}
			cantidadporproveedorplazos.put((int) (long) proveedor.getId(), cantidadporplazos);
		}

		return cantidadporproveedorplazos;
	}
	

}
