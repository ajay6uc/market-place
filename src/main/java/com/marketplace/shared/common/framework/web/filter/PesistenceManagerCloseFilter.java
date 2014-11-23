/**
 * 
 */
package com.marketplace.shared.common.framework.web.filter;

import java.io.IOException;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.orm.jdo.PersistenceManagerFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * This filter is invoked first in the request chain before any other processing (outside of that which the servlet
 * container performs) is performed.
 * 
 * The main purpose of this filter is do perform any request level initialization and cleanup. At this time, we simply
 * close out the @RequestScoped PersistenceManager
 * 
 * 
 */
@Component
@Scope("singleton")
public class PesistenceManagerCloseFilter implements Filter {
    
    private static final Logger logger = LoggerFactory.getLogger(PesistenceManagerCloseFilter.class);
    
    @SuppressWarnings("unused")
    private FilterConfig filterConfig = null;
    
    //@Autowired
   // @Qualifier("pmf")
    protected PersistenceManagerFactory persistenceManagerFactory;
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfigLocal) throws ServletException {
//        this.filterConfig = filterConfigLocal;
//        ServletContext servletContext = filterConfig.getServletContext();
//		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//		webApplicationContext.getAutowireCapableBeanFactory().autowireBeanProperties(this,
//                AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);  
//		//AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
//		
//		//autowireCapableBeanFactory.configureBean(this, "pmf");
    }
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        this.filterConfig = null;
    }
    
    /*
     * (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
     * javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain filterChain)
        throws IOException, ServletException {
        
        try {
            filterChain.doFilter(sRequest, sResponse);
        }
        finally {
//        	if(persistenceManagerFactory!=null){
//	            PersistenceManager pm =PersistenceManagerFactoryUtils.getPersistenceManager(persistenceManagerFactory, true);;
//	            if (pm != null) {
//	                logger.debug("Attempting to close PersistenceManager {}", pm);
//	                //Close out the @RequestScoped PersistenceManager
//	                pm.close();
//	                logger.debug("PersistenceManager closed successfully");
//	            }
//	            else {
//	                logger.debug("PersistenceManager is null and cannot be closed.");
//	            }
//        	}
        }
        
    }
    
   
}