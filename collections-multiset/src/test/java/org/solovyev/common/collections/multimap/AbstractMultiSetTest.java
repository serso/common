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

package org.solovyev.common.collections.multimap;

import junit.framework.Assert;
import org.junit.Test;
import org.solovyev.common.collections.multiset.ArrayListMultiSet;
import org.solovyev.common.collections.multiset.HashMapManyInstancesMultiSet;
import org.solovyev.common.collections.multiset.HashMapOneInstanceMultiSet;
import org.solovyev.common.collections.multiset.MultiSet;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Iterator;

/**
 * User: serso
 * Date: 7/8/12
 * Time: 2:30 PM
 */
public abstract class AbstractMultiSetTest {

	@Nonnull
	public abstract <E> MultiSet<E> createMultiSet();

	@Test
	public void testCount() throws Exception {
		final MultiSet<String> m = createMultiSet();

		Assert.assertEquals(0, m.count("1"));
		Assert.assertEquals(0, m.count("2"));
		Assert.assertEquals(0, m.count("3"));

		m.add("1", 2);
		Assert.assertEquals(2, m.count("1"));

		m.add("2", 22);
		Assert.assertEquals(22, m.count("2"));

		m.add("3", 222);
		Assert.assertEquals(222, m.count("3"));

		m.add("3", 1);
		Assert.assertEquals(223, m.count("3"));

		m.remove("3");
		Assert.assertEquals(222, m.count("3"));

		m.remove("3", 22);
		Assert.assertEquals(200, m.count("3"));

		m.remove("3", 200);
		Assert.assertEquals(0, m.count("3"));

		m.remove("3", 200);
		Assert.assertEquals(0, m.count("3"));

	}

	@Test
	public void testGetAll() throws Exception {
		final MultiSet<String> m = createMultiSet();

		m.add("1", 10);
		m.add("1", 10);
		m.add("1", 10);
		m.add("2", 10);
		m.add("3", 10);

		Assert.assertEquals(30, m.getAll("1").size());
		for (String el : m.getAll("1")) {
			Assert.assertEquals("1", el);
		}
		Assert.assertEquals(10, m.getAll("2").size());
		Assert.assertEquals(10, m.getAll("3").size());

		Assert.assertEquals(0, m.getAll("0").size());

	}

	@Test
	public void testToElementSet() throws Exception {
		final MultiSet<String> m = createMultiSet();

		m.add("1", 10);
		m.add("2", 10);
		m.add("3", 10);

		Assert.assertEquals(3, m.toElementSet().size());

		m.add("4", 1);
		Assert.assertEquals(4, m.toElementSet().size());

		m.remove("1", 10);
		Assert.assertEquals(3, m.toElementSet().size());
		Assert.assertFalse(m.toElementSet().contains("1"));
	}

	@Test
	public void testAdd() throws Exception {
		final MultiSet<String> m = createMultiSet();

		m.add("1", 10);
		Assert.assertEquals(10, m.size());
		m.add("2", 10);
		Assert.assertEquals(20, m.size());
		m.add("3", 10);
		Assert.assertEquals(30, m.size());

		m.add("1", 10);
		Assert.assertEquals(40, m.size());
		Assert.assertEquals(20, m.count("1"));

		m.add("1");
		m.add("2");
		m.add("3");
		m.add("4");
		Assert.assertEquals(44, m.size());
		Assert.assertEquals(21, m.count("1"));
		Assert.assertEquals(11, m.count("2"));
		Assert.assertEquals(11, m.count("3"));
		Assert.assertEquals(1, m.count("4"));
	}

	@Test
	public void testSize() {
		final MultiSet<String> m = createMultiSet();

		Assert.assertEquals(0, m.size());

		m.add("1");
		Assert.assertEquals(1, m.size());

		m.add("1");
		Assert.assertEquals(2, m.size());

		m.add("1", 8);
		Assert.assertEquals(10, m.size());

		m.add("2", 10);
		Assert.assertEquals(20, m.size());

		m.remove("2");
		Assert.assertEquals(19, m.size());

		m.clear();
		Assert.assertEquals(0, m.size());
	}

	@Test
	public void testIsEmpty() {
		final MultiSet<String> m = createMultiSet();
		Assert.assertTrue(m.isEmpty());

		m.add("1");
		Assert.assertFalse(m.isEmpty());
		m.remove("1");
		Assert.assertTrue(m.isEmpty());

		m.add("1");
		Assert.assertFalse(m.isEmpty());

		m.clear();
		Assert.assertTrue(m.isEmpty());
	}

