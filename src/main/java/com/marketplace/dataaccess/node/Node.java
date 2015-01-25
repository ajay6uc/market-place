package com.marketplace.dataaccess.node;

import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.Extension;
import javax.jdo.annotations.Extensions;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.Index;
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
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.jdo.annotations.DiscriminatorStrategy;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

import com.marketplace.org.Org;
import com.marketplace.question.ComplexityLevel;
import com.marketplace.question.Status;
import com.marketplace.shared.common.JDOInstanceCallbacksUtil;
import com.marketplace.shared.common.framework.entity.AbstractEntity;

/**
 * An entity to represent Node
 */
@PersistenceCapable(table = "Node", detachable = "true")
@Discriminator(strategy = DiscriminatorStrategy.CLASS_NAME, column = "NODE_TYPE")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public abstract class Node extends AbstractEntity implements StoreCallback {
	private static final long serialVersionUID = 1L;

	@Persistent
	@Column(name = "NAME")
	private String name;

	@Persistent
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
	@Column(name = "Status")
	@Extensions({
	    @Extension(vendorName="datanucleus", key="enum-getter-by-value", value="getEnumByValue"),
	    @Extension(vendorName="datanucleus", key="enum-value-getter", value="getValue")
	   })
	private Status status;
	
	@Persistent
	@Column(name = "EXAM")
	private String exam;

	@Persistent
	@Column(name = "PARENT_ID")
	@Index(name = "NODE_PARENTID_IDX")
	@ForeignKey(name = "NODE_PARENTID_FK", deleteAction = ForeignKeyAction.CASCADE, updateAction = ForeignKeyAction.CASCADE)
	private Node parent;
	
  	@Persistent
    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;
    
    @Persistent
    @Column(name = "IS_VISIBLE")
    private boolean visible;
    //THe backpointer object will not be populated while marshing object, in this case chidren.parent is pointed it back to this object
	//http://blog.bdoughan.com/2013/03/moxys-xmlinversereference-is-now-truly.html
    @Persistent(mappedBy = "parent")
    @XmlInverseReference(mappedBy = "parent")
	private Set<Node> children;

    @Persistent
	@Column(name = "ORG_ID")
	private Long orgId;
	
	@NotPersistent
	private Long parentId;
	
	@Persistent
	@Column(name = "CODE")
	private String code;
	

	@Persistent
	@Column(name = "IMAGE_PATH")
	private String imagePath;
	
	
	@Persistent
	@Column(name = "COMPLEXITY_LEVEL")
	@Extensions({
	    @Extension(vendorName="datanucleus", key="enum-getter-by-value", value="getEnumByValue"),
	    @Extension(vendorName="datanucleus", key="enum-value-getter", value="getValue")
	   })
	private ComplexityLevel complexityLevel;
	
	public Node() {
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
	
		if (this.parentId != null) {
			return this.parentId;
		} else {

			return this.getParent().getId();
		}
	}
	

	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
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



	public ComplexityLevel getComplexityLevel() {
		return complexityLevel;
	}



	public void setComplexityLevel(ComplexityLevel complexityLevel) {
		this.complexityLevel = complexityLevel;
	}



	public String getImagePath() {
		return imagePath;
	}



	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}



	public Status getStatus() {
		return status;
	}



	public void setStatus(Status status) {
		this.status = status;
	}

	

}