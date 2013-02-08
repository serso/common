package org.solovyev.common.security;

import junit.framework.Assert;
import org.junit.Test;
import org.solovyev.common.ByteArrayEqualizer;
import org.solovyev.common.Objects;

import javax.crypto.SecretKey;

public class AesSha1HashSecretKeyProviderTest {

    @Test
    public void testGetSecretKey() throws Exception {
        SecretKeyProvider keyProvider = AesSha1HashSecretKeyProvider.newInstance();

        final byte[] expectedBytes = {-42, -49, -27, -25, 108, -125, 71, -68, -128, 49, 104, -2, -122, 31, 105, -4};
        final SecretKey actual = keyProvider.getSecretKey("1234", "4321");
        final SecretKey expected = keyProvider.getSecretKey("1234", "4321");

        Assert.assertTrue(Objects.areEqual(actual.getEncoded(), expected.getEncoded(), ByteArrayEqualizer.getInstance()));
        Assert.assertTrue(Objects.areEqual(expectedBytes, actual.getEncoded(), ByteArrayEqualizer.getInstance()));

        final SecretKey another = keyProvider.getSecretKey("1234", "43212");
        final byte[] anotherExpectedBytes = {-128, -77, -72, 4, -17, -97, -13, -95, -14, 42, -96, -119, -125, 55, -33, 18};
        Assert.assertFalse(Objects.areEqual(actual.getEncoded(), another.getEncoded(), ByteArrayEqualizer.getInstance()));
        Assert.assertTrue(Objects.areEqual(anotherExpectedBytes, another.getEncoded(), ByteArrayEqualizer.getInstance()));

    }
}
