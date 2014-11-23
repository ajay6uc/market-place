
package com.marketplace.shared.common.framework.web;

import java.util.List;

import org.springframework.stereotype.Service;

import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.transactions.JDOTransactional;


/**
 */
@Service
public class TransactionalResource<T extends Entity> {
    
    /**
     * The extending class may provide the implementation.
     * 
     * @param id The id of the entity to be updated.
     * @param form capturing user input.
     * @throws JSONException If the transformer fails
//     */
//    @JDOTransactional
//    protected JSONObject update(Form form, T entity, Transformer<T> transformer, DBService<T> dbService)
//        throws JSONException {
//        entity = transformer.formToEntity(form, entity);
//        
//        if (entity != null) {
//            return transformer.entityToJSON(dbService.update(entity));
//        }
//        return null;
//    }
//    
    /**
     * The extending class may provide the implementation.
     * 
     * @param id The id of the entity to be updated.
     * @param form capturing user input.
     * @throws JSONException If the transformer fails
     */
//    @JDOTransactional
//    protected JSONObject modify(Form form, Transformer<T> transformer, DBService<T> dbService) throws JSONException {
//        List<? extends Entity> entities = transformer.formToEntities(form);
//        
//        return transformer.entitiesToJSON(dbService.modify(entities));
//    }
//    
}
