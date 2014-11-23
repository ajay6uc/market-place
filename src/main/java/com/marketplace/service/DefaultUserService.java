package com.marketplace.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jdo.JDOObjectNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.marketplace.org.User;
import com.marketplace.org.dataaccess.UserDAO;
import com.marketplace.shared.common.framework.entity.Entity;
import com.marketplace.shared.common.framework.searchengine.DefaultSearchCriteria;
import com.marketplace.shared.common.framework.searchengine.SearchCriteria;
import com.marketplace.shared.common.framework.service.AbstractDBService;


@Service
public class DefaultUserService extends AbstractDBService<User> implements UserService {
    static final String UNKNOWN_USER = "Unknown";

    /**
     *
     * @param dao The Data Access Object to connect to User data source.
     * @param ssoService The SSO Service
     * @param orgService The Org Service instance.
     * @param roleService The RoleService instance.
     * @param userOrgProfileService The UserOrgProfileService Service instance.
     */
    @Autowired
    public DefaultUserService(UserDAO dao) {
    		super(dao);
       }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeInsert(User entity) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeUpdate(User entity) {
        return;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateBeforeDelete(User entity) {
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

	
}