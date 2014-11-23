package com.marketplace.web.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.marketplace.org.User;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.web.AbstractResource;


/**
 * Defines operations for User.
 */
@Path("/user")
@Scope("request")
public class UserResource extends AbstractResource<User> {
    
   
    
    /**
     * 
     * @param service The User Service.
     * @param userTransformer The User Transformer.
     */
    @Autowired
    public UserResource(UserService service) {
        super(service);
    }

	
     
}