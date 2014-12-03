package com.marketplace.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.shiro.web.util.WebUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.marketplace.org.User;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.web.UserTransformer;


/**
 * Defines operations for User.
 */
@Path("/user")
@Scope("request")
public class UserResource extends AbstractResource<User> {
    
	
	UserService userService;
	

	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
    public User createUser(Form userForm) throws IOException  {
		
		
		User user = UserTransformer.formToEntity(userForm);
		MultivaluedMap<String, String>  userMap= userForm.asMap();
		boolean isRegisterFlow = new Boolean(userMap.getFirst("register"));
		userService.insert(user);
        if (isRegisterFlow) {
        	Map<String, String> params = new HashMap<String, String>();
            params.put("register", isRegisterFlow+"");
            WebUtils.issueRedirect(getRequest(), getResponse(), "/../login.html", params);
            return null;
        }
		return null;
		
	
	}
    
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/uploaddp")
    public User uploadDp(@FormDataParam("name") String name,
    		@FormDataParam("subject") String subject,
    		@FormDataParam("concept") String concept,
    		@FormDataParam("file") InputStream file,
    		@FormDataParam("file") FormDataContentDisposition filedata) throws IOException  {
		
		System.out.println (subject);
		System.out.println (concept);
		System.out.println (filedata);
		return null;
		
	
	}
	
    /**
     * 
     * @param service The User Service.
     * @param userTransformer The User Transformer.
     */
    @Autowired
    public UserResource(UserService userService) {
        super(userService);
        this.userService=userService;
    }

    
	
     
}