package org.solovyev.tasks;

import javax.annotation.Nonnull;
import java.util.concurrent.Executor;

/**
 * User: serso
 * Date: 4/3/13
 * Time: 9:59 PM
 */

/**
 * Entry point for background tasks management.
 * Here you can create task service, tasks and do common operation with them.
 */
public class Tasks {

	protected Tasks() {
		throw new AssertionError();
	}

	/**
	 * @return new default task service
	 */
	@Nonnull
	public static TaskService newTaskService() {
		return new TaskServiceImpl();
	}

	/**
	 * @param threadCount number of background threads working simultaneously
	 * @return new default task service with <var>threadCount</var> background threads
	 */
	@Nonnull
	public static TaskService newTaskService(int threadCount) {
		return new TaskServiceImpl(threadCount);
	}

	/**
	 * @param executor executor service on which background tasks will be executed
	 * @return new default task service with custom <var>executor</var>
	 */
	@Nonnull
	public static TaskService newTaskService(@Nonnull Executor executor) {
		return new TaskServiceImpl(executor);
	}
}
