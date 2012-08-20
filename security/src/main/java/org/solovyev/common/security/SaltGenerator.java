package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:13 PM
 */
public interface SaltGenerator {

    @NotNull
    String generateSalt() throws CiphererException;
}
