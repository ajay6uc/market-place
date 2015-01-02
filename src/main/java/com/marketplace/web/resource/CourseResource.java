package com.marketplace.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

import com.marketplace.dataaccess.node.Course;
import com.marketplace.dataaccess.node.Node;
import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.Topic;
import com.marketplace.org.User;
import com.marketplace.service.UserService;
import com.marketplace.service.amazon.AmazonS3Service;
import com.marketplace.service.node.CourseService;
import com.marketplace.service.node.QuestionService;
import com.marketplace.service.node.NodeService;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.web.NodeTransformer;
import com.marketplace.web.UserTransformer;


/**
 * Defines operations for User.
 */
@Path("/course")
@Scope("request")
public class CourseResource extends AbstractResource<Course> {
    
	
	UserService userService;
	NodeService nodeService;
	AmazonS3Service amazonS3Service;
    /**
     * 
     * @param service The User Service.
     * @param userTransformer The User Transformer.
     */
    @Autowired
    public CourseResource(CourseService courseService, UserService userService, AmazonS3Service amazonS3Service, NodeService nodeService) {
        super(courseService);
        this.userService=userService;
        this.amazonS3Service = amazonS3Service;
        this.nodeService = nodeService;
        
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getChildren/{parentId}")
	public List<Node> getChildren(@PathParam("parentId") Long parentId) throws IOException {
		return this.nodeService.getChildren(parentId);

	}
    
    
    
  
}    
