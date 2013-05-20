package org.solovyev.tasks;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/8/13
 * Time: 8:18 PM
 */
public interface NamedTask<V> extends Task<V> {

	@Nonnull
	String getName();
}
