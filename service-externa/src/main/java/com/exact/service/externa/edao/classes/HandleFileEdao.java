package com.exact.service.externa.edao.classes;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

@Repository
public class HandleFileEdao implements IHandleFileEdao {
	
	@Value("${service.handle.files}")
	private String handleFilesPath;
	
	@Autowired
	private IRequester requester;
	
	@Override
	public int upload(MultipartFile multipartFile, String ruta) throws IOException {
		HttpPost post = new HttpPost(handleFilesPath + "/upload");
		File file = CommonUtils.multipartFileToFile(multipartFile);
		FileBody fileBody = new FileBody(file);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addPart("file", fileBody);
		builder.addTextBody("ruta", ruta);
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		CloseableHttpResponse httpResponse = requester.request(post);
		try {
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				return Integer.parseInt(response);
			}else {
				return 0;
			}
		} finally {
			httpResponse.close();
		}
		
	}

}
