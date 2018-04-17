package org.jyu.web.entity.question;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @ClassName: Option 
 * @Description: 选项
 * @author: unclesky4
 * @date: Apr 1, 2018 10:57:50 AM
 */
@Entity
@Table(name="question_option")
public class Option implements Serializable {
	
	private static final long serialVersionUID = 3136645715815268398L;
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	@Column(nullable=false,length=50)
	private String content;
	
	public Option() {}

	public Option(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
