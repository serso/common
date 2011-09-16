package org.solovyev.common.utils;

/**
 * User: serso
 * Date: 9/16/11
 * Time: 2:15 PM
 */
public class MutableObject<T> {

	private T object;

	public MutableObject() {
	}

	public MutableObject(T object) {
		this.object = object;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}
}
