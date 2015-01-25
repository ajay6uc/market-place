package com.marketplace.dataaccess.node;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.listener.StoreCallback;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;

import com.marketplace.shared.common.JDOInstanceCallbacksUtil;
import com.marketplace.shared.common.framework.entity.AbstractEntity;

/**
 * An entity to represent Dpp
 */
@PersistenceCapable(table = "Dpp", detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class Dpp extends Node  {
	private static final long serialVersionUID = 1L;

	@Override
	public void jdoPreStore() {
		//JDOInstanceCallbacksUtil.populateEntityFields(this);
	}

}