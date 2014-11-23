
package com.marketplace.shared.common.framework.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jdo.PersistenceManagerFactoryUtils;

import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.searchengine.DefaultSearchCriteria;
import com.marketplace.shared.common.framework.searchengine.FilterExpression;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterAdvice;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;
import com.marketplace.shared.common.framework.searchengine.SearchCriteria;


/**
 * AbstractDBDAO.java
 * 
 * <p>
 * Data access layer base class. Implements DAO interface and provides base implementation for the methods that could be
 * extended by the descendant class. DAO class should extend from this class. It calls Before and After abstract/empty
 * methods for validation during insert, update, and delete which descendants needs to provide implementation. Any
 * entity specific database check like required, field length should be implemented here. Business logic should be
 * implemented in the service layer. If any of these methods throws an Runtime exception, the transaction will rolled
 * back.
 * 
 * <p>
 * This is a "singleton" class. This means a singleton per Guice instance. The factory creates a single instance; there
 * is no need for a private constructor, static factory method etc as in the traditional implementation of the Singleton
 * Design Pattern.
 * 
 * @param <T> An Object that implements Entity interface.
 */

public abstract class AbstractDBDAO<T extends Entity> implements DBDAO<T>, FilterAdvice {
    private static final Logger logger = LoggerFactory.getLogger(AbstractDBDAO.class);
    
