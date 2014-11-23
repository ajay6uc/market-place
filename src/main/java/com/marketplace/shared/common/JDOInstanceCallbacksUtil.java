package com.marketplace.shared.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.web.security.SessionUtil;


/**
 * 
 * 
 */
public final class JDOInstanceCallbacksUtil {
    private static final Logger logger = LoggerFactory.getLogger(JDOInstanceCallbacksUtil.class);
    
    /**
     * Populates date & user fields on the Entity
     * 
     * @param entity
     */
    public static void populateEntityFields(Entity entity) {
        final String className = entity.getClass().getName();
        logger.debug("populateEntityFields(entity = {}) invoked.", className);
        
        long time = System.currentTimeMillis();
        entity.setLastModifiedDate(time);
        if (entity.getCreateDate() == null) {
            entity.setCreateDate(time);
        }
        SessionProfile profile = SessionUtil.getProfile();
        if (profile != null) {
            entity.setLastModifiedBy(profile.getUserId());
            if (entity.getCreatedBy() == null) {
                entity.setCreatedBy(profile.getUserId());
            }
        }
        else {
            entity.setLastModifiedBy(null);
            logger.warn("Could not determine user/profile for entity {}, setting to null.", className);
        }
    }
    
    private JDOInstanceCallbacksUtil() {
    }
}
