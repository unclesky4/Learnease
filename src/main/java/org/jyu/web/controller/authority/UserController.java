package org.jyu.web.controller.authority;

import java.util.Map;

import javax.validation.constraints.NotBlank;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.Length;
import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.StudentJson;
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
	 * @param name   昵称
	 * @param email  邮箱
	 * @param pwd    密码
	 * @param pwd_repeat  确认密码
	 * @return  Result对象
	 */
	@RequestMapping(value="/user/register", method=RequestMethod.POST)
	public Result register(@Length(min=1, max=20, message="昵称长度为1-20位")String name,
			@Length(min=1, max=20, message="邮箱长度为1-50位")String email,
			@NotBlank(message="密码不能为空")String pwd,
			@NotBlank(message="确认密码不能为空")String pwd_repeat) {
		
		if (!pwd.equals(pwd_repeat)) {
			return new Result(false, "两次密码不匹配");
		}
		Result result = userService.save(name, email, pwd);
		if (result.getSuccess()) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					MailUtil.sendMail(email, result.toString());
				}
			}).start();
		}else{
			return new Result(true, "注册失败");
		}
		return new Result(true, "注册成功，请验证邮箱");
	}
	
	/**
	 * 删除用户
	 * @param uid  用户主键
	 * @return  Result对象
	 */
	@RequestMapping(value="/user/delete", method=RequestMethod.POST)
	public Result deleteById(String uid) {
		return userService.deleteById(uid);
	}
	
	/**
	 * 登陆用户修改昵称
	 * @param userName  昵称
	 * @return  Result对象
	 */
	@RequestMapping(value="/user/updateUserName", method=RequestMethod.POST)
	public Result updateUserName(@Length(min=1, max=20, message="昵称长度为1-20位")String userName) {
		String userId = (String) SecurityUtils.getSubject().getSession().getAttribute("userId");
		if (userId == null) {
			return new Result(false, "请登陆");
		}
		return userService.update(userId, userName, null);
	}
	
	/**
	 * 修改用户密码
	 * @param old_pwd  旧密码
	 * @param new_pwd1  新密码
	 * @param new_pwd2  确认密码
	 * @return   Result对象
	 */
	@RequestMapping(value="/user/updatePwd", method=RequestMethod.POST)
	public Result updatePwd(@NotBlank(message="请输入旧密码")String old_pwd, 
			@NotBlank(message="请输入新密码")String new_pwd1, 
			@NotBlank(message="请输入确认密码")String new_pwd2) {
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
	 * @param uid   主键
	 * @return  UserJson对象
	 */
	@RequestMapping(value="/user/findUserById", method=RequestMethod.GET)
	public UserJson findUserbyId(String uid) {
		User user = userService.findById(uid);
		UserJson json = transfer(user);
		return json;
	}
	
	/**
	 * 通过邮箱帐号查找用户
	 * @param userEmail   邮箱
	 * @return   Userson对象
	 */
	@RequestMapping(value="/user/findUserByEmail", method=RequestMethod.GET)
	public UserJson findUserByEmail(String userEmail) {
		User user = userService.findByEmail(userEmail);
		UserJson json = this.transfer(user);
		return json;
	}
	
	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize     分页大小
	 * @param sortOrder    排序
	 * @param searchText   查询字段
	 * @return  Map集合
	 */
	@RequestMapping(value="/user/page_json", method=RequestMethod.GET)
	public Map<String,Object> getAllUser(int pageNumber, int pageSize, String sortOrder, String searchText) {
		return userService.findUserByPage(pageNumber, pageSize, sortOrder, searchText);
	}
	
	/**
	 * 跳转到登陆界面
	 * @param mv   ModelAndView
	 * @return  ModelAndView
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public ModelAndView login_html(ModelAndView mv) {
		mv.setViewName("authority/login.html");
		return mv;
	}
	
	/**
	 * 登陆  - shiro
	 * @param email  邮箱
	 * @param pwd    密码
	 * @param rememberMe  记住我（true/false）
	 * @return   Result对象
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
	    subject.getSession().setTimeout(3*24*60*60*1000);
	    return new Result(true, "登陆成功");
	}
	
	/**
	 * 判断是否有用户登陆
	 * @return   Result对象
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
	 * @return    Result对象
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
		StudentJson json = studentService.findById(user.getStudent().getStuId());
		if (user.getStudent().getStatus() != null) {
			return new Result(false, json);
		}
		return new Result(true, json);
	}
	
	/**
	 * 判断是否有教师角色
	 * @return   Result对象
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
		if (user.getTeacher().getStatus() != null) {
			return new Result(false, json);
		}
		return new Result(true, json);
	}
	
	/**
	 * 邮箱激活页面
	 * @param mv  ModelAndView
	 * @return   ModelAndView
	 */
	@RequestMapping(value="/validate_html",method=RequestMethod.GET)
	public ModelAndView validate_html(ModelAndView mv) {
		mv.setViewName("authority/validate.html");
		return mv;
	}
	
	/**
	 * 重置密码页面
	 * @param mv   ModelAndView
	 * @return    ModelAndView
	 */
	@RequestMapping(value="/reset_pwd_html",method=RequestMethod.GET)
	public ModelAndView resetPwd_html(ModelAndView mv) {
		mv.setViewName("authority/resetPwd.html");
		return mv;
	}
	
	/**
	 * 用户激活
	 * @param code   唯一验证码
	 * @return  Result对象
	 */
	@RequestMapping(value="/user/validate",method=RequestMethod.POST)
	public Result validate(String code) {
		return userService.validate(code);
	}
	
	/**
	 * 发送重置密码邮件
	 * @param userEmail  邮箱
	 * @return  Result对象
	 */
	@RequestMapping(value="/user/resetPwdEmail",method=RequestMethod.POST)
	public Result resetPwdEmail(String userEmail) {
		User user = userService.findByEmail(userEmail);
		if (user == null) {
			return new Result(false, "邮箱不存在");
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				MailUtil.sendResetPwdEmail(userEmail, user.getCode());
			}
		}).start();
		return new Result(true, "重置密码邮箱已发送");
	}
	
	/**
	 * 重置密码
	 * @param code    唯一验证码
	 * @param pwd_1   新密码
	 * @param pwd_2   确认密码
	 * @return   Result对象
	 */
	@RequestMapping(value="/user/resetPwd",method=RequestMethod.POST)
	public Result resetPassword(String code, String pwd_1, String pwd_2) {
		if (!pwd_1.equals(pwd_2)) {
			return new Result(false, "两次密码不匹配");
		}
		return userService.resetPassword(code, pwd_1);
	}
	
	/**
	 * User转成UserJson对象
	 * @param user    User对象
	 * @return   UserJson对象
	 */
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
