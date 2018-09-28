package com.exact.service.externa.service.interfaces;

import com.exact.service.externa.entity.DocumentoGuia;

public interface IDocumentoGuiaService {

	DocumentoGuia validarDocumentoGuia(Long guiaId, Long documentoId, Long usuarioId) ;	
	
}
