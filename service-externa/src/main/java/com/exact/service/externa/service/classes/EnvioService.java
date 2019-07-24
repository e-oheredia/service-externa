package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.DENEGADO;
import static com.exact.service.externa.enumerator.EstadoTipoEnvio.ENVIO_REGULAR;
import static com.exact.service.externa.enumerator.EstadoAutorizacionEnum.APROBADA;
import static com.exact.service.externa.enumerator.EstadoAutorizacionEnum.DENEGADA;
import static com.exact.service.externa.enumerator.EstadoAutorizacionEnum.PENDIENTE;
import static com.exact.service.externa.enumerator.TipoPlazoDistribucionEnum.EXPRESS;
import static com.exact.service.externa.enumerator.TipoPlazoDistribucionEnum.REGULAR;
import static com.exact.service.externa.enumerator.TipoPlazoDistribucionEnum.ESPECIAL;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.mail.MessagingException;
import javax.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.ParseException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IEnvioDao;
import com.exact.service.externa.dao.IInconsistenciaDao;
import com.exact.service.externa.dao.IPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.edao.interfaces.IGestionUsuariosEdao;
import com.exact.service.externa.edao.interfaces.ISedeEdao;
import com.exact.service.externa.edao.interfaces.IServiceMailEdao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.edao.interfaces.IProductoEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EstadoAutorizado;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.SeguimientoAutorizado;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.TipoEnvio;
import com.exact.service.externa.service.interfaces.IEnvioService;
import com.exact.service.externa.utils.Encryption;
import com.exact.service.externa.utils.IAutogeneradoUtils;


@Service
public class EnvioService implements IEnvioService {

	@Autowired
	IDocumentoDao documentoDao;

	@Autowired
	IBuzonEdao buzonEdao;

	@Autowired
	IDistritoEdao distritoEdao;

	@Autowired
	ITipoDocumentoEdao tipoDocumentoEdao;

	@Autowired
	IAutogeneradoUtils autogeneradoUtils;

	@Autowired
	IHandleFileEdao handleFileEdao;

	@Autowired
	IEnvioDao envioDao;
	
	@Autowired
	ISedeEdao sedeDao;

	String observacionAutorizacion = "El documento ha sido autorizado";

	@Value("${storage.autorizaciones}")
	String storageAutorizaciones;
	
	@Value("${mail.subject}")
	String mailSubject;
	
	@Value("${mail.text}")
	String mailText;
	
	@Autowired
	IServiceMailEdao mailDao;
	
	@Autowired
	IGestionUsuariosEdao gestionUsuarioEdao;
	
	@Autowired
	IProductoEdao productoEdao;
	
	@Autowired
	IPlazoDistribucionDao plazoDistribucionDao;
	
	@Autowired
	IInconsistenciaDao inconsistenciaDao;

