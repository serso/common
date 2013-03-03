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

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/21/12
 * Time: 8:01 PM
 */
public class UnitImpl<V> implements Unit<V> {

    @Nonnull
    private V value;

    @Nonnull
    private UnitType<V> unitType;

    private UnitImpl() {
    }

    @Nonnull
    public static <V> Unit<V> newInstance(@Nonnull V value, @Nonnull UnitType<V> unitType) {
        final UnitImpl<V> result = new UnitImpl<V>();

        result.value = value;
        result.unitType = unitType;

        return result;
    }

    @Nonnull
    @Override
    public V getValue() {
        return this.value;
    }

    @Nonnull
    @Override
    public UnitType<V> getUnitType() {
        return unitType;
    }
}
