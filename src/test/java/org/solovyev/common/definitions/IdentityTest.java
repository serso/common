/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.definitions;
/**
 * User: serso
 * Date: May 1, 2010
 * Time: 12:16:12 AM
 */

import junit.framework.*;

public class IdentityTest extends TestCase {
	Identity identity;

	public void testToString() throws Exception {
		Identity<Integer> id = new Identity<Integer>(3434);

		System.out.print(id.toString());
		assertTrue(id.toString().length() > 0);
	}
}