	@Autowired
	Encryption encryption;
	
	
	@Override
	@Transactional
	public Envio registrarEnvio(Envio envio, Long idUsuario, MultipartFile file, String header) throws IOException, ParseException, MessagingException, JSONException {

		String autogeneradoAnterior = documentoDao.getMaxDocumentoAutogenerado();
		String ruta = "autorizaciones";
		String correos = null;

		for (Documento documento : envio.getDocumentos()) {
			String autogeneradoNuevo = autogeneradoUtils.generateDocumentoAutogenerado(autogeneradoAnterior);
			documento.setDocumentoAutogenerado(autogeneradoNuevo);
			documento.setEnvio(envio);
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(idUsuario,
					new EstadoDocumento(CREADO));
			seguimientoDocumento.setDocumento(documento);
			documento.addSeguimientoDocumento(seguimientoDocumento);
			autogeneradoAnterior = autogeneradoNuevo;
		}

		Long envioIdAnterior = envioDao.getMaxId();
		Long nuevoEnvioId = envioIdAnterior == null ? 1L : envioIdAnterior + 1L;
		TipoEnvio tipoEnvio = new TipoEnvio();
		tipoEnvio.setId(ENVIO_REGULAR);
		envio.setTipoEnvio(tipoEnvio);
		if(envio.getPlazoDistribucion().getTipoPlazoDistribucion().getId()==EXPRESS && file==null) {
			correos = gestionUsuarioEdao.obtenerCorreoUTD(header);
			Documento documentoCreado = envio.getDocumentos().iterator().next();
			String nombre = envio.getBuzon().get("nombre").toString();
			@SuppressWarnings("unchecked")
			Map<String,Object> area = (Map<String, Object>) envio.getBuzon().get("area");
			String texto="Se ha creado un envio con autogenerado "+ documentoCreado.getDocumentoAutogenerado() +" del usuario "+ nombre+" de la "
					+ "sede " +envio.getSede().get("nombre")+ " y area " +area.get("nombre")+ " con tipo de servicio "+ envio.getPlazoDistribucion().getNombre();
			mailDao.enviarMensaje(correos, mailSubject, texto);
		}
		if (file != null) {
			EstadoAutorizado estadoAutorizado = new EstadoAutorizado();
			List<SeguimientoAutorizado> lstseguimientoAutorizado = new ArrayList<SeguimientoAutorizado>();
			SeguimientoAutorizado seguimientoAutorizado = new SeguimientoAutorizado();
			estadoAutorizado.setId(PENDIENTE);
			seguimientoAutorizado.setEstadoAutorizado(estadoAutorizado);
			seguimientoAutorizado.setUsuarioId(idUsuario);
			String rutaAutorizacion = nuevoEnvioId.toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			envio.setRutaAutorizacion(rutaAutorizacion);
			MockMultipartFile multipartFile = new MockMultipartFile(rutaAutorizacion, rutaAutorizacion,
					file.getContentType(), file.getInputStream());
			if (handleFileEdao.upload(multipartFile,ruta) != 1) {
				return null;
			}
			Long tipoPlazo = envio.getPlazoDistribucion().getTipoPlazoDistribucion().getId();
			if(tipoPlazo==EXPRESS){
				correos = gestionUsuarioEdao.obtenerCorreoAutorizador(EXPRESS, header);
			}else if(tipoPlazo==REGULAR || tipoPlazo==ESPECIAL) {
				correos = gestionUsuarioEdao.obtenerCorreoAutorizador(REGULAR, header);
			}
			if(correos!=null) {
				Documento documentoCreado = envio.getDocumentos().iterator().next();
				String nombre = envio.getBuzon().get("nombre").toString();
				String texto="Se ha creado un envio de documento por autorizar con Autogenerado "+ documentoCreado.getDocumentoAutogenerado() +" del usuario "+ nombre;
				mailDao.enviarMensaje(correos, mailSubject, texto);
			}
			encryptarseguimiento(seguimientoAutorizado);
			seguimientoAutorizado.setEnvio(envio);
			lstseguimientoAutorizado.add(seguimientoAutorizado);
			Set<SeguimientoAutorizado> sa = new HashSet<SeguimientoAutorizado>(lstseguimientoAutorizado);
			envio.setSeguimientosAutorizado(sa);
		}
		Envio envioRegistrado = envioDao.save(envio);
		return envioRegistrado;
	}

	@Override
	public Iterable<Envio> listarEnviosNoAutorizados() throws IOException, Exception {
		Iterable<Envio> enviosNoAutorizados = envioDao.findByEstadoAutorizado(PENDIENTE);
		List<Envio> enviosNoAutorizadosActivos = StreamSupport.stream(enviosNoAutorizados.spliterator(), false)
				.filter(envioNoAutorizado -> {
					Long idUltimoEstado = (new ArrayList<Documento>(envioNoAutorizado.getDocumentos())).get(0)
							.getUltimoSeguimientoDocumento().getEstadoDocumento().getId();
					return idUltimoEstado == CREADO || idUltimoEstado == CUSTODIADO;
				}).collect(Collectors.toList());

		if (enviosNoAutorizadosActivos.size() != 0) {
			List<Long> buzonIds = enviosNoAutorizadosActivos.stream().map(Envio::getBuzonId)
					.collect(Collectors.toList());
			List<Long> tipoDocumentoIds = enviosNoAutorizadosActivos.stream().map(Envio::getTipoClasificacionId)
					.collect(Collectors.toList());
			List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
			List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao
					.listarByIds(tipoDocumentoIds);
			for (Envio envio : enviosNoAutorizadosActivos) {
				envio.setRutaAutorizacion(this.storageAutorizaciones + envio.getRutaAutorizacion());
				
				for(SeguimientoAutorizado sg : envio.getSeguimientosAutorizado()) {
					descryptarseguimiento(sg);
				}
				
				int i = 0;
				while (i < buzones.size()) {
					if (envio.getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
						envio.setBuzon(buzones.get(i));
						break;
					}
					i++;
				}
				int j = 0;
				while (j < tiposDocumento.size()) {
					if (envio.getTipoClasificacionId() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						envio.setClasificacion(tiposDocumento.get(j));
						break;
					}
					j++;
				}
			}
		}
		return enviosNoAutorizadosActivos;
	}

