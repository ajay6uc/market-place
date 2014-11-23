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
    @Column(name = "FNAME")
    private String fname;
    
    @Persistent
    @Column(name = "LNAME")
    private String lname;
    
    @Persistent
    @Column(name = "USERNAME")
    private String username;
    
    @Persistent
    @Column(name = "PASSWORD")
    private String password;
    
    @Persistent
    @Column(name = "EMAIL")
    private String email;
    
    public User() {
        super();
    }
    
    /**
     * @param sourceName The Source Name
     * @param sourceId The Source Id.
     * @param fname The First Name.
     * @param lname The Last Name.
     */
    public User(String sourceName, String sourceId, String fname, String lname) {
        this.fname = fname;
        this.lname = lname;
    }
    
    /**
     * @return the name
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * @param username the name to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getFname() {
        return fname;
    }
    
    public void setFname(String fname) {
        this.fname = fname;
    }
    
    public String getLname() {
        return lname;
    }
    
    public void setLname(String lname) {
        this.lname = lname;
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
    
   
    public void merge(User u) {
        setFname(u.getFname());
        setLname(u.getLname());
        setEmail(u.getEmail());
    }
    
    public String getDisplayFNameLName() {
        return StringUtils.join(fname, " ", lname);
    }
    
    public String getDisplayLNameFName() {
        return String.format("%s %s", getLname(), getFname());
    }
    
    @Override
    public void jdoPreStore() {
        JDOInstanceCallbacksUtil.populateEntityFields(this);
    }
    
}