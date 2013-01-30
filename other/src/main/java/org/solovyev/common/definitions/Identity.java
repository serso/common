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

package org.solovyev.common.definitions;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.Identifiable;
import org.solovyev.common.Objects;

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
		return Objects.compareIdentifiables(this, that);
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
