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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

import static org.solovyev.common.collections.Sortings.swap;

/**
 * User: serso
 * Date: 8/3/13
 * Time: 1:32 AM
 */
final class ArrayBinaryHeap<T> {

	@Nonnull
	private final Object[] array;

	@Nonnull
	private final Comparator<T> comparator;

	private int lastIndex = -1;

	private int size;

	private ArrayBinaryHeap(int height, @Nonnull Comparator<T> comparator) {
		this.comparator = comparator;
		this.size = ((int) Math.pow(2, height + 1)) - 1;

		// 0-based array
		this.array = new Object[size];
	}

	ArrayBinaryHeap(@Nonnull T[] array, @Nonnull Comparator<T> comparator) {
		this.array = array;
		this.comparator = comparator;
		this.size = array.length;
		this.lastIndex = size;
	}

	@Nonnull
	static <T extends Comparable<T>> ArrayBinaryHeap<T> heapify(@Nonnull T[] array) {
		return heapify(array, Collections.<T>naturalComparator());
	}

	@Nonnull
	static <T> ArrayBinaryHeap<T> heapify(@Nonnull T[] array, @Nonnull Comparator<T> c) {
		final ArrayBinaryHeap<T> heap = new ArrayBinaryHeap<T>(array, c);
		for (int i = heap.size / 2; i >= 0; i--) {
			heap.bubbleDown(i);
		}
		return heap;
	}

	@Nonnull
	static <T> ArrayBinaryHeap<T> newArrayBinaryHeapWithHeight(int height, @Nonnull Comparator<T> c) {
		return new ArrayBinaryHeap<T>(height, c);
	}

	@Nonnull
	static <T extends Comparable<T>> ArrayBinaryHeap<T> newArrayBinaryHeapWithHeight(int height) {
		return newArrayBinaryHeapWithHeight(height, Collections.<T>naturalComparator());
	}

	void add(@Nonnull T value) {
		array[lastIndex + 1] = value;
		bubbleUp(lastIndex + 1);
		lastIndex++;
	}

	void bubbleDown(int currentIndex) {
		final T current = getOrNull(currentIndex);

		if (current != null) {
			final int leftIndex = getLeftChildIndex(currentIndex);
			final T left = getOrNull(leftIndex);

			final int rightIndex = getRightChildIndex(currentIndex);
			final T right = getOrNull(rightIndex);

			int largestIndex = currentIndex;
			T largest = current;

			if (left != null && comparator.compare(left, current) > 0) {
				largestIndex = leftIndex;
				largest = left;
			}

			if (right != null && comparator.compare(right, largest) > 0) {
				largestIndex = rightIndex;
				largest = right;
			}

			if (largestIndex != currentIndex) {
				swap(array, largestIndex, currentIndex);
				bubbleDown(largestIndex);
			}
		}
	}

	@Nullable
	private T getOrNull(int index) {
		final T value;
		if (isValidIndex(index)) {
			value = get(index);
		} else {
			value = null;
		}
		return value;
	}

	private void bubbleUp(int currentIndex) {
		while (true) {
			final int parentIndex = getParentIndex(currentIndex);
			if (isValidIndex(parentIndex)) {
				final T parent = get(parentIndex);
				final T current = get(currentIndex);
				if (comparator.compare(parent, current) < 0) {
					swap(array, parentIndex, currentIndex);
					currentIndex = parentIndex;
				} else {
					// sorted
					break;
				}
			} else {
				// no parent
				break;
			}
		}
	}

	private int getParentIndex(int position) {
		return (position + 1) / 2 - 1;
	}

	@Nullable
	private T getParent(int position) {
		final int index = getParentIndex(position);
		if (isValidIndex(index)) {
			return get(index);
		} else {
			return null;
		}

	}

	T get(int index) {
		return (T) array[index];
	}

	int getLeftChildIndex(int parent) {
		return 2 * (parent + 1) - 1;
	}

	int getRightChildIndex(int parent) {
		return 2 * (parent + 1);
	}

	@Nonnull
	public T getRoot() {
		return get(getRootIndex());
	}

	public int getRootIndex() {
		return 0;
	}

	public int getSize() {
		return size;
	}

	@Nonnull
	Comparator<T> getComparator() {
		return comparator;
	}

	public boolean isValidIndex(int index) {
		return index >= getRootIndex() && index < size;
	}

	private void decreaseSize() {
		size--;
	}

	public static <T> void heapSort(@Nonnull T[] a, @Nonnull Comparator<? super T> c) {
		final ArrayBinaryHeap<? super T> heap = heapify(a, c);
		final int rootIndex = heap.getRootIndex();

		for (int i = a.length - 1; i >= 1; i--) {
			swap(a, i, rootIndex);
			heap.decreaseSize();
			heap.bubbleDown(rootIndex);
		}
	}
}
