package org.jyu.web.controller.pc2;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.jyu.web.service.pc2.PC2ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.csus.ecs.pc2.core.model.Problem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
 * @author unclesky4 10/10/2017
 *
 */
@RestController
public class PC2ProblemController {
	
	@Autowired
	private PC2ProblemService pc2ProblemService;

	/**
	 * 获取PC2所有的Problem
	 * @param response    HttpServletResponse
	 * @return   JSONArray对象
	 */
	@GetMapping(value="/pc2problem/list")
	public JSONArray getAllProblem(HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		
		Map<String, Problem> problemMap = pc2ProblemService.getAllProblem();
		Set<Entry<String, Problem>> set = problemMap.entrySet();
		
		JSONArray jsonArray = new JSONArray();
		
		Iterator<Entry<String, Problem>> iterator = set.iterator();
		Problem problem = null;
		while (iterator.hasNext()) {
			problem = iterator.next().getValue();
			JSONObject tmp = new JSONObject();
			tmp.put("shortName", problem.getShortName());
			if(problem.isActive() == true)
				tmp.put("active", "true");
			else
				tmp.put("active", "false");
			
			if(problem.isReadInputDataFromSTDIN() == true)
				tmp.put("readInputDataFromSTDIN", "true");
			else
				tmp.put("readInputDataFromSTDIN", "false");
			
			if(problem.isUsingPC2Validator() == true)
				tmp.put("usingPC2Validator", "true");
			else
				tmp.put("usingPC2Validator", "false");
			
			if(problem.isValidatedProblem() == true)
				tmp.put("validatedProblem", "true");
			else 
				tmp.put("validatedProblem", "false");
			jsonArray.add(tmp);
		}
		return jsonArray;
	}
	
	/**
	 * 对象转为JSON格式数据
	 * @param object   Object
	 * @return   SON格式数据(String)
	 */
	public String getJson(Object object) {
		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray = JSONArray.fromObject(object, jsonConfig);
		return jsonArray.toString();
	}
	
}
