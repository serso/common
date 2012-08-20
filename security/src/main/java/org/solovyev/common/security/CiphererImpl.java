package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.HexUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 7:44 PM
 */
public class CiphererImpl implements Cipherer {

    private static final String PROVIDER = "BC";
    private static final int IV_LENGTH = 16;

    private static final String RANDOM_ALGORITHM = "SHA1PRNG";
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";

    @NotNull
    private final String randomAlgorithm;

    private final int ivLength;

    @NotNull
    private final String cipherAlgorithm;

    @NotNull
    private final String provider;

    public CiphererImpl(@NotNull String randomAlgorithm,
                        int ivLength,
                        @NotNull String cipherAlgorithm,
                        @NotNull String provider) {
        this.randomAlgorithm = randomAlgorithm;
        this.ivLength = ivLength;
        this.cipherAlgorithm = cipherAlgorithm;
        this.provider = provider;
    }

    @NotNull
    public static Cipherer newAndroidAesCipherer() {
        return new CiphererImpl(RANDOM_ALGORITHM, IV_LENGTH, CIPHER_ALGORITHM, PROVIDER);
    }

    @Override
    @NotNull
    public String encrypt(@NotNull SecretKey secret, @NotNull String plainText) throws CiphererException {
        try {
            final byte[] iv = SecurityUtils.generateRandomBytes(randomAlgorithm, ivLength);
            final String ivHex = HexUtils.toHex(iv);
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            final Cipher encryptionCipher = Cipher.getInstance(cipherAlgorithm, provider);
            encryptionCipher.init(Cipher.ENCRYPT_MODE, secret, ivParameterSpec);

            final byte[] encrypted = encryptionCipher.doFinal(plainText.getBytes("UTF-8"));
            final String encryptedHex = HexUtils.toHex(encrypted);

            return ivHex + encryptedHex;
        } catch (Exception e) {
            throw new CiphererException("Unable to encrypt due to some errors!", e);
        }
    }

    @NotNull
    @Override
    public String decrypt(@NotNull SecretKey secret, @NotNull String encryptedText) throws CiphererException {
        try {
            final String ivHex = encryptedText.substring(0, ivLength * 2);
            final String encryptedHex = encryptedText.substring(ivLength * 2);
            final IvParameterSpec ivParameterSpec = new IvParameterSpec(HexUtils.toBytes(ivHex));

            final Cipher decryptionCipher = Cipher.getInstance(cipherAlgorithm, provider);
            decryptionCipher.init(Cipher.DECRYPT_MODE, secret, ivParameterSpec);

            byte[] decrypted = decryptionCipher.doFinal(HexUtils.toBytes(encryptedHex));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new CiphererException("Unable to decrypt due to some errors!", e);
        }
    }
}
