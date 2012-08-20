package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 8/20/12
 * Time: 8:24 PM
 */
public class AndroidAesSecurityService implements SecurityService {

    @NotNull
    private final Cipherer cipherer;

    @NotNull
    private final SecretKeyProvider secretKeyProvider;

    @NotNull
    private final SaltGenerator saltGenerator;

    public AndroidAesSecurityService() {
        cipherer = CiphererImpl.newAndroidAesCipherer();
        secretKeyProvider = PbeSecretKeyProvider.newAndroidDefaultInstance();
        saltGenerator = SaltGeneratorImpl.newAndroidDefaultInstance();
    }


    @NotNull
    @Override
    public SaltGenerator getSaltGenerator() {
        return saltGenerator;
    }

    @NotNull
    @Override
    public SecretKeyProvider getSecretKeyProvider() {
        return secretKeyProvider;
    }

    @NotNull
    @Override
    public Cipherer getCipherer() {
        return cipherer;
    }
}
