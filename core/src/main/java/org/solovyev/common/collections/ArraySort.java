package org.solovyev.common.collections;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
* User: serso
* Date: 8/1/13
* Time: 10:37 PM
*/
interface ArraySort<T> {
	void sort(@Nonnull T[] a, @Nonnull Comparator<? super T> c);
}
