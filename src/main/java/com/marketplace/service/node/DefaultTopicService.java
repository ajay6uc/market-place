package com.marketplace.service.node;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.dataaccess.node.Concept;
import com.marketplace.dataaccess.node.ConceptDAO;
import com.marketplace.dataaccess.node.Course;
import com.marketplace.dataaccess.node.CourseDAO;
import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.QuestionDAO;
import com.marketplace.dataaccess.node.Topic;
import com.marketplace.dataaccess.node.TopicDAO;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.service.AbstractDBService;


@Service
public class DefaultTopicService extends AbstractDBService<Topic> implements TopicService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTopicService.class);
    
    @Autowired
    UserService userService;
    
    /**
     *
     * @param dao The Data Access Object to connect to User data source.
     * @param ssoService The SSO Service
     * @param orgService The Org Service instance.
     * @param roleService The RoleService instance.
     * @param userOrgProfileService The UserOrgProfileService Service instance.
     */
    @Autowired
    public DefaultTopicService(TopicDAO dao) {
    		super(dao);
       }

	@Override
	protected void validateBeforeInsert(Topic entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeUpdate(Topic entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeDelete(Topic entity) {
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
