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

package org.solovyev.common;

import org.solovyev.common.equals.Equalizer;

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 2/7/13
 * Time: 10:33 PM
 */
public class ByteArrayEqualizer implements Equalizer<byte[]> {

	@Nonnull
	private static Equalizer<byte[]> instance = new ByteArrayEqualizer();

	private ByteArrayEqualizer() {
	}

	@Nonnull
	public static Equalizer<byte[]> getInstance() {
		return instance;
	}

	@Override
	public boolean areEqual(@Nonnull byte[] first, @Nonnull byte[] second) {
		boolean result = true;

		if (first.length == second.length) {
			for (int i = 0; i < first.length; i++) {
				if (first[i] != second[i]) {
					result = false;
					break;
				}
			}
		} else {
			result = false;
		}

		return result;
	}
}
