package com.marketplace.shared.common.framework.searchengine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jdo.Query;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Objects;

/**
 * This class holds a search expression used to filter JDO query results.
 */
public class FilterExpression {
    
    private static final Logger logger = LoggerFactory.getLogger(FilterExpression.class);
    private static final Pattern QUERY_PARSER = Pattern.compile("([^!]*)(!?+=)(.*)");
    
    public enum QueryMode {
        
        JPA("AND", "="), JDO("&&", "==");
        
        private final String andToken;
        private final String equalsToken;
        
        QueryMode(String andToken, String equalsToken) {
            this.andToken = andToken;
            this.equalsToken = equalsToken;
        }
        
        public String getAndToken() {
            return andToken;
        }
        
        public String getEqualsToken() {
            return equalsToken;
        }
        
    }
    
    private final QueryMode mode;
    private final Map<String, FilterTerm> terms;
    
    public FilterExpression(QueryMode mode) {
        this.mode = mode;
        this.terms = new LinkedHashMap<String, FilterTerm>();
    }
    
    public FilterExpression() {
        this(QueryMode.JDO);
    }
    
    public FilterExpression(Map<String, String> terms) {
        this(QueryMode.JDO);
        for (Entry<String, String> entry:terms.entrySet()) {
            this.terms.put(entry.getKey(), new FilterTerm(entry.getKey(), entry.getValue()));
        }
    }
    
    public void addTerm(FilterTerm term) {
        terms.put(term.getKey(), term);
    }
    
    public FilterTerm removeTerm(String key) {
        return terms.remove(key);
    }
    
    public void addTerms(String filterString) {
        if (filterString != null && !SearchCriteria.WILDCARD_TOKEN.equals(filterString.trim())) {
            String[] filterPairs = filterString.trim().split("&");
            
            for (String filterPair:filterPairs) {
                Matcher matcher = QUERY_PARSER.matcher(filterPair);
                if (matcher.matches() && matcher.groupCount() == 3) {
                    String key = matcher.group(1);
                    String operator = matcher.group(2);
                    String value = matcher.group(3);
                    FilterTerm term = null;
                    if ("!=".equals(operator)) {
                        term = new FilterTerm(Op.EXCLUDE, key, value, mode);
                    }
                    else if ("=".equals(operator)) {
                        term = new FilterTerm(Op.INCLUDE, key, value, mode);
                    }
                    else {
                        throw new IllegalArgumentException("Invalid operator  '" + operator +
                                                           "' for term " +
                                                           filterPair);
                    }
                    this.addTerm(term);
                }
            }
        }
    }
    
    public Collection<FilterTerm> terms() {
        return terms.values();
    }
    
    public int numTerms() {
        return terms.size();
    }
    
    public FilterTerm getTerm(String key) {
        return terms.get(key);
    }
    
