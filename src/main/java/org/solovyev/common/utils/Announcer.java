package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

/**
 * User: serso
 * Date: 9/18/11
 * Time: 8:34 PM
 */
public class Announcer<T extends EventListener> {

	@NotNull
	private final T proxy;

	@NotNull
	private final List<T> listeners = new ArrayList<T>();


	public Announcer(@NotNull Class<? extends T> listenerType) {
		proxy = listenerType.cast(Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class<?>[]{listenerType},
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						announce(method, args);
						return null;
					}
				}));
	}

	public void addListener(T listener) {
		listeners.add(listener);
	}

	public void removeListener(T listener) {
		listeners.remove(listener);
	}

	public T announce() {
		return proxy;
	}

	private void announce(Method m, Object[] args) {
		try {
			for (T listener : listeners) {
				m.invoke(listener, args);
			}
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("could not invoke listener", e);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();

			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			} else if (cause instanceof Error) {
				throw (Error) cause;
			} else {
				throw new UnsupportedOperationException("listener threw exception", cause);
			}
		}
	}

	public static <T extends EventListener> Announcer<T> to(Class<? extends T> listenerType) {
		return new Announcer<T>(listenerType);
	}
}