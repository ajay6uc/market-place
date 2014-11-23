package com.marketplace.shared.common.transactions;

import javax.jdo.PersistenceManager;
import javax.jdo.Transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Interceptor which wraps methods annotated with @JDOTransactional, enveloping them in a JDO transaction. Methods may
 * call into other methods which are also so marked; if the persistence manager's current transaction is active, we will
 * simply participate in it, and not begin/commit.
 * 
 * 
 */
public class JDOTransactionalInterceptor implements MethodInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(JDOTransactionalInterceptor.class);
    
   @Autowired
   private PersistenceManager persistenceManager;
    
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Transaction tx = persistenceManager.currentTransaction();
        boolean wasActive = false;
        
        try {
            if (tx.isActive()) {
                LOG.debug("Participating in existing transactional envelope");
                wasActive = true;
            }
            else {
                LOG.debug("Beginning new transaction");
                tx.begin();
            }
            
            Object result = invocation.proceed();
            
            // if we are not a nested transaction 
            if (!wasActive) {
                // and haven't been rolled back yet, commit
                if (!tx.getRollbackOnly()) {
                    LOG.debug("Committing valid transaction");
                    tx.commit();
                }
                // someone down the line has marked this tx as failed, so we'll rollback here
                else {
                    LOG.debug("Rolling back transaction marked as rollbackOnly");
                    tx.rollback();
                }
            }
            
            return result;
        }
        catch (Throwable t) {
            if (wasActive) {
                LOG.error("Nested transaction scope; marking tx as rollbackOnly");
                tx.setRollbackOnly();
            }
            else {
                LOG.error("Rolling back transaction due to uncaught exception", t);
                tx.rollback();
            }
            
            throw t;
        }
    }
    
    
}
