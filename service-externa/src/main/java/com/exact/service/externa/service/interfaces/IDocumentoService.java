package com.exact.service.externa.service.interfaces;
import com.exact.service.externa.entity.Documento;

public interface IDocumentoService {

	int custodiarDocumentos(Iterable<Documento> documentos, Long usuarioId);
}
