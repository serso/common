package org.solovyev.common.security;

import org.junit.Assert;
import org.junit.Test;
import org.solovyev.common.ByteArrayEqualizer;
import org.solovyev.common.Objects;

import javax.crypto.SecretKey;

public class Sha1HashSecretKeyProviderTest {

	@Test
	public void testGetSecretKey() throws Exception {
		SecretKeyProvider keyProvider = Sha1HashSecretKeyProvider.newAesInstance();

		final byte[] expectedBytes = {98, -112, 13, -94, -67, 30, -7, 80, -94, -111, 39, -22, 85, -90, -29, -111};

		byte[] salt = new byte[]{-42, -49, -27, -25, 108, -125, 71, -68, -128, 49, 104, -2, -122, 31, 105, -4};

		final SecretKey actual = keyProvider.getSecretKey("1234", salt);
		final SecretKey expected = keyProvider.getSecretKey("1234", salt);

		Assert.assertTrue(Objects.areEqual(actual.getEncoded(), expected.getEncoded(), ByteArrayEqualizer.getInstance()));
		Assert.assertTrue(Objects.areEqual(expectedBytes, actual.getEncoded(), ByteArrayEqualizer.getInstance()));


		// change salt
		salt[0] = (byte) (salt[0] - 1);

		final SecretKey another = keyProvider.getSecretKey("1234", salt);
		final byte[] anotherExpectedBytes = {123, -15, 62, 11, -96, 104, -11, -67, -17, -104, 47, -47, -53, 89, 85, 15};
		Assert.assertFalse(Objects.areEqual(actual.getEncoded(), another.getEncoded(), ByteArrayEqualizer.getInstance()));
		Assert.assertTrue(Objects.areEqual(anotherExpectedBytes, another.getEncoded(), ByteArrayEqualizer.getInstance()));

	}
}
