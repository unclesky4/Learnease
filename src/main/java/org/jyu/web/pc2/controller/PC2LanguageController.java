package org.jyu.web.pc2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.jyu.web.pc2.JsonLanguage;
import org.jyu.web.pc2.service.PC2LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.csus.ecs.pc2.core.model.Language;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 
 * @author unclesky4 10/10/2017
 *
 */
@Controller
@RequestMapping(value="/language")
public class PC2LanguageController {
	
	@Autowired
	private PC2LanguageService pc2LanguageService;

	/**
	 * 获取PC2的所有编程语言
	 * @param response
	 */
	@RequestMapping(value="list")
	public void getAllLanguage(HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		List<JsonLanguage> list = new ArrayList<JsonLanguage>();
		
		Map<String, Language> map = pc2LanguageService.getAllLanguage();
		Set<Entry<String, Language>> set = map.entrySet();
		Iterator<Entry<String, Language>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Language language = iterator.next().getValue();
			JsonLanguage jsonLanguage = new JsonLanguage();
			jsonLanguage.setDisplayName(language.getDisplayName());
			jsonLanguage.setCompilerCommandLine(language.getCompileCommandLine());
			jsonLanguage.setExecuteCommandLine(language.getProgramExecuteCommandLine());
			jsonLanguage.setJudgesCommandLine(language.getJudgeProgramExecuteCommandLine());
			list.add(jsonLanguage);
		}
		
		JsonConfig jsonConfig = new JsonConfig();
//		jsonConfig.setExcludes(new String[]{"compilerCommandLine"});
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		
		try {
			response.getWriter().println(jsonArray.toString());
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}


