package com.marketplace.service;

import com.marketplace.org.ResetPasswordRequest;
import com.marketplace.org.User;
import com.marketplace.shared.common.framework.service.DBService;

public interface ResetPasswordService extends DBService<ResetPasswordRequest> {

	public boolean requestResetPassword(String email);
	
}
