package com.marketplace.shared.common.framework.searchengine;


import java.io.Serializable;
import java.util.Map;

import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;

/**
 * SearchCriteria.java This is interface for searching entities.
 * 
 */
public interface SearchCriteria extends Serializable {
    /** Value for the Wildcared Token. */
    String WILDCARD_TOKEN = "*";
    
    /**
     * Getter of the property <tt>sortBy</tt>
     * 
     * @return the sortBy
     */
    String getSortBy();
    
    /**
     * Setter of the property <tt>sortBy</tt>
     * 
     * @param sortBy the sortBy to set
     */
    void setSortBy(String sortBy);
    
    /**
     * Getter of the property <tt>filter</tt>
     * 
     * @return the filter
     */
    FilterExpression getFilter();
    
    /**
     * Setter of the property <tt>filter</tt>
     * 
     * @param expr the filter to set
     */
    void setFilter(FilterExpression expr);
    
    /**
     * Convenient method to add filter.
     * 
     * @param filter A map of key/value pairs.
     */
    void setFilter(Map<String, String> filter);
    
    /**
     * Convenient method to add filter;
     * 
     * @param term FilterTerm
     */
    void addFilter(FilterTerm term);
    
    /**
     * Convenient method to add filter.
     * 
     * @param key the key.
     * @param value the value.
     */
    void addFilter(String key, String value);
    
    /**
     * Parse string to create key-value pair for SearchCriteria key/value is seperated by '=', multiple key/value pairs
     * are seperated by '&' Example id=100 id=100&name=Jiggy id=100,101&name=Jiggy, john
     * 
     * @param filterString the rowCount to set
     */
    void addFilter(String filterString);
    
    /**
     * Returns if pagination is enabled or disabled. Getter of the property <tt>paginationEnabled</tt>
     * 
     * @return true if pagination is enabled else false.
     */
    boolean isPaginationEnabled();
    
    /**
     * Getter of the property <tt>offset</tt>
     * 
     * @return the offset
     */
    int getOffset();
    
    /**
     * Setter of the property <tt>offset</tt>
     * 
     * @param offset the offset to set
     */
    void setOffset(int offset);
    
    /**
     * Getter of the property <tt>limit</tt>
     * 
     * @return the limit
     */
    int getLimit();
    
    /**
     * Setter of the property <tt>limit</tt>
     * 
     * @param limit the limit to set
     */
    void setLimit(int limit);
    
    /**
     * Getter of the property <tt>rowCount</tt>
     * 
     * @return the rowCount
     */
    int getRowCount();
    
    /**
     * Setter of the property <tt>rowCount</tt>
     * 
     * @param rowCount the rowCount to set
     */
    void setRowCount(int rowCount);
    
    /**
     * Getter of the property <tt>queryVariables</tt>
     * 
     * @return the queryVariables
     */
    String getQueryVariables();
    
    /**
     * Setter of the property <tt>queryVariables</tt>
     * 
     * Use only if the query involves a join
     * 
     * @param queryVariables the queryVariables to set
     */
    void setQueryVariables(String queryVariables);

    
    /**
     * Expose query parameters; used when the query involves a join
     */    
    void addQueryParameter(String queryParameterName, Object value);
    
    Map<String, Object> getQueryParameters();
}