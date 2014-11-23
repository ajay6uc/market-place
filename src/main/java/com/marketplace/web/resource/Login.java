package com.marketplace.web.resource;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.marketplace.service.LoginService;
/**
 * Root resource (exposed at "myresource" path)
 */
@Path("login")
@Component
@Scope("singleton")
public class Login {

	@Context 
	HttpServletRequest request; 
	@Context 
	HttpServletResponse response; 
	
	LoginService  login;
	
	@Autowired
	public Login(LoginService  login){
		this.login=login;
		
	}
	
	
    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws IOException 
     */
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String login(Form form) throws IOException {
        
    	form.asMap();
    	String redirectUrl = request.getParameter("redirectTo");
        if (StringUtils.isNotBlank(redirectUrl)) {
            response.sendRedirect(redirectUrl);
        }
        else {
            response.sendRedirect("/index.html");
        }
    	System.out.println(login);
    	return "Got it I am fine!";
    }
}
