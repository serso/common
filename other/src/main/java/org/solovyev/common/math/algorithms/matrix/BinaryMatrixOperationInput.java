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

package org.solovyev.common.math.algorithms.matrix;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.matrix.Matrix;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 7:20 PM
 */
public class BinaryMatrixOperationInput<T> {

	@NotNull
	private final Matrix<T> l;

	@NotNull
	private final Matrix<T> r;

	public BinaryMatrixOperationInput(@NotNull Matrix<T> l, @NotNull Matrix<T> r) {
		this.l = l;
		this.r = r;
	}

	@NotNull
	public Matrix<T> getL() {
		return l;
	}

	@NotNull
	public Matrix<T> getR() {
		return r;
	}
}
