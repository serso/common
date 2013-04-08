package org.solovyev.tasks;

import com.google.common.util.concurrent.FutureCallback;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.Callable;

/**
 * User: serso
 * Date: 4/3/13
 * Time: 9:26 PM
 */

/**
 * Service allows user to run background tasks.
 * <p/>
 * <p/>
 * Implementation note: class implementing current interface must be thread safe.
 * Implementation class must guarantee task execution (delayed execution is allowed if for example too many tasks are working simultaneously).
 * Usually,
 */
@ThreadSafe
public interface TaskService {

    /**
     * Method tries to run specified <var>task</var>. Task might not be executed if task with the same <var>taskName</var> is already running (or is waiting in queue).
     * There is no guarantee that task will be finished (task might be cancelled via {@link TaskService#tryCancel(java.lang.String)}).
     * The result will be published through {@link com.google.common.util.concurrent.FutureCallback#onSuccess(V)} or {@link com.google.common.util.concurrent.FutureCallback#onFailure(java.lang.Throwable)} methods
     *
     * @param taskName     task identification
     * @param task         task to be run
     * @param taskListener result listener
     * @param <T>          type of result
     * @throws TaskIsAlreadyRunningException if task with the same <var>taskName</var> is already running
     */
    <T> void tryRun(@Nonnull String taskName, @Nonnull Callable<T> task, @Nullable FutureCallback<T> taskListener) throws TaskIsAlreadyRunningException;

    /**
     * @see TaskService#tryRun(java.lang.String, java.util.concurrent.Callable<T>, com.google.common.util.concurrent.FutureCallback<T>)
     */
    <T> void tryRun(@Nonnull String taskName, @Nonnull Callable<T> task) throws TaskIsAlreadyRunningException;

    /**
     * @see TaskService#tryRun(java.lang.String, java.util.concurrent.Callable<T>, com.google.common.util.concurrent.FutureCallback<T>)
     */
    <T> void tryRun(@Nonnull String taskName, @Nonnull Task<T> task) throws TaskIsAlreadyRunningException;

    /**
     * @see TaskService#tryRun(java.lang.String, java.util.concurrent.Callable<T>, com.google.common.util.concurrent.FutureCallback<T>)
     */
    <T> void tryRun(@Nonnull NamedTask<T> task) throws TaskIsAlreadyRunningException;

    /**
     * Method runs specified <var>task</var>. If task with the same <var>taskName</var> is already running (or is waiting in queue) it will be cancelled.
     *
     * @param taskName     task identification
     * @param task         task to be run
     * @param taskListener result listener
     * @param <T>          type of result
     */
    <T> void run(@Nonnull String taskName, @Nonnull Callable<T> task, @Nullable FutureCallback<T> taskListener);

    /**
     * @see TaskService#run(java.lang.String, java.util.concurrent.Callable<T>, com.google.common.util.concurrent.FutureCallback<T>)
     */
    <T> void run(@Nonnull String taskName, @Nonnull Callable<T> task);

    /**
     * @see TaskService#run(java.lang.String, java.util.concurrent.Callable<T>, com.google.common.util.concurrent.FutureCallback<T>)
     */
    <T> void run(@Nonnull String taskName, @Nonnull Task<T> task);

    /**
     * @see TaskService#run(java.lang.String, java.util.concurrent.Callable<T>, com.google.common.util.concurrent.FutureCallback<T>)
     */
    <T> void run(@Nonnull NamedTask<T> task);

    /**
     * Method tries to cancel task.
     *
     * @param taskName name of the task to be cancelled
     * @throws NoSuchTaskException If no task with specified <var>taskName</var> is running
     * @throws TaskFinishedException If task with specified <var>taskName</var> has already been finished
     */
    void tryCancel(@Nonnull String taskName) throws NoSuchTaskException, TaskFinishedException;

    /**
     * Method cancels task, If no task is running or task has already been finished nothing happens.
     *
     * @param taskName task name to be cancelled
     * @return true if task was canceled by this method call, false otherwise
     */
    boolean cancel(@Nonnull String taskName);

    /**
     * Method checks if task with specified <var>taskName</var> is running (or waiting in the queue if any)
     *
     * @param taskName name of the task
     *
     * @return true if task is running (or waiting in the queue if any), false either if task has not been scheduled for execution or has already been finished
     */
    boolean isRunning(@Nonnull String taskName);

    /**
     * Method checks if task with specified <var>taskName</var> has already been finished, i.e. that task already has been started and finished.
     *
     * @param taskName name of the task
     *
     * @return true if task has already been finished, false otherwise
     */
    boolean isDone(@Nonnull String taskName);

    /**
     * Method tries to add task result listener (<var>taskListener</var>) to the task with specified <var>taskName</var>.
     *
     * @param taskName name of the task
     * @param taskListener result listener
     * @param <T> type of the result
     * @throws NoSuchTaskException If no task with specified <var>taskName</var> is running
     * @throws TaskFinishedException If task with specified <var>taskName</var> has already been finished
     */
    <T> void tryAddTaskListener(@Nonnull String taskName, @Nonnull FutureCallback<T> taskListener) throws NoSuchTaskException, TaskFinishedException;

    /**
     * Method tries to add task result listener (<var>taskListener</var>) to the task with specified <var>taskName</var>.
     * If no task is running or if task has already been finished nothing happens.
     *
     * @param taskName name of the task
     * @param taskListener result listener
     * @param <T> type of the result
     *
     * @return true if result listener has been added, false otherwise
     */
    <T> boolean addTaskListener(@Nonnull String taskName, @Nonnull FutureCallback<T> taskListener);

    /**
     * Method removed task result listener from the task with specified <var>taskName</var>
     *
     * @param taskName name of the task
     * @param taskListener result listener
     * @param <T> type of the result
     *
     * @return true if listener has been removed, false otherwise
     */
    <T> boolean removeTaskListener(@Nonnull String taskName, @Nonnull FutureCallback<T> taskListener);

    void  removeAllTaskListeners(@Nonnull String taskName);
}
