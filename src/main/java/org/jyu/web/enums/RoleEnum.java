package org.jyu.web.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
 * @ClassName: RoleEnum  
 * @Description: 角色
 * @author unclesky4 
 * @date Mar 24, 2018 1:44:43 PM 
 *
 */
public enum RoleEnum {
	
	Root("超级管理员", "root", Permission.getRootPermissions()),
	Admin("管理员", "admin", Permission.getAdminPermissions()),
	Student("学生", "student", Permission.getStudentPermissions()),
	Teacher("教师", "teacher", Permission.getTeacherPermissions());
	
	private String name;  //角色名称
	
	private String code;  //角色代码
	
	private Set<String> permissions;   //权限列表

	private RoleEnum(String name, String code, Set<String> permissions) {
		this.name = name;
		this.code = code;
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}
	
/*	public static void main(String[] args) {
		System.out.println(Permission.getRootPermissions());
	}*/

}

/**
 * 
 * @ClassName: Permission  
 * @Description: 权限类
 * @author unclesky4 
 * @date Mar 24, 2018 1:54:07 PM 
 *
 */
class Permission {
	static final String[] ROOT_PERMISSION = new String[]{
		"root:add",
		"root:del",
		"root:update",
		"root:query",
		"root:html",
		"admin:add",
		"admin:del",
		"admin:query",
		"admin:html"
	};
	
	static final String[] ADMIN_PERMISSION = new String[]{
		"admin:update",
		"student:add",
		"student:del",
		"student:query",
		"student:html",
		"teacher:add",
		"teacher:del",
		"teacher:query",
		"teacher:html",
	};
	
	static final String[] STUDENT_PERMISSION = new String[]{
		"student:update"
	};
	
	static final String[] TEACHER_PERMISSION = new String[]{
		"teacher:update",
		"question:add",
		"question:del"
	};
	
	static final String[] COMMON_PERMISSION = new String[]{
		"question:html",
		"question:query",
		"question:answer"
	};
	
	static Set<String> getRootPermissions() {
		List<String> list = new ArrayList<>();
		list.addAll(Arrays.asList(ROOT_PERMISSION));
		list.addAll(Arrays.asList(ADMIN_PERMISSION));
		list.addAll(Arrays.asList(TEACHER_PERMISSION));
		list.addAll(Arrays.asList(STUDENT_PERMISSION));
		list.addAll(Arrays.asList(COMMON_PERMISSION));
		Set<String> set = new HashSet<>();
		set.addAll(list);
		return set;
	}
	
	static Set<String> getAdminPermissions() {
		List<String> list = new ArrayList<>();
		list.addAll(Arrays.asList(ADMIN_PERMISSION));
		list.addAll(Arrays.asList(TEACHER_PERMISSION));
		list.addAll(Arrays.asList(STUDENT_PERMISSION));
		list.addAll(Arrays.asList(COMMON_PERMISSION));
		Set<String> set = new HashSet<>();
		set.addAll(list);
		return set;
	}

	static Set<String> getStudentPermissions() {
		List<String> list = new ArrayList<>();
		list.addAll(Arrays.asList(STUDENT_PERMISSION));
		list.addAll(Arrays.asList(COMMON_PERMISSION));
		Set<String> set = new HashSet<>();
		set.addAll(list);
		return set;
	}

	static Set<String> getTeacherPermissions() {
		List<String> list = new ArrayList<>();
		list.addAll(Arrays.asList(TEACHER_PERMISSION));
		list.addAll(Arrays.asList(COMMON_PERMISSION));
		Set<String> set = new HashSet<>();
		set.addAll(list);
		return set;
	}
}
