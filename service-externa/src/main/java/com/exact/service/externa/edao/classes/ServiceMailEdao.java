package com.exact.service.externa.edao.classes;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.IServiceMailEdao;
import com.exact.service.externa.request.IRequester;

@Repository
public class ServiceMailEdao implements IServiceMailEdao{

	@Value("${service.mail}")
	private String serviceMailPath;
	
	@Autowired
	private IRequester requester;
	
	@Override
	public void enviarMensaje(String to, String subject, String text) throws MessagingException, ParseException, IOException {
		HttpPost post = new HttpPost(serviceMailPath + "/send");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
		builder.addTextBody("para", to);
		builder.addTextBody("asunto", subject);
		builder.addTextBody("texto", text);
		HttpEntity entity = builder.build();
		post.setEntity(entity);
		requester.request(post);
		}

}
