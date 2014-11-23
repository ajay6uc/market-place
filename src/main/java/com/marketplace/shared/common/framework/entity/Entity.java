package com.marketplace.shared.common.framework.entity;

import java.io.Serializable;

/**
 * Domain.java
 * 
 */
public interface Entity extends Serializable {
    /** The Entity's Id column name. */
    String ID_COLUMN_NAME = "id";
    
    /**
     * Getter of the property <tt>id</tt> of the domain.
     * 
     * @return the id of the current entity.
     */
    Long getId();
    
    /**
     * Getter of the property <tt>createDate</tt>
     * 
     * @return the createDate
     */
    Long getCreateDate();
    
    /**
     * Setter of the property <tt>createDate</tt>
     * 
     * @param createDate the createDate to set
     */
    void setCreateDate(Long createDate);
    
    /**
     * Getter of the property <tt>lastModifiedDate</tt>
     * 
     * @return the lastModifiedDate
     */
    Long getLastModifiedDate();
    
    /**
     * Setter of the property <tt>lastModifiedDate</tt>
     * 
     * @param lastModifiedDate the lastModifiedDate to set
     */
    void setLastModifiedDate(Long lastModifiedDate);
    
    /**
     * Getter of the property <tt>createdBy</tt>
     * 
     * @return
     */
    Long getCreatedBy();
    
    /**
     * Setter of the property <tt>createdBy</tt>
     * 
     * @param createdBy the createdBy to set
     */
    void setCreatedBy(Long createdBy);
    
    /**
     * Getter of the property <tt>lastModifiedBy</tt>
     * 
     * @return
     */
    Long getLastModifiedBy();
    
    /**
     * Setter of the property <tt>lastModifiedBy</tt>
     * 
     * @param lastModifiedBy the lastModifiedBy to set
     */
    void setLastModifiedBy(Long lastModifiedBy);
    
}