package org.jyu.web.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jyu.web.entity.authority.Student;
import org.jyu.web.entity.authority.Teacher;
import org.jyu.web.entity.authority.User;
import org.jyu.web.entity.manage.Administrator;
import org.jyu.web.entity.manage.Permission;
import org.jyu.web.entity.manage.Role;
import org.jyu.web.service.authority.UserService;
import org.jyu.web.service.manage.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 身份校验,过滤条件 --核心类
 * @author clouder
 * 
 * 
    1、检查提交的进行认证的令牌信息
    2、根据令牌信息从数据源(通常为数据库)中获取用户信息
    3、对用户信息进行匹配验证。
    4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
    5、验证失败则抛出AuthenticationException异常信息。

 *
 */
public class MyShiroRealm extends AuthorizingRealm{

	//用于用户查询
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdministratorService adminService;
	
	//角色和对应权限添加 - 例中该方法的调用时机为需授权资源被访问时
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
		//获取登录用户
		String name = (String) principalCollection.getPrimaryPrincipal();
		
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		
		User user = userService.findByEmail(name);
		if(user != null) {
			//添加角色和权限
			Student student = user.getStudent();
			if(student != null && student.getStatus() == 1) {
				simpleAuthorizationInfo.addRole(student.getRole().getCode());
				for (String permission : student.getRole().getPermissions()) {
					//System.out.println(permission);
					simpleAuthorizationInfo.addStringPermission(permission);
				}
			}
			Teacher teacher = user.getTeacher();
			if(teacher != null && teacher.getStatus() == 1) {
				simpleAuthorizationInfo.addRole(teacher.getRole().getCode());
				for (String permission : teacher.getRole().getPermissions()) {
					//System.out.println(permission);
					simpleAuthorizationInfo.addStringPermission(permission);
				}
			}
			return simpleAuthorizationInfo;
		}
		
		Administrator administrator = adminService.findByName(name);
		if (administrator != null) {
			//添加角色和权限
			List<Role> roles = administrator.getRoles();
			for (Role role : roles) {
				simpleAuthorizationInfo.addRole(role.getCode());
				List<Permission> permissions = role.getPermissions();
				for (Permission permission : permissions) {
					simpleAuthorizationInfo.addStringPermission(permission.getCode());
				}
			}
			return simpleAuthorizationInfo;
		}
		return null;
	}

	//用户认证 -- 验证用户输入的账号是否存在
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken utoken = (UsernamePasswordToken) token;

        String username = utoken.getUsername();
        
        User user = userService.findByEmail(username);
        
        if(user != null) {
        	//放入shiro.调用CredentialsMatcher检验密码
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, user.getPwd(), getName());
            return simpleAuthenticationInfo;
        }
    	
        Administrator administrator = adminService.findByName(username);
        if (administrator != null) {
        	//放入shiro.调用CredentialsMatcher检验密码
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, administrator.getPwd(), getName());
            return simpleAuthenticationInfo;
		}
        return null;
	}

}
