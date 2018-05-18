package org.jyu.web.conf;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.jyu.web.shiro.CredentialsMatcher;
import org.jyu.web.shiro.MyShiroRealm;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ModelAttribute;

@Configuration
public class ShiroConfigration {
	
	//配置自定义权限登录器
	@Bean
    public Realm realm() {
		MyShiroRealm myShiroRealm = new MyShiroRealm();
		myShiroRealm.setCachingEnabled(true);
		myShiroRealm.setCredentialsMatcher(credentialsMatcher());
        return myShiroRealm;
    }
	
	//配置自定义的密码比较器
    @Bean
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    //处理拦截资源文件
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/", "anon");
    	chainDefinition.addPathDefinition("/admin/*", "anon");
    	chainDefinition.addPathDefinition("/static/**", "anon");
    	chainDefinition.addPathDefinition("/public/**", "anon");
    	chainDefinition.addPathDefinition("/logout", "logout");
        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
	}
	    
    @ModelAttribute(name = "subject")
    public Subject subject() {
        return SecurityUtils.getSubject();
    }
    
    //开启注解
    @Bean
    public static DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator(){

        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator=new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setUsePrefix(true);

        return defaultAdvisorAutoProxyCreator;
    }
}
