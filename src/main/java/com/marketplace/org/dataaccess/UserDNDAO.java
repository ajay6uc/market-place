package com.marketplace.org.dataaccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

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
public class UserDNDAO extends AbstractDBDAO<User> implements UserDAO {
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected Class<User> getEntity() {
        return User.class;
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
    
//    @Override
//    protected List<User> implementFind(SearchCriteria searchCriteria) {
//        boolean searchUops = false;
//        if (searchCriteria != null) {
//            FilterExpression filter = searchCriteria.getFilter();
//            if (filter != null) {
//                for (FilterTerm filterTerm:filter.terms()) {
//                    if (filterTerm.getKey().startsWith("role.")) {
//                        searchUops = true;
//                    }
//                }
//            }
//        }
//        return null;
//    }
}