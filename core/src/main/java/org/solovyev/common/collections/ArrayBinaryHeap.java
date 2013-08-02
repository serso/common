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

	private int lastIndex = 0;

	private final int size;

	private ArrayBinaryHeap(int height, @Nonnull Comparator<T> comparator) {
		this.comparator = comparator;
		this.size = ((int) Math.pow(2, height + 1)) - 1;

		// 1-based array
		this.array = new Object[size + 1];
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
		for (int i = 1; i < heap.size; i++) {
			heap.bubbleUp(i);
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

	private void bubbleUp(int currentIndex) {
		while (true) {
			final int parentIndex = getParentIndex(currentIndex);
			if (parentIndex > 0) {
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
		return position / 2;
	}

	@Nullable
	private T getParent(int position) {
		final int index = getParentIndex(position);
		if (index > 0) {
			return get(index);
		} else {
			return null;
		}

	}

	T get(int index) {
		return (T) array[index];
	}

	int getLeftChildIndex(int parent) {
		return 2 * parent;
	}

	int getRightChildIndex(int parent) {
		return 2 * parent + 1;
	}

	@Nonnull
	public T getRoot() {
		return get(getRootIndex());
	}

	public int getRootIndex() {
		return 1;
	}

	public int getSize() {
		return size;
	}

	@Nonnull
	Comparator<T> getComparator() {
		return comparator;
	}

	public boolean isValidIndex(int index) {
		return index > 0 && index < size;
	}
}
