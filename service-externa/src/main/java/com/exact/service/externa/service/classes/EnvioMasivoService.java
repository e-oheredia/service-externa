package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.IEnvioDao;
import com.exact.service.externa.dao.IEnvioMasivoDao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.entity.Documento;
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
	IEnvioMasivoDao envioMasivoDao;
	
	@Autowired
	IEnvioDao envioDao;
	
	@Autowired
	IAutogeneradoUtils autogeneradoUtils;
	
	@Autowired
	IHandleFileEdao handleFileEdao;
	

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

}
