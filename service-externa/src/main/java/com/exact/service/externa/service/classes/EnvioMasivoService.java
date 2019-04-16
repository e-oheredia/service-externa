package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoTipoEnvio.ENVIO_REGULAR;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.mail.MessagingException;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IEnvioDao;
import com.exact.service.externa.dao.IEnvioMasivoDao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.edao.interfaces.IGestionUsuariosEdao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.edao.interfaces.ISedeEdao;
import com.exact.service.externa.edao.interfaces.IServiceMailEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.TipoEnvio;
import com.exact.service.externa.service.interfaces.IEnvioMasivoService;
import com.exact.service.externa.utils.IAutogeneradoUtils;

@Service
public class EnvioMasivoService implements IEnvioMasivoService {
	
	@Autowired
	IDocumentoDao documentoDao;
	
	@Autowired
	IBuzonEdao buzonEdao;
	
	@Autowired
	IDistritoEdao distritoEdao;
	
	@Autowired
	ITipoDocumentoEdao tipoDocumentoEdao;
	
	@Autowired
	IEnvioMasivoDao envioMasivoDao;
	
	@Autowired
	IEnvioDao envioDao;
	
	@Autowired
	IAutogeneradoUtils autogeneradoUtils;
	
	@Autowired
	IHandleFileEdao handleFileEdao;
	
	@Autowired
	ISedeEdao sedeDao;
	
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
	

	@Override
	public EnvioMasivo registrarEnvioMasivo(EnvioMasivo envioMasivo, Long idUsuario, MultipartFile file, String header)
			throws IOException, ParseException, MessagingException, JSONException {
		
		String autogeneradoAnterior = documentoDao.getMaxDocumentoAutogenerado();	
		String ruta ="autorizaciones";
		
		for (Documento documento: envioMasivo.getDocumentos()) {
			String autogeneradoNuevo = autogeneradoUtils.generateDocumentoAutogenerado(autogeneradoAnterior);
			documento.setDocumentoAutogenerado(autogeneradoNuevo);
			documento.setEnvio(envioMasivo);
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(idUsuario, new EstadoDocumento(CREADO));
			seguimientoDocumento.setDocumento(documento);
			documento.addSeguimientoDocumento(seguimientoDocumento);
			autogeneradoAnterior = autogeneradoNuevo;
		}		
		
		Long envioIdAnterior = envioDao.getMaxId();		
		Long nuevoEnvioId = envioIdAnterior == null ? 1L : envioIdAnterior + 1L;
		
		String masivoAutogeneradoAnterior = envioMasivoDao.getMaxMasivoAutogenerado();
		String masivoAutogeneradoNuevo = autogeneradoUtils.generateMasivoAutogenerado(masivoAutogeneradoAnterior);
		envioMasivo.setMasivoAutogenerado(masivoAutogeneradoNuevo);
		
		String perfilUsuario = gestionUsuarioEdao.findPerfil(idUsuario, header);
		TipoEnvio tipoEnvio = new TipoEnvio();
		if(perfilUsuario.equals("USUARIO_REGULAR")) {
			tipoEnvio.setId(ENVIO_REGULAR);
		}
		envioMasivo.setTipoEnvio(tipoEnvio);
		
		if (file != null) {
			String rutaAutorizacion = nuevoEnvioId.toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
			envioMasivo.setRutaAutorizacion(rutaAutorizacion);
			MockMultipartFile multipartFile = new MockMultipartFile(rutaAutorizacion, rutaAutorizacion, file.getContentType(), file.getInputStream());
			if (handleFileEdao.upload(multipartFile,ruta) != 1) {
				return null;
			}
			String correolst = gestionUsuarioEdao.obtenerCorreoAutorizador(header);
			if(correolst!=null) {
				String nombre = envioMasivo.getBuzon().get("nombre").toString();
				String texto="Se ha creado un envio masivo de documentos con autogenerado "+ envioMasivo.getMasivoAutogenerado() +" del usuario "+nombre;
				mailDao.enviarMensaje(correolst, mailSubject, texto);
			}
		}		
		return envioMasivoDao.save(envioMasivo);
	}
	@Override	
	public Iterable<EnvioMasivo> listarEnviosMasivosCreados(String matricula) throws ClientProtocolException, IOException, JSONException {
		Map<String,Object> sede  = sedeDao.findSedeByMatricula(matricula);
		Iterable<EnvioMasivo> enviosCreados = envioMasivoDao.findByUltimoEstadoId(CREADO,Long.valueOf(sede.get("id").toString()));
		List<EnvioMasivo> enviosCreadosList = StreamSupport.stream(enviosCreados.spliterator(), false).collect(Collectors.toList());
		
		if (enviosCreadosList.size() != 0) {
			List<Long> distritoIds = new ArrayList<Long>();
			enviosCreadosList.stream().forEach(envioCreado -> {
				envioCreado.getDocumentos().stream().forEach(documento -> {
					distritoIds.add(documento.getDistritoId());
				});
			});

			List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarByIds(distritoIds);
			List<Long> buzonIds = enviosCreadosList.stream().map(Envio::getBuzonId).collect(Collectors.toList());
			List<Long> tipoDocumentoIds = enviosCreadosList.stream().map(Envio::getTipoClasificacionId).collect(Collectors.toList());
			List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
			List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarByIds(tipoDocumentoIds);
			for (Envio envio: enviosCreadosList) {				
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
				while(i < buzones.size()) {
					if (envio.getBuzonId().longValue() == Long.valueOf(buzones.get(i).get("id").toString())) {
						envio.setBuzon(buzones.get(i));
						break;
					}
					i++;
				}
				int j = 0;
				while(j < tiposDocumento.size()) {
					if (envio.getTipoClasificacionId().longValue() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						envio.setClasificacion(tiposDocumento.get(j));
						break;
					}
					j++;
				}
				
			}
		}			
		return enviosCreadosList;		
	}

}
