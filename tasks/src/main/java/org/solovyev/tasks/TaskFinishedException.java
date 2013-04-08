package org.solovyev.tasks;


import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/3/13
 * Time: 9:55 PM
 */
public class TaskFinishedException extends TaskException {

    public TaskFinishedException(@Nonnull String taskName) {
        super("Task has already been finished: " + taskName);
    }
}
