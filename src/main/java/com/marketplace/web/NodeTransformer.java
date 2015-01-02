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

import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.Node;
import com.marketplace.org.User;



/**
 * This is the transformer used for the BlogInfo entity
 * 
 */
public class NodeTransformer  {
    
	private static final String DESCRIPTION = "description";
    private static final String SUBJECT = "Subject";
    private static final String CONCEPT = "concept";
    private static final String PARENT_ID = "parentId";
    private static final String NAME = "name";
    private static final String GRADE = "grade";
    private static final String LEVEL = "level";
    private static final String VISIBLE = "visible";
    private static final String DISPLAY_ORDER = "displayOrder";
    
    public static Question formToEntity(FormDataMultiPart form) {
        
    	
    	Question dpp = new Question();
    	Map<String, List<FormDataBodyPart>>  dppMap= form.getFields();
    	if(form.getField(NAME)!=null){
    		dpp.setName(form.getField(NAME).getValueAs(String.class));
    	}
    	if(form.getField(SUBJECT) !=null){
    		dpp.setSubject(form.getField(SUBJECT).getValueAs(String.class));
    	}
    	if(form.getField(GRADE)!=null){
    		dpp.setGrade(form.getField(GRADE).getValueAs(String.class));
    	}
        return dpp;
    }
    	
  public static Node formToEntity(Form form, Node node) {
      
	  
	  MultivaluedMap<String, String>  nodeMap= form.asMap();
  	
      if (nodeMap.getFirst(DESCRIPTION) != null) {
          node.setDescription((nodeMap.getFirst(DESCRIPTION)));
      }
      
      if (nodeMap.getFirst(GRADE) != null) {
          node.setGrade(nodeMap.getFirst(GRADE));
      }
      
      if (nodeMap.getFirst(NAME) != null) {
          node.setName(nodeMap.getFirst(NAME));
      }
      
      if (nodeMap.getFirst(SUBJECT) != null) {
          node.setSubject(nodeMap.getFirst(SUBJECT));
      }
      
      if (nodeMap.getFirst(VISIBLE) != null) {
    	  node.setVisible(new Boolean(nodeMap.getFirst(VISIBLE)).booleanValue());
      }
      if (nodeMap.getFirst(DISPLAY_ORDER) != null) {
    	  node.setDisplayOrder(new Integer(nodeMap.getFirst(DISPLAY_ORDER)));
      }
        return node;
    }
    
}
