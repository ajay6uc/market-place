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
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.Order;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.listener.StoreCallback;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.StringUtils;

import com.marketplace.question.QuestionType;
import com.marketplace.question.Status;
import com.marketplace.shared.common.JDOInstanceCallbacksUtil;
import com.marketplace.shared.common.framework.entity.AbstractEntity;

/**
 * An entity to represent TestQuestion
 */
@PersistenceCapable(table = "TEST_QUESTION",  detachable = "true")
@Inheritance(strategy=InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class TestQuestion  extends AbstractEntity implements StoreCallback  {
	private static final long serialVersionUID = 1L;
	

	public TestQuestion() {
		super();
	}
	
	@NotPersistent
    private Long questionId;
	
	@Persistent
    @Column(name = "MARKS")
    private Long marks;
	
	@Persistent
    @Column(name = "QUESTION_ID")
	@ForeignKey(name = "TEST_QUESTION_QUESION_FK", deleteAction = ForeignKeyAction.CASCADE, updateAction = ForeignKeyAction.CASCADE)
    private Question question;
	
	
	
	public Long getMarks() {
		return marks;
	}



	public void setMarks(Long marks) {
		this.marks = marks;
	}



	public Question getQuestion() {
		return question;
	}



	public void setQuestion(Question question) {
		this.question = question;
	}



	
	public Long getQuestionId() {
		return questionId;
	}



	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}



	@Override
	public void jdoPreStore() {
		//JDOInstanceCallbacksUtil.populateEntityFields(this);
	}
	
	

}