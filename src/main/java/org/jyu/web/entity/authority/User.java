package org.jyu.web.entity.authority;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.jyu.web.entity.question.Question;
import org.jyu.web.entity.question.Solution;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * @ClassName: User  
 * @Description: 用户类 (用户可以同时是管理员,老师,学生)
 * @author unclesky4 
 * @date Mar 24, 2018 10:34:47 AM 
 *
 */

@Entity

public class User implements Serializable {

	private static final long serialVersionUID = 3923786688496854082L;

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String uid;
	
	@Column(nullable=false, length=20)
	private String name;    //昵称
	
	@Column(nullable=false, length=50)
	private String email;    //邮箱帐号
	
	@JsonIgnore
	@Column(nullable=false, length=32)
	private String pwd;  //密码
	
	@Column(nullable=false, length=1)
	private Integer validation;    //邮箱是否已验证   1：已验证   0：未验证
	
	@Column(nullable=false, length=22)
	private String registerTime;
	
	@JsonIgnore
	@Column(nullable=false, length=32)
	private String code;       //uuid值，用于验证
	
	@Column(nullable=false, length=22)
	private String deadline;     //验证期限（24小时）
	
	@OneToOne(mappedBy="user")
	private Teacher teacher;
	
	@OneToOne(mappedBy="user")
	private Student student;
	
	@OneToMany(mappedBy="author")
	private List<Question> questions;
	
	@OneToMany(mappedBy="submitter")
	private List<Solution> solutions;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Integer getValidation() {
		return validation;
	}

	public void setValidation(Integer validation) {
		this.validation = validation;
	}

	public String getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
	//private Set<QuestionSet> questionSets = new HashSet<>();
}
