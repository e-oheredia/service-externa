package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.AreaPlazoDistribucion;

public interface IAreaPlazoDistribucionService {
	AreaPlazoDistribucion listarById(Long id);
	AreaPlazoDistribucion actualizar(AreaPlazoDistribucion areaPlazoDistribucion, MultipartFile file) throws IOException;
	Iterable<AreaPlazoDistribucion> listarAreaPlazos() throws IOException, JSONException;
}
