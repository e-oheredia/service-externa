package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.EnvioMasivo;

public interface IEnvioMasivoService {
	EnvioMasivo registrarEnvioMasivo(EnvioMasivo envioMasivo, Long idUsuario, MultipartFile multipartFile) throws IOException;
}
