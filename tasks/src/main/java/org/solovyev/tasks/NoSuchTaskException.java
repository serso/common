package org.solovyev.tasks;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/3/13
 * Time: 9:48 PM
 */
public class NoSuchTaskException extends TaskException {

    public NoSuchTaskException(@Nonnull String taskName) {
        super("No such task: " + taskName);
    }
}
