package org.solovyev.common.math.calculators;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:35 AM
 */
public class CalculatorFactory {

	private final Map<String, Calculator<?>> cache = new HashMap<String, Calculator<?>>();

	private static CalculatorFactory defaultInstance;

	public static CalculatorFactory getDefaultInstance () {

		if ( defaultInstance == null ) {
			defaultInstance = createInstance();
		}

		return defaultInstance;
	}

	private static synchronized CalculatorFactory createInstance() {
		CalculatorFactory result = defaultInstance;
		if ( defaultInstance == null ) {
			result = new CalculatorFactory();

			result.registerCalculator(new DoubleCalculator());
		}
		return result;
	}

	public <T extends Number> void registerCalculator(@NotNull Calculator<T> calculator) {
		cache.put(calculator.getObjectClass().getName(), calculator);
	}

	@NotNull
	public <T> Calculator<T> getCalculator (@NotNull Class<T> clazz) {
		//noinspection unchecked
		final Calculator<T> result = (Calculator<T>)cache.get(clazz.getName());

		if ( result == null ) {
			throw new IllegalArgumentException("Class " + clazz.getName() + " has not registered calculator!" );
		}

		return result;
	}

}
