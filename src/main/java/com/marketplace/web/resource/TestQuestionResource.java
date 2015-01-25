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

import javax.swing.text.DefaultEditorKit.InsertBreakAction;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

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
import com.marketplace.service.node.TestQuestionService;
import com.marketplace.service.node.TestService;
import com.marketplace.shared.common.core.NullAwareBeanUtilsBean;
import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.service.DBService;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.util.ConstantUtils;
import com.marketplace.web.NodeTransformer;
import com.marketplace.web.UserTransformer;


/**
 * Defines operations for TestQuestion.
 */
@Path("/testQuestion")
@Scope("request")
public class TestQuestionResource extends AbstractResource<TestQuestion> {
	
    public static final Logger logger = LoggerFactory.getLogger(TestQuestionResource.class);
	
	TestQuestionService testQuestionService;
	 QuestionService questionService;
	
    @Autowired
	public TestQuestionResource(TestQuestionService testQuestionService, QuestionService questionService) {
    	
		super(testQuestionService);
		this.questionService = questionService;
	}
    
    @Override
    protected TestQuestion implementCreate(TestQuestion testQuestion)   {
    	logger.debug("Updating the testQuestion resource");

    	Question questionFromDb = questionService.findById(testQuestion.getQuestion().getId());
    	
    	testQuestion.setQuestion(questionFromDb);
    	return create(testQuestion);
    }

    @Override
    protected TestQuestion implementModify(Long id, TestQuestion testQuestion)   {
    	logger.debug("Updating the testQuestion resource");

    	TestQuestion testQuestionFromDb = find(testQuestion.getId());

    	NullAwareBeanUtilsBean nabu = new NullAwareBeanUtilsBean();
    	
    	Question questionFromDB = testQuestionFromDb.getQuestion();
    	try {
    		nabu.copyProperties(testQuestionFromDb, testQuestion);
    		nabu.copyProperties(questionFromDB, testQuestion.getQuestion());
    		
    	} catch (IllegalAccessException | InvocationTargetException ex) {
    		logger.debug("Error ocurred while copying question entity properties to db entity properties for update from UI", ex);
    	}

    	if (testQuestion != null) {

    		testQuestionFromDb = this.getService().update(testQuestionFromDb);
    		logger.info("Returning  updated testQuestion entity" + testQuestionFromDb);
    		return testQuestionFromDb;
    	}

    	return null;
    }
    
    
}    
