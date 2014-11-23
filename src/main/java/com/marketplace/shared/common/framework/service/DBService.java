package com.marketplace.shared.common.framework.service;


import java.util.List;

import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.searchengine.SearchCriteria;


public interface DBService<T extends Entity> extends Service {
    /**
     * Finds the entity for the given Id.
     * 
     * @param id The id for the entity to be searched for.
     * @return An instance of the entity object.
     */
    T findById(Long id);
    
    /**
     * Finds all the entity objects.
     * 
     * @return List of all the entity objects.
     */
    List<T> findAll();
    
    /**
     * Finds the entity for the given search criteria.
     * 
     * @param searchCriteria The SearchCriteria object with user input.
     * @return List of entity objects that matches the search criteria.
     */
    List<T> find(SearchCriteria searchCriteria);
    
    /**
     * Convenient method when one result is expected from the Search. Finds the entities that matches the given search
     * criteria.
     * 
     * @param searchCriteria The SearchCriteria object with user input.
     * @return An instance of the entity object.
     * 
     * @see SearchCriteria
     */
    T findOne(SearchCriteria searchCriteria);
    
    /**
     * Convenient method when one result is expected from the Search. If more than one record is found, it will return
     * the 1st one, so include a SORT criteria if you want a consistent result. Finds the entities that matches the
     * given search criteria.
     * 
     * @param searchCriteria The SearchCriteria object with user input.
     * @return An instance of the entity object or null in none found;
     * 
     * @see SearchCriteria
     */
    T findFirst(SearchCriteria searchCriteria);
    
    /**
     * Runs business logic and persists the entity object and its mapped associations to the underlying data store.
     * 
     * @param entity An instance of the domain/entity object.
     * 
     * @return The Id of the newly created entity.
     */
    T insert(T entity);
    
    /**
     * Runs business logic and persists the entity object and its mapped associations to the underlying data store.
     * 
     * @param entity An instance of the domain/entity object.
     * @return The modified entity.
     */
    T update(T entity);
    
    /**
     * Runs business logic and deletes the entity from the data store. Cascades if configured.
     * 
     * @param entity An instance of the domain/entity object that needs to be deleted
     */
    void delete(T entity);
    
    /**
     * Runs business logic and deletes the entity from the data store. Cascades if configured.
     * 
     * @param id The id of the domain/entity object that needs to be deleted.
     */
    void delete(Long id);
    
    /**
     * This operation can span across multiple DAO's or Services or combinations.
     * 
     * Runs business logic and persists the entity object and its mapped associations to the underlying data store.
     * 
     * @param entities List of entities of the domain/entity object.
     * 
     * @return List of newly created entities.
     */
    List<? extends Entity> create(List<? extends Entity> entities);
    
    /**
     * This operation can span across multiple DAO's or Services or combinations.
     * 
     * Runs business logic and persists the entity object and its mapped associations to the underlying data store.
     * 
     * @param entities List of entities of the domain/entity object.
     * @return List of modified entities.
     */
    List<? extends Entity> modify(List<? extends Entity> entities);
    
    /**
     * This operation can span across multiple DAO's or Services or combinations.
     * 
     * Runs business logic and deletes the entity from the data store. Cascades if configured.
     * 
     * @param entities List of entities of the domain/entity object.
     */
    void remove(List<? extends Entity> entities);

    /**
     * Finds the number of entity objects.
     */
    int count();

    /**
     * Finds the number of entities for the given search criteria.
     * 
     * @param searchCriteria The SearchCriteria object with user input.
     * @return the number of entity objects that matches the search criteria.
     */
    long count(SearchCriteria searchCriteria);
}