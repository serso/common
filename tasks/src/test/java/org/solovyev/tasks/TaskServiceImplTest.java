package org.solovyev.tasks;

import javax.annotation.Nonnull;

public class TaskServiceImplTest extends TaskServiceTest {

	@Nonnull
	@Override
	protected TaskService newTaskService() {
		return new TaskServiceImpl();
	}
}
