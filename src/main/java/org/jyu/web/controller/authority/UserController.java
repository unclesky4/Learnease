package org.jyu.web.controller.authority;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.TeacherJson;
import org.jyu.web.dto.authority.UserJson;
import org.jyu.web.entity.authority.User;
import org.jyu.web.service.authority.StudentService;
import org.jyu.web.service.authority.TeacherService;
import org.jyu.web.service.authority.UserService;
import org.jyu.web.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentService studentService;

	/**
	 * 注册用户
	 * @return
	 */
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	public Result register(String name,String email,String pwd, String pwd_repeat) {
		if (!pwd.equals(pwd_repeat)) {
			return new Result(false, "两次密码不匹配");
		}
		return userService.save(name, email, pwd);
	}
	
	/**
	 * 删除用户
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/user/delete", method=RequestMethod.POST)
	public Result deleteById(String uid) {
		return userService.deleteById(uid);
	}
	
	/**
	 * 修改昵称
	 * @return
	 */
	@RequestMapping(value="/user/updateUserName", method=RequestMethod.POST)
	public Result updateUserName(String userName) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "请登陆");
		}
		return userService.update(userId, userName, null);
	}
	
	/**
	 * 修改用户密码
	 * @param old_pwd
	 * @param new_pwd1
	 * @param new_pwd2
	 * @return
	 */
	@RequestMapping(value="/user/updatePwd", method=RequestMethod.POST)
	public Result updatePwd(String old_pwd, String new_pwd1, String new_pwd2) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "请登陆");
		}
		if (!new_pwd1.equals(new_pwd2)) {
			return new Result(false, "新密码不匹配");
		}
		if (old_pwd.equals(new_pwd1)) {
			return new Result(false, "新密码不能与原密码相同");
		}
		return userService.updatePwd(userId, old_pwd, new_pwd1);
	}
	
	/**
	 * 通过主键查找用户
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/user/findUserById", method=RequestMethod.GET)
	public UserJson findUserbyId(String uid) {
		User user = userService.findById(uid);
		UserJson json = transfer(user);
		return json;
	}
	
	/**
	 * 通过邮箱帐号查找用户
	 * @param userEmail
	 * @return
	 */
	@RequestMapping(value="/user/findUserByEmail", method=RequestMethod.GET)
	public UserJson findUserByEmail(String userEmail) {
		User user = userService.findByEmail(userEmail);
		UserJson json = this.transfer(user);
		return json;
	}
	
	/**
	 * 查询所有用户信息
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param searchText
	 * @return
	 */
	@RequestMapping(value="/user/getAllUser", method=RequestMethod.GET)
	public Map<String,Object> getAllUser(int pageNumber, int pageSize, String sortOrder, String searchText) {
		return userService.findUserByPage(pageNumber, pageSize, sortOrder, searchText);
	}
	
	/**
	 * 跳转到登陆界面
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/login_html", method=RequestMethod.GET)
	public ModelAndView login_html(ModelAndView mv) {
		mv.setViewName("/authority/login.html");
		return mv;
	}
	
	/**
	 * 登陆  - shiro
	 * @param email
	 * @param pwd
	 * @param rememberMe
	 * @return
	 */
	@RequestMapping(value="/user/login", method=RequestMethod.POST)
	public Result user_login(String email, String pwd, @RequestParam(value="rememberMe", defaultValue="false")Boolean rememberMe) {
		UsernamePasswordToken token = new UsernamePasswordToken(email, pwd);  
	    token.setRememberMe(rememberMe);
	    Subject subject = SecurityUtils.getSubject();
	    subject.login(token);
	    
	    User user = userService.findByEmail(email);
	    subject.getSession().setAttribute("userId", user.getUid());
	    subject.getSession().setAttribute("username", user.getName());
	    subject.getSession().setTimeout(5*60*60*1000);
	    return new Result(true, "登陆成功");
	}
	
	/**
	 * 判断是否有用户登陆
	 * @return
	 */
	@RequestMapping(value="/user/checkLogin",method=RequestMethod.GET)
	public Result checkLogin() {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "未登陆");
		}
		User user = userService.findById(userId);
		if (user == null) {
			return new Result(false, "用户不存在");
		}
		return new Result(true, transfer(user));
	}
	
	/**
	 * 判断是否有学生角色
	 * @return
	 */
	@RequestMapping(value="/user/isStudent",method=RequestMethod.GET)
	public Result isStudent() {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "未登陆");
		}
		User user = userService.findById(userId);
		if (user == null) {
			return new Result(false, "用户不存在");
		}
		if (user.getStudent() == null) {
			return new Result(false, "角色不存在");
		}
		return new Result(true, studentService.findById(user.getStudent().getStuId()));
	}
	
	/**
	 * 判断是否有教师角色
	 * @return
	 */
	@RequestMapping(value="/user/isTeacher",method=RequestMethod.GET)
	public Result isTeacher() {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "未登陆");
		}
		User user = userService.findById(userId);
		if (user == null) {
			return new Result(false, "用户不存在");
		}
		if (user.getTeacher() == null) {
			return new Result(false, "角色不存在");
		}
		TeacherJson json = teacherService.findById(user.getTeacher().getTid());
		return new Result(true, json);
	}
	
	/**
	 * 邮箱激活页面
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/validate_html",method=RequestMethod.GET)
	public ModelAndView validate_html(ModelAndView mv) {
		mv.setViewName("/authority/validate.html");
		return mv;
	}
	
	/**
	 * 重置密码页面
	 * @param mv
	 * @return
	 */
	@RequestMapping(value="/reset_pwd_html",method=RequestMethod.GET)
	public ModelAndView resetPwd_html(ModelAndView mv) {
		mv.setViewName("/authority/resetPwd.html");
		return mv;
	}
	
	/**
	 * 用户激活
	 * @return
	 */
	@RequestMapping(value="/user/validate",method=RequestMethod.POST)
	public Result validate(String code) {
		return userService.validate(code);
	}
	
	/**
	 * 发送重置密码邮件
	 * @param userEmail
	 * @return
	 */
	@RequestMapping(value="/user/resetPwdEmail",method=RequestMethod.POST)
	public Result resetPwdEmail(String userEmail) {
		User user = userService.findByEmail(userEmail);
		if (user == null) {
			return new Result(false, "邮箱不存在");
		}
		MailUtil.sendResetPwdEmail(userEmail, user.getCode());
		return new Result(true, "重置密码邮箱已发送");
	}
	
	/**
	 * 重置密码
	 * @return
	 */
	@RequestMapping(value="/user/resetPwd",method=RequestMethod.POST)
	public Result resetPassword(String code, String pwd_1, String pwd_2) {
		if (!pwd_1.equals(pwd_2)) {
			return new Result(false, "两次密码不匹配");
		}
		return userService.resetPassword(code, pwd_1);
	}
	
	
	UserJson transfer(User user) {
		UserJson json = new UserJson();
		json.setEmail(user.getEmail());
		json.setName(user.getName());
		json.setRegisterTime(user.getRegisterTime());
		json.setUid(user.getUid());
		json.setValidation(user.getValidation() == 0 ?"未验证":"已验证");
		return json;
	}
	
}
