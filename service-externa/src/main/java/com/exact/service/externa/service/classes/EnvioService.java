package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.DENEGADO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IEnvioDao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.service.interfaces.IEnvioService;
import com.exact.service.externa.utils.IAutogeneradoUtils;

@Service
public class EnvioService implements IEnvioService {
	
	@Autowired
	IDocumentoDao documentoDao;
	
	@Autowired
	IBuzonEdao buzonEdao;
	
	@Autowired
	ITipoDocumentoEdao tipoDocumentoEdao;
	
	@Autowired
	IAutogeneradoUtils autogeneradoUtils;
	
	@Autowired
	IHandleFileEdao handleFileEdao;
	
	@Autowired
	IEnvioDao envioDao;
	
	String observacionAutorizacion = "El documento ha sido autorizado";
	
	@Value("${storage.autorizaciones}")
	String storageAutorizaciones;
	
	

	@Override
	@Transactional
	public Envio registrarEnvio(Envio envio, Long idUsuario, MultipartFile file) throws IOException {
		
		String autogeneradoAnterior = documentoDao.getMaxDocumentoAutogenerado();		
		
		for (Documento documento: envio.getDocumentos()) {
			String autogeneradoNuevo = autogeneradoUtils.generateDocumentoAutogenerado(autogeneradoAnterior);
			documento.setDocumentoAutogenerado(autogeneradoNuevo);
			documento.setEnvio(envio);
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(idUsuario, new EstadoDocumento(CREADO));
			seguimientoDocumento.setDocumento(documento);
			documento.addSeguimientoDocumento(seguimientoDocumento);
			autogeneradoAnterior = autogeneradoNuevo;
		}		
		
		Long envioIdAnterior = envioDao.getMaxId();		
		Long nuevoEnvioId = envioIdAnterior == null ? 1L : envioIdAnterior + 1L;
		
		if (file != null) {
			String rutaAutorizacion = nuevoEnvioId.toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
			envio.setRutaAutorizacion(rutaAutorizacion);
			MockMultipartFile multipartFile = new MockMultipartFile(rutaAutorizacion, rutaAutorizacion, file.getContentType(), file.getInputStream());
			if (handleFileEdao.upload(multipartFile) != 1) {
				return null;
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
			Long idUltimoEstado = (new ArrayList<Documento>(envioNoAutorizado.getDocumentos()))
					.get(0).getUltimoSeguimientoDocumento().getEstadoDocumento().getId();			
			return idUltimoEstado == CREADO || idUltimoEstado == CUSTODIADO;
		}).collect(Collectors.toList());
		
		if (enviosNoAutorizadosActivos.size() != 0) {
			List<Long> buzonIds = enviosNoAutorizadosActivos.stream().map(Envio::getBuzonId).collect(Collectors.toList());
			List<Long> tipoDocumentoIds = enviosNoAutorizadosActivos.stream().map(Envio::getTipoDocumentoId).collect(Collectors.toList());
			List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
			List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarByIds(tipoDocumentoIds);
			for (Envio envio: enviosNoAutorizadosActivos) {
				envio.setRutaAutorizacion(this.storageAutorizaciones + envio.getRutaAutorizacion());
				int i = 0; 
				while(i < buzones.size()) {
					if (envio.getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
						envio.setBuzon(buzones.get(i));
						buzones.remove(i);
						break;
					}
					i++;
				}
				int j = 0;
				while(j < tiposDocumento.size()) {
					if (envio.getTipoDocumentoId() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						envio.setTipoDocumento(tiposDocumento.get(j));
						tiposDocumento.remove(j);
						break;
					}
					j++;
				}
				
			}
		}			
		return enviosNoAutorizadosActivos;		
	}

	@Override
	public Envio autorizarEnvio(Long idEnvio, Long idUsuario) {		
		
		Envio envio = envioDao.findById(idEnvio).orElse(null);
		if (envio == null) {
			return null;
		}		
		envio.setAutorizado(true);		
		envio.getDocumentos().stream().forEach(documento -> {
			SeguimientoDocumento seguimientoDocumento = 
					new SeguimientoDocumento(idUsuario, documento.getUltimoSeguimientoDocumento().getEstadoDocumento(), observacionAutorizacion);
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
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(idUsuario, new EstadoDocumento(DENEGADO));
			seguimientoDocumento.setDocumento(documento);
			documento.addSeguimientoDocumento(seguimientoDocumento);
		});
		
		return envioDao.save(envio);
	}
	
	
	

}