	@Override
	public Iterable<Envio> listarEnviosCreados(String matricula) throws IOException, Exception {
		
		Map<String,Object> sede  = sedeDao.findSedeByMatricula(matricula);
		Iterable<Envio> enviosCreados = envioDao.findByUltimoEstadoId(CREADO,Long.valueOf(sede.get("id").toString()));
		List<Envio> enviosCreadosList = StreamSupport.stream(enviosCreados.spliterator(), false)
				.collect(Collectors.toList());

		if (!enviosCreadosList.isEmpty()) {
			List<Long> buzonIds = enviosCreadosList.stream().map(Envio::getBuzonId).collect(Collectors.toList());
			List<Long> tipoDocumentoIds = enviosCreadosList.stream().map(Envio::getTipoClasificacionId)
					.collect(Collectors.toList());
//			List<Long> distritoIds = new ArrayList<Long>();
//			enviosCreadosList.stream().forEach(envioCreado -> {
//				envioCreado.getDocumentos().stream().forEach(documento -> {
//					distritoIds.add(documento.getDistritoId());
//				});
//			});
			
			//distritoIds=distritoIds.stream().distinct().collect(Collectors.toList());
			buzonIds=buzonIds.stream().distinct().collect(Collectors.toList());
			List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
			List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao
					.listarByIds(tipoDocumentoIds);
			List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
			for (Envio envio : enviosCreadosList) {
				envio.setRutaAutorizacion(this.storageAutorizaciones + envio.getRutaAutorizacion());
				for(SeguimientoAutorizado sg : envio.getSeguimientosAutorizado()) {
					descryptarseguimiento(sg);
				}
//				for (Documento documento : envio.getDocumentos()) {
//					int h = 0;
//					while (h < distritos.size()) {
//						if (documento.getDistritoId().longValue() == Long.valueOf(distritos.get(h).get("id").toString())) {
//							documento.setDistrito(distritos.get(h));
//							break;
//						}
//						h++;
//					}
//				}

				envio.setBuzon(buzones.get(0));
				
//				int i = 0;
//				while (i < buzones.size()) {
//					if (envio.getBuzonId().longValue() == Long.valueOf(buzones.get(i).get("id").toString())) {
//						envio.setBuzon(buzones.get(i));
//						break;
//					}
//					i++;
//				}
				int j = 0;
				while (j < tiposDocumento.size()) {
					if (envio.getTipoClasificacionId().longValue() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						envio.setClasificacion(tiposDocumento.get(j));
						break;
					}
					j++;
				}
				
				int k = 0;
				while (k < productos.size()) {
					if (envio.getProductoId().longValue() == Long.valueOf(productos.get(k).get("id").toString())) {
						envio.setProducto(productos.get(k));
						break;
					}
					k++;
				}

			}
		}
		return enviosCreadosList;
	}

