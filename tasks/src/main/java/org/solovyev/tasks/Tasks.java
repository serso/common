package org.solovyev.tasks;

import javax.annotation.Nonnull;
import java.util.concurrent.ExecutorService;

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
     *
     * @param executorService executor service on which background tasks will be executed
     * @return new default task service with custom <var>executorService</var>
     */
    @Nonnull
    public static TaskService newTaskService(@Nonnull ExecutorService executorService) {
        return new TaskServiceImpl(executorService);
    }
}