	@Test
	public void testContains() {
		final MultiSet<String> m = createMultiSet();

		Assert.assertFalse(m.contains("1"));

		m.add("1");
		Assert.assertTrue(m.contains("1"));

		m.remove("1");
		Assert.assertFalse(m.contains("1"));

		m.add("1", 10);
		Assert.assertTrue(m.contains("1"));

		m.add("2", 10);
		Assert.assertTrue(m.contains("2"));

		m.clear();

		Assert.assertFalse(m.contains("1"));
		Assert.assertFalse(m.contains("2"));
	}

	@Test
	public void testIterator() {
		final MultiSet<String> m = createMultiSet();

		m.add("1", 10);
		m.add("2", 10);
		m.add("3", 10);
		m.add("1", 10);

		int count = 0;
		for (String el : m) {
			count++;
		}

		Assert.assertEquals(40, count);

		removeAll(m, "1");
		Assert.assertEquals(20, m.size());

		removeAll(m, "3");
		Assert.assertEquals(10, m.size());

		removeAll(m, "2");
		Assert.assertEquals(0, m.size());


	}

	private void removeAll(@Nonnull MultiSet<String> m, @Nonnull String element) {
		for (Iterator<String> it = m.iterator(); it.hasNext(); ) {
			final String s = it.next();
			if (s.equals(element)) {
				it.remove();
			}
		}
	}

	@Test
	public void testToArray() {
		final MultiSet<String> m = createMultiSet();

		testArrayLength(m, 0);

		m.add("1", 10);
		testArrayLength(m, 10);

		m.add("2", 20);
		testArrayLength(m, 30);

		m.remove("2");
		testArrayLength(m, 29);
	}

	private void testArrayLength(@Nonnull MultiSet<String> m, int expected) {
		Object[] a = m.toArray();
		Assert.assertEquals(expected, a.length);
	}

	@Test
	public void testRemove() {
		final MultiSet<String> m = createMultiSet();

		m.add("1", 10);
		m.add("2", 10);
		m.add("3", 10);
		Assert.assertEquals(30, m.size());

		m.remove("1", 1);
		Assert.assertEquals(29, m.size());
		Assert.assertEquals(9, m.count("1"));

		m.remove("1");
		m.remove("2");
		m.remove("3");
		m.remove("4");
		Assert.assertEquals(26, m.size());
		Assert.assertEquals(8, m.count("1"));
		Assert.assertEquals(9, m.count("2"));
		Assert.assertEquals(9, m.count("3"));
		Assert.assertEquals(0, m.count("4"));
	}

	@Test
	public void testContainsAll() {
		final MultiSet<String> m = createMultiSet();

		m.add("1", 10);
		m.add("2", 10);
		m.add("3", 10);

		Assert.assertTrue(m.containsAll(Arrays.asList("1", "2", "3")));
		Assert.assertTrue(m.containsAll(Arrays.asList("1")));
		Assert.assertTrue(m.containsAll(Arrays.asList("1", "2")));

		Assert.assertFalse(m.containsAll(Arrays.asList("1", "2", "4")));
		Assert.assertTrue(m.containsAll(Arrays.asList()));
	}

	@Test
	public void testAddAll() {
		final MultiSet<String> m = createMultiSet();

		m.addAll(Arrays.asList("1", "2", "3"));
		Assert.assertEquals(1, m.count("1"));
		Assert.assertEquals(1, m.count("2"));
		Assert.assertEquals(1, m.count("3"));

		m.addAll(Arrays.asList("1", "2", "3"));
		Assert.assertEquals(2, m.count("1"));
		Assert.assertEquals(2, m.count("2"));
		Assert.assertEquals(2, m.count("3"));

		m.addAll(Arrays.asList("1"));
		Assert.assertEquals(3, m.count("1"));
		Assert.assertEquals(2, m.count("2"));
		Assert.assertEquals(2, m.count("3"));
	}

	@Test
	public void testRemoveAll() {
		final MultiSet<String> m = createAndFill();

		m.removeAll(Arrays.asList("1", "2", "3"));

		Assert.assertEquals(2, m.count("1"));
		Assert.assertEquals(2, m.count("2"));
		Assert.assertEquals(2, m.count("3"));

		m.removeAll(Arrays.asList("1", "2"));
		Assert.assertEquals(1, m.count("1"));
		Assert.assertEquals(1, m.count("2"));
		Assert.assertEquals(2, m.count("3"));

		m.removeAll(Arrays.asList("1"));
		Assert.assertEquals(0, m.count("1"));
		Assert.assertEquals(1, m.count("2"));
		Assert.assertEquals(2, m.count("3"));
	}

