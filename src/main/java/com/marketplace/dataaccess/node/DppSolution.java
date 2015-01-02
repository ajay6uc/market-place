package com.marketplace.dataaccess.node;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;
import javax.jdo.listener.StoreCallback;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.marketplace.org.User;
import com.marketplace.shared.common.JDOInstanceCallbacksUtil;
import com.marketplace.shared.common.framework.entity.AbstractEntity;

/**
 * An entity to represent DPP_SOLUTION
 */
@PersistenceCapable(table = "DPP_SOLUTION", detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@Unique(name="UNIQUE_DPP_ID_AND_USER_IDX", members={"dpp"})
@XmlRootElement
public class DppSolution extends AbstractEntity implements StoreCallback {
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
	@Column(name = "DPP_ID")
	@Index(name = "DPPSOUTION_DPPID_IDX")
    @ForeignKey(name = "DPPSOUTION_DPPID_FK",
                deleteAction = ForeignKeyAction.NONE,
                updateAction = ForeignKeyAction.NONE)
	private Question dpp;	
	
//	@Persistent
//	@Column(name = "USER_ID")
//	@Index(name = "DPPSOUTION_USERID_IDX")
//    @ForeignKey(name = "DPPSOUTION_USERID_FK",
//                deleteAction = ForeignKeyAction.NONE,
//                updateAction = ForeignKeyAction.NONE)
//	private User user;	

	public DppSolution() {
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

	
	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	
	
	public Question getDpp() {
		return dpp;
	}

	public void setDpp(Question dpp) {
		this.dpp = dpp;
	}

	@Override
	public void jdoPreStore() {
		//JDOInstanceCallbacksUtil.populateEntityFields(this);
	}

}