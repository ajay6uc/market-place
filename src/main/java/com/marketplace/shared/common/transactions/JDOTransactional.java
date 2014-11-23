/**
 * 
 */
package com.marketplace.shared.common.transactions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Denotes this method as one which participates in a JDO transaction, and needs to operate within a transactional
 * envelope. If this method is entered within an existing transactional envelope, it will participate in that
 * transaction (meaning errors will set the tx as rollbackOnly).
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface JDOTransactional {
    
}
