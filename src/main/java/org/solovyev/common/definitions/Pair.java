package org.solovyev.common.definitions;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: Oct 24, 2009
 * Time: 12:35:34 AM
 */
public class Pair<K, V> {

    private K first;
    private V second;

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

	@NotNull
	public static <F, S> Pair<F, S> create(F first, S second) {
		return new Pair<F,S>(first, second);
	}
}
