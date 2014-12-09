package com.marketplace.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

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
    
    public static Dpp formToEntity(FormDataMultiPart form) {
        
    	
    	Dpp dpp = new Dpp();
    	Map<String, List<FormDataBodyPart>>  dppMap= form.getFields();
    	if(form.getField(NAME)!=null){
    		dpp.setName(form.getField(NAME).getValueAs(String.class));
    	}
    	if(form.getField(CONCEPT)!=null){
    		dpp.setConcept(form.getField(CONCEPT).getValueAs(String.class));
        }
    	if(form.getField(SUBJECT) !=null){
    		dpp.setSubject(form.getField(SUBJECT).getValueAs(String.class));
    	}
    	if(form.getField(GRADE)!=null){
    		dpp.setGrade(form.getField(GRADE).getValueAs(String.class));
    	}
        return dpp;
    }
    
    
}
