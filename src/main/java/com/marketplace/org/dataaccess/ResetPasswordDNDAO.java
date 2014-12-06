package com.marketplace.org.dataaccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.marketplace.org.ResetPasswordRequest;
import com.marketplace.org.User;
import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.searchengine.FilterExpression;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;
import com.marketplace.shared.common.framework.searchengine.SearchCriteria;

/**
 * Implementation of CRUD operations on User domain object
 * 
 */
@Repository
public class ResetPasswordDNDAO extends AbstractDBDAO<ResetPasswordRequest> implements ResetPasswordDAO {
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<ResetPasswordRequest> getEntity() {
        return ResetPasswordRequest.class;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Object> getParamValues(FilterTerm filterTerm) {
        List<Object> objectValues = new ArrayList<Object>();
        String key = filterTerm.getKey();
        String value = filterTerm.getValue();
        if ("org.id".equalsIgnoreCase(key)) {
            objectValues.add(Long.valueOf(value));
        }
        else {
            objectValues = super.getParamValues(filterTerm);
        }
        
        return objectValues;
    }

}