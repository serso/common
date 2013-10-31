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

import org.junit.Assert;
import org.solovyev.common.Bytes;
import org.solovyev.common.text.Strings;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.Random;

public abstract class AbstractStringCiphererTest {

	protected static <E> void doRandomCiphererTest(SecretKeyProvider secretKeyProvider, Cipherer<E, String> cipherer) throws CiphererException, NoSuchProviderException, NoSuchAlgorithmException {
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		final Random r = new Random(new Date().getTime());
		for (int i = 0; i < 1000; i++) {

			final String expected = Strings.generateRandomString(r.nextInt(500) + 500);

			final SecretKey sk = secretKeyProvider.getSecretKey(Strings.generateRandomString(10), Bytes.generateRandomBytes(10));

			final E encrypted = cipherer.encrypt(sk, expected);
			final String decrypted = cipherer.decrypt(sk, encrypted);

			Assert.assertEquals(expected, decrypted);
		}
	}

	protected static <E> void doCipherTest(SecretKey sk, Cipherer<E, String> cipherer, String expected) throws CiphererException {
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		final E encrypted = cipherer.encrypt(sk, expected);
		final String decrypted = cipherer.decrypt(sk, encrypted);

		Assert.assertFalse(encrypted.equals(expected));
		Assert.assertFalse(decrypted.equals(encrypted));
		Assert.assertEquals(expected, decrypted);
	}
}
