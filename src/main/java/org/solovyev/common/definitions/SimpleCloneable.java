package org.solovyev.common.definitions;

/**
 * User: serso
 * Date: 13.04.2009
 * Time: 11:31:23
 */
public interface SimpleCloneable<T> extends Cloneable{
    public T clone();
}
