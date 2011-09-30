package org.solovyev.common;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

/**
 * User: serso
 * Date: 9/30/11
 * Time: 4:34 PM
 */
public class WaiterSingleton {

	private static WaiterSingleton instance;

	@Nullable
	private Date date;

	public WaiterSingleton() {
		System.out.println(Thread.currentThread().getName() + " org.solovyev.common.WaiterSingleton.WaiterSingleton()" + instance);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		date = new Date();
	}

	@NotNull
	public static WaiterSingleton getInstance() {
		if (instance != null) {
			System.out.println(Thread.currentThread().getName() + " org.solovyev.common.WaiterSingleton.getInstance() instance != null: " + instance);
			return instance;
		} else {
			System.out.println(Thread.currentThread().getName() + " org.solovyev.common.WaiterSingleton.getInstance() instance == null: " + instance);
			return createInstance();
		}
	}

	@NotNull
	private static synchronized WaiterSingleton createInstance() {
		System.out.println(Thread.currentThread().getName() + " org.solovyev.common.WaiterSingleton.createInstance()" + instance);
		if (instance == null) {
			instance = new WaiterSingleton();
		}
		return instance;
	}

	@Nullable
	public Date getDate() {
		return date;
	}
}
