package com.marketplace.shared.common.core;

@SuppressWarnings("serial")
public class HashID implements java.io.Serializable {
    public String hid;
    
    public HashID() {
        //
    }
    
    public HashID(String hid) {
        super();
        this.hid = hid;
    }
    
    public String getHid() {
        return hid;
    }
    
    public void setHid(String hid) {
        this.hid = hid;
    }
    
    @Override
    public String toString() {
        return hid;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof HashID)) {
            return false;
        }
        
        HashID other = (HashID) obj;
        return hid.equals(other.hid);
    }
    
    @Override
    public int hashCode() {
        return hid.hashCode();
    }
    
}
