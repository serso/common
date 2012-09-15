package org.solovyev.common.definitions;

import org.solovyev.common.Identifiable;

/**
 * User: serso
 * Date: 28.03.2009
 * Time: 15:59:27
 */
public interface MultiIdentifiable<T> extends Identifiable<T> {
    public T getId(Integer i);
    public int getNumberOfIds();
    public void addNewId(T id);
    public void addNewId();
    public Integer getCurrentUsedId();
}
