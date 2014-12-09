package com.marketplace.shared.common.framework.web;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.marketplace.shared.common.core.NullAwareBeanUtilsBean;
import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.searchengine.DefaultSearchCriteria;
import com.marketplace.shared.common.framework.searchengine.SearchCriteria;
import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;
import com.marketplace.shared.common.framework.service.DBService;

/**
 * AbstractResource.java
 * 
 * <p>
 * Web-Service layer facade base class with common functionality and extension points for the descendants. Implements
 * DBResource interface and provides base implementation for the methods that could be extended by the descendant class.
 * It calls Before and After abstract/empty methods for additional logic during create, modify, and delete which
 * descendants needs to provide implementation. This is not where business or data validation logic should be
 * implemented.
 * @param <T> The Entity object used for transformation.
 * 
 */
public abstract class AbstractResource<T extends Entity> extends BaseResource implements DBResource<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractResource.class);
    
    private DBService<T> service = null;
    
    @Autowired
    private TransactionalResource<T> transactionalResource;
    
    
    /**
     * 
     * @param service The Service that DBResource interacts with.
     * @param transformer The transformer.
     */
    public AbstractResource(DBService<T> service) {
        super();
        this.service = service;
    }
    
    /**
     * Getter of the property <tt>service</tt>
     * 
     * @return the service
     */
    public DBService<T> getService() {
        return this.service;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public T findById(Long id) {
        this.beforeFindById(id);
        T foundEntity = this.implementFindById(id);
        this.afterFindById(id, foundEntity);
        
        return foundEntity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll() {
        this.beforeFindAll();
        List<T> foundEntities = this.implementFindAll();
        this.afterFindAll(foundEntities);
        
        return foundEntities;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> find()  {
        this.beforeFind();
        List<T> entities = this.implementFind();
        this.afterFind(entities);
        
        return entities;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public T create(T entity)  {
        this.beforeCreate(entity);
        T entityCreated = implementCreate(entity);
        this.afterCreate(entityCreated);
        
        return entity;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public T modify(Long id, T entity)  {
        this.beforeModify(id, entity);
        T jsonObject = this.implementModify(id, entity);
        this.afterModify(jsonObject);
        return jsonObject;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id) {
        this.beforeRemove(id);
        this.implementRemove(id);
        this.afterRemove(id);
    }
    
    /**
     * Implements non-business/non-data validation logic, typically a security access check before entity is accessed.
     * 
     * @param id The id of the Domain/Entity that is about to be accessed.
     */
    protected void beforeFindById(Long id) {
        return;
    }
    
    /**
     * The extending class may provide the implementation.
     * 
     * @param id The id for the entity to be searched for
     * @return If found, an instance of the JSONObject of the found entity object, else null.
     * @throws JSONException If the transformer fails
     */
    protected T implementFindById(Long id) {
        T entity = this.getService().findById(id);
        return entity;
    }
    
    /**
     * Implements business (non-data) validation logic after finding an entity by id.
     * 
     * @param id The id received from the client.
     * @param jsonObject found.
     */
    protected void afterFindById(Long id, T entity) {
        return;
    }
    
    /**
     * Implements non-business/non-data validation logic, typically a security access check before entity is accessed.
     */
    protected void beforeFindAll() {
        return;
    }
    
    /**
     * The extending class may provide the implementation.
     * 
     * @return If found, a list of the JSONObject of the found entity object, else null.
     * @throws JSONException
     */
    protected  List<T>  implementFindAll() {
        List<T> entities = this.getService().findAll();
        return entities;
    }
    
    /**
     * Implements business (non-data) validation logic after finding an entity by id.
     * 
     * @param jsonNArray contains list of jsonObjects found.
     */
    protected void afterFindAll(List<T> entities) {
        return;
    }
    
    /**
     * Implements non-business/non-data validation logic, typically a security access check before entity is accessed.
     * 
     * @param form capturing user input.
     */
    protected void beforeFind() {
        return;
    }
    
    /**
     * The extending class may over-ride the implementation.
     * 
     * @return If found, a list of the JSONObject of the found entity object, else null.
     * @throws JSONException any JSONException during transformation.
     */
    protected List<T> implementFind()  {
        SearchCriteria searchCriteria = this.buildSearchCriteria();
        List<T> entities = null;
        if (searchCriteria != null) {
             entities = this.getService().find(searchCriteria);
        }
        
        return entities;
    }
    
    protected List<T> createJsonFromEntities(SearchCriteria searchCriteria, List<T> entities)  {
       
     
        return entities;
    }
    
    /**
     * Builds Search Criteria from the Query param.
     * 
     * @return An instance of the Search Criteria.
     */
    protected SearchCriteria buildSearchCriteria() {
    	MultivaluedHashMap<String, String> query = new MultivaluedHashMap(this.getUriInfo().getQueryParameters());
        Iterator<String> keys = query.keySet().iterator();
        DefaultSearchCriteria defaultSearchCriteria = null;
        
        while (keys.hasNext()) {
            if (defaultSearchCriteria == null) {
                defaultSearchCriteria = new DefaultSearchCriteria();
            }
            
            String key = keys.next();
            String val = query.getFirst(key);
            
            if ("sort".equalsIgnoreCase(key)) {
                defaultSearchCriteria.setSortBy(val);
            }
            else if ("offset".equalsIgnoreCase(key)) {
                defaultSearchCriteria.setOffset(Integer.parseInt(val));
            }
            else if ("limit".equalsIgnoreCase(key)) {
                defaultSearchCriteria.setLimit(Integer.parseInt(val));
            }
            else {
                defaultSearchCriteria.addFilter(new FilterTerm(key, val));
            }
        }
        
        return defaultSearchCriteria;
    }
    
    /**
     * Implements business (non-data) validation logic after finding an entity by id.
     * 
     * @param entities capturing user input.
     * 
     * @param list of jsonObjects found.
     */
    protected void afterFind(List<T> entities) {
        return;
    }
    
    /**
     * Implements non-business/non-data validation logic before entity is created in the data store.
     * 
     * @param form capturing user input.
     */
    protected void beforeCreate(T entity) {
        return;
    }
    
    /**
     * The extending class may provide the implementation.
     * 
     * @param form capturing user input.
     * @throws JSONException
     */
    protected T implementCreate(T entity)  {
        LOGGER.debug("Check if this is an insert request");
        
       
        if (entity != null) {
            return this.getService().insert(entity);
        }
        
        LOGGER.debug("Check if this is an create request");
        return null;
     }
    
    /**
     * Implements non-business/non-data validation logic after entity is created in the data store.
     * 
     * @param jsonObject The JSONObject capturing newly created entities.
     */
    protected void afterCreate(T entity) {
        return;
    }
    
    /**
     * Implements non-business/non-data validation logic before entity is updated in the data store.
     * 
     * @param id The id of the entity to be updated.
     * @param form capturing user input.
     */
    protected void beforeModify(Long id, T entity) {
        return;
    }
    
    /**
     * The extending class may provide the implementation.
     * 
     * @param id The id of the entity to be updated.
     * @param form capturing user input.
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws JSONException If the transformer fails
     */
    /*
     * protected JSONObject implementModify(Long id, Form form) throws JSONException {
     * logger.debug("Check if this is an update request"); T entity = find(id, form); entity =
     * this.getTransformer().formToEntity(form, entity); if (entity != null) return
     * this.getTransformer().entityToJSON(this.getService().update(entity));
     * logger.debug("Check if this is an modify request"); List<? extends Entity> entities =
     * this.getTransformer().formToEntities(form); return
     * this.getTransformer().entitiesToJSON(this.getService().modify(entities)); }
     */
    
    protected T implementModify(Long id, T entity)   {
        LOGGER.debug("Check if this is an update request");
        
        T entityFound = find(id, entity);
        NullAwareBeanUtilsBean nabu = new NullAwareBeanUtilsBean();
        try {
			nabu.copyProperties(entityFound, entity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (entity != null) {
        	
        	entityFound = this.getService().update(entityFound);
        	LOGGER.info("Returning entity found " + entityFound);
        	return entityFound;
        }
        
//        
//        JSONObject update = this.transactionalResource.update(form, entity, transformer, this.getService());
//        if (null == update) {
//            LOGGER.debug("Check if this is an modify request");
//            
//            update = this.transactionalResource.modify(form, transformer, this.getService());
//        }
        return entityFound;
    }
    
    /**
     * Finds an entity from the form input.
     * 
     * @param form capturing user input.
     * @return an Entity if found else null.
     */
    protected T find(Long id, T entity) {
        T entityFound = null;
        
        if (id != null) {
        	return entityFound = this.getService().findById(id);
        }
        
//        if (entity == null) {
//            Object entityId = this.getTransformer().formToEntityId(form);
//            
//            if (entityId instanceof SearchCriteria) {
//                entity = this.getService().findOne((SearchCriteria) entityId);
//            }
//        }
        
        return entityFound;
    }
    
    /**
     * Implements non-business/non-data validation logic after entity is updated in the data store.
     * 
     * @param jsonObject The JSONObject capturing modified created entity.
     */
    protected void afterModify(T entity) {
        return;
    }
    
    /**
     * Implements non-business/non-data validation logic before entity is deleted from the data store.
     * 
     * @param id the Id of the entity to be removed.
     */
    protected void beforeRemove(Long id) {
        return;
    }
    
    /**
     * The extending class may provide the implementation.
     * 
     * @param id the Id of the entity to be removed.
     */
    protected void implementRemove(Long id) {
        this.getService().delete(id);
    }
    
    /**
     * Implements non-business/non-data validation logic after entity is deleted from the data store.
     * 
     * @param id the Id of the entity to be removed.
     */
    protected void afterRemove(Long id) {
        return;
    }
    
    /**
     * Getter of the property <tt>transactionalResource</tt>
     * 
     * @return the transactionalResource
     */
    public TransactionalResource<T> getTransactionalResource() {
        return transactionalResource;
    }
    
    /**
     * Setter of the property <tt>transactionalResource</tt>
     * 
     * @param transactionalResource the transactionalResource to set
     */
    public void setTransactionalResource(TransactionalResource<T> transactionalResource) {
        this.transactionalResource = transactionalResource;
    }
}