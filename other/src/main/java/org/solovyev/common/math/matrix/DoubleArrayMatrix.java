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

package org.solovyev.common.math.matrix;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.graph.Graph;
import org.solovyev.common.math.matrix.helpers.MatrixHelper;

import java.io.IOException;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:49 AM
 */
public class DoubleArrayMatrix extends AbstractArrayMatrix<Double> implements MathMatrix<Double> {

	public DoubleArrayMatrix() {
	}

	public DoubleArrayMatrix(int m) {
		super(m);
	}

	public DoubleArrayMatrix(int m, int n) {
		super(m, n);
	}

	public DoubleArrayMatrix(int m, int n, Double defaultValue) {
		super(m, n, defaultValue);
	}

	public DoubleArrayMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
		super(fName, fileFormat);
	}

	public DoubleArrayMatrix(Graph<?, Double> g) {
		super(g);
	}

	@NotNull
	@Override
	public MatrixHelper<Double> getMatrixHelper() {
		return MatrixHelper.Helper.double_.getHelper();
	}
}
