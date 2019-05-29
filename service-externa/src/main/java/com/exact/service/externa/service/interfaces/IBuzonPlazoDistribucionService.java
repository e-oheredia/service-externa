package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.BuzonPlazoDistribucion;

public interface IBuzonPlazoDistribucionService {
	
	BuzonPlazoDistribucion listarById(Long id);

	BuzonPlazoDistribucion actualizar(BuzonPlazoDistribucion buzonPlazoDistribucion, MultipartFile file) throws IOException;
	
	Iterable<BuzonPlazoDistribucion> listarBuzonPlazo() throws IOException, JSONException ;
}
