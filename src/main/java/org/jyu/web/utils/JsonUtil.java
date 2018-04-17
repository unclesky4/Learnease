package org.jyu.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	
	public static String toJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		String tmp = null;
		try {
			tmp = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.out.println(">>>>>> 转JSON时发生错误   ---- JsonUtil.java");
		}
		return tmp;
	}

}
