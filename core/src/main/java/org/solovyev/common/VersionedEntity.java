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

package org.solovyev.common;

import javax.annotation.Nonnull;

import java.io.Serializable;

/**
 * User: serso
 * Date: 4/29/12
 * Time: 9:45 PM
 */

/**
 * Entity which stores version identifier
 * @param <I>
 */
public interface VersionedEntity<I> extends Serializable, JCloneable<VersionedEntity<I>> {

    @Nonnull
    public static final Integer FIRST_VERSION = 1;

    /**
     * Identifier of the entity (unique for all entities of the same type)
     *
     * @return entity identifier
     */
    @Nonnull
    I getId();

    /**
     * Sequential number of version starting from {@link VersionedEntity#FIRST_VERSION}
     *
     * @return version number
     */
    @Nonnull
    Integer getVersion();

    // check if two entities are the same == this.id equals to that.id
    boolean equals(Object that);

    // check if two entities are the same version == this.id equals to that.id && this.version equals to that.version
    boolean equalsVersion(Object that);
}
