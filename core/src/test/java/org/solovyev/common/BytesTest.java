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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org/java/jcl
 */

package org.solovyev.common;

import org.junit.Assert;
import org.junit.Test;
import org.solovyev.common.text.HexString;
import org.solovyev.common.text.Strings;

public class BytesTest {

	@Test
	public void testHexToBytes() throws Exception {
		final String test = "test";
		final HexString testHex = HexString.fromString(test);

		Assert.assertTrue(Objects.areEqual(test.getBytes(), testHex.getOriginal().getBytes(), ByteArrayEqualizer.getInstance()));
		for (int i = 0; i < 1000; i++) {
			final String text = Strings.generateRandomString(100);
			Assert.assertTrue(Objects.areEqual(Bytes.hexToBytes(Bytes.toHex(text)), text.getBytes("UTF-8"), ByteArrayEqualizer.getInstance()));
		}
	}

}
