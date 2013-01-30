
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

package org.solovyev.common.filter;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.JPredicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * User: serso
 * Date: Jan 18, 2010
 * Time: 10:52:20 AM
 */

/**
 * Used for filtering lists using iterator
 * @param <T>
 */
public class Filter<T> {

	//filter rule used for filtration
	protected final JPredicate<T> filterRule;

	private final boolean inverseFilter;

	/**
	 * @param filterRule rule according to which filtration will be done
	 */
	public Filter(JPredicate<T> filterRule) {
		this(filterRule, false);
	}

	public Filter(JPredicate<T> filterRule, boolean inverseFilter) {
		this.filterRule = filterRule;
		this.inverseFilter = inverseFilter;
	}

	/**
	 * Method goes to the next element in iterator and filter it
	 *
	 * @param it list iterator
	 * @return filter data - object consisted of list element and boolean that says if has element been filtered
	 *
	 * NOTE: method must work as ch.sc.common.collections.Filter#nextAndFilterWithNoResult(java.util.Iterator<T>)!!!
	 */
	public FilterData<T> nextAndFilter(Iterator<T> it) {
		FilterData<T> elementData = null;

		if (it != null && it.hasNext()) {
			T element = it.next();
			elementData = FilterData.newInstance(element, filter(element, it));
		}

		return elementData;
	}

	/**
	 * Method goes to the next element in iterator and filter it
	 *
	 * @param it list iterator
	 */
	private void nextAndFilterWithNoResult(Iterator<T> it) {
		if (it != null && it.hasNext()) {
			filter(it.next(), it);
		}
	}

	/**
	 * Method filters current list element
	 *
	 * @param element current element in list
	 * @param it	  iterator in the next step after first argument element
	 * @return was element filtered or not
	 */
	public boolean filter(T element, Iterator<T> it) {
		boolean wasFiltered = false;

		if (filterRule != null && it != null) {
			wasFiltered = filterRule.apply(element) != inverseFilter;
			if (wasFiltered) {
				it.remove();
			}
		}

		return wasFiltered;
	}

	/**
	 * Method filters according to filter rule some iterator
	 * @param it iterator must be at the fist element (before first invoking next())
	 */
	public void filter ( Iterator<T> it ) {
		if ( it != null ) {
			while (it.hasNext()) {
				nextAndFilterWithNoResult(it);
			}
		}
	}

	public T firstOccurrence (Iterator<T> it) {
		T firstOccurrence = null;
		T el;
		while ( it != null && it.hasNext() ) {
			el = it.next();
			if ( filterRule != null ) {
				if ( filterRule.apply(el) ) {
					firstOccurrence = el;
					break;
				}
			} else {
				firstOccurrence = el;
				break;
			}
		}
		return firstOccurrence;
	}

	/**
	 * Method filters COPY of passed list according to filter rule
	 *
	 * @param list list to be filtered NOTE: no affection on passed list, only copy will be filtered
	 * @param filterRule rule bu which filtration will be done
	 *
	 * @param <T> type
	 *
	 * @return copy of passed list filtered according to filter rule
	 */
	public static <T> List<T> filterCopy(@NotNull Collection<T> list, @NotNull JPredicate<T> filterRule) {
		return filter(new ArrayList<T>(list), filterRule);
	}

	public static <T> void filter(@NotNull Iterator<T> it, final boolean inverseFilter, @NotNull JPredicate<T> filterRule) {
		final Filter<T> filter = new Filter<T>(filterRule, inverseFilter);

		filter.filter(it);
	}

	/**
	 * Method filters passed list according to filter rule
	 *
	 * @param list list to be filtered
	 * @param filterRule rule bu which filtration will be done
	 *
	 * @param <T> type
	 *
	 * @return passed list instance filtered according to filter rule
	 */
	public static <X extends Collection<T>, T> X filter (@NotNull X list, @NotNull JPredicate<T> filterRule) {
		return filter(list, false, filterRule);
	}

	public static <X extends Collection<T>, T> X filter (@NotNull X list, boolean inverseFilter, @NotNull JPredicate<T> filterRule) {
		filter(list.iterator(), inverseFilter, filterRule);
		return list;
	}
}