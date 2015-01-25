package com.marketplace.dataaccess.node;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.Extensions;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.listener.StoreCallback;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.marketplace.question.QuestionType;
import com.marketplace.question.Status;
import com.marketplace.shared.common.JDOInstanceCallbacksUtil;
import com.marketplace.shared.common.framework.entity.AbstractEntity;

/**
 * An entity to represent TEST
 */
@PersistenceCapable(table = "TEST",  detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class Test extends Node  {
	private static final long serialVersionUID = 1L;
	

	public Test() {
		super();
	}
	
	@Persistent
    @Column(name = "MARKS")
    private Long marks;
	
	@Persistent
    @Column(name = "DURATIOIN")
    private Long minutes;
	    
	@Persistent
	@Element(column="TEST_ID", dependent="true")
	Set<TestQuestion> testQuestions;
			
	
	@Override
	public void jdoPreStore() {
		//JDOInstanceCallbacksUtil.populateEntityFields(this);
	}


	public Long getMarks() {
		return marks;
	}


	public void setMarks(Long marks) {
		this.marks = marks;
	}


	public Long getMinutes() {
		return minutes;
	}


	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}


	public Set<TestQuestion> getTestQuestions() {
		return testQuestions;
	}


	public void setTestQuestions(Set<TestQuestion> testQuestions) {
		this.testQuestions = testQuestions;
	}
	
	

}