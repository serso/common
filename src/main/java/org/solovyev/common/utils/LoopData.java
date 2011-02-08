/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.utils;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:56:24 PM
 */
public class LoopData {

	private int index = 0;
	private int size = 0;

	public LoopData(@NotNull Collection c) {
		this.init();
		this.size = c.size();
	}

	public LoopData(@NotNull Object[] ar) {
		this.init();
		this.size = ar.length;
	}

	public boolean isFirstAndNext() {
		boolean result = index == 0;

		index++;

		return result;
	}

	public boolean isFirst() {
		return index == 0;
	}

	public int next() {
		return index++;
	}

	public void init() {
		this.index = 0;
	}

	public boolean isLast() {
		return index >= size;
	}

	public int getIndex() {
		return index;
	}

	public boolean isOdd() {
		return index % 2 == 1;
	}

	public boolean isEven() {
		return !isOdd();
	}
}
