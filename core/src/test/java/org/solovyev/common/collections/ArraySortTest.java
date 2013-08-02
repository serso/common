package org.solovyev.common.collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;

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
	public void testShouldSort10000Elements() throws Exception {
		testSort(10000);
	}

	private void testSort(int size) {
		final List<Integer> list = CollectionsTest.generateIntList(size);
		final Integer[] actual = list.toArray(new Integer[size]);
		final Comparator<Integer> c = Collections.naturalComparator();
		sort.sort(actual, c);

		final Integer[] expected = list.toArray(new Integer[size]);
		Arrays.sort(expected, c);

		assertArrayEquals(expected, actual);
	}

	@Test
	public void testPredefinedArray() throws Exception {
		final Integer[] actual = {2, 8, 7, 1, 3, 5, 6, 4};
		final Integer[] expected = {1, 2, 3, 4, 5, 6, 7, 8};
		newArraySort().sort(actual, Collections.<Integer>naturalComparator());
		Assert.assertArrayEquals(expected, actual);
	}
}
