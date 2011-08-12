package org.solovyev.common.definitions;

import org.apache.log4j.Logger;

import java.io.Serializable;

/**
 * User: serso
 * Date: Oct 15, 2009
 * Time: 12:02:52 AM
 */
public class Identity<T extends Serializable> implements Identifiable<T>, Cloneable, Serializable {

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
    @SuppressWarnings("unchecked")
    public Identifiable<T> clone() {
        Identifiable<T> clone = null;
        try {
            clone = (Identifiable<T>)super.clone();
        } catch (CloneNotSupportedException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return clone;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Identity identity = (Identity) o;

		if (id != null ? !id.equals(identity.id) : identity.id != null) return false;

		return true;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Identity");
		sb.append("{id=").append(id);
		sb.append('}');
		sb.append('\'').append("super class:").append('\'').append(super.toString());
		return sb.toString();
	}
}
