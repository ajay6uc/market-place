package com.marketplace.dataaccess.node;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.listener.StoreCallback;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.marketplace.shared.common.JDOInstanceCallbacksUtil;
import com.marketplace.shared.common.framework.entity.AbstractEntity;

/**
 * An entity to represent User
 */
@PersistenceCapable(table = "Dpp", detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class Dpp extends AbstractEntity implements StoreCallback {
	private static final long serialVersionUID = 1L;

	@Persistent
	@Column(name = "NAME")
	private String name;

	@Persistent
	@Column(name = "Description")
	private String description;

	@Persistent
	@Column(name = "Subject")
	private String subject;

	@Persistent
	@Column(name = "Grade")
	private String grade;

	@Persistent
	@Column(name = "Concept")
	private String concept;

	@Persistent
	@Column(name = "ContentUrl")
	private String contentUrl;

	@Persistent
	@Column(name = "level")
	private String level;

	public Dpp() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Override
	public void jdoPreStore() {
		JDOInstanceCallbacksUtil.populateEntityFields(this);
	}

}