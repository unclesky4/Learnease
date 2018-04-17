package org.jyu.web.pc2.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jyu.web.pc2.InitPC2;
import org.jyu.web.pc2.JsonJudgeResult;
import org.jyu.web.pc2.JudgeResult;
import org.jyu.web.pc2.enums.JudgeResultType;
import org.jyu.web.utils.FileUtil;
import org.jyu.web.utils.UUIDUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.csus.ecs.pc2.core.execute.Executable;
import edu.csus.ecs.pc2.core.execute.ExecuteRun;
import edu.csus.ecs.pc2.core.execute.ExecutionData;
import edu.csus.ecs.pc2.core.model.Language;
import edu.csus.ecs.pc2.core.model.Problem;
import edu.csus.ecs.pc2.core.model.ProblemDataFiles;
import edu.csus.ecs.pc2.core.model.Run;
import edu.csus.ecs.pc2.core.model.SerializedFile;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 在线编译 - Controller层
 * @author unclesky4 10/10/2017
 *
 */
@Controller
@RequestMapping(value="/judge")
public class JudgeRunController {
	
	/**
	 * 编译运行用户提交的程序
	 * @param session
	 * @param response
	 * @param problemTopic - 问题的主题
	 * @param languageDisplayName - 编程语言的名称  Java , GNU C++ (Unix / Windows) , GNU C (Unix / Windows) , Python , PHP 
	 * @param inputParam - 程序输入(String-参数)
	 * @param solutionContent - 程序代码
         * return 传输JsonJudgeResult对象的JSON数据
	 */
	@RequestMapping(value="/execute")
	public void doExecute(HttpSession session, HttpServletResponse response,String problemTopic, String languageDisplayName,
			String inputParam, String solutionContent) {
		response.setContentType("application/json;charset=UTF-8");
		
		JsonJudgeResult jsonJudgeResult = new JsonJudgeResult();
		jsonJudgeResult.setProblemTopic(problemTopic);
		jsonJudgeResult.setLanguageDisplayName(languageDisplayName);
		jsonJudgeResult.setSolution(solutionContent);
		
		//判断是否有用户登陆
/*		User user = (User) session.getAttribute("user");
		if(user == null || user.getStudents().isEmpty()) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("用户未登陆或者已登陆用户没有学生权限");
			showInfo(response, getJson(jsonJudgeResult));
			return ;
		}*/
		
		//判断用户是否有学生角色
/*		boolean roleActive = false;
		Iterator<Student> iterator = user.getStudents().iterator();
		while (iterator.hasNext()) {
			Student stu = iterator.next();
			if(iterator.next().getStudentStatus().equals(RoleStatus.ADOPT)){
				roleActive = true;
				runInfo.setStuId(stu.getStuId());
				jsonJudgeResult.setStuId(stu.getStuId());
				break;
			}
		}
		if(!roleActive) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("用户没有学生角色");
			showInfo(response, getJson(jsonJudgeResult));
			return ;
		}*/

		if(problemTopic == "" || problemTopic == null) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("请求参数错误");
			showInfo(response, getJson(jsonJudgeResult));
			System.err.println("-------请求参数错误");
			return ;
		}
		
