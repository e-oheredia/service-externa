package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.io.FilenameUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.dao.IAreaPlazoDistribucionDao;
import com.exact.service.externa.edao.interfaces.IAreaEdao;
import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.entity.AreaPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IAreaPlazoDistribucionService;

@Service
public class AreaPlazoDistribucionService implements IAreaPlazoDistribucionService {
	@Autowired
	IAreaPlazoDistribucionDao areaPlazoDistribucionDao;
	
	@Autowired
	IAreaEdao areaEdao;
	
	@Autowired
	IHandleFileEdao handleFileEdao;
	
	@Override
	public AreaPlazoDistribucion listarById(Long id) {
		return areaPlazoDistribucionDao.getPlazoDistribucionIdByAreaId(id);
	}
	
	@Override
	public AreaPlazoDistribucion actualizar(AreaPlazoDistribucion areaPlazoDistribucion, MultipartFile file) throws IOException {
		if (areaPlazoDistribucionDao.existsById(areaPlazoDistribucion.getAreaId())) {
			String ruta = "autorizaciones";
			if(file!=null) {
				String rutaAutorizacion = areaPlazoDistribucion.getAreaId().toString() + "."
						+ FilenameUtils.getExtension(file.getOriginalFilename());
				areaPlazoDistribucion.setRutaAutorizacion(rutaAutorizacion);
				MockMultipartFile multipartFile = new MockMultipartFile(rutaAutorizacion, rutaAutorizacion,
						file.getContentType(), file.getInputStream());
				if (handleFileEdao.upload(multipartFile,ruta) != 1) {
					return null;
				}
				areaPlazoDistribucion.setFechaAsociacion(new Date());
				return areaPlazoDistribucionDao.save(areaPlazoDistribucion);
			}
			return null;
		}
		return null;
	}

	@Override
	public Iterable<AreaPlazoDistribucion> listarAreaPlazos() throws IOException, JSONException {
		List<Map<String, Object>> areas = (List<Map<String, Object>>) areaEdao.listarAll();
		Iterable<AreaPlazoDistribucion> areasplazoBD = areaPlazoDistribucionDao.findAll();
		List<AreaPlazoDistribucion> areasplazo = StreamSupport.stream(areasplazoBD.spliterator(), false).collect(Collectors.toList());
		for(AreaPlazoDistribucion areaplazo : areasplazo) {
			int i = 0; 
			while(i < areas.size()) {
				if (areaplazo.getAreaId() == Long.valueOf(areas.get(i).get("id").toString())) {
					areaplazo.setArea(areas.get(i));
					break;
				}
				i++;
			}
		}
		return areasplazo;
	}

}
