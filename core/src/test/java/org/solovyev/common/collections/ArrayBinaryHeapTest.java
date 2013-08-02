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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org/java/jcl
 */

package org.solovyev.common.collections;

import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Nonnull;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.solovyev.common.collections.ArrayBinaryHeap.heapify;
import static org.solovyev.common.collections.ArrayBinaryHeap.newArrayBinaryHeapWithHeight;
import static org.solovyev.common.collections.CollectionsTest.generateIntList;

/**
 * User: serso
 * Date: 8/3/13
 * Time: 1:53 AM
 */
public class ArrayBinaryHeapTest {

	@Test
	public void testShouldHaveCorrectSize() throws Exception {
		assertEquals(1, newIntArrayBinaryHeap(0).getSize());
		assertEquals(3, newIntArrayBinaryHeap(1).getSize());
		assertEquals(7, newIntArrayBinaryHeap(2).getSize());
		assertEquals(15, newIntArrayBinaryHeap(3).getSize());
	}

	@Nonnull
	private static ArrayBinaryHeap<Integer> newIntArrayBinaryHeap(int height) {
		return newArrayBinaryHeapWithHeight(height);
	}

	@Test
	public void testShouldCreateHeap() throws Exception {
		ArrayBinaryHeap<Integer> heap = newIntArrayBinaryHeap(2);

		heap.add(6);
		heap.add(2);
		heap.add(9);
		heap.add(11);
		heap.add(0);
		heap.add(4);
		heap.add(11);

		assertHeapIsCorrect(heap);
	}

	@Test
	public void testHeapifyShouldWork() throws Exception {
		final List<Integer> values = generateIntList(100);
		assertHeapIsCorrect(heapify(values.toArray(new Integer[100])));
	}

	private void assertHeapIsCorrect(@Nonnull ArrayBinaryHeap<Integer> heap) {
		assertHeapIsCorrect(heap, heap.getRootIndex());
	}

	@Test
	public void testShouldCreateHeapForArray100() throws Exception {
		ArrayBinaryHeap<Integer> heap = newIntArrayBinaryHeap(10);

		for (Integer value : generateIntList(heap.getSize())) {
			heap.add(value);
		}

		assertHeapIsCorrect(heap);
	}

	private void assertHeapIsCorrect(@Nonnull ArrayBinaryHeap<Integer> heap, int parentIndex) {
		final Integer parent = heap.get(parentIndex);
		final int leftChildIndex = heap.getLeftChildIndex(parentIndex);
		final int rightChildIndex = heap.getRightChildIndex(parentIndex);

		if (heap.isValidIndex(leftChildIndex)) {
			final Integer leftChild = heap.get(leftChildIndex);
			Assert.assertTrue(heap.getComparator().compare(leftChild, parent) <= 0);
			assertHeapIsCorrect(heap, leftChildIndex);
		}

		if (heap.isValidIndex(rightChildIndex)) {
			final Integer rightChild = heap.get(rightChildIndex);
			Assert.assertTrue(heap.getComparator().compare(rightChild, parent) <= 0);
			assertHeapIsCorrect(heap, rightChildIndex);
		}
	}
}