		if(InitPC2.getAdmin_serverConnection() == null) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("未开启编译运行服务后台");
			showInfo(response, getJson(jsonJudgeResult));
			System.err.println("--------未开启编译运行服务后台");
			return ;
		}

		//从PC2中找到Problem对象
		Problem problem = null;
		Problem[] problems = InitPC2.getAdmin_serverConnection().getIInternalContest().getProblems();
		for(int i=0; i<problems.length; i++) {
			if(problems[i].getShortName().equals(problemTopic)) {
				problem = problems[i];
			}
		}
		if(problem == null) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("没有找到题目对象");
			showInfo(response, getJson(jsonJudgeResult));
			System.err.println("------------没有找到题目对象");
			return ;
		}
		//测试在线评判----------------------------
		problem.setHideOutputWindow(true);
		problem.setValidatedProblem(true);
		problem.setValidatorCommandLine("{:validator} {:infile} {:outfile} {:ansfile} {:resfile} "); 
		problem.setUsingPC2Validator(true);
		problem.setWhichPC2Validator(1);
		problem.setIgnoreSpacesOnValidation(true);
		problem.setValidatorCommandLine("{:validator} {:infile} {:outfile} {:ansfile} {:resfile}  -pc2 " + problem.getWhichPC2Validator() + " " + 
		problem.isIgnoreSpacesOnValidation());
		problem.setValidatorProgramName("pc2.jar edu.csus.ecs.pc2.validator.Validator");
		
		//从PC2中找到Language对象
		Language[] languages = InitPC2.getAdmin_serverConnection().getIInternalContest().getLanguages();
		Language language = null;
		for(int i=0; i<languages.length; i++) {
			//System.out.println(languages[i].getDisplayName());
			if(languages[i].getDisplayName().equals(languageDisplayName)) {
				language = languages[i];
			}
		}
		if (language == null) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("没有找到编译语言对象");
			showInfo(response, getJson(jsonJudgeResult));
			System.err.println("------------没有找到编译语言对象");
			return ;
		}
		
		//创建用于存放用户提交的程序和数据的目录
		String tmpDir = FileUtil.tmpPath+UUIDUtil.getUUID()+"/"; 
		tmpDir = tmpDir.replace("/", System.getProperty("file.separator"));
		boolean isMakeDir = FileUtil.createDir(tmpDir);
		if(!isMakeDir) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("创建临时目录失败");
			showInfo(response, getJson(jsonJudgeResult));
			System.err.println("----创建临时目录失败");
			return ;
		}
		
		//用户有自定义输入数据时
		String inputFileName = null;
		File tmpInputFile = null;
		
		if(inputParam != null && inputParam != "") {
			inputFileName = tmpDir+"data.input";
			tmpInputFile = FileUtil.createFile(inputFileName);
			if(tmpInputFile == null) {
				jsonJudgeResult.setStatus(false);
				jsonJudgeResult.setFailInfo("创建数据输入文件失败");
				System.err.println("-----创建数据输入文件失败");
				showInfo(response, getJson(jsonJudgeResult));
				//删除目录及其子文件
				FileUtil.deleteDir(new File(tmpDir));
				return ;
			}
			if(!FileUtil.writeFile(tmpInputFile, inputParam)) {
				jsonJudgeResult.setStatus(false);
				jsonJudgeResult.setFailInfo("写入数据到数据文件失败");
				System.err.println("---------写入数据到数据文件失败");
				showInfo(response, getJson(jsonJudgeResult));
				FileUtil.deleteDir(new File(tmpDir));
				return ;	
			}

			ProblemDataFiles problemDataFiles = InitPC2.getAdmin_serverConnection().getIInternalContest().getProblemDataFile(problem);
			
			problemDataFiles.setJudgesDataFile(new SerializedFile(inputFileName));
			InitPC2.getAdmin_serverConnection().getIInternalContest().updateProblem(problem, problemDataFiles);
		}else{
			ProblemDataFiles problemDataFiles = InitPC2.getAdmin_serverConnection().getIInternalContest().getProblemDataFile(problem);
			problemDataFiles.setJudgesDataFile(new SerializedFile(problem.getColorName()));
			InitPC2.getAdmin_serverConnection().getIInternalContest().updateProblem(problem, problemDataFiles);
			
/*			System.out.println(problem.getDataFileName());
			System.out.println(problem.getColorName()+"  " +problem.getColorRGB());
			System.out.println(problem.getExecutionPrepCommand());
			System.out.println(problem.getExternalDataFileLocation());*/
		}
		
		//创建程序主文件
		String fileName = "";
		switch (languageDisplayName) {
		case "Java":
			String className = getClassName(solutionContent);
			fileName = tmpDir+className+".java";
			break;
		case "GNU C++":
			fileName = tmpDir+"solve.cpp";
			break;
		case "GNU C":
			fileName = tmpDir+"solve.c";
			break;
		case "PHP":
			fileName = tmpDir+"solve.php";
			break;
		case "Python":
			fileName = tmpDir+"solve.py";
			break;
		default:
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("未知的编程语言");
			System.err.println("--------未知的编程语言");
			showInfo(response, getJson(jsonJudgeResult));
			FileUtil.deleteDir(new File(tmpDir));
			return ;
		}
		File file = FileUtil.createFile(fileName);
		if(file == null) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("创建程序文件失败");
			System.err.println("--------创建程序文件失败");
			showInfo(response, getJson(jsonJudgeResult));
			FileUtil.deleteDir(new File(tmpDir));
			return ;
		}
		
		if(!FileUtil.writeFile(file, solutionContent)) {
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("文件写入程序代码失败");
			System.err.println("-----文件写入程序代码失败");
			showInfo(response, getJson(jsonJudgeResult));
			FileUtil.deleteDir(new File(tmpDir));
			return ;
		}
		
		Run run = new Run(InitPC2.getAdmin_serverConnection().getIInternalContest().getClientId(), language, problem);
		
		ExecuteRun executeRun = null;
		try {
			executeRun = new ExecuteRun(InitPC2.getAdmin_serverConnection(), run, fileName);
			
			//程序运行输出文件
			File ouputfile = executeRun.getOutputFile();
			
			//编译错误输出文件
			//File compileErrorFile = new File(executeRun.getRanExecute().getExecutionData().getCompileStderr().getAbsolutePath());
			File compileErrorFile = executeRun.getErrorFile();
			//String info = executeRun.readFile(ouputfile);
			
			Executable executable = executeRun.getRanExecute();
			ExecutionData executionData = executeRun.getRanExecute().getExecutionData();
			
			//编译失败
			if(!executionData.isCompileSuccess()) {
				//JudgeResult对象实例
				JudgeResult judgeResult = new JudgeResult(JudgeResultType.COMPILERFAILED);
				if(compileErrorFile != null && compileErrorFile.exists()) {
					judgeResult.setProgramOuputInfo(executeRun.readFile(compileErrorFile));
				}
				
				jsonJudgeResult.setJudgeResult(judgeResult);
				showInfo(response, getJson(jsonJudgeResult));
				
				FileUtil.deleteDir(new File(tmpDir));
				if(executeRun.getExecuteDirectoryPath() != null) {
					FileUtil.deleteDir(new File(executeRun.getExecuteDirectoryPath()));
				}
				return ;
			}
			
			//运行失败 - 运行退出值为1表示异常退出，0表示正常运行退出
			if(executionData.getExecuteExitValue() == 1) {
				JudgeResult judgeResult = new JudgeResult(JudgeResultType.EXECUTEFAILED);
				jsonJudgeResult.setJudgeResult(judgeResult);
				showInfo(response, getJson(jsonJudgeResult));
				FileUtil.deleteDir(new File(tmpDir));
				if(executeRun.getExecuteDirectoryPath() != null) {
					FileUtil.deleteDir(new File(executeRun.getExecuteDirectoryPath()));
				}
				return;
			}
			
			//运行成功
			JudgeResult judgeResult = new JudgeResult(JudgeResultType.SUCCESS);
			judgeResult.setCompileTimeMS(executionData.getCompileTimeMS());
			judgeResult.setExecuteTimeMS(executionData.getExecuteTimeMS());
			if(ouputfile.exists()) {
				judgeResult.setProgramOuputInfo(executeRun.readFile(ouputfile));
				//System.out.println(executeRun.readFile(ouputfile));
			}
			judgeResult.setTimeOutInSeconds(executable.getProblem().getTimeOutInSeconds());
			jsonJudgeResult.setJudgeResult(judgeResult);
			
			showInfo(response, getJson(jsonJudgeResult));
			FileUtil.deleteDir(new File(tmpDir));
			if(executeRun.getExecuteDirectoryPath() != null) {
				FileUtil.deleteDir(new File(executeRun.getExecuteDirectoryPath()));
			}
			
			//获取在线评判结果
//			System.out.println(executable.getProblem().getValidatorCommandLine());
//			System.out.println(executable.getProblem().getValidatorProgramName());
//			System.out.println(executable.getProblem().getWhichPC2Validator());
//			System.out.println(executable.getProblem().isValidatedProblem());
//			
//			System.out.println(executable.getValidatorOutputFilenames());
//			System.out.println("ValidationResults:"+executable.getValidationResults());
//			System.out.println(executionData.getValidationResults());
//			System.out.println(executionData.getValidationReturnCode());
			
		} catch (NullPointerException e) {
			e.printStackTrace();
			jsonJudgeResult.setStatus(false);
			jsonJudgeResult.setFailInfo("编译失败");
			System.err.println("-----编译失败");
			showInfo(response, getJson(jsonJudgeResult));
			FileUtil.deleteDir(new File(tmpDir));
			if(executeRun.getExecuteDirectoryPath() != null) {
				FileUtil.deleteDir(new File(executeRun.getExecuteDirectoryPath()));
			}
		}
	}
	
	/**
	 * 截取代码中的类名--针对java程序
	 * @param data - 程序代码
	 * @return
	 */
	public String getClassName(String data) {
		int start = data.indexOf("class")+5;
		int end = data.indexOf("{");
		return data.substring(start, end).trim();
	}
	
	public void showInfo(HttpServletResponse response, String info) {
		try {
			response.getWriter().print(info);
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getJson(Object object) {
		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray = JSONArray.fromObject(object, jsonConfig);
		return jsonArray.toString();
	}

}
