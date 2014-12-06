package com.marketplace.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sun.util.logging.resources.logging;


import com.marketplace.org.ResetPasswordRequest;
import com.marketplace.org.User;
import com.marketplace.org.dataaccess.ResetPasswordDAO;
import com.marketplace.org.dataaccess.UserDAO;
import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.searchengine.DefaultSearchCriteria;
import com.marketplace.shared.common.framework.searchengine.SearchCriteria;
import com.marketplace.shared.common.framework.service.AbstractDBService;
import com.marketplace.shared.common.framework.web.AbstractResource;


@Service
public class DefaultResetPasswordService extends AbstractDBService<ResetPasswordRequest> implements ResetPasswordService {
    static final String UNKNOWN_USER = "Unknown";
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultResetPasswordService.class);
    
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
    public DefaultResetPasswordService(ResetPasswordDAO dao) {
    		super(dao);
       }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeInsert(ResetPasswordRequest entity) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeUpdate(ResetPasswordRequest entity) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeDelete(ResetPasswordRequest entity) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeCreate(List<? extends Entity> entities) {
     //   validateData(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeModify(List<? extends Entity> entities) {
       // validateData(entities);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeRemove(List<? extends Entity> entities) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void afterInsert(ResetPasswordRequest entity) {
    	System.out.println("After reseting passing inserted " + entity);
    }

	@Override
	@Transactional
	public boolean requestResetPassword(String email) {

		ResetPasswordRequest resetPassword = new ResetPasswordRequest();
		resetPassword.setEmail(email);
		resetPassword.setExpiryOn(System.currentTimeMillis() + 24 * 3600);
		DefaultSearchCriteria userSearchCriteria = new DefaultSearchCriteria();
		userSearchCriteria.addFilter("email", email);
		List<User> userList = userService.find(userSearchCriteria);
		if (userList.size() > 0) {
			resetPassword = insert(resetPassword);
			resetPassword.setExpiryOn(System.currentTimeMillis());
			update(resetPassword);
			return true;
		}

		return false;
	}
    
    
	
}