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
import java.util.Comparator;

import static org.solovyev.common.collections.ArrayBinaryHeap.heapSort;

/**
 * User: serso
 * Date: 8/3/13
 * Time: 11:53 PM
 */
class HeapSort<T> implements ArraySort<T> {

	@Override
	public void sort(@Nonnull T[] a, @Nonnull Comparator<? super T> c) {
		heapSort(a, c);
	}
}
