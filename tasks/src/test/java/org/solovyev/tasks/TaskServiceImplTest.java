package org.solovyev.tasks;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/8/13
 * Time: 10:03 PM
 */
public class TaskServiceImplTest extends TaskServiceTest {

    @Nonnull
    @Override
    protected TaskService newTaskService() {
        return new TaskServiceImpl();
    }
}
