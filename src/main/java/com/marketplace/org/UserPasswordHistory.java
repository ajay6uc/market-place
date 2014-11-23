package com.marketplace.org;

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
 * An entity to represent UserPasswordHistory 
 */
@PersistenceCapable(table = "USER_PASSWORD_HISTORY",  detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class UserPasswordHistory extends AbstractEntity implements StoreCallback {
    private static final long serialVersionUID = 1L;
    
    
    @Persistent
    @Column(name = "PREV_PASSWORD")
    private String prevPassword;
    
    @Persistent
    @Column(name = "CURRENT_PASSWORD")
    private String currentPassword;
    
    @Persistent
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    
    @Persistent
    @Column(name = "USER_ID")
    private Long userId;
    
    
    /**
     */
    public UserPasswordHistory() {
        super();
    }
    
    /**
     * 
     * @param sourceName The Source Name
     * @param sourceId The Source Id.
     * @param fname The First Name.
     * @param lname The Last Name.
     */

	public UserPasswordHistory(String prevPassword, String currentPassword,
			boolean isActive, Long userId) {
		super();
		this.prevPassword = prevPassword;
		this.currentPassword = currentPassword;
		this.isActive = isActive;
		this.userId = userId;
	}
	
	
	
	public String getPrevPassword() {
		return prevPassword;
	}

	public void setPrevPassword(String prevPassword) {
		this.prevPassword = prevPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
    public void jdoPreStore() {
        JDOInstanceCallbacksUtil.populateEntityFields(this);
    }
    
}