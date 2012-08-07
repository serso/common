package org.solovyev.common.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 12/18/11
 * Time: 8:30 PM
 */
public class MathUtilsTest {

	public static final double EPS = 0.005;

	@Test
	public void testGetAngle() throws Exception {
		final Point2d axis = new Point2d(1f, 0f);

		final Point2d zero = new Point2d(0f, 0f);
		final Point2d up = new Point2d(-1f, 0f);
		final Point2d down = new Point2d(1f, 0f);
		final Point2d left = new Point2d(0f, -1f);
		final Point2d right = new Point2d(0f, 1f);
		final MutableObject<Boolean> leftPos = new MutableObject<Boolean>();
		Assert.assertEquals(180f, Math.toDegrees(MathUtils.getAngle(zero, axis, up, leftPos)), EPS);
	  	Assert.assertEquals(0f, Math.toDegrees(MathUtils.getAngle(zero, axis, down, leftPos)), EPS);
	  	Assert.assertEquals(90f, Math.toDegrees(MathUtils.getAngle(zero, axis, left, leftPos)), EPS);
		Assert.assertTrue(leftPos.getObject());
	  	Assert.assertEquals(90f, Math.toDegrees(MathUtils.getAngle(zero, axis, right, leftPos)), EPS);
		Assert.assertFalse(leftPos.getObject());
	}
}
