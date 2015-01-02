package com.marketplace.dataaccess.node;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;

/**
* Implementation of CRUD operations on Topic domain object
* 
*/
@Repository
public class TopicDNDAO extends AbstractDBDAO<Topic> implements TopicDAO {
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected Class<Topic> getEntity() {
       return Topic.class;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public List<Object> getParamValues(FilterTerm filterTerm) {
       List<Object> objectValues = new ArrayList<Object>();
       String key = filterTerm.getKey();
       String value = filterTerm.getValue();
       if ("orgId".equalsIgnoreCase(key)) {
           objectValues.add(Long.valueOf(value));
       }
       else {
           objectValues = super.getParamValues(filterTerm);
       }
       
       return objectValues;
   }
}