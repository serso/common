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

import org.apache.commons.codec.Charsets;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.Converter;

/**
 * Converter from bytes to String.
 * Bytes must be a valid bytes which have been previously constructed through {@link String#getBytes()} method or by {@link StringDecoder} converter
 */
public class StringEncoder implements Converter<byte[], String> {

    @NotNull
    private static Converter<byte[], String> instance = new StringEncoder();

    private StringEncoder() {
    }

    @NotNull
    public static Converter<byte[], String> getInstance() {
        return instance;
    }

    @NotNull
    @Override
    public String convert(@NotNull byte[] bytes) {
        return new String(bytes, Charsets.UTF_8);
    }
}
