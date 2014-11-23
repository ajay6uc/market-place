package com.marketplace.web.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.SecurityContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.marketplace.org.User;
import com.marketplace.service.DefaultUserService;
import com.marketplace.service.LoginService;
import com.marketplace.service.UserService;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
@Component
@Scope("request")
public class MyResource {

	@Context 
	Request request; 
	LoginService  login;
	UserService userService;
	 
	@Autowired
	public MyResource(LoginService  login, UserService userService){
		this.login=login;
		this.userService=userService;
		
	}
	
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	
    	User user = userService.findById(1L);
        System.out.println(login);
    	return "Got it I am fine!";
    }
}
