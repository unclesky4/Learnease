package org.jyu.web.pc2.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.jyu.web.pc2.JsonProblem;
import org.jyu.web.pc2.service.PC2ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.csus.ecs.pc2.core.model.Problem;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
 * @author unclesky4 10/10/2017
 *
 */
@Controller
@RequestMapping(value="/pc2problem")
public class PC2ProblemController {
	
	@Autowired
	private PC2ProblemService pc2ProblemService;

	/**
	 * 获取PC2所有的Problem
	 * @param response
	 */
	@RequestMapping(value="/list")
	public void getAllProblem(HttpServletResponse response) {
		response.setContentType("application/json;charset=UTF-8");
		
		List<JsonProblem> list = new ArrayList<JsonProblem>();
		
		Map<String, Problem> problemMap = pc2ProblemService.getAllProblem();
		Set<Entry<String, Problem>> set = problemMap.entrySet();
		
		JSONArray jsonArray = new JSONArray();
		
		Iterator<Entry<String, Problem>> iterator = set.iterator();
		Problem problem = null;
		while (iterator.hasNext()) {
			problem = iterator.next().getValue();
			/*JsonProblem jsonProblem = new JsonProblem();
			if(problem.isActive() == true)
				jsonProblem.setActive("true");
			else
				jsonProblem.setActive("false");
			
			if(problem.isReadInputDataFromSTDIN() == true)
				jsonProblem.setReadInputDataFromSTDIN("true");
			else
				jsonProblem.setReadInputDataFromSTDIN("false");
			
			if(problem.isUsingPC2Validator() == true)
				jsonProblem.setUsingPC2Validator("true");
			else
				jsonProblem.setUsingPC2Validator("false");
			
			if(problem.isValidatedProblem() == true)
				jsonProblem.setValidatedProblem("true");
			else 
				jsonProblem.setValidatedProblem("false");
			
			jsonProblem.setShortName(problem.getShortName());*/
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
		showInfo(response, getJson(jsonArray));
		//System.out.println(getJson(jsonArray));
	}
	
	/**
	 * 返回数据
	 * @param response
	 * @param info
	 */
	public void showInfo(HttpServletResponse response, String info) {
		try {
			response.getWriter().print(info);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 对象转为JSON格式数据
	 * @param object
	 * @return
	 */
	public String getJson(Object object) {
		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray = JSONArray.fromObject(object, jsonConfig);
		return jsonArray.toString();
	}
	
}
