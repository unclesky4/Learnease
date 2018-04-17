package edu.csus.ecs.pc2.core.execute;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jyu.web.pc2.ServerConnection;
import org.jyu.web.utils.UUIDUtil;

import edu.csus.ecs.pc2.core.IInternalController;
import edu.csus.ecs.pc2.core.model.IInternalContest;
import edu.csus.ecs.pc2.core.model.Run;
import edu.csus.ecs.pc2.core.model.RunFiles;
import edu.csus.ecs.pc2.core.model.SerializedFile;

/**
 * 直接使用PC^2的Executable类 -- 用于实际生产环境
 * @author unclesky4 unclesky4 02/25/2018
 *
 */
public class ExecuteRun{
	
	Executable executable = null;
	
	//编译运行所在目录的绝对路径
	String executeDirectoryPath = null;
	
	IInternalContest iInternalContest = null;
	
	IInternalController iInternalController = null;
	
	//获取已运行的Executable对象
	public Executable getRanExecute() {
		return this.executable;
	}
	
	//读取程序输出文件内容
	public String readFile(File file) {
		BufferedReader reader = null;
		StringBuffer info = new StringBuffer();
		String tempString = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			while ((tempString = reader.readLine()) != null) {
	            info.append(tempString+"\n");
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
		return info.toString();
	}
	
	/**
	 * 获取程序的输出文件
	 * @return
	 */
	public File getOutputFile() {
		String name = null;
		
		//测试
		//System.out.println(this.executable.getTeamsOutputFilenames());
		
		for(String tmp : this.executable.getTeamsOutputFilenames()) {
			//System.out.println(tmp);
			String[] aStrings = tmp.split(File.separator);
			name = aStrings[aStrings.length-1];
		}
		File runOutputFile = new File(getExecuteDirectoryPath()+File.separator+name);
		return runOutputFile;
	}
	
	/**
	 * 获取程序的编译错误输出文件
	 * @return
	 */
	public File getErrorFile() {
		File file = null;
		if(this.executable.getExecutionData() != null && this.executable.getExecutionData().getCompileStderr() != null) {
			file = new File(this.executable.getExecutionData().getCompileStderr().getAbsolutePath());
		}
		return file;
	}

	/**
	 * 构造方法
	 * @param serverConnection  - JUDGE角色登陆后的ServerConnection对象
	 * @param run- 用户提交的Run
	 * @param mainProgramFile  - 主程序文件名（含路径）
	 */
	public ExecuteRun(ServerConnection serverConnection, Run run, String mainProgramFile){
		
		iInternalContest = serverConnection.getIInternalContest();
		iInternalController = serverConnection.getIInternalController();
		
		SerializedFile[] serializedFiles = new SerializedFile[0];
		SerializedFile mainFile =  new SerializedFile(mainProgramFile);
		RunFiles runFiles = new RunFiles(run, mainFile, serializedFiles);
		this.executable = new Executable(iInternalContest, iInternalController, run, runFiles);
		//为编译运行的目录添加后缀，确保每次运行不在同一目录下（因为程序输出文件名相同，不能更改）
		this.executable.setExecuteDirectoryNameSuffix(UUIDUtil.getUUID());
		this.executable.setUsingGUI(false);
		this.executable.validateProgram(1);
		this.executable.execute();
		
		//System.out.println("是否编译成功："+this.executable.getExecutionData().isCompileSuccess());
		//System.out.println(executable.getExecutionData().getCompileStdout().getAbsolutePath());
		//System.out.println(executable.getExecutionData().getExecuteProgramOutput().getAbsolutePath());
	}
	
	/**
	 * 获取编译目录的绝对路径
	 * @return
	 */
	public String getExecuteDirectoryPath() {
		String path = executable.getExecutionData().getCompileStdout().getAbsolutePath();
		String[] tmp = path.split(System.getProperty("file.separator"));
		path = "";
		int length = tmp.length;
		for(int i = 1; i < length-1; i++)
			path = path + System.getProperty("file.separator") + tmp[i];
		return path;
	}
	
	/**
	 * 测试
	 * @param args
	 */
/*	public static void main(String[] args) {
		//连接服务器
		ServerConnection serverConnection = new ServerConnection();
		IContest iContest = null;
		
		//登陆--judge1
		try {
			iContest = serverConnection.login("judge1", "judge1");
			iContest = serverConnection.getContest();
		} catch (LoginFailureException e) {
			e.printStackTrace();
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}
		
		Problem[] problems = serverConnection.getIInternalContest().getProblems();
		Language language = null;
		Language[] languages = serverConnection.getIInternalContest().getLanguages();
		for(Language language2 : languages) {
			if(language2.getDisplayName().equals("Java")) {
				System.out.println(language2.getDisplayName());
				language = language2;
			}
		}
		Run run = new Run(serverConnection.getIInternalContest().getClientId(), language, problems[1]);
		
		String mainProgramFile = "/home/uncle/Desktop/pc2_data/solve.java";
		ExecuteRun executeRun = new ExecuteRun(serverConnection, run, mainProgramFile);
		System.out.println(executeRun.geteExecuteDirectoryName());
		
		
		try {
			serverConnection.logoff();
		} catch (NotLoggedInException e) {
			e.printStackTrace();
		}
	}*/
}
