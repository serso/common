package org.solovyev.tasks;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * User: serso
 * Date: 4/3/13
 * Time: 9:31 PM
 */
final class TaskServiceImpl implements TaskService {

    /*
    **********************************************************************
    *
    *                           CONSTANTS
    *
    **********************************************************************
    */

    private static final int DEFAULT_THREAD_COUNT = 10;

    /*
    **********************************************************************
    *
    *                           FIELDS
    *
    **********************************************************************
    */

    @GuardedBy("tasks")
    @Nonnull
    private final Map<String, ListenableFutureTask> tasks = new HashMap<String, ListenableFutureTask>();

    @Nonnull
    private final ExecutorService executor;

    /*
    **********************************************************************
    *
    *                           CONSTRUCTORS
    *
    **********************************************************************
    */

    TaskServiceImpl() {
        this(DEFAULT_THREAD_COUNT);
    }

    TaskServiceImpl(int threadCount) {
        this(Executors.newFixedThreadPool(threadCount));
    }

    TaskServiceImpl(@Nonnull ExecutorService executor) {
        this.executor = executor;
    }

    /*
    **********************************************************************
    *
    *                           METHODS
    *
    **********************************************************************
    */

    @Override
    public <T> void tryRun(@Nonnull String taskName, @Nonnull Callable<T> task) throws TaskIsAlreadyRunningException {
        tryRun(taskName, task, null);
    }

    @Override
    public boolean isRunning(@Nonnull String taskName) {
        synchronized (tasks) {
            final ListenableFutureTask<?> task = tasks.get(taskName);
            if (task != null && !task.isDone()) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public <T> void tryAddTaskListener(@Nonnull String taskName, @Nonnull FutureCallback<T> taskListener) throws NoSuchTaskException, TaskFinishedException {
        synchronized (tasks) {
            final ListenableFutureTask<T> task = tasks.get(taskName);
            if (task == null) {
                throw new NoSuchTaskException(taskName);
            } else {
                if (task.isDone()) {
                    throw new TaskFinishedException(taskName);
                } else {
                    Futures.addCallback(task, taskListener);
                }
            }
        }
    }


    @Override
    public <T> void tryRun(@Nonnull String taskName, @Nonnull Callable<T> callable, @Nullable final FutureCallback<T> taskListener) throws TaskIsAlreadyRunningException {
        final ListenableFutureTask<T> task = ListenableFutureTask.create(callable);
        synchronized (tasks) {
            final ListenableFutureTask<T> oldTask = tasks.get(taskName);
            if (oldTask == null || oldTask.isDone()) {
                tasks.put(taskName, task);
                if (taskListener != null) {
                    Futures.addCallback(task, taskListener);
                }
                executor.execute(task);
            } else {
                throw new TaskIsAlreadyRunningException(taskName);
            }
        }
    }

    @Override
    public <T> void tryRun(@Nonnull String taskName, @Nonnull Task<T> task) throws TaskIsAlreadyRunningException {
        tryRun(taskName, task, task);
    }

    @Override
    public <T> void tryRun(@Nonnull NamedTask<T> task) throws TaskIsAlreadyRunningException {
        tryRun(task.getName(), task);
    }

    @Override
    public <T> void run(@Nonnull String taskName, @Nonnull Callable<T> task) {
        run(taskName, task, null);
    }

    @Override
    public <T> void run(@Nonnull String taskName, @Nonnull Callable<T> callable, @Nullable FutureCallback<T> taskListener) {
        final ListenableFutureTask<T> task = ListenableFutureTask.create(callable);
        synchronized (tasks) {
            final ListenableFutureTask<T> oldTask = tasks.get(taskName);
            if ( oldTask != null && !oldTask.isDone() ) {
                oldTask.cancel(false);
            }
            tasks.put(taskName, task);
            if (taskListener != null) {
                Futures.addCallback(task, taskListener);
            }
            executor.execute(task);
        }
    }

    @Override
    public <T> void run(@Nonnull String taskName, @Nonnull Task<T> task) {
        run(taskName, task, task);
    }

    @Override
    public <T> void run(@Nonnull NamedTask<T> task) {
        run(task.getName(), task);
    }

    @Override
    public void tryCancel(@Nonnull String taskName) throws NoSuchTaskException, TaskFinishedException {
        synchronized (tasks) {
            final ListenableFutureTask<?> task = tasks.get(taskName);
            if (task == null) {
                throw new NoSuchTaskException(taskName);
            } else {
                if (task.isDone()) {
                    throw new TaskFinishedException(taskName);
                } else {
                    task.cancel(false);
                }
            }
        }
    }

    @Override
    public boolean cancel(@Nonnull String taskName) {
        try {
            tryCancel(taskName);
            return true;
        } catch (NoSuchTaskException e) {
            return false;
        } catch (TaskFinishedException e) {
            return false;
        }
    }

    @Override
    public boolean isDone(@Nonnull String taskName) {
        synchronized (tasks) {
            final ListenableFutureTask<?> task = tasks.get(taskName);
            if (task != null && task.isDone()) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public <T> boolean addTaskListener(@Nonnull String taskName, @Nonnull FutureCallback<T> taskListener) {
        try {
            tryAddTaskListener(taskName, taskListener);
            return true;
        } catch (NoSuchTaskException e) {
            return false;
        } catch (TaskFinishedException e) {
            return false;
        }
    }
}
