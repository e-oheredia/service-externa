package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.Documento;

public interface IDocumentoService {

	Documento registrarDocumento(Documento documento, Long idUsuario, MultipartFile file)
			throws IOException, JSONException;
}
