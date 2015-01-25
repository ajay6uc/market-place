package com.marketplace.web.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
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


import com.marketplace.shared.common.framework.searchengine.FilterExpression.FilterTerm;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.shiro.web.util.WebUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.marketplace.dataaccess.node.Concept;
import com.marketplace.dataaccess.node.Course;
import com.marketplace.dataaccess.node.Node;
import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.QuestionOption;
import com.marketplace.dataaccess.node.Test;
import com.marketplace.dataaccess.node.TestQuestion;
import com.marketplace.dataaccess.node.Topic;
import com.marketplace.org.User;
import com.marketplace.question.ComplexityLevel;
import com.marketplace.question.QuestionFileErrorsInfo;
import com.marketplace.question.QuestionFileLoadInfo;
import com.marketplace.question.QuestionType;
import com.marketplace.question.Status;
import com.marketplace.service.UserService;
import com.marketplace.service.amazon.AmazonS3Service;
import com.marketplace.service.node.ConceptService;
import com.marketplace.service.node.QuestionService;
import com.marketplace.service.node.NodeService;
import com.marketplace.service.node.TestService;
import com.marketplace.shared.common.core.NullAwareBeanUtilsBean;
import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.searchengine.DefaultSearchCriteria;
import com.marketplace.shared.common.framework.searchengine.SearchCriteria;
import com.marketplace.shared.common.framework.service.DBService;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.util.ConstantUtils;
import com.marketplace.web.NodeTransformer;
import com.marketplace.web.UserTransformer;


/**
 * Defines operations for User.
 */
@Path("/test")
@Scope("request")
public class TestResource extends AbstractResource<Test> {
	
	
	public static final Logger logger = LoggerFactory.getLogger(TestResource.class);

	TestService testService;
	QuestionService  questionService;
	ConceptService  conceptService;
	
	
    @Autowired
	public TestResource(TestService testService, QuestionService  questionService, ConceptService  conceptService) {
		super(testService);
		this.questionService = questionService;
		this.conceptService = conceptService;
	}
    
	
	@Override
	public Test implementCreate(Test test) {
		List<Concept> node = null;
		Long parentId = test.getParentId();
		SearchCriteria defaultSearchCriteria =  new DefaultSearchCriteria();
	    defaultSearchCriteria.addFilter(new FilterTerm("id", parentId+""));
	    node = conceptService.find(defaultSearchCriteria);
		test.setParent(node.get(0));
		Test newTest = getService().insert(test);
		return newTest;

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addTestQuestions")
    public Test addTestQuestions(Test test)   {
    	logger.debug("Adding the testQuestions ");
    	
    	Test testFromDB = getService().findById(test.getId());
    	
    	
		for(TestQuestion testQuestion : test.getTestQuestions()){
			Question questionFromDb = questionService.findById(testQuestion.getQuestionId());
			testQuestion.setQuestion(questionFromDb);
			Set<TestQuestion> questionOptionsFromDB = testFromDB.getTestQuestions();
			if(questionOptionsFromDB == null){
				questionOptionsFromDB = new LinkedHashSet<TestQuestion>();
				questionOptionsFromDB.add(testQuestion);
			}else{
				questionOptionsFromDB.add(testQuestion);
			}
	
		}
    	if (test != null) {
    		//testFromDB.setTestQuestions(test.getTestQuestions());
    		testFromDB = this.getService().update(testFromDB);
    		logger.info("Returning  updated test entity" + testFromDB);
    		return testFromDB;
    	}
    	
    	
    	return null;
    }
     
    
}    
