
package com.marketplace.shared.common.framework.entity;


import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Version;
import javax.jdo.annotations.VersionStrategy;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.base.Objects;


/**
 * AbstractEntity.java This is an abstract base class for the Entity class to extend from. Common functionality used by
 * descendants is implemented here.
 */
@Version(strategy = VersionStrategy.VERSION_NUMBER)
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@PersistenceCapable(objectIdClass = javax.jdo.identity.LongIdentity.class, detachable = "true")
@XmlRootElement
public abstract class AbstractEntity implements Entity {
    private static final long serialVersionUID = 1L;
    
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Persistent
    @Column(name = "CREATED_DATE")
    private Long createDate;
    
    @Persistent
    @Column(name = "LAST_MODIFIED_DATE")
    private Long lastModifiedDate;
    
    @Persistent
    @Column(name = "CREATED_BY")
    private Long createdBy;
    
    @Persistent
    @Column(name = "LAST_MODIFIED_BY")
    private Long lastModifiedBy;
    
    public AbstractEntity() {
        super();
    }
    
    /**
     * 
     * @param id The ID for this domain object.
     * 
     */
    public AbstractEntity(final Long id) {
        super();
        this.id = id;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }
    
    /**
     * Setter of the property <tt>id</tt>
     * 
     * @param id the id to set
     */
    public void setId(final Long id) {
        this.id = id;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Long getCreateDate() {
        return createDate;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Long getLastModifiedDate() {
        return lastModifiedDate;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastModifiedDate(Long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
    
    @Override
    public Long getCreatedBy() {
        return createdBy;
    }
    
    @Override
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
    
    @Override
    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }
    
    @Override
    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object entity) {
        if (entity == null) {
            return false;
        }
        if (this == entity) {
            return true;
        }
        if (entity.getClass() != getClass()) {
            return false;
        }
        
        AbstractEntity other = (AbstractEntity) entity;
        return Objects.equal(id, other.id);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
}