package com.marketplace.service.node;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.QuestionDAO;
import com.marketplace.dataaccess.node.TestQuestion;
import com.marketplace.dataaccess.node.TestQuestionDAO;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.service.AbstractDBService;


@Service
public class DefaultTestQuestionService extends AbstractDBService<TestQuestion> implements TestQuestionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTestQuestionService.class);
    
    @Autowired
    UserService userService;
    
    @Autowired
    public DefaultTestQuestionService(TestQuestionDAO dao) {
    		super(dao);
       }

	@Override
	protected void validateBeforeInsert(TestQuestion entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeUpdate(TestQuestion entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeDelete(TestQuestion entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeCreate(List<? extends Entity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeModify(List<? extends Entity> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeRemove(List<? extends Entity> entities) {
		// TODO Auto-generated method stub
		
	}

	
   
}
