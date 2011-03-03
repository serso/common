package org.solovyev.common.definitions;

/**
 * User: serso
 * Date: 13.04.2009
 * Time: 11:31:23
 */

/**
 * Typed cloneable interface with no CloneNotSupported exception, instead of CloneNotSupported runtime IllegalArgumentException is thrown
 * @param <T> type of object to be cloned
 */
public interface SimpleCloneable<T> extends Cloneable{

	/**
	 * @return cloned object of current object
	 */
	public T clone();
	
}
