package org.solovyev.common.filter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 10/15/11
 * Time: 2:03 PM
 */
public class FilterRulesChain<T> implements FilterRule<T> {

	@NotNull
	private final List<FilterRule<T>> filters = new ArrayList<FilterRule<T>>();

	public FilterRulesChain() {
	}

	public void addFilterRule(@NotNull FilterRule<T> filterRule) {
		filters.add(filterRule);
	}

	@Override
	public boolean isFiltered(T object) {
		boolean result = false;

		for (FilterRule<T> filter : filters) {
			if (filter.isFiltered(object)) {
				result = true;
				break;
			}
		}

		return result;
	}
}
