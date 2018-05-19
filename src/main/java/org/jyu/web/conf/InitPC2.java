package org.jyu.web.conf;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jyu.web.pc2.AutoJudge;
import org.jyu.web.pc2.ServerConnection;
import org.springframework.web.context.support.WebApplicationContextUtils;

import edu.csus.ecs.pc2.api.exceptions.LoginFailureException;
import edu.csus.ecs.pc2.api.exceptions.NotLoggedInException;
import edu.csus.ecs.pc2.core.model.Account;

/**
 * 实现监听器，服务启动时自动连接PC^2服务器
 * 自动创建ADMINISTRATOR， JUDGE角色的ServerConnection实例（如果角色帐号不存在）
 * @author unclesky4 28/09/2017
 *
 */
@WebListener
public class InitPC2 implements ServletContextListener {
	
	static ServerConnection admin_serverConnection = null;
	
	static ServerConnection judge_serverConnection  = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if(admin_serverConnection != null && admin_serverConnection.isLoggedIn()) {
			try {
				admin_serverConnection.logoff();
			} catch (NotLoggedInException e) {
				e.printStackTrace();
			}
		}
		
/*		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}*/
		
		if(judge_serverConnection != null && judge_serverConnection.isLoggedIn()) {
			try {
				judge_serverConnection.logoff();
			} catch (NotLoggedInException e) {
				e.printStackTrace();
			}
		}
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.out.println("已注销ServerConnection");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext())
        .getAutowireCapableBeanFactory().autowireBean(this);
		
		admin_serverConnection = new ServerConnection();
		try {
			admin_serverConnection.login("administrator1", "administrator1");
			//设置自动评判
			admin_serverConnection.getIInternalContest().getClientSettings().setAutoJudging(true);
			AutoJudge autoJudge = new AutoJudge();
			autoJudge.setContestAndController(admin_serverConnection.getIInternalContest(), 
					admin_serverConnection.getIInternalController());
		
		} catch (LoginFailureException e) {
			e.printStackTrace();
		}
		//是否存在JUDGE角色名'judge1'
		boolean existJudge = true;
		for(Account account : admin_serverConnection.getIInternalContest().getAccounts()) {
			if(account.getTeamName().equals("judge1")) {
				existJudge = false;
				break;
			}
		}
		if(existJudge) {
			try {
				admin_serverConnection.addAccount("judge", "judge1", "judge1");
				System.out.println("已添加judge1用户");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*
		System.out.println("========================");
		for(Language language : admin_serverConnection.getIInternalContest().getLanguages()) {
			System.out.println(language.getDisplayName());
		}
		System.out.println("========================");
		*/
		
		//是否添加编程语言
		/*
			Java
			GNU C++ (Unix / Windows)
			GNU C (Unix / Windows)
			Perl
			PHP
			Python
			Python 3
			Ruby
			Microsoft C++
			Kylix Delphi
			Kylix C++
			Free Pascal
		*/
		if(admin_serverConnection.getIInternalContest().getLanguages().length < 1) {
			admin_serverConnection.addLanguage("Java");
			admin_serverConnection.addLanguage("GNU C++ (Unix / Windows)");
			admin_serverConnection.addLanguage("GNU C (Unix / Windows)");
			admin_serverConnection.addLanguage("PHP");
//			admin_serverConnection.addLanguage("Microsoft C++");
			admin_serverConnection.addLanguage("Python");
			System.out.println("已添加编程语言");
		}
		
		judge_serverConnection  = new ServerConnection();
		try {
			judge_serverConnection.login("judge1", "judge1");
		} catch (LoginFailureException e) {
			e.printStackTrace();
		}
		System.out.println("已初始化ServerConnection");
	}
	
	/**
	 * 获取Administrator角色的连接
	 * @return
	 */
	public static ServerConnection getAdmin_serverConnection() {
		return admin_serverConnection;
	}
	
	/**
	 * 获取Judge角色的连接
	 * @return
	 */
	public static ServerConnection getJudge_serverConnection() {
		return judge_serverConnection;
	}

}

