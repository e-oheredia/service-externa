package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IHandleFileService {
	public int upload(MultipartFile file) throws IOException;
}
