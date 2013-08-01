package org.solovyev.common.collections;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 8/1/13
 * Time: 10:40 PM
 */
public class QuickSortTest extends ArraySortTest {

	@Nonnull
	@Override
	protected ArraySort<Integer> newArraySort() {
		return new QuickSort<Integer>();
	}
}
