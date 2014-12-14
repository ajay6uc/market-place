package com.marketplace.dataaccess.node;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;

@Repository
public class DppSolutionDNDAO extends AbstractDBDAO<DppSolution> implements DppSolutionDAO {
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected Class<DppSolution> getEntity() {
       return DppSolution.class;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public List<Object> getParamValues(FilterTerm filterTerm) {
       List<Object> objectValues = new ArrayList<Object>();
       String key = filterTerm.getKey();
       String value = filterTerm.getValue();
       if ("dpp.id".equalsIgnoreCase(key)) {
           objectValues.add(Long.valueOf(value));
       }
       else {
           objectValues = super.getParamValues(filterTerm);
       }
       
       return objectValues;
   }
}