package org.solovyev.common;

import javax.annotation.Nonnull;

public final class Entities {

	private Entities() {
		throw new AssertionError();
	}

	public static <I> VersionedEntity<I> newEntity(@Nonnull I id) {
		return new VersionedEntityImpl<I>(id);
	}

	public static <I> VersionedEntity<I> newEntity(@Nonnull I id, @Nonnull Integer version) {
		return new VersionedEntityImpl<I>(id, version);
	}

	public static <I> VersionedEntity<I> newEntityCopy(@Nonnull VersionedEntity<I> entity) {
		return new VersionedEntityImpl<I>(entity);
	}

	public static <I> VersionedEntity<I> newEntityVersion(@Nonnull VersionedEntity<I> entity) {
		return VersionedEntityImpl.newVersion(entity);
	}
}
