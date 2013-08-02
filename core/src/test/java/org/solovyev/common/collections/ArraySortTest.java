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
		final List<Integer> list = CollectionsTest.generateIntList(0);
		sort.sort(list.toArray(new Integer[0]), Collections.<Integer>naturalComparator());
	}

	@Test
	public void testShouldSort1Element() throws Exception {
		final List<Integer> list = CollectionsTest.generateIntList(1);
		sort.sort(list.toArray(new Integer[1]), Collections.<Integer>naturalComparator());
	}

	@Test
	public void testShouldSort2Elements() throws Exception {
		final List<Integer> list = CollectionsTest.generateIntList(2);
		sort.sort(list.toArray(new Integer[2]), Collections.<Integer>naturalComparator());
	}

	@Test
	public void testShouldSort10Elements() throws Exception {
		final List<Integer> list = CollectionsTest.generateIntList(10);
		sort.sort(list.toArray(new Integer[10]), Collections.<Integer>naturalComparator());
	}

	@Test
	public void testShouldSort100Elements() throws Exception {
		final List<Integer> list = CollectionsTest.generateIntList(100);
		sort.sort(list.toArray(new Integer[100]), Collections.<Integer>naturalComparator());
	}
}
