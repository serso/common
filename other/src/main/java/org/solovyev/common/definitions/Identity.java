package org.solovyev.common.definitions;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.compare.CompareTools;

import java.io.Serializable;

/**
 * User: serso
 * Date: Oct 15, 2009
 * Time: 12:02:52 AM
 */
abstract class Identity<T extends Serializable & Comparable<T>> implements Identifiable<T>, Cloneable, Serializable, Comparable<Identity<T>> {

    private T id = null;

	public Identity() {
	}

	public Identity(T id) {
        this.id = id;
    }

    public T getId() {
        return this.id;
    }

    public void setId(T id) {
        this.id = id;
    }

	@Override
	@SuppressWarnings({"unchecked", "CloneDoesntDeclareCloneNotSupportedException"})
	@NotNull
    public Identifiable<T> clone() {
        final Identifiable<T> clone;

		try {
            clone = (Identifiable<T>)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }

        return clone;
    }

	@Override
	public int compareTo(@NotNull Identity<T> that) {
		return CompareTools.compareIdentifiableObjects(this, that);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Identity)) return false;

		Identity that = (Identity) o;

		if (id != null ? !id.equals(that.id) : that.id != null) return false;

		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();

		sb.append("Identity");
		sb.append("{id=").append(id).append('}');

		return sb.toString();
	}
}
