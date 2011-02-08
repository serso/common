/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.html;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.definitions.Pair;
import org.solovyev.common.utils.LoopData;

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