	@Test
	public void testRetainAll() {
		final MultiSet<String> m = createAndFill();

		m.retainAll(Arrays.asList("1", "2", "3"));

		Assert.assertEquals(3, m.count("1"));
		Assert.assertEquals(3, m.count("2"));
		Assert.assertEquals(3, m.count("3"));

		m.retainAll(Arrays.asList("1", "2"));

		Assert.assertEquals(3, m.count("1"));
		Assert.assertEquals(3, m.count("2"));
		Assert.assertEquals(2, m.count("3"));

		m.retainAll(Arrays.asList("1"));

		Assert.assertEquals(3, m.count("1"));
		Assert.assertEquals(2, m.count("2"));
		Assert.assertEquals(1, m.count("3"));

		m.retainAll(Arrays.asList("4"));

		Assert.assertEquals(2, m.count("1"));
		Assert.assertEquals(1, m.count("2"));
		Assert.assertEquals(0, m.count("3"));

		m.retainAll(Arrays.asList("5"));

		Assert.assertEquals(1, m.count("1"));
		Assert.assertEquals(0, m.count("2"));
		Assert.assertEquals(0, m.count("3"));
	}

	@Nonnull
	private MultiSet<String> createAndFill() {
		final MultiSet<String> m = createMultiSet();

		fillMultiSet(m);

		Assert.assertEquals(3, m.count("1"));
		Assert.assertEquals(3, m.count("2"));
		Assert.assertEquals(3, m.count("3"));

		return m;
	}

	@Nonnull
	private MultiSet<String> fillMultiSet(@Nonnull MultiSet<String> m) {
		m.addAll(Arrays.asList("1", "2", "3"));
		m.addAll(Arrays.asList("1", "2", "3"));
		m.addAll(Arrays.asList("1", "2", "3"));

		return m;
	}

	@Test
	public void testClear() {
		final MultiSet<String> m = createAndFill();

		m.clear();
		Assert.assertTrue(m.isEmpty());
	}

	@Test
	public void testEquals() {
		final MultiSet<String> m = createAndFill();

		final MultiSet<String> m1 = fillMultiSet(ArrayListMultiSet.<String>newInstance());
		final MultiSet<String> m2 = fillMultiSet(HashMapManyInstancesMultiSet.<String>newInstance());
		final MultiSet<String> m3 = fillMultiSet(HashMapOneInstanceMultiSet.<String>newInstance());

		final MultiSet<Integer> m4 = ArrayListMultiSet.newInstance();
		m4.add(1, 3);
		m4.add(2, 3);
		m4.add(3, 3);

		final MultiSet<String> m5 = fillMultiSet(HashMapOneInstanceMultiSet.<String>newInstance());
		m5.remove("3", 1);
		m5.add("4", 1);

		final MultiSet<String> m6 = fillMultiSet(HashMapOneInstanceMultiSet.<String>newInstance());
		m6.remove("3", 100);
		m6.add("4", 10);


		Assert.assertEquals(m1, m);
		Assert.assertEquals(m2, m);
		Assert.assertEquals(m3, m);
		Assert.assertFalse(m.equals(m4));
		Assert.assertFalse(m.equals(m5));
		Assert.assertFalse(m.equals(m6));
	}

	@Test
	public void testHashCode() {
		final MultiSet<String> m = createAndFill();

		final MultiSet<String> m1 = fillMultiSet(ArrayListMultiSet.<String>newInstance());
		final MultiSet<String> m2 = fillMultiSet(HashMapManyInstancesMultiSet.<String>newInstance());
		final MultiSet<String> m3 = fillMultiSet(HashMapOneInstanceMultiSet.<String>newInstance());

		Assert.assertEquals(m1.hashCode(), m.hashCode());
		Assert.assertEquals(m2.hashCode(), m.hashCode());
		Assert.assertEquals(m3.hashCode(), m.hashCode());

		final MultiSet<Integer> i1 = HashMapManyInstancesMultiSet.newInstance();
		i1.add(1);
		i1.add(2);
		i1.add(3);
		i1.hashCode();

		final MultiSet<Integer> i2 = HashMapManyInstancesMultiSet.newInstance();
		i2.add(2);
		i2.add(1);
		i2.add(3);
		i2.hashCode();

		final MultiSet<Integer> i3 = HashMapManyInstancesMultiSet.newInstance();
		i3.add(2);
		i3.add(3);
		i3.add(1);
		i3.hashCode();


	}
}
