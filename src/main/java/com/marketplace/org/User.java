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
 * An entity to represent User 
 */
@PersistenceCapable(table = "USER",  detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class User extends AbstractEntity implements StoreCallback {
    private static final long serialVersionUID = 1L;
    
    
    @Persistent
    @Column(name = "NAME")
    private String name;
    
    @Persistent
    @Column(name = "CODE")
    private String secretCode;
    
    @Persistent
    @Column(name = "REMEMBER_ME")
    private boolean rememberMe;
    
    @Persistent
    @Column(name = "PASSWORD")
    private String password;
    
    @Persistent
    @Column(name = "EMAIL")
    private String email;
    
    @Persistent
    @Column(name = "CELL_NO")
    private String cellNo;
    
    
    public User() {
        super();
    }
    
      
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Deprecated
    public String getPassword() {
        return password;
    }
    
    @Deprecated
    public void setPassword(String password) {
        this.password = password;
    }
    
   
    
    public String getSecretCode() {
		return secretCode;
	}

	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}
	
	
	public boolean isRememberMe() {
		return rememberMe;
	}

	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}



	@Override
    public void jdoPreStore() {
        JDOInstanceCallbacksUtil.populateEntityFields(this);
    }
    
}