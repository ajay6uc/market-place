
package com.marketplace.shared.common.framework.interceptors.exception;


import java.util.Arrays;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;


@Scope("singleton")
public final class ResourceExceptionInterceptor implements MethodInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(ResourceExceptionInterceptor.class);
    
    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(final MethodInvocation mthodInvocation) throws Throwable {
        Exception coreException = null;
        
        try {
            return mthodInvocation.proceed();
        }
        catch (final Exception ex) {
            coreException = ex;
            logger.error("Uncaught Exception from Resource Method: " + ex.toString(), ex);
            return null;
            // throw convertException(ex);
        }
        finally {
            if (logger.isDebugEnabled()) {
                final String methodName = mthodInvocation.getMethod().getName();
                final String args = Arrays.toString(mthodInvocation.getArguments());
                String className = null;
                
                try {
                    className = mthodInvocation.getThis().getClass().getName();
                }
                catch (final Exception ignored) {
                    // Smothered to prevent reentrancy [GS]
                }
                finally {
                    if (coreException != null) {
                        if (!(coreException instanceof WebApplicationException)) {
                            logger.debug("Resource Method {} on Class {} with Parameters {} threw Core Exception [{}] -- converting into Resource Exception.",
                                         new Object[]{methodName,
                                                      (className == null ? "UNKNOWN" : className),
                                                      args,
                                                      coreException.toString()});
                            logger.debug("Resource Exception Stack:", coreException);
                        }
                    }
                }
            }
        }
    }
    
  
}