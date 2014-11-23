
package com.marketplace.shared.common.framework.dao;

import java.util.List;

import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.searchengine.SearchCriteria;



// Third-party classes

/**
 * DBDAO.java All the DAO/Data access classes will implement this interface.
 * 
 * @param <T> An Object that implements Entity interface.
 * 
 */
public interface DBDAO<T extends Entity> extends DAO {
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
     * Finds all the entities that matches the given search criteria.
     * 
     * @param searchCriteria The SearchCriteria object with user input.
     * @return List of entity objects that matches the search criteria.
     * 
     * @see SearchCriteria
     */
    List<T> find(SearchCriteria searchCriteria);
    
    /**
     * Convenient method when one result is expected from the Search. Finds the entities that matches the given search
     * criteria.
     * 
     * @param searchCriteria The SearchCriteria object with user input.
     * @return An instance of the entity object or null if none OR MORE THAN ONE is found.
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
     * @param entity An instance of the entity object.
     * 
     * @return The Id of the newly created entity.
     */
    T insert(T entity);
    
    /**
     * Runs business logic and persists the entity object and its mapped associations to the underlying data store.
     * 
     * @param entity An instance of the entity object.
     * @return The modified entity.
     */
    T update(T entity);
    
    /**
     * Runs business logic and deletes the entity from the data store. Cascades if configured.
     * 
     * @param entity An instance of the entity object that needs to be deleted.
     */
    void delete(T entity);
    
    /**
     * Returns the count of such entities.
     * 
     * @param entity
     * @return
     */
    int count();

    /**
      * Returns the count of such entities matching the criteria    
     * 
     * @param criteria
     */
    int count(SearchCriteria criteria);
}