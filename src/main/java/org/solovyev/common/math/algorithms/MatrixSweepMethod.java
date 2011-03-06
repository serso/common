package org.solovyev.common.math.algorithms;

import org.solovyev.common.math.matrix.*;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * User: SerSo
 * Date: 20.05.2009
 * Time: 1:43:02
 */
public class MatrixSweepMethod extends AbstractAlgorithm<MatrixSweepMethod.Input, Matrix<Double>> {

	public static class Input {
		private final Matrix<Double> a;
		private final Matrix<Double> b;

		public Input(Matrix<Double> a, Matrix<Double> b) {
			if (a.getNumberOfColumns() == a.getNumberOfRows() && a.getNumberOfRows() == b.getNumberOfRows() && b.getNumberOfColumns() == 1) {
				this.a = a;
				this.b = b;
			} else {
				throw new IllegalArgumentException("Illegal dimensions of input matrices!");
			}

		}
	}


	public Matrix<Double> doAlgorithm() {
		double aArray[] = new double[input.a.getNumberOfRows()];
		double bArray[] = new double[input.a.getNumberOfRows()];

		aArray[1] = -input.a.get(0, 1) / input.a.get(0, 0);
		bArray[1] = input.b.get(0, 0) / input.a.get(0, 0);

		for (int i = 2; i < aArray.length; i++) {
			aArray[i] = -input.a.get(i - 1, i) / (aArray[i - 1] * input.a.get(i - 1, i - 2) + input.a.get(i - 1, i - 1));
			bArray[i] = (input.b.get(i - 1, 0) - input.a.get(i - 1, i - 2) * bArray[i - 1]) / (input.a.get(i - 1, i - 2) * aArray[i - 1] + input.a.get(i - 1, i - 1));
		}

		result = new DoubleArrayMatrix(input.b.getNumberOfRows(), 1);

		result.set(result.getNumberOfRows() - 1, 0,
				(input.b.get(input.b.getNumberOfRows() - 1, 0) - input.a.get(input.a.getNumberOfRows() - 1, input.a.getNumberOfColumns() - 2) * bArray[bArray.length - 1])
						/ (input.a.get(input.a.getNumberOfRows() - 1, input.a.getNumberOfColumns() - 1) + input.a.get(input.a.getNumberOfRows() - 1, input.a.getNumberOfColumns() - 2) * aArray[aArray.length - 1]));

		for (int i = result.getNumberOfRows() - 2; i >= 0; i--) {
			result.set(i, 0, aArray[i + 1] * result.get(i + 1, 0) + bArray[i + 1]);
		}

		return result;
	}

	public static void main(String[] args) {
		try {
			Matrix<Double> a = new DoubleArrayMatrix("test_sweep.txt", MatrixFileFormat.SHORTED);
			Matrix<Double> b = new DoubleArrayMatrix("test_sweep_2.txt", MatrixFileFormat.SIMPLE);

			MatrixSweepMethod msm = new MatrixSweepMethod();
			msm.init(new Input(a, b));


			msm.doAlgorithm();

			PrintWriter out = new PrintWriter(System.out, true);
			a.textDisplay(out);
			out.println();

			b.textDisplay(out);
			out.println();

			msm.getResult().textDisplay(out);
			out.println();

			MatrixUtils.multiply(a, msm.getResult()).textDisplay(out);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
