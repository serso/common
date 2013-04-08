package org.solovyev.tasks;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/3/13
 * Time: 9:37 PM
 */
public class TaskIsAlreadyRunningException extends TaskException {

    public TaskIsAlreadyRunningException(@Nonnull String taskName) {
        super("Task is already running: " + taskName);
    }
}
