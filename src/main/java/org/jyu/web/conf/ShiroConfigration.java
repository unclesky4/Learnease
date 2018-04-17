package org.jyu.web.conf;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.subject.Subject;
import org.jyu.web.shiro.CredentialsMatcher;
import org.jyu.web.shiro.MyShiroRealm;
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
        chainDefinition.addPathDefinition("/permission_html", "anon");
        chainDefinition.addPathDefinition("/public/**", "anon");
        chainDefinition.addPathDefinition("/static/**", "anon");
        chainDefinition.addPathDefinition("/login.html", "authc"); // need to accept POSTs from the login form
        chainDefinition.addPathDefinition("/logout", "logout");
        return chainDefinition;
    }
    
    @ModelAttribute(name = "subject")
    public Subject subject() {
        return SecurityUtils.getSubject();
    }
}
