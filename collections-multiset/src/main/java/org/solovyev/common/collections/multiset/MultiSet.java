/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.collections.multiset;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Set;

/**
 * User: serso
 * Date: 7/5/12
 * Time: 1:53 PM
 */
public interface MultiSet<E> extends Collection<E> {

	/**
	 * Returns the total number of the instances of the specified element
	 * in this multiset.
	 * More formally, returns the total number of elements <tt>e</tt> such that
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 *
	 * @param e element to search for
	 * @return total number of instances of specified object
	 * @throws ClassCastException   if the type of the specified element
	 *                              is incompatible with this multiset (optional)
	 * @throws NullPointerException if the specified element is null and this
	 *                              multiset does not permit null elements (optional)
	 */
	int count(E e);

	/**
	 * Returns the collection of all instances of the specified object
	 * in this multiset.
	 * More formally, returns the collection <tt>c</tt> such that all elements <tt>e</tt> in it satisfy
	 * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(e))</tt>.
	 * Depending on implementation this collection might b
	 *
	 * @param e element to search for
	 * @return the collection containing all the instances of specified object in this multiset
	 * @throws ClassCastException   if the type of the specified element
	 *                              is incompatible with this multiset (optional)
	 * @throws NullPointerException if the specified element is null and this
	 *                              multiset does not permit null elements (optional)
	 */
	@Nonnull
	Collection<E> getAll(E e);

	/**
	 * Returns current multiset as set. More formally, returns the set <tt>c</tt> such that all elements <tt>e</tt>
	 * in it occurred only once.
	 *
	 * @return set for current multiset
	 */
	@Nonnull
	Set<E> toElementSet();

	/**
	 * Adds a number of the specified element instances to this
	 * multiset.
	 *
	 * @param e     the element to be added
	 * @param count number of instances of object to be added
	 * @return <tt>true</tt> if this multiset changed as a result of the
	 *         call
	 * @throws IllegalArgumentException if {@code occurrences} is negative
	 */
	boolean add(E e, int count);

	/**
	 * Removes a number of occurrences of the specified element from this
	 * multiset. If the multiset contains fewer than this number of occurrences to
	 * begin with, all occurrences will be removed.  Note that if
	 * {@code occurrences == 1}, this is functionally equivalent to the call
	 * {@code remove(element)}.
	 *
	 * @param e     the element to conditionally remove occurrences of
	 * @param count the number of occurrences of the element to remove. May
	 *              be zero, in which case no change will be made.
	 * @return the count of the element before the operation; possibly zero
	 * @throws IllegalArgumentException if {@code occurrences} is negative
	 */
	int remove(E e, int count);
}
