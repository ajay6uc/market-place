package com.marketplace.dataaccess.node;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;

/**
* Implementation of CRUD operations on Dpp domain object
* 
*/
@Repository
public class ConceptDNDAO extends AbstractDBDAO<Concept> implements ConceptDAO {
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected Class<Concept> getEntity() {
       return Concept.class;
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
       }else if ("parent.id".equalsIgnoreCase(key)) {
           objectValues.add(Long.valueOf(value));
       }
       else {
           objectValues = super.getParamValues(filterTerm);
       }
       
       return objectValues;
   }
}