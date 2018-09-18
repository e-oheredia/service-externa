package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;

import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.dao.IDocumentoDao;
import com.exact.service.externa.dao.ISeguimientoDocumentoDao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.service.interfaces.IDocumentoService;
import com.exact.service.externa.utils.IAutogeneradoUtils;

@Service
public class DocumentoService implements IDocumentoService {

	@Autowired
	private IDocumentoDao documentoDao;

	@Autowired
	private IAutogeneradoUtils autogeneradoUtils;

	@Autowired
	private ISeguimientoDocumentoDao seguimientoDocumentodao;

	@Autowired
	private IHandleFileEdao handleFileEdao;

	@Override
	@Transactional
	public Documento registrarDocumento(Documento documento, Long idUsuario, MultipartFile file)
			throws IOException, JSONException {
		String autogeneradoAnterior = documentoDao.getMaxDocumentoAutogenerado();
		String autogeneradoNuevo = autogeneradoUtils.generateDocumentoAutogenerado(autogeneradoAnterior);
		
		if (file != null) {
			String rutaAutorizacion = autogeneradoNuevo + "." + FilenameUtils.getExtension(file.getOriginalFilename());
			MockMultipartFile multipartFile = new MockMultipartFile(rutaAutorizacion, rutaAutorizacion, file.getContentType(), file.getInputStream());
			if (handleFileEdao.upload(multipartFile) != 1) {
				return null;
			}
		}		
		documento.setDocumentoAutogenerado(autogeneradoNuevo);
		SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(idUsuario, new EstadoDocumento(CREADO));
		seguimientoDocumento.setDocumento(documento);
		documentoDao.save(documento);
		seguimientoDocumentodao.save(seguimientoDocumento);
		return documento;
	}

}
