package org.jyu.web.pc2.service;

import java.util.Map;

import edu.csus.ecs.pc2.core.model.Language;

/**
 * 
 * @author unclesky4 09/10/2017
 *
 */
public interface PC2LanguageService {

	/**
	 * 获取编程语言
	 * @return
	 */
	public Map<String, Language> getAllLanguage();
}
