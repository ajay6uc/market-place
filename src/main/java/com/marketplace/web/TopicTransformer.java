package com.marketplace.web;

import java.util.HashMap;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;

import com.marketplace.dataaccess.node.Topic;
import com.marketplace.org.User;



/**
 * This is the transformer used for the BlogInfo entity
 * 
 */
public class TopicTransformer  {
    
	private static final String DESCRIPTION = "description";
    private static final String SUBJECT = "Subject";
    private static final String PARENT_ID = "parentId";
    private static final String NAME = "name";
    private static final String GRADE = "grade";
    private static final String LEVEL = "level";
    
    public static Topic formToEntity(Form form) {
        
    	Topic topic = new Topic();
    	MultivaluedMap<String, String>  topicMap= form.asMap();
    	
        if (topicMap.getFirst(DESCRIPTION) != null) {
            topic.setDescription(topicMap.getFirst(DESCRIPTION));
        }
        
        if (topicMap.getFirst(SUBJECT) != null) {
            topic.setSubject(topicMap.getFirst(SUBJECT));
        }
        
        if (topicMap.getFirst(NAME) != null) {
            topic.setName(topicMap.getFirst(NAME));
        }
        
        if (topicMap.getFirst(GRADE) != null) {
        	topic.setGrade(topicMap.getFirst(GRADE));
        }
        
        
        return topic;
    }
    
    
}
