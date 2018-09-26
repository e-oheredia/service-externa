package com.exact.service.externa.service.classes;

import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.CREADO;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IGuiaDao;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.service.interfaces.IGuiaService;

@Service
public class GuiaService implements IGuiaService{

	@Autowired
	IGuiaDao guiaDao;
	
	@Override
	public Iterable<Guia> listarGuiasCreadas() throws ClientProtocolException, IOException, JSONException {
		Iterable<Guia> guiasCreadas = guiaDao.findByUltimoEstadoId(CREADO);
		List<Guia> guiasCreadasList = StreamSupport.stream(guiasCreadas.spliterator(), false).collect(Collectors.toList());
		
		/*if (guiasCreadasList.size() != 0) {
			List<Long> buzonIds = guiasCreadasList.stream().map(Guia::getBuzonId).collect(Collectors.toList());
			List<Long> tipoDocumentoIds = guiasCreadasList.stream().map(Envio::getTipoDocumentoId).collect(Collectors.toList());
			List<Map<String, Object>> buzones = (List<Map<String, Object>>) buzonEdao.listarByIds(buzonIds);
			List<Map<String, Object>> tiposDocumento = (List<Map<String, Object>>) tipoDocumentoEdao.listarByIds(tipoDocumentoIds);
			for (Envio envio: guiasCreadasList) {
				envio.setRutaAutorizacion(this.storageAutorizaciones + envio.getRutaAutorizacion());
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
		}			*/
		return guiasCreadasList;		
	}

}
