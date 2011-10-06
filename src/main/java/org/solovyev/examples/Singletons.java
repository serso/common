package org.solovyev.examples;

import java.util.Date;

/**
 * User: serso
 * Date: 10/6/11
 * Time: 8:48 PM
 */
class Singletons {

	// not intended for instantiation
	private Singletons() {
		throw new AssertionError();
	}


	/**
	 * Singleton example
	 */
	private static enum EnumSingleton {

		instance;

		private final Date date;

		private EnumSingleton() {
			date = new Date();
		}

		public Date getDate() {
			return date;
		}
	}

	private static class SimpleSingleton {

		private static SimpleSingleton simpleSingleton;

		public synchronized SimpleSingleton getInstance () {
			if (simpleSingleton == null) {
				simpleSingleton = new SimpleSingleton();
			}

			return simpleSingleton;
		}
	}
}
