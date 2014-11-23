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
 * An entity to represent UserSession 
 */
@PersistenceCapable(table = "USER_SESSION",  detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class UserSession extends AbstractEntity implements StoreCallback {
    private static final long serialVersionUID = 1L;
    
    
    @Persistent
    @Column(name = "USER_ID")
    private Long userId;
    
    @Persistent
    @Column(name = "SESSION_ID")
    private Long sessionId;
        
    @Persistent
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
    
    @Persistent
    @Column(name = "IP_ADDRESS")
    private Long ipAddress;
    
    /**
     */
    public UserSession() {
        super();
    }
    
    /**
     * 
     * @param sourceName The Source Name
     * @param sourceId The Source Id.
     * @param fname The First Name.
     * @param lname The Last Name.
     */
    
    
    @Override
    public void jdoPreStore() {
        JDOInstanceCallbacksUtil.populateEntityFields(this);
    }

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(Long ipAddress) {
		this.ipAddress = ipAddress;
	}

	public UserSession(Long userId, boolean isActive, Long ipAddress) {
		super();
		this.userId = userId;
		this.isActive = isActive;
		this.ipAddress = ipAddress;
	}
    
}