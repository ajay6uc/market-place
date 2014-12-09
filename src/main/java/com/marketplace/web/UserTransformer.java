package com.marketplace.web;

import java.util.HashMap;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;

import com.marketplace.org.User;



/**
 * This is the transformer used for the BlogInfo entity
 * 
 */
public class UserTransformer  {
    
	private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String REMEMBERME = "rememeberMe";
    
    public static User formToEntity(Form form) {
        
    	User user = new User();
    	MultivaluedMap<String, String>  userMap= form.asMap();
    	
        if (userMap.getFirst(EMAIL) != null) {
            user.setEmail(userMap.getFirst(EMAIL));
        }
        
        if (userMap.getFirst(MOBILE) != null) {
            user.setCellNo(userMap.getFirst(MOBILE));
        }
        
        if (userMap.getFirst(NAME) != null) {
            user.setName(userMap.getFirst(NAME));
        }
        
        if (userMap.getFirst(PASSWORD) != null) {
            user.setPassword(userMap.getFirst(PASSWORD));
        }
        
        if (userMap.getFirst(REMEMBERME) != null) {
            user.setRememberMe(new Boolean(userMap.getFirst(REMEMBERME)));
        }
        
        return user;
    }
    
    
}
