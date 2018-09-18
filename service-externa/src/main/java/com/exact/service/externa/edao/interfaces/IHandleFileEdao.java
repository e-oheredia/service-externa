package com.exact.service.externa.edao.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IHandleFileEdao {
	public int upload(MultipartFile file) throws IOException;
}
