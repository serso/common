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
	 * @param e     the element to add or remove occurrences of; may be null
	 *              only if explicitly allowed by the implementation
	 * @param count the desired count of the element in this multiset
	 * @return the count of the element before the operation; possibly zero
	 * @throws IllegalArgumentException if {@code count} is negative
	 * @throws NullPointerException     if {@code element} is null and this
	 *                                  implementation does not permit null elements. Note that if {@code
	 *                                  count} is zero, the implementor may optionally return zero instead.
	 */
	int setCount(E e, int count);
}
