package org.solovyev.common.security;

import junit.framework.Assert;
import org.junit.Test;
import org.solovyev.common.ByteArrayEqualizer;
import org.solovyev.common.Bytes;
import org.solovyev.common.Objects;
import org.solovyev.common.text.Strings;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Random;

/**
 * User: serso
 * Date: 9/13/12
 * Time: 8:12 PM
 */
public class ByteArrayCiphererTest {

	@Test
	public void testEncryptDecrypt() throws Exception {
		java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		final Random random = new Random(new Date().getTime());

		for (int i = 0; i < 1000; i++) {
			final SecretKeyProvider secretKeyProvider = Sha1HashSecretKeyProvider.newAesInstance();
			final SecretKey sk = secretKeyProvider.getSecretKey(Strings.generateRandomString(10), Bytes.generateRandomBytes(10));

			final Cipherer<byte[], byte[]> cipherer = ByteArrayCipherer.newNoIv("AES/ECB/PKCS5Padding", "BC");
			final byte[] expected = Strings.generateRandomString(100).getBytes("UTF-8");

			final byte[] encrypted = cipherer.encrypt(sk, expected);
			final byte[] decrypted = cipherer.decrypt(sk, encrypted);

			Assert.assertTrue(Objects.areEqual(expected, decrypted, ByteArrayEqualizer.getInstance()));
		}

		final SecretKeyProvider secretKeyProvider = Sha1HashSecretKeyProvider.newAesInstance();
		final SecretKey sk = secretKeyProvider.getSecretKey("1234", new byte[]{78, 34, -1, 2});

		final Cipherer<byte[], byte[]> cipherer = ByteArrayCipherer.newNoIv("AES/ECB/PKCS5Padding", "BC");
		final byte[] expected = new byte[]{1, 2, 3, 4};

		final byte[] encrypted = cipherer.encrypt(sk, expected);
		final byte[] decrypted = cipherer.decrypt(sk, encrypted);

		Assert.assertTrue(Objects.areEqual(expected, decrypted, ByteArrayEqualizer.getInstance()));
	}

/*    @Test
	public void testAndroidEncryptDecrypt() throws Exception {
        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        final SecretKeyProvider secretKeyProvider = Security.newAndroidSecretKeyProvider();
        final SecretKey sk = secretKeyProvider.getSecretKey("1234", "4321");

        final Cipherer<byte[], byte[]> cipherer = Security.newAndroidAesCipherer();
        final byte[] expected = "test".getBytes();

        final byte[] encrypted = cipherer.encrypt(sk, expected);
        final byte[] decrypted = cipherer.decrypt(sk, encrypted);

        Assert.assertTrue(Objects.areEqual(encrypted, decrypted, ByteArrayEqualizer.getInstance()));
    }*/

}
