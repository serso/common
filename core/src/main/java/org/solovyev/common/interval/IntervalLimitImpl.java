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

package org.solovyev.common.interval;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.JCloneable;
import org.solovyev.common.JObject;
import org.solovyev.common.JObjects;

/**
 * User: serso
 * Date: 8/9/12
 * Time: 12:14 PM
 */
public class IntervalLimitImpl<T extends Comparable<T>> extends JObject implements IntervalLimit<T> {

    public static enum Type {
        lowest,
        between,
        highest
    }

    @Nullable
    private T value;

    private boolean closed;

    @NotNull
    private Type type;

    private IntervalLimitImpl() {
    }

    @NotNull
    public static <T extends Comparable<T>> IntervalLimit<T> newInstance(@NotNull T value, boolean closed) {
        final IntervalLimitImpl<T> result = new IntervalLimitImpl<T>();

        result.value = value;
        result.closed = closed;
        result.type = Type.between;

        return result;
    }

    @NotNull
    public static <T extends Comparable<T>> IntervalLimit<T> newLowest() {
        return newInstance(Type.lowest);
    }

    @NotNull
    public static <T extends Comparable<T>> IntervalLimit<T> newHighest() {
        return newInstance(Type.highest);
    }

    @NotNull
    private static <T extends Comparable<T>> IntervalLimit<T> newInstance(@NotNull Type type) {
        final IntervalLimitImpl<T> result = new IntervalLimitImpl<T>();

        result.value = null;
        result.type = type;
        result.closed = false;

        return result;
    }

    @Nullable
    @Override
    public T getValue() {
        return this.value;
    }

    @Override
    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public boolean isOpened() {
        return !this.closed;
    }

    @Override
    public boolean isLowest() {
        return this.type == Type.lowest;
    }

    @Override
    public boolean isHighest() {
        return this.type == Type.highest;
    }

    @Override
    public boolean isLowerThan(@NotNull T that) {
        if (this.isLowest()) {
            return true;
        } else if (this.isHighest()) {
            return false;
        } else {
            assert this.value != null;
            return this.value.compareTo(that) < 0;
        }
    }

    @Override
    public boolean isLowerThan(@NotNull IntervalLimit<T> that) {
        if (this.isLowest()) {
            return !that.isLowest();
        } else if (this.isHighest()) {
            return false;
        } else {
            return this.compareTo(that) < 0;
        }
    }

    @Override
    public boolean isLowerOrEqualsThan(@NotNull T that) {
        if (this.isLowest()) {
            return true;
        } else if (this.isHighest()) {
            return false;
        } else {
            assert this.value != null;
            if (this.isClosed()) {
                return this.value.compareTo(that) <= 0;
            } else {
                return this.value.compareTo(that) < 0;
            }
        }
    }

    @Override
    public boolean isLowerOrEqualsThan(@NotNull IntervalLimit<T> that) {
        if (this.isLowest()) {
            return that.isLowest();
        } else if (this.isHighest()) {
            return that.isHighest();
        } else {
            if (this.isClosed()) {
                return this.compareTo(that) <= 0;
            } else {
                return this.compareTo(that) < 0;
            }
        }
    }

    @Override
    public boolean isHigherThan(@NotNull T that) {
        if (this.isHighest()) {
            return true;
        } else if (this.isLowest()) {
            return false;
        } else {
            assert this.value != null;
            return this.value.compareTo(that) > 0;
        }
    }

    @Override
    public boolean isHigherThan(@NotNull IntervalLimit<T> that) {
        if (this.isHighest()) {
            return !that.isHighest();
        } else if (this.isLowest()) {
            return false;
        } else {
            return this.compareTo(that) > 0;
        }
    }

    @Override
    public boolean isHigherOrEqualsThan(@NotNull T that) {
        if (this.isHighest()) {
            return true;
        } else if (this.isLowest()) {
            return false;
        } else {
            assert this.value != null;
            if (this.isClosed()) {
                return this.value.compareTo(that) >= 0;
            } else {
                return this.value.compareTo(that) > 0;
            }
        }
    }

    @Override
    public boolean isHigherOrEqualsThan(@NotNull IntervalLimit<T> that) {
        if (this.isHighest()) {
            return that.isHighest();
        } else if (this.isLowest()) {
            return that.isLowest();
        } else {
            if (this.isClosed()) {
                return this.compareTo(that) >= 0;
            } else {
                return this.compareTo(that) > 0;
            }
        }
    }

    @Override
    public int compareTo(@NotNull IntervalLimit<T> that) {
        if (this == that) {
            return 0;
        }

        if (this.isLowest()) {
            if (that.isLowest()) {
                return 0;
            } else {
                return -1;
            }
        } else if (this.isHighest()) {
            if (that.isHighest()) {
                return 0;
            } else {
                return 1;
            }
        }

        return JObjects.comparePreparedObjects(this.value, that.getValue());
    }

    @NotNull
    @Override
    public IntervalLimitImpl<T> clone() {
        final IntervalLimitImpl<T> clone = (IntervalLimitImpl<T>) super.clone();

        if (this.value instanceof JCloneable) {
            clone.value = (T) ((JCloneable) this.value).clone();
        }

        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntervalLimitImpl)) return false;

        IntervalLimitImpl that = (IntervalLimitImpl) o;

        if (closed != that.closed) return false;
        if (type != that.type) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (closed ? 1 : 0);
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        if (this.isLowest()) {
            return "-Inf";
        } else if (this.isHighest()) {
            return "Inf";
        } else {
            return String.valueOf(this.value);
        }
    }
}
