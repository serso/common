package org.solovyev.common.collections;

import org.junit.Test;
import org.solovyev.common.text.Strings;

import javax.annotation.Nonnull;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static org.solovyev.common.collections.Collections.naturalComparator;

/**
 * User: serso
 * Date: 8/1/13
 * Time: 10:07 PM
 */
public class CollectionsTest {

	@Test
	public void testShouldSortIntegersWithNaturalComparator() throws Exception {
		final List<Integer> list = generateUniqueIntList(100);
		java.util.Collections.sort(list, naturalComparator());
		assertListSorted(list, naturalComparator());
	}

	@Test
	public void testShouldSortStringsWithNaturalComparator() throws Exception {
		final List<String> list = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			list.add(Strings.generateRandomString(10));
		}
		java.util.Collections.sort(list, naturalComparator());
		assertListSorted(list, naturalComparator());
	}

	@Nonnull
	public static List<Integer> generateUniqueIntList(int size) {
		final List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < size; i++) {
			list.add(i);
		}
		java.util.Collections.shuffle(list);
		return list;
	}

	@Nonnull
	public static List<Integer> generateIntListWithCollisions(int size, int maxValue) {
		final List<Integer> list = new ArrayList<Integer>();
		final Random random = new Random(new Date().getTime());
		for (int i = 0; i < size; i++) {
			list.add(random.nextInt(maxValue));
		}
		java.util.Collections.shuffle(list);
		return list;
	}

	public static <T> void assertListSorted(@Nonnull List<T> list, @Nonnull Comparator<? super T> comparator) {
		for (int i = 0; i < list.size(); i++) {
			if (i > 0) {
				final T previous = list.get(i - 1);
				final T current = list.get(i);
				assertTrue(comparator.compare(previous, current) <= 0);
			}
		}
	}
}
