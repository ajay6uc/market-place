package com.marketplace.dataaccess.node;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.listener.StoreCallback;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.jdo.annotations.DiscriminatorStrategy;

import org.apache.commons.lang3.StringUtils;

import com.marketplace.org.Org;
import com.marketplace.shared.common.JDOInstanceCallbacksUtil;
import com.marketplace.shared.common.framework.entity.AbstractEntity;

/**
 * An entity to represent Node
 */
@PersistenceCapable(table = "Node", detachable = "true")
@Discriminator(strategy = DiscriminatorStrategy.CLASS_NAME, column = "NODE_TYPE")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Node extends AbstractEntity implements StoreCallback {
	private static final long serialVersionUID = 1L;

	@Persistent
	@Column(name = "NAME")
	private String name;

	@Persistent
	@XmlTransient
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Persistent
	@Column(name = "CONTENT_URL")
	private String contentUrl;

	@Persistent
	@Column(name = "SUBJECT")
	private String subject;
	@Persistent
	
	@Column(name = "GRADE")
	private String grade;
	
	@Persistent
	@Column(name = "EXAM")
	private String exam;

	
	@Persistent
	@Column(name = "PARENT_ID")
	@ForeignKey(name = "NODE_PARENTID_FK", deleteAction = ForeignKeyAction.NONE, updateAction = ForeignKeyAction.NONE)
	@XmlTransient
	private Node parent;
	
  	@Persistent
    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
    
    @Persistent
    @Column(name = "IS_VISIBLE")
    private boolean visible;
	
    @NotPersistent
    private Set<Node> children;
//	
	@Persistent
	@Column(name = "ORG_ID")
	private Long orgId;
	
	@NotPersistent
	private Long parentId;
	
	@Persistent
	@Column(name = "CODE")
	private String code;
	
	@Persistent
	@Column(name = "COMPLEXITY_LEVEL")
	private String complexityLevl;
	
	public Node() {
		super();
	}
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlTransient
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
	
	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	
	
	public String getExam() {
		return exam;
	}

	public void setExam(String exam) {
		this.exam = exam;
	}
	
	@Override
	public void jdoPreStore() {
		JDOInstanceCallbacksUtil.populateEntityFields(this);
	}
	
	@XmlTransient
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public boolean isVisible() {
		return visible;
	}



	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Integer getDisplayOrder() {
		return displayOrder;
	}


	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}



	public Long getParentId() {
		return parentId;
	}

	

	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getComplexityLevl() {
		return complexityLevl;
	}



	public void setComplexityLevl(String complexityLevl) {
		this.complexityLevl = complexityLevl;
	}



	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}



	public Set<Node> getChildren() {
		return children;
	}



	public void setChildren(Set<Node> children) {
		this.children = children;
	}
	
	
	

}