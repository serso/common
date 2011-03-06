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
	protected MatrixHelper<Double> getMatrixHelper() {
		return MatrixHelper.Helper.double_.getHelper();
	}
}
