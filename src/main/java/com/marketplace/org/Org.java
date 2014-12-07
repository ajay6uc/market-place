package com.marketplace.org;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.ForeignKeyAction;
import javax.jdo.annotations.Index;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;
import javax.jdo.listener.StoreCallback;

import com.marketplace.shared.common.framework.entity.AbstractEntity;

@PersistenceCapable(table = "ORG")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
public class Org extends AbstractEntity implements StoreCallback {
    private static final long serialVersionUID = -5969438530157784630L;

    public static final int ORGTYPE_INSTITUTION = 1;
    public static final int ORGTYPE_DEPARTMENT = 2;
    public static final int ORGTYPE_CLASS = 3;
    public static final int ORGTYPE_SECTION = 4;
    public static final int ORGTYPE_OTHER = 5;

    @Persistent
    @Column(name = "NAME")
    private String name;

    @Persistent
    @Column(name = "PARENT_ID")
    @Index(name = "ORG_PARENTID_IDX")
    @ForeignKey(name = "ORG_PARENTID_FK", deleteAction = ForeignKeyAction.NONE, updateAction = ForeignKeyAction.NONE)
    private Org parent;

//    /**
//     * Link to snapshots which belong to this org
//     */
//    @Persistent(table = "ORG_SNAPSHOT")
//    @Join(column = "ORG_ID")
//    @Element(column = "SNAPSHOT_ID", dependent = "true")
//    @Index(name = "ORG_SNAPSHOT_IDX")
//    @ForeignKey(name = "ORG_SNAPSHOT_FK", deleteAction = ForeignKeyAction.CASCADE, updateAction = ForeignKeyAction.NONE)
//    private Set<Snapshot> snapshots = new HashSet<Snapshot>();
//
//    @Persistent(mappedBy = "parent", dependentElement = "true")
//    private List<Org> children;
//
//    @Persistent(mappedBy = "org", dependentElement = "true")
//    private Set<UserOrgProfile> userOrgProfiles;
//
    @Persistent
    @Column(name = "ORG_TYPE")
    private Integer orgType;

//    @Persistent
//    @Unique(name = "ORG_ENTERNALID_UNQ")
//    @Column(name = "EXTERNAL_ID", length = 255, allowsNull = "false")
//    private String externalId;


 
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the root org for this org, or itself if it is a root (parent-less)
     */
    public Org getRootOrg() {
        Org o = this;

        while (o.getParent() != null) {
            o = o.getParent();
        }

        return o;
    }

    public Org getParent() {
        return parent;
    }

    public void setParent(Org parent) {
        this.parent = parent;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

   
	@Override
	public void jdoPreStore() {
		// TODO Auto-generated method stub
		
	}

    
}
