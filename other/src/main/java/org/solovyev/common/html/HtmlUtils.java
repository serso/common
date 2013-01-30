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
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.html;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.definitions.Pair;
import org.solovyev.common.collections.LoopData;

/**
 * User: serso
 * Date: May 26, 2010
 * Time: 10:35:21 PM
 */
public class HtmlUtils {

	@NotNull
	public static String addRequestParam (@NotNull String url, @NotNull Pair<String, ?>... properties) {

		StringBuilder sb = new StringBuilder(url);
		boolean containsOtherParams = true;
		if ( !url.contains("?") ) {
			sb.append("?");
			containsOtherParams = false;
		}
		boolean isFirst = true;
		LoopData ld = new LoopData(properties);
		for (Pair<String, ?> property : properties) {
			ld.next();
			if (!url.contains(property.getFirst())) {
				if (isFirst && containsOtherParams) {
					sb.append("&");
					isFirst = false;
				}
				sb.append(property.getFirst()).append("=").append(property.getSecond());
				if (!ld.isLast()) {
					sb.append("&");
				}
			}
		}
		return sb.toString();
	}
}
