package com.marketplace.service.node;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marketplace.dataaccess.node.Dpp;
import com.marketplace.dataaccess.node.DppDAO;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.service.AbstractDBService;


@Service
public class DefaultDppService extends AbstractDBService<Dpp> implements DppService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultDppService.class);
    
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
    public DefaultDppService(DppDAO dao) {
    		super(dao);
       }

	@Override
	protected void validateBeforeInsert(Dpp entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeUpdate(Dpp entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void validateBeforeDelete(Dpp entity) {
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
