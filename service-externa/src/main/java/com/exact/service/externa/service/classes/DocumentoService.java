package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CUSTODIADO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public int custodiarDocumentos(Iterable<Documento> documentos, Long usuarioId) {
		List<SeguimientoDocumento> seguimientosDocumento = new ArrayList<SeguimientoDocumento>();
		for (Documento documento : documentos) {
			SeguimientoDocumento seguimientoDocumento = new SeguimientoDocumento(usuarioId, new EstadoDocumento(CUSTODIADO));
			seguimientoDocumento.setDocumento(documento);
			seguimientosDocumento.add(seguimientoDocumento);
		}
		seguimientoDocumentodao.saveAll(seguimientosDocumento);
		return 1;
	}
	
}
