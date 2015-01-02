package com.marketplace.dataaccess.node;

import java.util.ArrayList;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;

/**
* Implementation of CRUD operations on Question domain object
* 
*/
@Repository
public class QuestionDNDAO extends AbstractDBDAO<Question> implements QuestionDAO {
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected Class<Question> getEntity() {
       return Question.class;
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