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
