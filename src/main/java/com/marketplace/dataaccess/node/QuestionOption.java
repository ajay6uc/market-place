package com.marketplace.dataaccess.node;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.listener.StoreCallback;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @Column(name = "QUESTION_ID")
    @ForeignKey(name = "QUESTION_QUESTION_OPTION_FK", deleteAction = ForeignKeyAction.CASCADE, updateAction = ForeignKeyAction.CASCADE)
    @XmlTransient
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

	@XmlTransient
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
        //JDOInstanceCallbacksUtil.populateEntityFields(this);
    }
    
}