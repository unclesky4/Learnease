package org.jyu.web.utils;

import java.util.UUID;

/**
 * 
 * @author HUang Zhibiao 2018/03/14
 *
 */
public class UUIDUtil {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
