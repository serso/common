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
import org.joda.time.DateTime;

/**
 * User: serso
 * Date: 4/29/12
 * Time: 9:53 PM
 */
public final class DateVersionedEntityImpl<I> implements DateVersionedEntity<I> {

    @Nonnull
    private VersionedEntity<I> versionedEntity;

    @Nonnull
    private DateTime creationDate;

    @Nonnull
    private DateTime modificationDate;

    private DateVersionedEntityImpl() {
    }

    @Nonnull
    public static <I> DateVersionedEntity<I> newEntity(@Nonnull I id) {
        final DateVersionedEntityImpl<I> result = new DateVersionedEntityImpl<I>();

        result.versionedEntity = new VersionedEntityImpl<I>(id);
        result.creationDate = DateTime.now();
        result.modificationDate = result.creationDate;

        return result;
    }

    @Nonnull
    public static <I> DateVersionedEntity<I> newVersion(@Nonnull DateVersionedEntity<I> dateVersionedEntity) {
        final DateVersionedEntityImpl<I> result = new DateVersionedEntityImpl<I>();

        // increase version
        result.versionedEntity = new VersionedEntityImpl<I>(dateVersionedEntity.getId(), dateVersionedEntity.getVersion() + 1);
        result.creationDate = dateVersionedEntity.getCreationDate();
        result.modificationDate = DateTime.now();

        return result;
    }

    @Nonnull
    public static <I> DateVersionedEntity<I> newInstance(@Nonnull VersionedEntity<I> versionedEntity, @Nonnull DateTime creationDate, @Nonnull DateTime modificationDate) {
        final DateVersionedEntityImpl<I> result = new DateVersionedEntityImpl<I>();

        result.versionedEntity = versionedEntity;
        result.creationDate = creationDate;
        result.modificationDate = modificationDate;

        return result;
    }

    @Nonnull
    @Override
    public DateTime getCreationDate() {
        return this.creationDate;
    }

    @Nonnull
    @Override
    public DateTime getModificationDate() {
        return this.modificationDate;
    }

    @Override
    @Nonnull
    public I getId() {
        return versionedEntity.getId();
    }

    @Override
    @Nonnull
    public Integer getVersion() {
        return versionedEntity.getVersion();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateVersionedEntityImpl)) return false;

        DateVersionedEntityImpl that = (DateVersionedEntityImpl) o;

        if (!versionedEntity.equals(that.versionedEntity)) return false;

        return true;
    }

    @Override
    public boolean equalsVersion(Object that) {
        return this.equals(that) && this.versionedEntity.equalsVersion(((DateVersionedEntityImpl) that).versionedEntity);
    }

    @Override
    public int hashCode() {
        return versionedEntity.hashCode();
    }

    @Nonnull
    @Override
    public DateVersionedEntityImpl<I> clone() {
        final DateVersionedEntityImpl<I> clone;

        try {
            //noinspection unchecked
            clone = (DateVersionedEntityImpl<I>) super.clone();

            clone.versionedEntity = this.versionedEntity.clone();

            // dates are immutable => can leave links as is

        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }

        return clone;
    }
}
