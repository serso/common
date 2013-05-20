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

import org.solovyev.common.Bytes;
import org.solovyev.common.Converter;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 2/10/13
 * Time: 1:45 PM
 */
class HashCodeConverter<T> implements Converter<T, byte[]> {

	@Nonnull
	private static final Converter<?, byte[]> instance = new HashCodeConverter<Object>();

	private HashCodeConverter() {
	}

	@Nonnull
	static <T> Converter<T, byte[]> getInstance() {
		return (Converter<T, byte[]>) instance;
	}

	@Nonnull
	@Override
	public byte[] convert(@Nonnull T t) {
		return Bytes.intToBytes(t.hashCode());
	}
}
