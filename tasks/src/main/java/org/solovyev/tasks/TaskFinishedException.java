package org.solovyev.tasks;


import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/3/13
 * Time: 9:55 PM
 */

/**
 * Thrown when operation is requested on the task which has already been finished
 */
public class TaskFinishedException extends TaskException {

	public TaskFinishedException(@Nonnull String taskName) {
		super("Task has already been finished: " + taskName);
	}
}