    private static final Long NULL_ID = -1l;
    @Autowired
    //@Qualifier("myPmfProxy")
    protected PersistenceManagerFactory persistenceManagerFactory;
    protected PersistenceManager persistenceManager;
  //  ersistenceManagerFactoryUtils.getPersistenceManager(pmf, true);
    /**
     * Getter of the property <tt>persistenceManager</tt>
     * 
     * @return the persistenceManager
     */
    public PersistenceManager getPersistenceManager() {
        this.persistenceManager =  PersistenceManagerFactoryUtils.getPersistenceManager(persistenceManagerFactory, true);
    	// this.persistenceManager =  persistenceManagerFactory.getPersistenceManager();
    	logger.debug("Obtained PersistenceManager: {}", this.persistenceManager.toString());
        return this.persistenceManager;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public int count() {
        return count(null);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final int count(SearchCriteria searchCriteria) {
        Query rowCountQuery = this.getPersistenceManager().newQuery(this.getPersistenceManager()
                                                                    .getExtent(this.getEntity(), false), "true");
        
        if (searchCriteria == null) {
            searchCriteria = new DefaultSearchCriteria();
        }
                
        Map<Object, Object> paramMap = setupQueryAndParams(searchCriteria, rowCountQuery);

        rowCountQuery.setResult("count(this)");
        Long rowCountResult = (Long) rowCountQuery.executeWithMap(paramMap);
        
        if (rowCountResult != null) {
            return rowCountResult.intValue();
        }
        
        return -1;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final T findById(final Long id) {
        if (id == null) {
            return null;
        }
        
        T entity = this.implementFindById(id);
        
        this.afterFind(entity);
        
        return entity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final List<T> findAll() {
        final List<T> entities = this.implementFindAll();
        
        if (entities != null) {
            for (final T entity:entities) {
                this.afterFind(entity);
            }
        }
        
        return entities;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final List<T> find(final SearchCriteria searchCriteria) {
        final List<T> entities = this.implementFind(searchCriteria);
        
        if (entities != null) {
            for (final T entity:entities) {
                this.afterFind(entity);
            }
        }
        
        return entities;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final T findOne(final SearchCriteria searchCriteria) {
        return this.implementFindOne(searchCriteria);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final T findFirst(final SearchCriteria searchCriteria) {
        return this.implementFindFirst(searchCriteria);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final T insert(final T entity) {
        this.validateBeforeInsert(entity);
        this.beforeInsert(entity);
        T newEntity = this.implementInsert(entity);
        this.afterInsert(newEntity);
        this.validateAfterInsert(newEntity);
        
        return newEntity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final T update(final T entity) {
        this.validateBeforeUpdate(entity);
        this.beforeUpdate(entity);
        T updatedEntity = this.implementUpdate(entity);
        this.afterUpdate(entity);
        this.validateAfterUpdate(entity);
        
        return updatedEntity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final void delete(final T entity) {
        this.validateBeforeDelete(entity);
        this.beforeDelete(entity);
        this.implementDelete(entity);
        this.afterDelete(entity);
        this.validateAfterDelete(entity);
    }
    
    /**
     * Returns the Entity objects class that this dao is working with used by finder methods.
     * 
     * @return The Entity Class.
     */
    protected abstract Class<T> getEntity();
    
    /**
     * Implements non-data/non-business validation logic after entity is retrieved from the data store.
     * 
     * @param entity An instance of the Entity that is retrieved from the data store.
     */
    protected void afterFind(final T entity) {
        // Descendants to implement
    }
    
    /**
     * Implements logic to find an entity for the given id.
     * 
     * @param id The id of the entity being searched.
     * @return entity An instance of the Entity that is retrieved from the data store.
     */
    protected T implementFindById(final Long id) {
        return this.getPersistenceManager().getObjectById(this.getEntity(), id);
    }
    
    /**
     * Implements logic to find all the entities.
     * 
     * @return List of all entities retrieved from the data store.
     */
    protected List<T> implementFindAll() {
        return this.find(null);
    }
    
    /**
     * Implements logic to find all the entities that matches the given search criteria.
     * 
     * @param searchCriteria The SearchCriteria object with user input
     * @return List of entity objects that matches the search criteria
     * 
     * @see SearchCriteria
     */
    @SuppressWarnings("unchecked")
    protected List<T> implementFind(SearchCriteria searchCriteria) {
        Query query = this.getPersistenceManager().newQuery(this.getEntity());
        
        if (searchCriteria == null) {
            searchCriteria = new DefaultSearchCriteria();
        }
        
        
        Map<Object, Object> paramMap = setupQueryAndParams(searchCriteria, query);

        List<T> result = (List<T>) query.executeWithMap(paramMap);
        
        if (searchCriteria.getRowCount() < 0 && result != null) {
            searchCriteria.setRowCount(result.size());
        }
        
        return result;
    }

    @SuppressWarnings("unused")
    protected void addSubQuery(SearchCriteria searchCriteria, Query query) {
    }

    /**
     * @param searchCriteria
     * @param query
     * @return
     */
    private Map<Object, Object> setupQueryAndParams(SearchCriteria searchCriteria, Query query) {
        Map<Object, Object> paramMap = null;        
        if (StringUtils.isNotBlank(searchCriteria.getQueryVariables())) {
                query.declareVariables(searchCriteria.getQueryVariables());
        }

        this.applySecurityFilter(searchCriteria);

        addSubQuery(searchCriteria, query);
        
        if (searchCriteria != null) {
            String filter = null;
            Map<String, Map<Object, Object>> filterMap = buildFilter(searchCriteria);
            
            if (filterMap != null && !filterMap.isEmpty()) {
                Map.Entry<String, Map<Object, Object>> filterMapEntry = filterMap.entrySet().iterator().next();
                filter = filterMapEntry.getKey();
                paramMap = filterMapEntry.getValue();
            }
            
            if (searchCriteria.isPaginationEnabled()) {
                setupQueryPagination(searchCriteria, paramMap, query, filter);
            }
            
            logger.debug("Adding filters to the Main Search query.");
            if (filter != null) {
                query.setFilter(filter);
            }
            
            logger.debug("Adding sorting to the Main Search query.");
            if (!StringUtils.isBlank(searchCriteria.getSortBy())) {
                query.setOrdering(searchCriteria.getSortBy());
            }
            
            Map<String,Object> values = searchCriteria.getQueryParameters();
            if (values != null) {
                paramMap.putAll(values);
            }
        }
        
        logger.debug("Executing the Main Search Query={}  with Param={}", query.toString(), paramMap);
        return paramMap;
    }

    private void setupQueryPagination(SearchCriteria searchCriteria, Map<Object, Object> paramMap, Query query,
                                  String filter) {
        logger.debug("Pagination is enabled, calculate range.");
        int rowCount = -1;
        Long rowCountResult;
        logger.debug("Build Row Count query.");
        Query rowCountQuery = this.getPersistenceManager().newQuery(this.getPersistenceManager()
                                                                        .getExtent(this.getEntity(), false),
                                                                    "true");
        addSubQuery(searchCriteria, rowCountQuery);
        
        if (StringUtils.isNotBlank(searchCriteria.getQueryVariables())) {
            rowCountQuery.declareVariables(searchCriteria.getQueryVariables());
        }
        
        rowCountQuery.setResult("count(this)");
        
        logger.debug("Adding filters to Row Count query.");
        if (filter != null) {
            rowCountQuery.setFilter(filter);
        }
        
        logger.debug("Adding soring to the Main Search query.");
        if (!StringUtils.isBlank(searchCriteria.getSortBy())) {
            rowCountQuery.setOrdering(searchCriteria.getSortBy());
        }
        
        logger.debug("Executing the Row Count Query={}  with Param={}", rowCountQuery.toString(), paramMap);
        rowCountResult = (Long) rowCountQuery.executeWithMap(paramMap);
        
        if (rowCountResult != null) {
            rowCount = rowCountResult.intValue();
        }
        
        if (searchCriteria.getOffset() > -1 && searchCriteria.getLimit() > 0 && rowCount > 0) {
            searchCriteria.setRowCount(rowCount);
            
            logger.debug("Calculate the Start Row Number.");
            long lowerBound = searchCriteria.getOffset() < rowCount ? searchCriteria.getOffset()
                                                                   : rowCount - searchCriteria.getLimit();
            
            logger.debug("Calculate the End Row Number.");
            long upperBound = lowerBound + searchCriteria.getLimit() <= rowCount ? lowerBound + searchCriteria.getLimit()
                                                                                : rowCount;
            logger.debug("Adding range to the Main Search query.");
            if (lowerBound != -1 && upperBound != -1 && lowerBound != upperBound) {
                query.setRange(lowerBound, upperBound);
            }
        }
    }
    
    /**
     * @param searchCriteria
     */
    protected void applySecurityFilter(SearchCriteria searchCriteria) {
        return;
    }
    
    /**
     * Implements logic to find the entity that matches the given search criteria.
     * 
     * @param searchCriteria The SearchCriteria object with user input
     * @return The entity objects that matches the search criteria
     * 
     * @see SearchCriteria
     */
    protected T implementFindOne(final SearchCriteria searchCriteria) {
        T entity = null;
        final List<T> entities = this.find(searchCriteria);
        
        if (entities != null && entities.size() == 1) {
            entity = entities.get(0);
        }
        
        return entity;
    }
    
    protected T implementFindFirst(final SearchCriteria searchCriteria) {
        T entity = null;
        final List<T> entities = this.find(searchCriteria);
        
        if (entities != null && entities.size() > 0) {
            entity = entities.get(0);
        }
        
        return entity;
    }
    
    /**
     * Maps the input search criteria to a JDO query object
     * 
     * @param searchCriteria An instance of the Search Criteria object.
     * @param Map of 1 item where key is the query and the value is a parameters for the query.
     */
    protected Map<String, Map<Object, Object>> buildFilter(final SearchCriteria searchCriteria) {
        FilterExpression queryFilter = searchCriteria.getFilter();
        Map<String, Map<Object, Object>> filterMap = null;
        String filter = null;
        
        if (queryFilter != null) {
            filter = queryFilter.toQueryString(this);
            if (filter.length() > 0) {
                Map<Object, Object> queryParams = queryFilter.getQueryParams(this);
                filterMap = new HashMap<String, Map<Object, Object>>();
                filterMap.put(filter, queryParams);
            }
        }
        return filterMap;
    }
    
    @Override
    public String getQueryString(FilterTerm term) {
        return term.toQueryString();
    }
    
    @Override
    public List<Object> getParamValues(FilterTerm term) {
        
        String key = term.getKey();
        String value = term.getValue();
        
        List<Object> objectValues = new ArrayList<Object>();
        if (value == null) {
            objectValues.add(null);
        }
        else {
            Set<String> longFields = new HashSet<String>(Arrays.asList("ID", "CREATEDATE", "CREATEDBY",
                                                                       "LASTMODIFIEDDATE", "LASTMODIFIEDBY"));
            String[] values = value.split(",");
            
            for (String splitValue:values) {
                if (longFields.contains(key.toUpperCase())) {
                    if (StringUtils.isNotBlank(splitValue)) {
                        objectValues.add(Long.valueOf(splitValue.trim()));
                    }
                    else {
                        objectValues.add(NULL_ID);
                    }
                    
                }
                else {
                    objectValues.add(splitValue);
                }
            }
        }
        return objectValues;
    }
    
    /**
     * Implements data (non-business) validation logic before entity is inserted in the data store.
     * 
     * @param entity An instance of the Entity that is about to be created in the data store.
     */
    protected void validateBeforeInsert(final T entity) {
        return;
    }
    
    /**
     * Implements non-data/non-business validation logic before entity is inserted in the data store.
     * 
     * @param entity An instance of the Entity that is about to be created in the data store.
     */
    protected void beforeInsert(final T entity) {
        return;
    }
    
    /**
     * Implements logic to persist the entity and its mapped associations to the underlying data store.
     * 
     * @param entity An instance of the entity object.
     * 
     * @return The Id of the newly created entity.
     */
    protected T implementInsert(final T entity) {
        return this.getPersistenceManager().makePersistent(entity);
    }
    
    /**
     * Implements non-data/non-business validation logic after entity is inserted in the data store.
     * 
     * @param newEntity The reference to the new Entity that is created in the data store.
     */
    protected void afterInsert(final T newEntity) {
        return;
    }
    
    /**
     * Implements business (non-data) validation logic after entity is inserted in the data store.
     * 
     * @param newEntity The reference to the new Entity that is created in the data store.
     */
    protected void validateAfterInsert(final T newEntity) {
        return;
    }
    
    /**
     * Implements data (non-business) validation logic before entity is updated in the data store.
     * 
     * @param entity An instance of the Entity that is about to be updated in the data store.
     */
    protected void validateBeforeUpdate(final T entity) {
        return;
    }
    
    /**
     * Implements non-data/non-business validation logic before entity is updated in the data store.
     * 
     * @param entity An instance of the Entity that is about to be updated in the data store.
     */
    protected void beforeUpdate(final T entity) {
        return;
    }
    
    /**
     * Implements logic to persist the modified entity and its mapped associations to the underlying data store.
     * 
     * @param entity An instance of the entity object.
     * @return The modified entity.
     */
    protected T implementUpdate(final T entity) {
        this.getPersistenceManager().setCopyOnAttach(false);
        return this.getPersistenceManager().makePersistent(entity);
    }
    
    /**
     * Implements non-data/non-business validation logic after entity is updated in the data store.
     * 
     * @param entity An instance of the Entity that is updated in the data store.
     */
    protected void afterUpdate(final T entity) {
        return;
    }
    
    /**
     * Implements data (non-business) validation logic after entity is updated in the data store.
     * 
     * @param entity An instance of the Entity that is updated in the data store.
     */
    protected void validateAfterUpdate(final T entity) {
        return;
    }
    
    /**
     * Implements data (non-business) validation logic after entity is updated in the data store.
     * 
     * @param entity An instance of the Entity that is updated in the data store.
     */
    protected void validateBeforeDelete(final T entity) {
        return;
    }
    
    /**
     * Implements non-data/non-business validation logic before entity is deleted from the data store.
     * 
     * @param entity An instance of the Entity that is about to be deleted from the data store.
     */
    protected void beforeDelete(final T entity) {
        return;
    }
    
    /**
     * Implements logic to delete an persisted entity and its mapped associations to the underlying data store.
     * 
     * @param entity An instance of the entity object that needs to be deleted.
     */
    protected void implementDelete(final T entity) {
        this.getPersistenceManager().deletePersistent(entity);
    }
    
    /**
     * Implements non-data/non-business validation logic after entity is deleted from the data store.
     * 
     * @param entity An instance of the Entity that is deleted from the data store.
     */
    protected void afterDelete(final T entity) {
        return;
    }
    
    /**
     * Implements data (non-business) validation logic after entity is deleted from the data store.
     * 
     * @param entity An instance of the Entity that is deleted from the data store.
     */
    protected void validateAfterDelete(final T entity) {
        return;
    }
}
