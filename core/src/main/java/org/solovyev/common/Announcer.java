/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 10:40 AM
 */

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

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

	public void addListener(@NotNull T listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(@NotNull T listener) {
		listeners.remove(listener);
	}

	public void clear() {
		listeners.clear();
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
			throw new IllegalArgumentException("Could not invoke listener!", e);
		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();

			if (cause instanceof RuntimeException) {
				throw (RuntimeException) cause;
			} else if (cause instanceof Error) {
				throw (Error) cause;
			} else {
				throw new UnsupportedOperationException("Listener threw exception!", cause);
			}
		}
	}

	public static <T extends EventListener> Announcer<T> to(@NotNull Class<? extends T> listenerType) {
		return new Announcer<T>(listenerType);
	}
}
