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

package org.solovyev.common.text.hex;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.Converter;
import org.solovyev.common.text.HexString;

/**
 * Converter from base64 encoded String to bytes.
 * Notes: resulted bytes are DECODED
 */
public class HexStringDecoder implements Converter<HexString, byte[]> {

    @NotNull
    private static Converter<HexString, byte[]> instance = new HexStringDecoder();

    private HexStringDecoder() {
    }

    @NotNull
    public static Converter<HexString, byte[]> getInstance() {
        return instance;
    }

    @NotNull
    @Override
    public byte[] convert(@NotNull HexString s) {
        return s.getOriginalBytes();
    }
}
