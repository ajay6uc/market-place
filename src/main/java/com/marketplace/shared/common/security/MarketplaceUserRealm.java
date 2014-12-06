package com.marketplace.shared.common.security;

import java.util.List;

import javax.inject.Singleton;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.marketplace.org.User;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.searchengine.DefaultSearchCriteria;


@Component
@Singleton
public class MarketplaceUserRealm extends AuthorizingRealm {

	protected UserService userService = null;
	
	@Autowired
	public void setUserService(UserService userService){
		setCredentialsMatcher(new SimpleCredentialsMatcher());
		setName("MarketplaceUserRealm");
		this.userService= userService;
	}
   
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        
    	UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        DefaultSearchCriteria userSearchCriteria = new DefaultSearchCriteria();
		userSearchCriteria.addFilter("email",token.getUsername());
		List<User> userList = userService.find(userSearchCriteria);
		User user = null;
		if (userList.size() > 0) {
			user = userList.get(0);
		}
        if( user != null ) {
            return new SimpleAuthenticationInfo(token.getUsername(), user.getPassword(), getName());
        } else {
            return null;
        }
    }


    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        Long userId = (Long) principals.fromRealm(getName()).iterator().next();
//        User user = userDAO.getUser(userId);
//        if( user != null ) {
//            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//            for( Role role : user.getRoles() ) {
//                info.addRole(role.getName());
//                info.addStringPermissions( role.getPermissions() );
//            }
//            return info;
//        } else {
//            return null;
//        }
    		return null;
    }

}