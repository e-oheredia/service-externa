package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAmbitoDistritoDao;
import com.exact.service.externa.edao.interfaces.IDistritoEdao;
import com.exact.service.externa.edao.interfaces.IRegionEdao;
import com.exact.service.externa.entity.AmbitoDistrito;
import com.exact.service.externa.service.interfaces.IAmbitoDistritoService;
 
@Service
public class AmbitoDistritoService implements IAmbitoDistritoService{

	@Autowired
	IAmbitoDistritoDao ambitoDistritoDao;
	
	@Autowired
	IDistritoEdao distritoEdao;
	
	@Autowired
	IRegionEdao ambitoEdao;
	
	@Override
	public Iterable<AmbitoDistrito> listarAmbitoDistritos() throws ClientProtocolException, IOException, JSONException {
		Iterable<AmbitoDistrito> ambitoDistritos = ambitoDistritoDao.findAll();
		List<Long> distritosIds = new ArrayList<>();
		List<Long> ambitosIds = new ArrayList<>();
		for(AmbitoDistrito ambitodistrito : ambitoDistritos) {
			distritosIds.add(ambitodistrito.getDistritoId());
			ambitosIds.add(ambitodistrito.getAmbitoId());
		}
		List<Map<String, Object>> distritos = (List<Map<String, Object>>) distritoEdao.listarByIds(distritosIds);
		List<Map<String, Object>> ambitos = (List<Map<String, Object>>) ambitoEdao.listarAmbitosByIds(ambitosIds);
		for(AmbitoDistrito ambitodistrito : ambitoDistritos) {
			int i =0;
			while(i<distritos.size()) {
				Long distritoId = Long.valueOf(distritos.get(i).get("id").toString());
				if(ambitodistrito.getDistritoId().longValue()==distritoId) {
					ambitodistrito.setDistrito(distritos.get(i));
					break;
				}
				i++;
			}
			int j=0;
			while(j<ambitos.size()) {
				Long ambitoId=Long.valueOf(ambitos.get(j).get("id").toString());
				if(ambitodistrito.getAmbitoId()==ambitoId){
					ambitodistrito.setAmbito(ambitos.get(j));
					break;
				}
				j++;
			}
			
		}
		
		return ambitoDistritos;
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public Iterable<AmbitoDistrito> validarActualizarAmbitoDistrito(List<Map<String,Object>> distritosExcel) throws ClientProtocolException, IOException, JSONException {
		Iterable<AmbitoDistrito> ambitoDistritoBD = ambitoDistritoDao.findAll();
		List<AmbitoDistrito> ambitoDistritoBDlst = StreamSupport.stream(ambitoDistritoBD.spliterator(), false).collect(Collectors.toList());
		List<String> ubigeoslst = new ArrayList<>();
		int m=0;
		while(m<distritosExcel.size()) {
			ubigeoslst.add(distritosExcel.get(m).get("ubigeo").toString());
			m++;
		}
		List<Map<String,Object>> distritosBD =  (List<Map<String, Object>>) distritoEdao.listarDistritoIdsByUbigeos(ubigeoslst);
		for(Map<String, Object> distritoExcel :distritosExcel) {
			String ubigeoExcel= distritoExcel.get("ubigeo").toString();
			for(Map<String,Object> distritoBD : distritosBD) {
				String ubigeoBD = distritoBD.get("ubigeo").toString();	
					if(ubigeoExcel.equals(ubigeoBD)) {
						Long distritoId = Long.valueOf(distritoBD.get("id").toString());
						for(AmbitoDistrito ambitodistrito: ambitoDistritoBDlst) {
							if(distritoId==ambitodistrito.getDistritoId().longValue()) {
								Map<String,Object> ambitoExcel = (Map<String, Object>) distritoExcel.get("ambito");
								Long ambitoExcelId = Long.valueOf(ambitoExcel.get("id").toString());
								if(ambitodistrito.getAmbitoId()!=ambitoExcelId) {
									ambitodistrito.setAmbitoId(ambitoExcelId);
									break;
								}
							}
						}
					}
				}
		}
		ambitoDistritoDao.saveAll(ambitoDistritoBDlst);
		return ambitoDistritoBD;
	}
	
}
