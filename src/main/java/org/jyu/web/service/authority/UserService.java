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
	 * @Description: 模糊查询邮箱
	 * @param: @param email
	 * @return: List<User>
	 * @throws
	 *
	 */
	List<UserJson> findByEmailLike(String email);
	
	User findByEmail(String email);
	
	User findByEmailAndPwd(String email, String pwd);
	
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param sortOrder
	 * @param searchText
	 * @return
	 */
	Map<String, Object> findUserByPage(Integer pageNumber, Integer pageSize, String sortOrder, String searchText);
	
	/**
	 * 用户验证邮箱
	 * @param code
	 * @return
	 */
	Result validate(String code);
	
	/**
	 * 重置密码
	 * @param code
	 * @param pwd
	 * @return
	 */
	Result resetPassword(String code, String pwd);

}
