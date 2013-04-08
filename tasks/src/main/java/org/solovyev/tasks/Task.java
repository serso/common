package org.solovyev.tasks;

import com.google.common.util.concurrent.FutureCallback;

import java.util.concurrent.Callable;

/**
 * User: serso
 * Date: 4/8/13
 * Time: 8:16 PM
 */
public interface Task<V> extends Callable<V>, FutureCallback<V> {
}
