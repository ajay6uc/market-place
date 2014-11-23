package com.marketplace.shared.common.core;
import  org.datanucleus.store.valuegenerator.AbstractGenerator;
@SuppressWarnings("serial")
public class NBUID implements java.io.Serializable {
    
    public String uid;
    
    public NBUID() {
        //
    }
    
    public NBUID(String uid) {
        super();
        this.uid = uid;
    }
    
    public String getUid() {
        return uid;
    }
    
    public void setUid(String uid) {
        this.uid = uid;
    }
    
    @Override
    public String toString() {
        return uid;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof NBUID)) {
            return false;
        }
        
        NBUID other = (NBUID) obj;
        return uid.equals(other.uid);
    }
    
    @Override
    public int hashCode() {
        return uid.hashCode();
    }
    
}
