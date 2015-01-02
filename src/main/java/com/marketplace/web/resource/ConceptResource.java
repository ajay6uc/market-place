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

import com.marketplace.dataaccess.node.Concept;
import com.marketplace.dataaccess.node.Course;
import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.Node;
import com.marketplace.dataaccess.node.Topic;
import com.marketplace.org.User;
import com.marketplace.service.UserService;
import com.marketplace.service.amazon.AmazonS3Service;
import com.marketplace.service.node.ConceptService;
import com.marketplace.service.node.CourseService;
import com.marketplace.service.node.QuestionService;
import com.marketplace.service.node.NodeService;
import com.marketplace.service.node.TopicService;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.web.NodeTransformer;
import com.marketplace.web.UserTransformer;


/**
 * Defines operations for Topic.
 */
@Path("/concept")
@Scope("request")
public class ConceptResource extends AbstractResource<Concept> {
    
	
	UserService userService;
	TopicService topicService;
	AmazonS3Service amazonS3Service;
	private static final String PARENT_ID = "parentId";
    /**
     * 
     * @param service The User Service.
     * @param userTransformer The User Transformer.
     */
    @Autowired
    public ConceptResource(ConceptService conceptService, UserService userService, AmazonS3Service amazonS3Service, TopicService nodeService) {
        super(conceptService);
        this.userService=userService;
        this.amazonS3Service = amazonS3Service;
        this.topicService = nodeService;
        
    }
    
//    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/create")
//	public Topic createConcept(Form form) throws IOException {
//		Course course = null;
//		Topic topic = new Topic();
//		topic = (Topic) NodeTransformer.formToEntity(form, topic);
//		MultivaluedMap<String, String>  nodeMap= form.asMap();
//		Long parentId = null;
//		if (nodeMap.getFirst(PARENT_ID) != null) {
//			parentId = new Long(nodeMap.getFirst(PARENT_ID));
//	      }
//		course = (Course)nodeService.findById(parentId);
//		topic.setParent(course);
//    	return getService().insert(topic);
//
//	}
    
    public Concept implementCreate(Concept concept)  {
		Topic topic = null;
		Long parentId = concept.getParentId();
		topic = (Topic)topicService.findById(parentId);
		concept.setParent(topic);
    	return getService().insert(concept);

	}

}    
