package org.solovyev.tasks;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/3/13
 * Time: 9:37 PM
 */

/**
 * Thrown when task start requested for task which is already running
 */
public class TaskIsAlreadyRunningException extends TaskException {

	public TaskIsAlreadyRunningException(@Nonnull String taskName) {
		super("Task is already running: " + taskName);
	}
}
