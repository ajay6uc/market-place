package com.marketplace.service.node;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.QuestionDAO;
import com.marketplace.dataaccess.node.Node;
import com.marketplace.dataaccess.node.NodeDAO;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.service.AbstractDBService;


@Service
public class DefaultNodeService extends AbstractDBService<Node> implements NodeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultQuestionService.class);
    
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
    public DefaultNodeService(NodeDAO dao) {
    		super(dao);
       }

	@Override
	protected void validateBeforeInsert(Node entity) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<Node> getChildren(Long parentId) {
		return ((NodeDAO) getDao()).getChildren(parentId);
		
	}
	
	@Override
	protected void validateBeforeUpdate(Node entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeDelete(Node entity) {
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