	@Override
	public Envio autorizarEnvio(Long idEnvio, Long idUsuario, String header,String nombreUsuario) throws ParseException, IOException, JSONException {

		Envio envio = envioDao.findById(idEnvio).orElse(null);
		if (envio == null) {
			return null;
		}
		//String nombreUsuario = gestionUsuarioEdao.obtenerNombreUsuario(idUsuario, header);
		EstadoAutorizado estadoAutorizado = new EstadoAutorizado();
		List<SeguimientoAutorizado> lstseguimientoAutorizado = new ArrayList<SeguimientoAutorizado>();
		SeguimientoAutorizado seguimientoAutorizado = new SeguimientoAutorizado();
		estadoAutorizado.setId(APROBADA);
		seguimientoAutorizado.setEstadoAutorizado(estadoAutorizado);
		seguimientoAutorizado.setUsuarioId(idUsuario);
		//ACA VA EL METODO DE ENCRYPTAR NOMBREUSUARIO
		seguimientoAutorizado.setNombreUsuario(nombreUsuario);
		encryptarseguimiento(seguimientoAutorizado);
		envio.getDocumentos().stream().forEach(documento -> {
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(idUsuario,
					documento.getUltimoSeguimientoDocumento().getEstadoDocumento(), observacionAutorizacion);
			seguimientoDocumento.setDocumento(documento);
			documento.addSeguimientoDocumento(seguimientoDocumento);
		});
		seguimientoAutorizado.setEnvio(envio);
		lstseguimientoAutorizado.add(seguimientoAutorizado);
		Set<SeguimientoAutorizado> sa = new HashSet<SeguimientoAutorizado>(lstseguimientoAutorizado);
		envio.setSeguimientosAutorizado(sa);
		return envioDao.save(envio);
	}

	@Override
	public Envio denegarEnvio(Long idEnvio, Long idUsuario, String header) throws ParseException, IOException, JSONException{
		Envio envio = envioDao.findById(idEnvio).orElse(null);
		if (envio == null) {
			return null;
		}
		envio.getDocumentos().stream().forEach(documento -> {
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(idUsuario,
					new EstadoDocumento(DENEGADO));
			seguimientoDocumento.setDocumento(documento);
			documento.addSeguimientoDocumento(seguimientoDocumento);
		});
		String nombreUsuario = gestionUsuarioEdao.obtenerNombreUsuario(idUsuario, header);
		EstadoAutorizado estadoAutorizado = new EstadoAutorizado();
		List<SeguimientoAutorizado> lstseguimientoAutorizado = new ArrayList<SeguimientoAutorizado>();
		SeguimientoAutorizado seguimientoAutorizado = new SeguimientoAutorizado();
		estadoAutorizado.setId(DENEGADA);
		seguimientoAutorizado.setEstadoAutorizado(estadoAutorizado);
		seguimientoAutorizado.setUsuarioId(idUsuario);
		seguimientoAutorizado.setNombreUsuario(nombreUsuario);
		seguimientoAutorizado.setEnvio(envio);
		lstseguimientoAutorizado.add(seguimientoAutorizado);
		Set<SeguimientoAutorizado> sa = new HashSet<SeguimientoAutorizado>(lstseguimientoAutorizado);
		envio.setSeguimientosAutorizado(sa);

		return envioDao.save(envio);
	}

	@Override
	public Iterable<Envio> listarEnviosAutorizacion(String fechaIni, String fechaFin) throws IOException, Exception {
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Iterable<Envio> envioAutorizaciones = null;
		List<Envio> lstenvioAutorizaciones=null;
		if(dateF.compareTo(dateI)>0 || dateF.equals(dateI)) {
			envioAutorizaciones = envioDao.listarEnviosAutorizacion(dateI,dateF);
			lstenvioAutorizaciones = StreamSupport.stream(envioAutorizaciones.spliterator(),false).collect(Collectors.toList());
		}else {
			return null;
		}
		if(lstenvioAutorizaciones.isEmpty()) {
			return null;
		}
		List<Long> buzonIds = lstenvioAutorizaciones.stream().map(Envio::getBuzonId).collect(Collectors.toList());
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
		List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
		for(Envio envio: lstenvioAutorizaciones) {
			for( SeguimientoAutorizado sa : envio.getSeguimientosAutorizado()) {
				descryptarseguimiento(sa);
			}
			envio.setRutaAutorizacion(this.storageAutorizaciones + envio.getRutaAutorizacion());
			int i = 0;
			while (i < buzones.size()) {
				if (envio.getBuzonId().longValue() == Long.valueOf(buzones.get(i).get("id").toString())) {
					envio.setBuzon(buzones.get(i));
					break;
				}
				i++;
			}
			int k = 0;
			while (k < productos.size()) {
				if (envio.getProductoId().longValue() == Long.valueOf(productos.get(k).get("id").toString())) {
					envio.setProducto(productos.get(k));
					break;
				}
				k++;
			}

		}
		
		
		return lstenvioAutorizaciones;
	}

