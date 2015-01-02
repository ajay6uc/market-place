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
@PersistenceCapable(table = "QUESTION_OPTION",  detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class QuestionOption extends AbstractEntity implements StoreCallback {
    private static final long serialVersionUID = 1L;
    
    
    @Persistent
	@Column(name = "CODE")
	private String code;

	@Persistent
	@Column(name = "DESCRIPTION")
	private String description;
    
    @Persistent
    @Column(name = "IS_CORRECT")
    private boolean correct;
    
    @Persistent
    @Column(name = "QUESTION_ID")
    private Question question;
    
    
    
    public QuestionOption() {
        super();
    }
    
    
	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public boolean isCorrect() {
		return correct;
	}



	public void setCorrect(boolean correct) {
		this.correct = correct;
	}



	public Question getQuestion() {
		return question;
	}



	public void setQuestion(Question question) {
		this.question = question;
	}
	
	



	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	@Override
    public void jdoPreStore() {
        JDOInstanceCallbacksUtil.populateEntityFields(this);
    }
    
}