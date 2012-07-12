package org.solovyev.common.collections.multiset;

/**
 * User: serso
 * Date: 7/8/12
 * Time: 1:38 PM
 */
public interface OneInstanceMultiSet<E> extends MultiSet<E> {

    /**
     * Adds or removes the necessary occurrences of an element such that the
     * element attains the desired count.
     *
     * @param e the element to add or remove occurrences of; may be null
     *                only if explicitly allowed by the implementation
     * @param count   the desired count of the element in this multiset
     * @return the count of the element before the operation; possibly zero
     * @throws IllegalArgumentException if {@code count} is negative
     * @throws NullPointerException     if {@code element} is null and this
     *                                  implementation does not permit null elements. Note that if {@code
     *                                  count} is zero, the implementor may optionally return zero instead.
     */
    int setCount(E e, int count);
}
