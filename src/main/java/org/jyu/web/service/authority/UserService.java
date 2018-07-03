package org.jyu.web.service.authority;

import java.util.List;
import java.util.Map;

import org.jyu.web.dto.Result;
import org.jyu.web.dto.authority.UserJson;
import org.jyu.web.entity.authority.User;

public interface UserService {
	
	Result save(String name,String email,String pwd);
	
	Result update(String id, String name, Integer validation);
	
	Result updatePwd(String id, String oldPwd, String newPwd);
	
	Result delete(List<String> uids);
	
	Result deleteById(String uid);
	
	User findById(String uid);
	
	/**
	 * 
	 * 通过邮箱模糊查询
	 * @param email   邮箱
	 * @return: List集合
	 *
	 */
	List<UserJson> findByEmailLike(String email);
	
	/**
	 * 通过邮箱查询
	 * @param email    邮箱
	 * @return    User对象
	 */
	User findByEmail(String email);
	
	/**
	 * 通过邮箱和密码登陆
	 * @param email   邮箱
	 * @param pwd    密码
	 * @return  User对象
	 */
	User findByEmailAndPwd(String email, String pwd);
	
	/**
	 * 分页查询
	 * @param pageNumber   页码
	 * @param pageSize    分页大小
	 * @param sortOrder   排序
	 * @param searchText  查询字段
	 * @return  Map集合
	 */
	Map<String, Object> findUserByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText);
	
	/**
	 * 用户验证邮箱
	 * @param code    唯一验证码
	 * @return  Result对象
	 */
	Result validate(String code);
	
	/**
	 * 重置密码
	 * @param code   唯一验证码
	 * @param pwd   新密码
	 * @return   Result对象
	 */
	Result resetPassword(String code, String pwd);

}
