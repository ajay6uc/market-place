package com.marketplace.org;

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
 * An entity to represent User
 */
@PersistenceCapable(table = "RESET_PASSWORD_REQUEST",  detachable = "true")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@XmlRootElement
public class ResetPasswordRequest extends AbstractEntity implements
		StoreCallback {
	private static final long serialVersionUID = 1L;

	@Persistent(customValueStrategy = "uuid")
	@Column(name = "HASH_UUID")
	private String uuId;

	@Persistent
	@Column(name = "EMAIL")
	private String email;

	@Persistent
	@Column(name = "EXPIRY_ON")
	private long expiryOn;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getExpiryOn() {
		return expiryOn;
	}

	public void setExpiryOn(long expiryOn) {
		this.expiryOn = expiryOn;
	}

	@Override
	public void jdoPreStore() {
		JDOInstanceCallbacksUtil.populateEntityFields(this);
	}

}