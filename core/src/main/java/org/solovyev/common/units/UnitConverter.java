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

package org.solovyev.common.units;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 4/21/12
 * Time: 7:53 PM
 */
public interface UnitConverter<T> {

    boolean isSupported(@NotNull UnitType<?> from, @NotNull UnitType<T> to);

    @NotNull
    Unit<T> convert(@NotNull Unit<?> from, @NotNull UnitType<T> toType);

    public static class Dummy implements UnitConverter<Object> {

        @NotNull
        private static final Dummy instance = new Dummy();

        @NotNull
        public static <T> UnitConverter<T> getInstance() {
            return (UnitConverter<T>)instance;
        }

        private Dummy() {
        }

        @Override
        public boolean isSupported(@NotNull UnitType<?> from, @NotNull UnitType<Object> to) {
            return false;
        }

        @NotNull
        @Override
        public Unit<Object> convert(@NotNull Unit<?> from, @NotNull UnitType<Object> toType) {
            throw new IllegalArgumentException();
        }
    }

}
