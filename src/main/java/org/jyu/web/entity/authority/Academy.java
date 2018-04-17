package org.jyu.web.entity.authority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

/**
 * 
 * @ClassName: Academy  
 * @Description: 学院
 * @author unclesky4 
 * @date Mar 24, 2018 10:32:08 PM 
 *
 */
//@Entity
public class Academy {
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	@Column(nullable=false, length=32)
	private String name; //学院名
	
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

	public Academy() {}

	public Academy(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