    public Map<String, String> toMap() {
        Map<String, String> result = new HashMap<String, String>(terms.size());
        for (Entry<String, FilterTerm> entry:terms.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getValue());
        }
        return result;
    }
    
    public String toQueryString(FilterAdvice handler) {
        StringBuilder filterBuffer = new StringBuilder();
        for (FilterTerm term:terms()) {
            if (filterBuffer.length() > 0) {
                filterBuffer.append(' ').append(mode.getAndToken()).append(' ');
            }
            filterBuffer.append(handler.getQueryString(term));
        }
        return filterBuffer.toString();
    }
    
    public void appendToQuery(Query q, FilterAdvice advice) {
        if (numTerms() > 0) {
            
            StringBuilder whereClause = new StringBuilder();
            String sql = q.toString();
            int whereDelim = sql.indexOf("WHERE");
            if (whereDelim != -1) {
                whereClause.append(sql.substring(whereDelim + "WHERE".length()));
            }
            q.setFilter(whereClause + " " + mode.getAndToken() + " " + toQueryString(advice));
        }
    }
    
    public String toHttpString() {
        StringBuilder result = new StringBuilder();
        Collection<FilterTerm> termValues = this.terms.values();
        Iterator<FilterTerm> iter = termValues.iterator();
        while (iter.hasNext()) {
            FilterTerm term = iter.next();
            term.toHttpString(result);
            if (iter.hasNext()) {
                result.append('&');
            }
        }
        return result.toString();
    }
    
    public Map<Object, Object> getQueryParams(FilterAdvice handler) {
        
        Map<Object, Object> queryParams = new HashMap<Object, Object>();
        for (FilterTerm entry:terms()) {
            entry.toQueryParams(queryParams, handler);
        }
        return queryParams;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(mode, terms);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        FilterExpression other = (FilterExpression) obj;
        return Objects.equal(mode, other.mode) && Objects.equal(terms, other.terms);
    }
    
    /**
     * FilterAdvice provides a way for the caller to influence the execution of a FilterExpression. It is mainly used to
     * perform type conversion on particular fields within the filter.
     */
    public interface FilterAdvice {
        List<Object> getParamValues(FilterTerm term);
        
        String getQueryString(FilterTerm term);
    }
    
    /**
     * FilterTerm Operator
     */
    public enum Op {
        EXCLUDE, INCLUDE;
    }
    
    /**
     * This class holds a term within a filter expression. It is essentially just a name-value pair.
     */
    public static class FilterTerm {
        
        private final QueryMode mode;
        private final String key;
        private final Op operator;
        private String value;
        
        public FilterTerm(String key, String value) {
            this(Op.INCLUDE, key, value, QueryMode.JDO);
        }
        
        public FilterTerm(Op operator, String key, String value, QueryMode mode) {
            rejectNull(key, "Filter Keys cannot be null");
            this.key = key.trim();
            this.value = (value == null) ? null : value.trim();
            this.operator = operator;
            this.mode = mode;
        }
        
        private void rejectNull(String string, String rejection) {
            if (string == null) {
                throw new IllegalArgumentException(rejection);
            }
        }
        
        public String getKey() {
            return key;
        }
        
        public String getParameterizedKey() {
            return key.replace('.', '_');
        }
        
        public String getValue() {
            return value;
        }
        
        public Op getOperator() {
            return operator;
        }
        
        public String toQueryString() {
            StringBuilder result = new StringBuilder();
            String paramName = getParameterizedKey();
            
            //If we are dealing with a set of values or the null set, use the "contains" operator,
            //otherwise, use an "equals" operator
            
            if (StringUtils.contains(value, ',')) {
                logger.info("Converting comma delimited string to list for " + key + ":" + value);
                result.append(" :").append(paramName).append(".contains(").append(key).append(")");
                if (operator == Op.EXCLUDE) {
                    result.append(" == false");
                }
            }
            else {
                result.append(key);
                if (operator == Op.EXCLUDE) {
                    result.append(" != :");
                }
                else {
                    result.append(' ').append(mode.getEqualsToken()).append(" :");
                }
                result.append(paramName);
            }
            return result.toString();
        }
        
        private void toQueryParams(Map<Object, Object> queryParams, FilterAdvice handler) {
            String paramName = getParameterizedKey();
            List<Object> valuesList = handler.getParamValues(this);
            if (valuesList.size() > 1) {
                queryParams.put(paramName, valuesList);
            }
            else if (valuesList.size() == 1) {
                Object firstValue = valuesList.get(0);
                queryParams.put(paramName, firstValue);
            }
        }
        
        public void addValue(String toAdd) {
            if (value == null || "".equals(value.trim())) {
                value = toAdd;
            }
            else {
                String[] values = value.split(",");
                if (!ArrayUtils.contains(values, toAdd)) {
                    value = value + "," + toAdd;
                }
            }
        }
        
        public void removeValue(String toRemove) {
            if (value != null) {
                String[] values = value.split(",");
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < values.length; i++) {
                    if (!values[i].equals(toRemove)) {
                        if (builder.length() > 0) {
                            builder.append(',');
                        }
                        builder.append(values[i]);
                    }
                }
                value = builder.toString();
            }
        }
        
        public List<Long> getLongValues() {
            List<Long> result = new ArrayList<Long>(1);
            String[] values = value.split(",");
            for (String splitValue:values) {
                if (StringUtils.isNotBlank(splitValue)) {
                    result.add(Long.valueOf(splitValue));
                }
            }
            return result;
        }
        
        public void toHttpString(StringBuilder buffer) {
            buffer.append(key);
            if (operator == Op.EXCLUDE) {
                buffer.append("!");
            }
            buffer.append("=");
            buffer.append(value);
        }
        
        @Override
        public String toString() {
            StringBuilder buffer = new StringBuilder();
            toHttpString(buffer);
            return buffer.toString();
        }
        
        @Override
        public int hashCode() {
            return Objects.hashCode(key, mode, operator, value);
        }
        
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            
            FilterTerm other = (FilterTerm) obj;
            return Objects.equal(key, other.key) && Objects.equal(mode, other.mode) &&
                   Objects.equal(operator, other.operator) &&
                   Objects.equal(value, other.value);
        }
        
    }

    
    
}
