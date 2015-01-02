package com.marketplace.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.Node;
import com.marketplace.org.User;
import com.marketplace.service.UserService;
import com.marketplace.service.amazon.AmazonS3Service;
import com.marketplace.service.node.QuestionService;
import com.marketplace.service.node.NodeService;
import com.marketplace.shared.common.framework.service.DBService;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.web.NodeTransformer;
import com.marketplace.web.UserTransformer;


/**
 * Defines operations for User.
 */
@Path("/node")
@Scope("request")
public class NodeResource extends AbstractResource<Node> {
    



	UserService userService;
	NodeService nodeService;

	
    
//	@POST
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/uploadDpp")
//    public User uploadDp(@FormDataParam("name") String name,
//    		@FormDataParam("subject") String subject,
//    		@FormDataParam("concept") String concept,
//    		@FormDataParam("file") InputStream file,
//    		@FormDataParam("file") FormDataContentDisposition filedata) throws IOException  {
//		
//		//Dpp dpp = DppTransformer.formToEntity(userForm);
//		System.out.println (subject);
//		System.out.println (concept);
//		System.out.println (filedata);
//		return null;
//		
//	
//	}
	
    /**
     * 
     * @param service The User Service.
     * @param userTransformer The User Transformer.
     */
    @Autowired
    public NodeResource(NodeService nodeService,  UserService userService) {
        super(nodeService);
        this.userService=userService;
        
    }

}    
