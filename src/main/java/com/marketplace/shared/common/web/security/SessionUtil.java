package com.marketplace.shared.common.web.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.marketplace.shared.common.SessionProfile;

public final class SessionUtil {

	public static final String USER_ORG_PROFILE_ATTRIBUTE = SessionProfile.class
			.getName();

	private SessionUtil() {
	}

	public static Subject getCurrentUser() {
		return SecurityUtils.getSubject();	
	}

	public static Session getSession() {
		return getCurrentUser().getSession();
	}

	public static Object getAttribute(String attribute) {
		return getSession().getAttribute(attribute);
	}

	public static void setAttribute(String attribute, Object value) {
		getSession().setAttribute(attribute, value);
	}

	public static SessionProfile getProfile() {
		return (SessionProfile) getAttribute(USER_ORG_PROFILE_ATTRIBUTE);
	}

	public static void setProfile(SessionProfile profile) {
		setAttribute(USER_ORG_PROFILE_ATTRIBUTE, profile);
	}

}
