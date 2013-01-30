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
 * Date: 3/3/11
 * Time: 10:59 AM
 */
public class DoubleSparseMatrix extends AbstractSparseMatrix<Double> implements MathMatrix<Double> {

	public DoubleSparseMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
		super(fName, fileFormat);
	}

	public DoubleSparseMatrix(Graph<?, Double> g) {
		super(g);
	}

	public DoubleSparseMatrix(int m, int n) {
		super(m, n);
	}

	public DoubleSparseMatrix(int m) {
		super(m);
	}

	public DoubleSparseMatrix() {
	}

	@NotNull
	@Override
	public MatrixHelper<Double> getMatrixHelper() {
		return MatrixHelper.Helper.double_.getHelper();
	}
}
