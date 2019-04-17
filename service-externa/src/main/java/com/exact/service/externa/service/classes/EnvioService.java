package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.DENEGADO;
import static com.exact.service.externa.enumerator.EstadoTipoEnvio.ENVIO_BLOQUE;
import static com.exact.service.externa.enumerator.EstadoTipoEnvio.ENVIO_REGULAR;
import static com.exact.service.externa.enumerator.EstadoTipoGuia.GUIA_BLOQUE;
import static com.exact.service.externa.enumerator.EstadoTipoGuia.GUIA_REGULAR;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IEnvioDao;
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
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.TipoEnvio;
import com.exact.service.externa.service.interfaces.IEnvioService;
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

	@Override
	@Transactional
	public Envio registrarEnvio(Envio envio, Long idUsuario, MultipartFile file, String header) throws IOException, ParseException, MessagingException, JSONException {

		String autogeneradoAnterior = documentoDao.getMaxDocumentoAutogenerado();
		String ruta = "autorizaciones";

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
		
		String perfilUsuario = gestionUsuarioEdao.findPerfil(idUsuario, header);
		TipoEnvio tipoEnvio = new TipoEnvio();
		if(perfilUsuario.equals("USUARIO_REGULAR")) {
			tipoEnvio.setId(ENVIO_REGULAR);
		}else if(perfilUsuario.equals("USUARIO_BLOQUE")) {
			tipoEnvio.setId(ENVIO_BLOQUE);
		}
		envio.setTipoEnvio(tipoEnvio);
		

		if (file != null) {
			String rutaAutorizacion = nuevoEnvioId.toString() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());
			envio.setRutaAutorizacion(rutaAutorizacion);
			MockMultipartFile multipartFile = new MockMultipartFile(rutaAutorizacion, rutaAutorizacion,
					file.getContentType(), file.getInputStream());
			if (handleFileEdao.upload(multipartFile,ruta) != 1) {
				return null;
			}
			String correoslst = gestionUsuarioEdao.obtenerCorreoAutorizador(header);
			if(correoslst!=null) {
				Documento documentoCreado = envio.getDocumentos().iterator().next();
				String nombre = envio.getBuzon().get("nombre").toString();
				String texto="Se ha creado un envio de documento por autorizar con Autogenerado "+ documentoCreado.getDocumentoAutogenerado() +" del usuario "+ nombre;
				mailDao.enviarMensaje(correoslst, mailSubject, texto);
			}
		}
		Envio envioRegistrado = envioDao.save(envio);
		return envioRegistrado;
	}

	@Override
	public Iterable<Envio> listarEnviosNoAutorizados() throws ClientProtocolException, IOException, JSONException {
		Iterable<Envio> enviosNoAutorizados = envioDao.findByAutorizado(false);
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

		if (enviosCreadosList.size() != 0) {
			List<Long> buzonIds = enviosCreadosList.stream().map(Envio::getBuzonId).collect(Collectors.toList());
			List<Long> tipoDocumentoIds = enviosCreadosList.stream().map(Envio::getTipoClasificacionId)
					.collect(Collectors.toList());
			List<Long> distritoIds = new ArrayList<Long>();
			enviosCreadosList.stream().forEach(envioCreado -> {
				envioCreado.getDocumentos().stream().forEach(documento -> {
					distritoIds.add(documento.getDistritoId());
				});
			});

			List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarByIds(distritoIds);
			List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
			List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao
					.listarByIds(tipoDocumentoIds);
			List<Map<String, Object>> productos = (List<Map<String, Object>>) productoEdao.listarAll();
			for (Envio envio : enviosCreadosList) {
				envio.setRutaAutorizacion(this.storageAutorizaciones + envio.getRutaAutorizacion());

				for (Documento documento : envio.getDocumentos()) {
					int h = 0;
					while (h < distritos.size()) {
						if (documento.getDistritoId().longValue() == Long.valueOf(distritos.get(h).get("id").toString())) {
							documento.setDistrito(distritos.get(h));
							break;
						}
						h++;
					}
				}

				int i = 0;
				while (i < buzones.size()) {
					if (envio.getBuzonId().longValue() == Long.valueOf(buzones.get(i).get("id").toString())) {
						envio.setBuzon(buzones.get(i));
						break;
					}
					i++;
				}
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
	public Envio autorizarEnvio(Long idEnvio, Long idUsuario) {

		Envio envio = envioDao.findById(idEnvio).orElse(null);
		if (envio == null) {
			return null;
		}
		envio.setAutorizado(true);
		envio.getDocumentos().stream().forEach(documento -> {
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(idUsuario,
					documento.getUltimoSeguimientoDocumento().getEstadoDocumento(), observacionAutorizacion);
			seguimientoDocumento.setDocumento(documento);
			documento.addSeguimientoDocumento(seguimientoDocumento);
		});
		return envioDao.save(envio);
	}

	@Override
	public Envio denegarEnvio(Long idEnvio, Long idUsuario) {
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

		return envioDao.save(envio);
	}

}
