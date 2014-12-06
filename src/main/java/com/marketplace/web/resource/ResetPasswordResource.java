package com.marketplace.web.resource;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.marketplace.org.ResetPasswordRequest;
import com.marketplace.org.User;
import com.marketplace.service.ResetPasswordService;
import com.marketplace.service.UserService;
import com.marketplace.shared.common.framework.web.AbstractResource;


/**
 * Defines operations for User.
 */
@Path("/resetPassword")
@Scope("request")
public class ResetPasswordResource extends AbstractResource<ResetPasswordRequest> {
    
	
	ResetPasswordService resetPasswordService;
	
	
	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void requestResetPassword(Form resetForm) throws IOException  {
		
		String userEmail = resetForm.asMap().getFirst("email");
		boolean resetSuccess = resetPasswordService.requestResetPassword(userEmail);
		Map<String, String> params = new HashMap<String, String>();
        params.put("email", userEmail);
        if(resetSuccess){
        	WebUtils.issueRedirect(getRequest(), getResponse(), "/../views/resetPassword.html", params);
        }else{
        	params.put("error", "true");
        	WebUtils.issueRedirect(getRequest(), getResponse(), "/../views/resetPassword.html", params);
        }
	
	}
    /**
     * 
     * @param service The ResetPassword Service.
     */
    @Autowired
    public ResetPasswordResource(ResetPasswordService resetPasswordService) {
        super(resetPasswordService);
        this.resetPasswordService=resetPasswordService;
    }

	
     
}