package org.jyu.web.service.manage;

import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.entity.manage.Administrator;

public interface AdministratorService {
	
	/**
	 * 添加管理员
	 * @param name    姓名
	 * @param pwd    密码
	 * @param sex    性别
	 * @param phone  电话
	 * @param email   邮箱
	 * @param roleIds   角色主键（逗号分割）
	 * @return
	 */
	Result save(String name, String pwd, String sex, String phone, String email, String roleIds);
	
	/**
	 * 更新管理员信息
	 * @param id     管理员主键
	 * @param name   姓名
	 * @param sex    性别
	 * @param phone  电话
	 * @param email  邮箱
	 * @param roleIds  角色主键（逗号分割）
	 * @return
	 */
	Result update(String id, String name, String sex, String phone, String email, String roleIds);
	
	/**
	 * 修改某个管理员的密码
	 * @param id   管理员主键
	 * @param oldPwd   旧密码
	 * @param newPwd   新密码
	 * @return
	 */
	Result updatePwd(String id, String oldPwd, String newPwd);
	
	/**
	 * 修改密码
	 * @param id  管理员主键
	 * @param password  新密码
	 * @return
	 */
	Result updatePassword(String id, String password);
	
	/**
	 * 删除管理员
	 * @param id   管理员主键
	 * @return
	 */
	Result deleteById(String id);
	
	/**
	 * 通过主键查询管理员
	 * @param id   管理员主键
	 * @return
	 */
	Administrator findById(String id);
	
	/**
	 * 通过姓名查询管理员
	 * @param name   姓名
	 * @return
	 */
	Administrator findByName(String name);
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> finAll(Integer pageNumber, Integer pageSize);

}
