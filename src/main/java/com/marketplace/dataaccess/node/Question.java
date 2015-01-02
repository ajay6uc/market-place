package com.marketplace.dataaccess.node;

import java.util.Set;

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
 * An entity to represent Question
 */
@PersistenceCapable(table = "QUESTION",  detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class Question extends Node  {
	private static final long serialVersionUID = 1L;

//	@Persistent
//	@Column(name = "NAME")
//	private String name;

	
	public Question() {
		super();
	}

//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
	
	@Persistent(mappedBy = "question", dependentElement = "true")
	private Set<QuestionOption> questionOptions;
		
	
	@Override
	public void jdoPreStore() {
		//JDOInstanceCallbacksUtil.populateEntityFields(this);
	}


	public Set<QuestionOption> getQuestionOptions() {
		return questionOptions;
	}


	public void setQuestionOptions(Set<QuestionOption> questionOptions) {
		this.questionOptions = questionOptions;
	}
	
	

}