	@Override
	@Transactional
	public Envio modificaPlazo(Long idEnvio, PlazoDistribucion plazo, Long idUsuario, String header) throws ParseException, IOException, JSONException {
		Envio envioBD = envioDao.findEnvioConAutorizacion(idEnvio);
		if(envioBD == null) {
			return null;
		}
		if(envioBD.getPlazoDistribucion().getId()==plazo.getId()) {
			return null;
		}
		envioBD.setPlazoDistribucion(plazo);
		List<PlazoDistribucion> plazos = (List<PlazoDistribucion>) plazoDistribucionDao.findAll();
		for(int i=0;i<plazos.size();i++) {
			if(envioBD.getPlazoDistribucion().getId().longValue()==plazos.get(i).getId().longValue()) {
				envioBD.setPlazoDistribucion(plazos.get(i));
				break;
			}
		}
		
	
		String nombreUsuario = gestionUsuarioEdao.obtenerNombreUsuario(idUsuario, header); 	//============================================================
		
		EstadoAutorizado estadoAutorizado = new EstadoAutorizado();
		List<SeguimientoAutorizado> lstseguimientoAutorizado = new ArrayList<SeguimientoAutorizado>();
		SeguimientoAutorizado seguimientoAutorizado = new SeguimientoAutorizado();
		estadoAutorizado.setId(APROBADA);
		seguimientoAutorizado.setEstadoAutorizado(estadoAutorizado);
		seguimientoAutorizado.setUsuarioId(idUsuario);
		seguimientoAutorizado.setNombreUsuario(nombreUsuario);
		encryptarseguimiento(seguimientoAutorizado);
		seguimientoAutorizado.setEnvio(envioBD);
		lstseguimientoAutorizado.add(seguimientoAutorizado);
		Set<SeguimientoAutorizado> sa = new HashSet<SeguimientoAutorizado>(lstseguimientoAutorizado);
		envioBD.setSeguimientosAutorizado(sa);
		
		return envioDao.save(envioBD);
		
	}

	@Override
	public Iterable<Envio> listarEnviosInconsistencias(String fechaIni, String fechaFin) throws IOException, Exception {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Iterable<Envio> enviosInconsistencias =null;
		List<Envio> enviosInconsistenciaslst = null;
		if(dateF.compareTo(dateI)>0 || dateF.equals(dateI)) {
			enviosInconsistencias = envioDao.listarEnviosInconsistencias(dateI, dateF);
			enviosInconsistenciaslst = StreamSupport.stream(enviosInconsistencias.spliterator(),false).collect(Collectors.toList());
			if(enviosInconsistenciaslst.isEmpty()) {
				return null;
			}
		}else {
			return null;
		}
		
		List<Long> buzonIds = enviosInconsistenciaslst.stream().map(Envio::getBuzonId).collect(Collectors.toList());
		List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);

		for(Envio envio : enviosInconsistenciaslst) {
			
			int i = 0;
			
			for(SeguimientoAutorizado sg : envio.getSeguimientosAutorizado()) {
			descryptarseguimiento(sg);
			}
			
			while (i < buzones.size()) {
				if (envio.getBuzonId().longValue() == Long.valueOf(buzones.get(i).get("id").toString())) {
					envio.setBuzon(buzones.get(i));
					break;
				}
				i++;
			}
		}
		
		return enviosInconsistenciaslst;
	}
	
	
	public void descryptarseguimiento(SeguimientoAutorizado  seguimiento) throws UnsupportedEncodingException, IOException {
		seguimiento.setNombreUsuario(encryption.decrypt( seguimiento.getNombreUsuarioencryptado()));
	}
	
	public void encryptarseguimiento(SeguimientoAutorizado  seguimiento) throws IOException {
		seguimiento.setNombreUsuarioencryptado(encryption.encrypt(seguimiento.getNombreUsuario()));
	}	

}
