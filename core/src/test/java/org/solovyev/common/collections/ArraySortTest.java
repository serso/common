package org.solovyev.common.collections;

import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * User: serso
 * Date: 8/1/13
 * Time: 10:37 PM
 */
public abstract class ArraySortTest {

	@Nonnull
	private ArraySort<Integer> sort;

	@Before
	public void setUp() throws Exception {
		sort = newArraySort();
	}

	@Nonnull
	protected abstract ArraySort<Integer> newArraySort();

	@Test
	public void testShouldSortEmptyList() throws Exception {
		testSort(0);
	}

	@Test
	public void testShouldSort1Element() throws Exception {
		testSort(1);
	}

	@Test
	public void testShouldSort2Elements() throws Exception {
		testSort(2);
	}

	@Test
	public void testShouldSort10Elements() throws Exception {
		testSort(10);
	}

	@Test
	public void testShouldSort100Elements() throws Exception {
		testSort(100);
	}


	@Test
	public void testShouldSort1000000Elements() throws Exception {
		testSort(1000000);
	}

	private void testSort(int size) {
		final List<Integer> list = CollectionsTest.generateIntList(size);
		sort.sort(list.toArray(new Integer[size]), Collections.<Integer>naturalComparator());
	}
}
