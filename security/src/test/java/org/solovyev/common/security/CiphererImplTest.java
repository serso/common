package org.solovyev.common.security;

import junit.framework.Assert;
import org.junit.Test;
import org.solovyev.common.text.HexString;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Random;

/**
 * User: serso
 * Date: 9/13/12
 * Time: 8:12 PM
 */
public class CiphererImplTest {

    @Test
    public void testEncryptDecrypt() throws Exception {
        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        final Random random = new Random(new Date().getTime());

        for ( int i = 0; i < 100; i++ ) {
            // todo serso: fix
            //final Cipherer cipherer = new CiphererImpl("SHA1PRNG", 16, "PBEWithMD5AndDES", null);
            //final SecretKeyProvider secretKeyProvider = new PbeSecretKeyProvider(20, "PBEWithMD5AndDES", "PBEWithMD5AndDES", null, 128);
            //final SecretKey sk = secretKeyProvider.getSecretKey("test", HexUtils.toHex("test"));

            //final String text = StringUtils.generateRandomString(random.nextInt(256) + 1);
            //final String encryptedText = cipherer.encrypt(sk, text);
        }

        final SecretKeyProvider secretKeyProvider = AesSecretKeyProvider.newInstance();
        final SecretKey sk = secretKeyProvider.getSecretKey("1234", "4321");

        final Cipherer cipherer = CiphererImpl.newInstance(null, "AES/ECB/PKCS5Padding", "BC");
        final String expected = "test";

        final HexString encrypted = cipherer.encrypt(sk, expected);
        final String decrypted = cipherer.decrypt(sk, encrypted);

        Assert.assertEquals(expected, decrypted);
    }

    @Test
    public void testAndroidEncryptDecrypt() throws Exception {
        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        final SecretKeyProvider secretKeyProvider = Security.newAndroidSecretKeyProvider();
        final SecretKey sk = secretKeyProvider.getSecretKey("1234", "4321");

        final Cipherer cipherer = Security.newAndroidAesCipherer();
        final String expected = "test";

        final HexString encrypted = cipherer.encrypt(sk, expected);
        final String decrypted = cipherer.decrypt(sk, encrypted);

        Assert.assertEquals(expected, decrypted);
    }

}
