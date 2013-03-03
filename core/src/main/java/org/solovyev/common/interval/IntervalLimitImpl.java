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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.solovyev.common.JObject;
import org.solovyev.common.Objects;

/**
 * User: serso
 * Date: 8/9/12
 * Time: 12:14 PM
 */
class IntervalLimitImpl<T extends Comparable<T>> extends JObject implements IntervalLimit<T> {

    public static enum Type {
        lowest,
        between,
        highest
    }

    @Nullable
    private T value;

    private boolean closed;

    @Nonnull
    private Type type;

    private IntervalLimitImpl() {
    }

    @Nonnull
    public static <T extends Comparable<T>> IntervalLimit<T> newInstance(@Nonnull T value, boolean closed) {
        final IntervalLimitImpl<T> result = new IntervalLimitImpl<T>();

        result.value = value;
        result.closed = closed;
        result.type = Type.between;

        return result;
    }

    @Nonnull
    public static <T extends Comparable<T>> IntervalLimit<T> newLowest() {
        return newInstance(Type.lowest);
    }

    @Nonnull
    public static <T extends Comparable<T>> IntervalLimit<T> newHighest() {
        return newInstance(Type.highest);
    }

    @Nonnull
    private static <T extends Comparable<T>> IntervalLimit<T> newInstance(@Nonnull Type type) {
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
    public boolean isLowerThan(@Nonnull T that) {
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
    public boolean isLowerThan(@Nonnull IntervalLimit<T> that) {
        if (this.isLowest()) {
            return !that.isLowest();
        } else if (this.isHighest()) {
            return false;
        } else {
            return this.compareTo(that) < 0;
        }
    }

    @Override
    public boolean isLowerOrEqualsThan(@Nonnull T that) {
        if (this.isLowest()) {
            return true;
        } else if (this.isHighest()) {
            return false;
        } else {
            if (this.isClosed()) {
                assert this.value != null;
                return this.value.compareTo(that) <= 0;
            } else {
                assert this.value != null;
                return this.value.compareTo(that) < 0;
            }
        }
    }

    @Override
    public boolean isLowerOrEqualsThan(@Nonnull IntervalLimit<T> that) {
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
    public boolean isHigherThan(@Nonnull T that) {
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
    public boolean isHigherThan(@Nonnull IntervalLimit<T> that) {
        if (this.isHighest()) {
            return !that.isHighest();
        } else if (this.isLowest()) {
            return false;
        } else {
            return this.compareTo(that) > 0;
        }
    }

    @Override
    public boolean isHigherOrEqualsThan(@Nonnull T that) {
        if (this.isHighest()) {
            return true;
        } else if (this.isLowest()) {
            return false;
        } else {
            assert this.value != null;
            if (this.isClosed()) {
                assert this.value != null;
                return this.value.compareTo(that) >= 0;
            } else {
                assert this.value != null;
                return this.value.compareTo(that) > 0;
            }
        }
    }

    @Override
    public boolean isHigherOrEqualsThan(@Nonnull IntervalLimit<T> that) {
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
    public int compareTo(@Nonnull IntervalLimit<T> that) {
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

        return Objects.compare(this.value, that.getValue());
    }

    @Nonnull
    @Override
    public IntervalLimitImpl<T> clone() {
        return (IntervalLimitImpl<T>) super.clone();
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
