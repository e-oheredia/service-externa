package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import com.exact.service.externa.dao.IEnvioMasivoDao;
import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.edao.interfaces.ITipoDocumentoEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.SeguimientoDocumento;
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
	
	@Value("${storage.autorizaciones}")
	String storageAutorizaciones;
	

	@Override
	public EnvioMasivo registrarEnvioMasivo(EnvioMasivo envioMasivo, Long idUsuario, MultipartFile file)
			throws IOException {
		
		String autogeneradoAnterior = documentoDao.getMaxDocumentoAutogenerado();		
		
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
		
		if (file != null) {
			String rutaAutorizacion = nuevoEnvioId.toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
			envioMasivo.setRutaAutorizacion(rutaAutorizacion);
			MockMultipartFile multipartFile = new MockMultipartFile(rutaAutorizacion, rutaAutorizacion, file.getContentType(), file.getInputStream());
			if (handleFileEdao.upload(multipartFile) != 1) {
				return null;
			}
		}		
		
		String masivoAutogeneradoAnterior = envioMasivoDao.getMaxMasivoAutogenerado();
		String masivoAutogeneradoNuevo = autogeneradoUtils.generateMasivoAutogenerado(masivoAutogeneradoAnterior);
		
		envioMasivo.setMasivoAutogenerado(masivoAutogeneradoNuevo);
		
		EnvioMasivo envioRegistrado =envioMasivoDao.save(envioMasivo);
		
		return envioRegistrado;
	}
	@Override	
	public Iterable<EnvioMasivo> listarEnviosMasivosCreados() throws ClientProtocolException, IOException, JSONException {
		Iterable<EnvioMasivo> enviosCreados = envioMasivoDao.findByUltimoEstadoId(CREADO);
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
			List<Long> tipoDocumentoIds = enviosCreadosList.stream().map(Envio::getTipoDocumentoId).collect(Collectors.toList());
			List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
			List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarByIds(tipoDocumentoIds);
			for (Envio envio: enviosCreadosList) {				
				envio.setRutaAutorizacion(this.storageAutorizaciones + envio.getRutaAutorizacion());				
				for (Documento documento : envio.getDocumentos()) {
					int h = 0;
					while (h < distritos.size()) {
						if (documento.getDistritoId() == Long.valueOf(distritos.get(h).get("id").toString())) {
							documento.setDistrito(distritos.get(h));
							break;
						}
						h++;
					}
				}
				int i = 0; 
				while(i < buzones.size()) {
					if (envio.getBuzonId() == Long.valueOf(buzones.get(i).get("id").toString())) {
						envio.setBuzon(buzones.get(i));
						break;
					}
					i++;
				}
				int j = 0;
				while(j < tiposDocumento.size()) {
					if (envio.getTipoDocumentoId() == Long.valueOf(tiposDocumento.get(j).get("id").toString())) {
						envio.setTipoDocumento(tiposDocumento.get(j));
						break;
					}
					j++;
				}
				
			}
		}			
		return enviosCreadosList;		
	}

}
