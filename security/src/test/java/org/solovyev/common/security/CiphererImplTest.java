package org.solovyev.common.security;

import org.junit.Test;

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
        final Random random = new Random(new Date().getTime());

        for ( int i = 0; i < 100; i++ ) {
            // todo serso: fix
            //final Cipherer cipherer = new CiphererImpl("SHA1PRNG", 16, "PBEWithMD5AndDES", null);
            //final SecretKeyProvider secretKeyProvider = new PbeSecretKeyProvider(20, "PBEWithMD5AndDES", "PBEWithMD5AndDES", null, 128);
            //final SecretKey sk = secretKeyProvider.getSecretKey("test", HexUtils.toHex("test"));

            //final String text = StringUtils.generateRandomString(random.nextInt(256) + 1);
            //final String encryptedText = cipherer.encrypt(sk, text);

        }
    }

}
