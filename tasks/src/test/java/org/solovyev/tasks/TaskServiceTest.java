package org.solovyev.tasks;

import com.google.common.util.concurrent.FutureCallback;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * User: serso
 * Date: 4/8/13
 * Time: 9:56 PM
 */
public abstract class TaskServiceTest {

    private static final int TASK_COUNT = 20;
    private static final int TEST_COUNT = 200;
    private static final int THREAD_MAX_SLEEP_MS = 1000;

    @Nonnull
    private TaskService taskService;

    @Nonnull
    private Set<String> runningTasks;

    @Before
    public void setUp() {
        runningTasks = new HashSet<String>();
        taskService = newTaskService();
    }

    @Nonnull
    protected abstract TaskService newTaskService();


    @Test
    public void testTryRun() throws Exception {
        final String taskName = "Test";

        taskService.tryRun(taskName, new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(5000);
                return null;
            }
        });

        try {
            taskService.tryRun(taskName, new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return null;
                }
            });
            Assert.fail();
        } catch (TaskIsAlreadyRunningException e) {

        }

        Assert.assertFalse(taskService.isDone(taskName));
        Assert.assertTrue(taskService.isRunning(taskName));

        Assert.assertTrue(taskService.addTaskListener(taskName, new FutureCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                Assert.fail();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        }));

        Assert.assertTrue(taskService.cancel(taskName));
    }

    @Test
    public void testTryRandomRun() throws Exception {
        final Random r = new Random(new Date().getTime());
        for (int i = 0; i < TEST_COUNT; i++) {
            Thread.sleep(r.nextInt(100));
            final String taskName = "task_" + r.nextInt(TASK_COUNT);

            try {
                synchronized (runningTasks) {
                    runningTasks.add(taskName);
                }
                taskService.tryRun(taskName, new Callable<Object>() {
                            @Override
                            public Object call() throws Exception {
                                Thread.sleep(r.nextInt(THREAD_MAX_SLEEP_MS));
                                return taskName;
                            }
                        }, new FutureCallback<Object>() {
                            @Override
                            public void onSuccess(Object result) {
                                synchronized (runningTasks) {
                                    runningTasks.remove(taskName);
                                }
                                Assert.assertEquals(taskName, result);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                synchronized (runningTasks) {
                                    runningTasks.remove(taskName);
                                }
                                Assert.fail();
                            }
                        }
                );
            } catch (TaskIsAlreadyRunningException e) {
                synchronized (runningTasks) {
                    if (!runningTasks.contains(taskName)) {
                        Assert.fail();
                    }
                }
            }
        }

        Thread.sleep(5000);
        Assert.assertTrue(runningTasks.isEmpty());

    }

    @Test
    public void testRandomRun() throws Exception {
        final Random r = new Random(new Date().getTime());
        for (int i = 0; i < TEST_COUNT; i++) {
            Thread.sleep(r.nextInt(100));
            final String taskName = "task_" + r.nextInt(TASK_COUNT);

            synchronized (runningTasks) {
                runningTasks.add(taskName);
            }
            taskService.run(taskName, new Callable<Object>() {
                        @Override
                        public Object call() throws Exception {
                            Thread.sleep(r.nextInt(THREAD_MAX_SLEEP_MS));
                            return taskName;
                        }
                    }, new FutureCallback<Object>() {
                        @Override
                        public void onSuccess(Object result) {
                            synchronized (runningTasks) {
                                runningTasks.remove(taskName);
                            }
                            Assert.assertEquals(taskName, result);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            synchronized (runningTasks) {
                                runningTasks.remove(taskName);
                            }
                            // cancel allowed
                            // Assert.fail();
                        }
                    }
            );

        }

        Thread.sleep(5000);
        Assert.assertTrue(runningTasks.isEmpty());
    }
}
