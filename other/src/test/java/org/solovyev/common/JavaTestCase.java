package org.solovyev.common;

/**
 * User: serso
 * Date: 5/18/11
 * Time: 11:23 AM
 */

import junit.framework.TestCase;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.time.Timer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Test case for java code: to show how code works under different circumstances
 */
public class JavaTestCase extends TestCase {

	public void testVarArgs() throws Exception {
	    Object o1 = new Object();
		Object o2 = new Object();
		Object o3 = null;
		Object[] objects1 = new Object[0];
		Integer[] integers1 = new Integer[0];
		Object[] objects2 = new Object[1];

		assertEquals(new Integer(3), length(o1, o2, o3));
		assertEquals(new Integer(2), length(o1, o3));
		assertEquals(new Integer(1), length(o3));
		assertEquals(new Integer(0), length());
		assertEquals(new Integer(0), length(objects1));
		assertEquals(new Integer(1), length(objects2));

		assertFalse(isNull(objects1));
		assertFalse(isNull(integers1));
		assertTrue(isNull(null));
		assertNull(length(null));
		assertEquals(new Integer(0), length());
	}

	public void testIntegers() {
		assertTrue(89999 == 89999);
		assertFalse(new Integer(89999) == new Integer(89999));
		assertFalse(new Integer(1) == new Integer(1));
		assertTrue(89999 == getInteger());
		assertTrue(getInt() == getInteger());
		assertTrue(getCastInteger() == getInteger());

/*
	must be run only in ONE THREAD test
		assertEquals(ObjectSizer.getObjectSize(new ObjectSizer.Creator() {
			@Override
			public Object create() throws Exception {
				return 1;
			}
		}), 0);


		assertNotSame(ObjectSizer.getObjectSize(new ObjectSizer.Creator() {
			@Override
			public Object create() throws Exception {
				return 1999999;
			}
		}), 0);*/
	}

	public void testDouble () {
		final double[] array = new double[100000];
		final Double[] arrayDouble = new Double[100000];

		for( int i = 0; i < 100000; i++ ) {
			array[i] = (double)i;
			arrayDouble[i] = (double) i;
		}

		//
		doDoubleTest(array, true);
		doDoubleTestDouble(arrayDouble, true);
		doDoubleTest(array, false);
		doDoubleTestDouble(arrayDouble, false);

		Timer.startTask("small double");
		for (  int i = 0; i < 100; i++ ) {
			doDoubleTest(array, i % 2 == 0);
		}
		System.out.println(Timer.getTimeInSeconds("small double"));


		Timer.startTask("big double");
		for (  int i = 0; i < 100; i++ ) {
			doDoubleTestDouble(arrayDouble, i % 2 == 0);
		}
		System.out.println(Timer.getTimeInSeconds("big double"));
	}

	private void doDoubleTestDouble(Double[] array, boolean multiplication) {
		for ( int i = 0; i < array.length; i++ ) {
			if ( multiplication ) {
				array[i]  = array[i] * array[i];
			} else {
				array[i]  = array[i] / array[i];
			}
		}
	}

	private void doDoubleTest(double[] array, boolean multiplication) {
		for ( int i = 0; i < array.length; i++ ) {
			if ( multiplication ) {
				array[i]  = array[i] * array[i];
			} else {
				array[i]  = array[i] / array[i];
			}
		}
	}

	private int getInt() {
		return 89999;
	}

	private Integer getInteger() {
		return new Integer(89999);
	}

	private int getCastInteger() {
		return new Integer(89999);
	}

	public void testStrings () {
		"test".intern();
		"testtesttesttesttesttesttesttesttesttesttesttesttest".intern();

        assertTrue("test" == "te" + "st");
        assertTrue("test" == "test");

		assertTrue(new String("testtesttesttesttesttesttesttesttesttesttesttesttest").intern() == "testtesttesttesttesttesttesttesttesttesttesttesttest");
        assertTrue(new String("test").intern() == new String("test").intern());
        assertTrue(new StringBuffer().append('t').append('e').append('s').append('t').toString().intern() == "test");
        assertTrue(new StringBuffer().append('t').append('e').append('s').append('t').toString().intern() == new String("test").intern());

		assertEquals( "test" + null, "testnull" );
    }

    public void testToArray () {
		final List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);

		Integer[] array = new Integer[5];

		printArrayInfo(list.toArray(array));
		list.clear();

		System.out.print(System.getProperty("line.separator"));

		list.add(9);
		printArrayInfo(list.toArray(array));
	}

	private void printArrayInfo(Integer[] result) {
		System.out.print("Size=" + result.length);
		System.out.print(System.getProperty("line.separator"));
		for (Integer element : result) {
			System.out.print(element);
			System.out.print(" ");
		}
	}

	public void testBooleanPriority () {
		// pretty nice example of operations priorities
		assertTrue(false && true ? true : true);
	}

	public void testArithmeticOperations () {
		// test of double, long in arithmetic operations

		long long1 = 1;
		long long2 = 2;
		double double1 = 1d;
		double double2 = 2d;
		assertEquals(long1 / double2, 0.5);
		assertEquals(long1 / double1, 1d);
		assertEquals(long1 / long2, 0);
		assertEquals((long1 / long2) * double1, 0d);
		assertEquals(((double)long1 / long2) * double1, 0.5d);
	}


	@Nullable
	private Integer length (Object... objects) {
		return objects == null ? null : objects.length;
	}

	private boolean isNull (Integer... objects) {
		return objects == null;
	}

	private boolean isNull (Object... objects) {
		return objects == null;
	}

	public void testDoublePrecision() {

		for (int i = 0; i < 100; i++) {
			if (i < 53) {
				assertFalse(new Double(Math.pow(2, i)).equals(Math.pow(2, i) + 1));
			} else {
				assertTrue(new Double(Math.pow(2, i)).equals(Math.pow(2, i) + 1));
			}
		}
	}

	public void testDate() {

		long minValue = 100000000000000L;
		long numberOfSteps = 10;

		System.out.println(new Date(-12219292800000L));

		System.out.println("######## Date < 1970 ########");
		for (long i = 0; i >= -minValue; i -= minValue / numberOfSteps) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(i));
			System.out.println("java.util.Date: " + new Date(i) + " java.util.Calendar: " + calendar);
		}

		System.out.println("######## Date > 1970 ########");
		for (long i = 0; i < minValue; i += minValue / numberOfSteps) {
			final Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(i));
			System.out.println(new Date(i));
			System.out.println("java.util.Date: " + new Date(i) + " java.util.Calendar: " + calendar);

		}
	}
}


