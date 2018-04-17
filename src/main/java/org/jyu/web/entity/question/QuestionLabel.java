package org.jyu.web.entity.question;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @ClassName: QuestionLabel 
 * @Description: 问题标签
 * @author: unclesky4
 * @date: Apr 1, 2018 10:00:37 AM
 */
@Entity
public class QuestionLabel implements Serializable {
	
	private static final long serialVersionUID = -5095660055271932970L;

	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;   //主键
	
	@Column(nullable=false, length=20)
	private String name;  //标签名

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
