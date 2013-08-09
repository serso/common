package org.solovyev.common.collections;

import java.util.Comparator;

import org.solovyev.common.Objects;

/**
 * User: serso
 * Date: 8/1/13
 * Time: 10:04 PM
 */
final class NaturalComparator implements Comparator<Comparable> {

	NaturalComparator() {
	}

	@Override
	public int compare(Comparable o1, Comparable o2) {
		return Objects.compare(o1 ,o2);
	}
}
