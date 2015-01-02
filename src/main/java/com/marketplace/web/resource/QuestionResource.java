package com.marketplace.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import com.marketplace.dataaccess.node.Node;
import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.QuestionOption;
import com.marketplace.dataaccess.node.Topic;
import com.marketplace.org.User;
import com.marketplace.service.UserService;
import com.marketplace.service.amazon.AmazonS3Service;
import com.marketplace.service.node.QuestionService;
import com.marketplace.service.node.NodeService;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.web.NodeTransformer;
import com.marketplace.web.UserTransformer;


/**
 * Defines operations for User.
 */
@Path("/question")
@Scope("request")
public class QuestionResource extends AbstractResource<Question> {
    
	
	UserService userService;
	NodeService nodeService;
	AmazonS3Service amazonS3Service;
	private static final String PARENT_ID = "parentId";
	
    
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
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/uploadDpp")
	public Question uploadDp(FormDataMultiPart form) throws IOException {
		Long parentId = null;
		Course course = null;
		Question dpp = NodeTransformer.formToEntity(form);
		FormDataBodyPart filePart = form.getField("file");
		if(form.getField(PARENT_ID)!=null){
			parentId = form.getField(PARENT_ID).getValueAs(Long.class);
        }
		course = (Course)nodeService.findById(parentId);
    	ContentDisposition headerOfFilePart = filePart.getContentDisposition();
		String filePath = "org-test-1/" + headerOfFilePart.getFileName();
		String fileType = filePath.substring(filePath.lastIndexOf(".") +1);
		InputStream fileInputStream = filePart.getValueAs(InputStream.class);
		this.amazonS3Service.uploadPdfOrEpub("org-1", filePath, "image/"+ fileType, fileInputStream);
		dpp.setContentUrl("https://s3-ap-northeast-1.amazonaws.com/futor-static-develop/" + filePath);
		dpp.setParent(course);
		return getService().insert(dpp);

	}

	@PUT
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
	
    /**
     * 
     * @param service The User Service.
     * @param userTransformer The User Transformer.
     */
    @Autowired
    public QuestionResource(QuestionService dppService, UserService userService, AmazonS3Service amazonS3Service, NodeService nodeService) {
        super(dppService);
        this.userService=userService;
        this.amazonS3Service = amazonS3Service;
        this.nodeService = nodeService;
        
    }
    
    public Question implementCreate(Question question)  {
		Node node = null;
		Long parentId = question.getParentId();
		node = nodeService.findById(parentId);
		QuestionOption  questionOption= new QuestionOption();
		Set<QuestionOption> questionOptions = new HashSet<QuestionOption>();
		questionOptions.add(questionOption);
		question.setQuestionOptions(questionOptions);
		question.setParent(node);
    	return getService().insert(question);

	}
}    
