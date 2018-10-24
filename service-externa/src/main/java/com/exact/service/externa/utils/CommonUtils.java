package com.exact.service.externa.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.Envio;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;

public class CommonUtils {
	
	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
	    Map<String, Object> retMap = new HashMap<String, Object>();

	    if(json != JSONObject.NULL) {
	        retMap = toMap(json);
	    }
	    return retMap;
	}
	
	public static Iterable<Map<String, Object>> jsonArrayToMap(JSONArray jsonArray) throws JSONException {
		
		List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
		
		for (int i = 0; i < jsonArray.length(); i++) {
			maps.add(jsonToMap((JSONObject)jsonArray.get(i)));
		}
		
		return maps;
		
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
	    Map<String, Object> map = new HashMap<String, Object>();

	    @SuppressWarnings("unchecked")
		Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);
	        if (value == null) {
	        	value = "";
	        }

	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        map.put(key, value);
	    }
	    return map;
	}
	
	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
	public static File multipartFileToFile(MultipartFile multipartFile) throws IOException {
		File convFile = new File(multipartFile.getOriginalFilename());
	    convFile.createNewFile();
	    FileOutputStream fos = new FileOutputStream(convFile);
	    fos.write(multipartFile.getBytes());
	    fos.close();
	    return convFile;		
	}
	
	public String filterListaObjetoJson(Iterable<?> lista,Map<String, String> filtro) throws ClientProtocolException, IOException, JSONException{
		ObjectMapper mapper = new ObjectMapper();		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		
		Iterator it = filtro.entrySet().iterator();
		
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        filterProvider.addFilter(pair.getKey().toString() , SimpleBeanPropertyFilter.serializeAllExcept(pair.getValue().toString()));
		     }
		//filterProvider.addFilter(nombreFiltro, SimpleBeanPropertyFilter.serializeAllExcept(campoFiltro)); 		
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setFilterProvider(filterProvider); 		
	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lista);
	}
	
	public String filterObjetoJson(Object objeto,Map<String, String> filtro) throws ClientProtocolException, IOException, JSONException{
		ObjectMapper mapper = new ObjectMapper();	
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		Iterator it = filtro.entrySet().iterator();
		
		 while (it.hasNext()) {
		        Map.Entry pair = (Map.Entry)it.next();
		        filterProvider.addFilter(pair.getKey().toString() , SimpleBeanPropertyFilter.serializeAllExcept(pair.getValue().toString()));
		     }
		//filterProvider.addFilter(filtro.ke , SimpleBeanPropertyFilter.serializeAllExcept(filtro.get("envioFilter"))); 
		
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setFilterProvider(filterProvider); 		
	    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objeto);
	}
	
}
