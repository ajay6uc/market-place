package com.marketplace.service.node;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.QuestionDAO;
import com.marketplace.dataaccess.node.Test;
import com.marketplace.dataaccess.node.TestDAO;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.service.AbstractDBService;


@Service
public class DefaultTestService extends AbstractDBService<Test> implements TestService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTestService.class);
    
    @Autowired
    UserService userService;
    
    @Autowired
    public DefaultTestService(TestDAO dao) {
    		super(dao);
       }

	@Override
	protected void validateBeforeInsert(Test entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeUpdate(Test entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeDelete(Test entity) {
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
