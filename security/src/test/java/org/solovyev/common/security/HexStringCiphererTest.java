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
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.security;

import org.junit.Test;
import org.solovyev.common.text.HexString;

import javax.crypto.SecretKey;

public class HexStringCiphererTest extends AbstractStringCiphererTest {

	@Test
	public void testEncryptDecrypt() throws Exception {
		final SecretKeyProvider secretKeyProvider = Sha1HashSecretKeyProvider.newAesInstance();
		final SecretKey sk = secretKeyProvider.getSecretKey("1234", new byte[]{0, -122, 2, 3, 6, 32, 1, 3});

		final Cipherer<HexString, String> cipherer = Security.newHexStringCipherer(ByteArrayCipherer.newNoIv("AES/ECB/PKCS5Padding", "BC"));
		final String expected = "test";

		doCipherTest(sk, cipherer, expected);
	}

	@Test
	public void testRandomEncryptDecrypt() throws Exception {
		final SecretKeyProvider secretKeyProvider = Sha1HashSecretKeyProvider.newAesInstance();
		final Cipherer<HexString, String> cipherer = Security.newHexStringCipherer(ByteArrayCipherer.newNoIv("AES/ECB/PKCS5Padding", "BC"));

		doRandomCiphererTest(secretKeyProvider, cipherer);
	}
}
