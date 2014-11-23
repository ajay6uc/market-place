package com.marketplace;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.ws.rs.core.Response.Status;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VerboseFormAuthenticationFilter extends FormAuthenticationFilter {

 String loginFormUrl;
 private static final Logger logger = LoggerFactory.getLogger(VerboseFormAuthenticationFilter.class);
 
 public VerboseFormAuthenticationFilter() {
     this.loginFormUrl =  "/../login.html";
   }
 
 /**
  * {@inheritDoc}
  */
 @Override
 protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                  ServletResponse response) {
     
     try {
         Map<String, String> params = new HashMap<String, String>();
         params.put("error", "true");
         WebUtils.issueRedirect(request, response, loginFormUrl, params);
         return false;
     }
     catch (IOException ex) {
         return super.onLoginFailure(token, e, request, response);
     }
 }
 
 /**
  * {@inheritDoc}
  */
 @Override
 protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
     if (!isLoginRequest(request, response) && !isLoginSubmission(request, response)) {
         WebUtils.toHttp(response).setStatus(Status.UNAUTHORIZED.getStatusCode());
         return false;
     }
     
     return super.onAccessDenied(request, response);
 }
protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
	ae.printStackTrace();
	logger.error(ae.toString());
	String message = ae.getMessage();
	request.setAttribute(getFailureKeyAttribute(), message);
}


}