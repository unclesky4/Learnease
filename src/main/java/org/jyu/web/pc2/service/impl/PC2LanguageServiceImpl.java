package org.jyu.web.pc2.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.jyu.web.pc2.InitPC2;
import org.jyu.web.pc2.service.PC2LanguageService;
import org.springframework.stereotype.Service;

import edu.csus.ecs.pc2.core.model.Language;

/**
 * 
 * @author unclesky4 09/10/2017
 *
 */
@Service(value="pc2LanguageService")
public class PC2LanguageServiceImpl implements PC2LanguageService{

	@Override
	public Map<String, Language> getAllLanguage() {
		Language[] languages = InitPC2.getJudge_serverConnection().getIInternalContest().getLanguages();
		Map<String, Language> map = new HashMap<String, Language>();
		for(int i=0; i<languages.length; i++) {
			map.put(languages[i].getDisplayName(), languages[i]);
		}
		return map;
	}

}
