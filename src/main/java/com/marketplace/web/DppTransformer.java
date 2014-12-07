package com.marketplace.web;

import java.util.HashMap;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;

import com.marketplace.dataaccess.node.Dpp;
import com.marketplace.org.User;



/**
 * This is the transformer used for the BlogInfo entity
 * 
 */
public class DppTransformer  {
    
	private static final String DESCRIPTION = "description";
    private static final String SUBJECT = "Subject";
    private static final String CONCEPT = "concept";
    private static final String NAME = "name";
    private static final String GRADE = "grade";
    private static final String LEVEL = "level";
    
    public static Dpp formToEntity(Form form) {
        
    	
    	Dpp dpp = new Dpp();
    	MultivaluedMap<String, String>  dppMap= form.asMap();
    	
        if (dppMap.getFirst(DESCRIPTION) != null) {
        	dpp.setDescription(dppMap.getFirst(DESCRIPTION));
        }
        
        if (dppMap.getFirst(SUBJECT) != null) {
        	dpp.setSubject(dppMap.getFirst(SUBJECT));
        }
        
        if (dppMap.getFirst(NAME) != null) {
        	dpp.setName(dppMap.getFirst(NAME));
        }
        
        if (dppMap.getFirst(CONCEPT) != null) {
        	dpp.setConcept(dppMap.getFirst(CONCEPT));
        }
        
        if (dppMap.getFirst(GRADE) != null) {
        	dpp.setGrade(dppMap.getFirst(GRADE));
        }
        
        if (dppMap.getFirst(LEVEL) != null) {
            dpp.setLevel(dppMap.getFirst(LEVEL));
        }
        
        return dpp;
    }
    
    
}
