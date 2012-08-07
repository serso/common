package org.solovyev.common.definitions;

import java.io.Serializable;

/**
 * User: serso
 * Date: 10/4/11
 * Time: 11:36 PM
 */
public class IdentityImpl<T extends Serializable & Comparable<T>> extends Identity<T> {

	public IdentityImpl() {
		super();
	}

	public IdentityImpl(T id) {
		super(id);
	}
}
