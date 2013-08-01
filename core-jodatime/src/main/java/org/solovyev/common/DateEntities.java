package org.solovyev.common;

import javax.annotation.Nonnull;

import org.joda.time.DateTime;

public final class DateEntities {

	private DateEntities() {
		throw new AssertionError();
	}

	public static <I> DateVersionedEntity<I> newDateEntity(@Nonnull I id) {
		return DateVersionedEntityImpl.newEntity(id);
	}

	public static <I> DateVersionedEntity<I> newDateEntity(@Nonnull I id, @Nonnull Integer version) {
		return DateVersionedEntityImpl.newInstance(id, version, DateTime.now(), DateTime.now());
	}

	public static <I> DateVersionedEntity<I> newDateEntity(@Nonnull I id, @Nonnull Integer version, @Nonnull DateTime creationDate, @Nonnull DateTime modificationDate) {
		return DateVersionedEntityImpl.newInstance(id, version, creationDate, modificationDate);
	}

	public static <I> DateVersionedEntity<I> newDateEntity(@Nonnull VersionedEntity<I> entity, @Nonnull DateTime creationDate, @Nonnull DateTime modificationDate) {
		return DateVersionedEntityImpl.newInstance(entity, creationDate, modificationDate);
	}

	public static <I> DateVersionedEntity<I> newDateEntityVersion(@Nonnull DateVersionedEntity<I> entity) {
		return DateVersionedEntityImpl.newVersion(entity);
	}
}
