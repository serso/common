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

import org.solovyev.common.Converter;
import org.solovyev.common.TrivialConverter;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 2/10/13
 * Time: 12:54 PM
 */
public class TypedHashProvider<T, H> implements HashProvider<T, H> {

	@Nonnull
	private final HashProvider<byte[], byte[]> byteHashProvider;

	@Nonnull
	private final Converter<T, byte[]> elementConverter;

	@Nonnull
	private final Converter<byte[], H> hashConverter;

	private TypedHashProvider(@Nonnull HashProvider<byte[], byte[]> byteHashProvider,
							  @Nonnull Converter<T, byte[]> elementConverter,
							  @Nonnull Converter<byte[], H> hashConverter) {
		this.byteHashProvider = byteHashProvider;
		this.elementConverter = elementConverter;
		this.hashConverter = hashConverter;
	}

	@Nonnull
	public static <T, H> TypedHashProvider<T, H> newInstance(@Nonnull HashProvider<byte[], byte[]> byteHashProvider,
															 @Nonnull Converter<T, byte[]> elementConverter,
															 @Nonnull Converter<byte[], H> hashConverter) {
		return new TypedHashProvider<T, H>(byteHashProvider, elementConverter, hashConverter);
	}

	@Nonnull
	public static <T> TypedHashProvider<T, byte[]> newByteInstance(@Nonnull HashProvider<byte[], byte[]> byteHashProvider,
																   @Nonnull Converter<T, byte[]> elementConverter) {
		return new TypedHashProvider<T, byte[]>(byteHashProvider, elementConverter, TrivialConverter.<byte[]>getInstance());
	}

	@Nonnull
	public static <T> TypedHashProvider<T, byte[]> newByteHashCodeInstance(@Nonnull HashProvider<byte[], byte[]> byteHashProvider) {
		return new TypedHashProvider<T, byte[]>(byteHashProvider, HashCodeConverter.<T>getInstance(), TrivialConverter.<byte[]>getInstance());
	}

	@Nonnull
	@Override
	public H getHash(@Nonnull T object, @Nonnull byte[] salt) throws CiphererException {
		final byte[] bytes = elementConverter.convert(object);
		final byte[] hashBytes = byteHashProvider.getHash(bytes, salt);
		return hashConverter.convert(hashBytes);
	}

	@Nonnull
	public HashProvider<byte[], byte[]> getByteHashProvider() {
		return byteHashProvider;
	}
}
