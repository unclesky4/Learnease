package org.jyu.web.entity.paper;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

/**
 * @ClassName: PapersLabel 
 * @Description: 试卷标签
 * @author: unclesky4
 * @date: May 1, 2018 1:14:52 PM
 */
@Entity
public class PaperLabel implements Serializable {

	private static final long serialVersionUID = -2145995042963865418L;
	
	@Id
	@GenericGenerator(name="system-uuid", strategy="uuid")
	@GeneratedValue(generator="system-uuid")
	@Column(length=32)
	private String id;
	
	//试卷标签名称
	@Column(nullable=false, length=20, unique=true)
	private String name;
	
	@Column(nullable=false, length=20, updatable=false)
	private String createTime;

	//关联试卷
	@ManyToMany(mappedBy="paperLabels")
	private List<Paper> papers;

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<Paper> getPapers() {
		return papers;
	}

	public void setPapers(List<Paper> papers) {
		this.papers = papers;
	}
}
