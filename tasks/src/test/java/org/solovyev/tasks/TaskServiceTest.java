package org.solovyev.tasks;

import com.google.common.util.concurrent.FutureCallback;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class TaskServiceTest {

	private static final int TASK_COUNT = 20;
	private static final int TEST_COUNT = 50;
	private static final int THREAD_MAX_SLEEP_MS = 300;

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
				sleep(500);
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
			fail();
		} catch (TaskIsAlreadyRunningException e) {

		}

		assertTrue(taskService.isRunning(taskName));

		assertNotNull(taskService.addTaskListener(taskName, new FutureCallback<Object>() {
			@Override
			public void onSuccess(Object result) {
				fail();
			}

			@Override
			public void onFailure(Throwable t) {

			}
		}));

		assertTrue(taskService.cancel(taskName));

		taskService.tryRun(taskName, new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				sleep(500);
				return null;
			}
		});

		final FutureCallback<Object> listener = taskService.addTaskListener(taskName, new FutureCallback<Object>() {
			@Override
			public void onSuccess(Object result) {
				fail();
			}

			@Override
			public void onFailure(Throwable t) {
				fail();
			}
		});

		assertNotNull(listener);
		taskService.removeTaskListener(taskName, listener);

		sleep(1000);
	}

	@Test
	public void testTryRandomRun() throws Exception {
		final Random r = new Random(new Date().getTime());
		for (int i = 0; i < TEST_COUNT; i++) {
			sleep(r.nextInt(50));
			final String taskName = "task_" + r.nextInt(TASK_COUNT);

			try {
				synchronized (runningTasks) {
					runningTasks.add(taskName);
				}
				taskService.tryRun(taskName, new Callable<Object>() {
							@Override
							public Object call() throws Exception {
								sleep(r.nextInt(THREAD_MAX_SLEEP_MS));
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
								fail();
							}
						}
				);
			} catch (TaskIsAlreadyRunningException e) {
				synchronized (runningTasks) {
					if (!runningTasks.contains(taskName)) {
						fail();
					}
				}
			}
		}

		sleep(1000);
		assertTrue(runningTasks.isEmpty());

	}

	@Test
	public void testRandomRun() throws Exception {
		final Random r = new Random(new Date().getTime());
		for (int i = 0; i < TEST_COUNT; i++) {
			sleep(r.nextInt(50));
			final String taskName = "task_" + r.nextInt(TASK_COUNT);

			synchronized (runningTasks) {
				runningTasks.add(taskName);
			}
			taskService.run(taskName, new Callable<Object>() {
						@Override
						public Object call() throws Exception {
							sleep(r.nextInt(THREAD_MAX_SLEEP_MS));
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

		sleep(1000);
		assertTrue(runningTasks.isEmpty());
	}
}
