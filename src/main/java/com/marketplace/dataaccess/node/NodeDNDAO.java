package com.marketplace.dataaccess.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

import javax.jdo.Extent;
import javax.jdo.annotations.Query;


import org.springframework.stereotype.Repository;
import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;

/**
* Implementation of CRUD operations on Dpp domain object
* 
*/
@Repository
public class NodeDNDAO extends AbstractDBDAO<Node> implements NodeDAO {
   
   /**
    * {@inheritDoc}
    */
   @Override
   protected Class<Node> getEntity() {
       return Node.class;
   }
   
   /**
    * {@inheritDoc}
    */
   @Override
   public List<Object> getParamValues(FilterTerm filterTerm) {
       List<Object> objectValues = new ArrayList<Object>();
       String key = filterTerm.getKey();
       String value = filterTerm.getValue();
       if ("parent.id".equalsIgnoreCase(key)) {
           objectValues.add(Long.valueOf(value));
       }
       else {
           objectValues = super.getParamValues(filterTerm);
       }
       
       return objectValues;
   }
   
   
   @SuppressWarnings("unchecked")
   @Override
   public List<Node> getChildren(Long parentId) {
	   List<Node> results = null;
	   Class<? extends Node> clazz = Topic.class;
	   Extent nodeExtent = this.getPersistenceManager().getExtent(Node.class, true);
       String filter = "parent.id == pId";
       javax.jdo.Query query = this.getPersistenceManager().newQuery(nodeExtent, filter);
       query.declareParameters("Long pId");
      // query.setOrdering("displayOrder");
       results = (List<Node>) query.execute(parentId);
       
       return results;
       
      
   }
}