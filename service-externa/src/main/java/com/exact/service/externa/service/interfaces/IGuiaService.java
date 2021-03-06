package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.Guia;

public interface IGuiaService {

	Iterable<Guia> listarGuiasCreadas(String matricula) throws ClientProtocolException, IOException, JSONException, Exception;
	Guia crearGuiaRegular(Guia guia, Long usuarioId, String matricula) throws ClientProtocolException, IOException, JSONException;
	int quitarDocumentosGuia(Long guiaId) throws ClientProtocolException, IOException, JSONException;
	int enviarGuiaRegular (Long guiaId, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	int modificarGuia(Guia guia) throws  ClientProtocolException, IOException, JSONException;
	int eliminarGuia(Long guiaId) throws  ClientProtocolException, IOException, JSONException;
	Iterable<Guia> listarGuiasParaProveedor()throws  ClientProtocolException, IOException, JSONException, Exception;
	Iterable<Guia> listarGuiasSinCerrar()throws  ClientProtocolException, IOException, JSONException, Exception;
	Guia listarPorNumeroGuia(String numeroguia, Long verificador, Long tipoGuia) throws ClientProtocolException, IOException, JSONException, Exception;
	Iterable<Guia> listarGuiasPorFechas(String fechaIni, String fechaFin, Long verificador, Long tipoGuia)throws  ClientProtocolException, IOException, JSONException, Exception;
	Guia fechaDescargaGuia(Long id, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	Guia crearGuiaBloque(EnvioMasivo envioMasivo,Long usuarioId ,String codigoGuia, Long proveedorId, String matricula) throws ClientProtocolException, IOException, JSONException;
	Guia enviarGuiaBloque(Long guiaId, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	Iterable<Guia> listarGuiasBloqueCompletadas(Long usuarioId, String matricula) throws ClientProtocolException, IOException, JSONException, Exception;
	Iterable<Guia> listarGuiasBloques(Long usuario_Id,String matricula) throws IOException, Exception;
	Iterable<Documento> listarDocumentosPorGuiaId(Long guiaId) throws ClientProtocolException, IOException, JSONException, Exception;
	Map<Integer,String> cargarResultadosDevolucion(List<Documento> documentos, Long usuarioId) throws ClientProtocolException, IOException, JSONException, Exception;
	Date getFechaLimite(Guia guia) throws ClientProtocolException, IOException, JSONException , URISyntaxException, ParseException;

}
