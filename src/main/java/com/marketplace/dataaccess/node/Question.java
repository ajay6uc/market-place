package com.marketplace.dataaccess.node;

import java.util.Set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.Extensions;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.listener.StoreCallback;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.marketplace.question.QuestionType;
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
	

	public Question() {
		super();
	}
	

	@Persistent
	@Column(name = "QUESION_TYPE")
	@Extensions({
	    @Extension(vendorName="datanucleus", key="enum-getter-by-value", value="getEnumByValue"),
	    @Extension(vendorName="datanucleus", key="enum-value-getter", value="getValue")
	   })
	private QuestionType questionType;

	@Persistent
    @Column(name = "ANSWER")
    private String answer;
	    
	
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


	public QuestionType getQuestionType() {
		return questionType;
	}


	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}

	
	
	

}