package org.jyu.web.service.question.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jyu.web.dao.authority.UserRepository;
import org.jyu.web.dao.question.QuestionLabelRepository;
import org.jyu.web.dao.question.QuestionProgramRepository;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.question.QuestionProgramJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.question.Answer;
import org.jyu.web.entity.question.QuestionLabel;
import org.jyu.web.entity.question.QuestionProgram;
import org.jyu.web.enums.QuestionType;
import org.jyu.web.service.question.QuestionProgramService;
import org.jyu.web.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionProgramServiceImpl implements QuestionProgramService {

	@Autowired
	private QuestionProgramRepository questionProgramDao;

	@Autowired
	private UserRepository userDao;
	
	@Autowired
	private QuestionLabelRepository questionLabelDao;

	@Transactional
	@Override
	public Result save(String shortName, String content, Integer difficulty, String description, String input,
			String output, String exampleInput, String exampleOutput, String hint, String answerContent, String analyse,
			List<String> labelIds, String userId) {
		User user = userDao.getOne(userId);
		if(user == null) {
			return new Result(false, "用户不存在");
		}
		if (questionProgramDao.findByShortName(shortName).size() > 0) {
			return new Result(false, "主题重复");
		}
		QuestionProgram questionProgram = new QuestionProgram();
		questionProgram.setAuthor(user);
		questionProgram.setContent(content);
		questionProgram.setCreateTime(DateUtil.DateToString(DateUtil.YMDHMS, new Date()));
		questionProgram.setDescription(description);
		questionProgram.setDifficulty(difficulty);
		questionProgram.setExampleInput(exampleInput);
		questionProgram.setExampleOutput(exampleOutput);
		questionProgram.setHint(hint);
		questionProgram.setInput(input);
		questionProgram.setOutput(output);
		questionProgram.setShortName(shortName);
		questionProgram.setType(QuestionType.PROGRAM);
		
		List<QuestionLabel> list = new ArrayList<>();
		for (String string : labelIds) {
			list.add(questionLabelDao.getOne(string));
		}
		questionProgram.setQuestionLabels(list);
		
		Answer answer = new Answer();
		answer.setContent(answerContent);
		answer.setAnalyse(analyse);
		questionProgram.setAnswer(answer);
		questionProgramDao.saveAndFlush(questionProgram);
		return new Result(true, "保存成功");
	}

	@Transactional
	@Override
	public Result update(String id, String shortName, String content, Integer difficulty, String description,
			String input, String output, String exampleInput, String exampleOutput, String hint, String answerContent,
			String analyse, List<String> labelIds) {
		QuestionProgram questionProgram = questionProgramDao.getOne(id);
		if(questionProgram == null) {
			return new Result(false, "题目不存在");
		}
		if(shortName != null && shortName != "") {
			questionProgram.setShortName(shortName);
		}
		if(content != null && content != "") {
			questionProgram.setContent(content);
		}
		if(difficulty != null) {
			questionProgram.setDifficulty(difficulty);
		}
		if(description != null && description != "") {
			questionProgram.setDescription(description);
		}
		if(input != null && input != "") {
			questionProgram.setInput(input);
		}
		if(output != null && output != "") {
			questionProgram.setOutput(output);
		}
		if(exampleInput != null && exampleInput != "") {
			questionProgram.setExampleInput(exampleInput);
		}
		
		if(exampleOutput != null && exampleOutput != "") {
			questionProgram.setExampleOutput(exampleOutput);
		}
		if(hint != null && hint != "") {
			questionProgram.setHint(hint);
		}
		if(answerContent != null && answerContent != "") {
			questionProgram.getAnswer().setContent(answerContent);
		}
		
		if(analyse != null && analyse != "") {
			questionProgram.getAnswer().setAnalyse(analyse);;
		}
		if (labelIds != null && labelIds.size() > 0) {
			questionProgram.getQuestionLabels().clear();
			List<QuestionLabel> list = new ArrayList<>();
			for (String string : labelIds) {
				list.add(questionLabelDao.getOne(string));
			}
			questionProgram.setQuestionLabels(list);
		}
		questionProgramDao.saveAndFlush(questionProgram);
		return new Result(true, "修改成功");
	}

	@Transactional
	@Override
	public Result delete(String id) {
		questionProgramDao.deleteById(id);
		return new Result(true, "删除成功");
	}

	@Override
	public QuestionProgramJson findById(String id) {
		QuestionProgram program = questionProgramDao.getOne(id);
		QuestionProgramJson questionProgramJson = new QuestionProgramJson();
		if(program != null) {
			questionProgramJson.setContent(program.getContent());
			questionProgramJson.setDescription(program.getDescription());
			questionProgramJson.setDifficulty(program.getDifficulty());
			questionProgramJson.setExampleInput(program.getExampleInput());
			questionProgramJson.setExampleOutput(program.getExampleOutput());
			questionProgramJson.setHint(program.getHint());
			questionProgramJson.setId(program.getId());
			questionProgramJson.setInput(program.getInput());
			questionProgramJson.setOutput(program.getOutput());
			questionProgramJson.setShortName(program.getShortName());
			List<String> labels = new ArrayList<>();
			for (QuestionLabel questionLabel : program.getQuestionLabels()) {
				labels.add(questionLabel.getName());
			}
			questionProgramJson.setLabel(labels.toString().replaceAll("[", "").replaceAll("]", ""));
		}
		return questionProgramJson;
	}

	@Override
	public Map<String, Object> list(int pageNumber, int pageSize, String sortOrder) {
		Sort sort = new Sort(Direction.DESC,"createTime");
		if(sortOrder.toLowerCase().equals("asc")) {
			sort = new Sort(Direction.ASC,"createTime");
		}
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber-1, pageSize,sort);
		Page<QuestionProgram> page = questionProgramDao.findAll(pageable);
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", this.convertData(page.getContent()));
		return map;
	}

	@Override
	public Result judgeResult(String qid, String solution) {
		return null;
	}
	
	List<QuestionProgramJson> convertData(List<QuestionProgram> programs) {
		List<QuestionProgramJson> list = new ArrayList<>();
		for (QuestionProgram questionProgram : programs) {
			QuestionProgramJson json = new QuestionProgramJson();
			json.setContent(questionProgram.getContent());
			json.setDescription(questionProgram.getDescription());
			json.setDifficulty(questionProgram.getDifficulty());
			json.setExampleInput(questionProgram.getExampleInput());
			json.setExampleOutput(questionProgram.getExampleOutput());
			json.setHint(questionProgram.getHint());
			json.setId(questionProgram.getId());
			json.setInput(questionProgram.getInput());
			json.setOutput(questionProgram.getOutput());
			json.setShortName(questionProgram.getShortName());
			List<QuestionLabel> labels = questionProgram.getQuestionLabels();
			String[] labels_array = new String[labels.size()];
			for (int i = 0; i < labels.size(); i++) {
				labels_array[i] = labels.get(i).getName();
			}
			json.setLabel(Arrays.toString(labels_array));
			list.add(json);
		}
		return list;
	}
	
	/**
	 * 使用正则表达式删除HTML标签
	 * @param htmlStr
	 * @return
	 */
	String delHTMLTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 

        return htmlStr.trim(); //返回文本字符串
	}

}
