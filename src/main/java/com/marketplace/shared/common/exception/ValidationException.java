package com.marketplace.shared.common.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 */
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(ValidationException.class);
    
    private Map<String, String> errorMap;
    
    /**
     * 
     * @param errorCode The error code.
     * @param errorMessage The error message.
     */
    public ValidationException(String errorCode, String errorMessage) {
        this(generateErrorMap(errorCode, errorMessage));
    }
    
    /**
     * 
     * @param uiElementName -- UI/form/modal element name
     * @param errorCode The error code.
     * @param errorMessage The error message.
     */
    public ValidationException(String uiElementName, String errorCode, String errorMessage) {
        this(generateErrorMap(uiElementName, errorCode, errorMessage));
    }
    
    /**
     * 
     * @param errorMap The Map of errorCode and errorMessage.
     */
    public ValidationException(Map<String, String> errorMap) {
        super();
        this.errorMap = errorMap;
    }
    
    /**
     * @param errorCode The error code.
     * @param errorMessage The error message.
     * 
     * @return
     */
    private static Map<String, String> generateErrorMap(String errorCode, String errorMessage) {
        Map<String, String> errMap = new HashMap<String, String>();
        
        errMap.put(errorCode, errorMessage);
        return errMap;
    }
    
    /**
     * @param errorCode The error code.
     * @param errorMessage The error message.
     * 
     * @return
     */
    private static Map<String, String> generateErrorMap(String uiElementName, String errorCode, String errorMessage) {
        final Map<String, String> errMap = generateErrorMap(errorCode, errorMessage);
        errMap.put("name", uiElementName);
        return errMap;
    }
    
    
}