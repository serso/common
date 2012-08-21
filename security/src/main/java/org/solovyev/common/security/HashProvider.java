package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:17 PM
 */
public interface HashProvider {

    @NotNull
    String getHash(@NotNull String text, @NotNull String salt) throws CiphererException;
}
