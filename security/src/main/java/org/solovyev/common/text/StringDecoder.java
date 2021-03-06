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
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.text;

import org.solovyev.common.Charsets;
import org.solovyev.common.Converter;

import javax.annotation.Nonnull;

/**
 * Converter from String to bytes.
 */
public class StringDecoder implements Converter<String, byte[]> {

	@Nonnull
	private static Converter<String, byte[]> instance = new StringDecoder();

	private StringDecoder() {
	}

	@Nonnull
	public static Converter<String, byte[]> getInstance() {
		return instance;
	}

	@Nonnull
	@Override
	public byte[] convert(@Nonnull String s) {
		return s.getBytes(Charsets.UTF_8);
	}
}
