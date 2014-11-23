package com.marketplace.shared.common;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.split;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SessionProfile implements Serializable {
    
    public static final String TOKEN_DELIMITER = "||";
    
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    
    //User related data
    private Long userId;
    private String userName;
    private String userSourceName;
    private String userSourceId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    
    //Org related data
    private Long orgId;
    private String orgName;
    private String orgExternalId;
    private Integer orgType;
    private Long orgParentId;
    private String orgParentExternalId;
    
  
    public Long getId() {
        return this.id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public Long getUserId() {
        return this.userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserSourceName() {
        return this.userSourceName;
    }
    
    public void setUserSourceName(String userSourceName) {
        this.userSourceName = userSourceName;
    }
    
    public String getUserSourceId() {
        return this.userSourceId;
    }
    
    public void setUserSourceId(String userSourceId) {
        this.userSourceId = userSourceId;
    }
    
    public String getUserFirstName() {
        return this.userFirstName;
    }
    
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }
    
    public String getUserLastName() {
        return this.userLastName;
    }
    
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }
    
    public String getUserEmail() {
        return this.userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public Long getOrgId() {
        return this.orgId;
    }
    
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    
    public String getOrgName() {
        return this.orgName;
    }
    
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    
    public String getOrgExternalId() {
        return this.orgExternalId;
    }
    
    public void setOrgExternalId(String orgExternalId) {
        this.orgExternalId = orgExternalId;
    }
    
    public Integer getOrgType() {
        return this.orgType;
    }
    
    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }
    
    public Long getOrgParentId() {
        return this.orgParentId;
    }
    
    public void setOrgParentId(Long orgParentId) {
        this.orgParentId = orgParentId;
    }
    
    public String getOrgParentExternalId() {
        return this.orgParentExternalId;
    }
    
    public void setOrgParentExternalId(String orgParentExternalId) {
        this.orgParentExternalId = orgParentExternalId;
    }
    
    /**
     * Convenient method to return User's firstName & lastName like: "John Smith"
     * 
     * @return String - User FirstName & LastName
     */
    public String getUserFNameLName() {
        return String.format("%s %s", getUserFirstName(), getUserLastName());
    }
    
    /**
     * Convenient method to return User's latName & firstName like: "Smith John"
     * 
     * @return
     */
    public String getUserLNameFName() {
        return String.format("%s %s", getUserLastName(), getUserFirstName());
    }
    
    public String getUserToken() {
        if (userSourceName == null && userSourceId == null && orgId == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        return sb.append(userSourceName)
                 .append(TOKEN_DELIMITER)
                 .append(userSourceId)
                 .append(TOKEN_DELIMITER)
                 .append(orgId.toString())
                 .toString();
    }
    
    /**
     * @param userToken
     * @return
     */
    public static SessionProfile createFromToken(String userToken) {
        SessionProfile profile = new SessionProfile();
        if (isBlank(userToken)) {
            return profile;
        }
        String[] tokens = split(userToken, TOKEN_DELIMITER);
        profile.setUserSourceName(tokens[0]);
        profile.setUserSourceId(tokens[1]);
        profile.setOrgId(Long.parseLong(tokens[2]));
        
        return profile;
    }
    
  
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}
