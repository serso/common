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

package org.solovyev.common.security;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.Converter;
import org.solovyev.common.TrivialConverter;

/**
 * User: serso
 * Date: 2/10/13
 * Time: 12:54 PM
 */
public class TypedHashProvider<T, H> implements HashProvider<T, H> {

    @NotNull
    private final HashProvider<byte[], byte[]> hashProvider;

    @NotNull
    private final Converter<T, byte[]> elementConverter;

    @NotNull
    private final Converter<byte[], H> hashConverter;

    private TypedHashProvider(@NotNull HashProvider<byte[], byte[]> hashProvider,
                              @NotNull Converter<T, byte[]> elementConverter,
                              @NotNull Converter<byte[], H> hashConverter) {
        this.hashProvider = hashProvider;
        this.elementConverter = elementConverter;
        this.hashConverter = hashConverter;
    }

    @NotNull
    public static <T, H> TypedHashProvider<T, H> newInstance(@NotNull HashProvider<byte[], byte[]> hashProvider,
                                                              @NotNull Converter<T, byte[]> elementConverter,
                                                              @NotNull Converter<byte[], H> hashConverter) {
        return new TypedHashProvider<T, H>(hashProvider, elementConverter, hashConverter);
    }

    @NotNull
    public static <T> TypedHashProvider<T, byte[]> newByteInstance(@NotNull HashProvider<byte[], byte[]> hashProvider,
                                                                       @NotNull Converter<T, byte[]> elementConverter) {
        return new TypedHashProvider<T, byte[]>(hashProvider, elementConverter, TrivialConverter.<byte[]>getInstance());
    }

    @NotNull
    public static <T> TypedHashProvider<T, byte[]> newByteHashCodeInstance(@NotNull HashProvider<byte[], byte[]> hashProvider) {
        return new TypedHashProvider<T, byte[]>(hashProvider, HashCodeConverter.<T>getInstance(), TrivialConverter.<byte[]>getInstance());
    }

    @NotNull
    @Override
    public H getHash(@NotNull T object, @NotNull byte[] salt) throws CiphererException {
        final byte[] bytes = elementConverter.convert(object);
        final byte[] hashBytes = hashProvider.getHash(bytes, salt);
        return hashConverter.convert(hashBytes);
    }
}
