package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.Guia;

public interface IGuiaService {

	Iterable<Guia> listarGuiasCreadas(String matricula) throws ClientProtocolException, IOException, JSONException;
	Guia crearGuiaRegular(Guia guia, Long usuarioId, String matricula) throws ClientProtocolException, IOException, JSONException;
	int quitarDocumentosGuia(Long guiaId) throws ClientProtocolException, IOException, JSONException;
	int enviarGuiaRegular (Long guiaId, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	int modificarGuia(Guia guia) throws  ClientProtocolException, IOException, JSONException;
	int eliminarGuia(Long guiaId) throws  ClientProtocolException, IOException, JSONException;
	Iterable<Guia> listarGuiasRegularParaProveedor()throws  ClientProtocolException, IOException, JSONException, Exception;
	Iterable<Guia> listarGuiasSinCerrar()throws  ClientProtocolException, IOException, JSONException;
	Guia listarPorNumeroGuia(String numeroguia) throws ClientProtocolException, IOException, JSONException;
	Iterable<Guia> listarGuiasPorFechas(String fechaIni, String fechaFin)throws  ClientProtocolException, IOException, JSONException;
	Guia fechaDescargaGuia(Long id, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	Guia crearGuiaBloque(EnvioMasivo envioMasivo,Long usuarioId ,String codigoGuia, Long proveedorId, String matricula) throws ClientProtocolException, IOException, JSONException;
	Guia enviarGuiaBloque(Long guiaId, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	Iterable<Guia> listarGuiasBloqueParaProveedor() throws ClientProtocolException, IOException, JSONException, Exception;
}
