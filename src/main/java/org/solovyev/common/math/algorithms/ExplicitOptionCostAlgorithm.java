package org.solovyev.common.math.algorithms;

import org.solovyev.common.math.Function;
import org.solovyev.common.math.matrix.*;
import org.solovyev.common.utils.Interval;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * User: SerSo
 * Date: 31.05.2009
 * Time: 2:34:59
 */
public class ExplicitOptionCostAlgorithm extends AbstractAlgorithm<ExplicitOptionCostAlgorithm.Input, Matrix<Double>> {

	public static class Input {

		private final int sNum;
		private final int tNum;
		private final Interval sInterval;
		private final Interval tInterval;
		private final Double sigma;
		private final Double e;
		private final Matrix<Double> exactSolutionForU;

		public Input(int sNum, int tNum, Interval sInterval, Interval tInterval, Double sigma, Double e, Matrix<Double> exactSolutionForU) {
			this.sNum = sNum;
			this.tNum = tNum;
			this.sInterval = sInterval;
			this.tInterval = tInterval;
			this.sigma = sigma;
			this.e = e;
			this.exactSolutionForU = exactSolutionForU;
		}
	}


	public Matrix<Double> doAlgorithm() {
//        Double k = 2d * r / Math.pow(sigma, 2);
		int xNum = this.input.sNum;
		int tauNum = this.input.tNum;
		Interval xInterval = new Interval(Math.log(this.input.sInterval.getStart() / input.e), Math.log(this.input.sInterval.getEnd()) /input. e);
		Interval tauInterval = new Interval(0d, (this.input.tInterval.getEnd() - this.input.tInterval.getStart()) * Math.pow(this.input.sigma, 2) / 2d);
		ExplicitFiniteDifferenceMethod nefdm = new ExplicitFiniteDifferenceMethod();
		nefdm.init(new ExplicitFiniteDifferenceMethod.Input(xNum, tauNum, xInterval, tauInterval, null, new AddFunction(), this.input.exactSolutionForU));
		nefdm.doAlgorithm();
		this.result = nefdm.getResult();

		return result;
	}

	private class AddFunction implements Function {
		public double getValue(double... params) {
			return 0d;
		}
	}

	@SuppressWarnings({"UnusedDeclaration"})
	public static void mainMatlab(String paramsFile, String exactSolutionForU, String fileToSave) throws IOException {
		String[] str = new String[3];
		str[0] = paramsFile;
		str[1] = exactSolutionForU;
		str[2] = fileToSave;
		main(str);
	}

	public static void main(String[] arg) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(arg[0]));

		Interval xInt = new Interval(Double.valueOf(in.readLine()), Double.valueOf(in.readLine()));
		Interval tInt = new Interval(Double.valueOf(in.readLine()), Double.valueOf(in.readLine()));
		Integer sNum = Double.valueOf(in.readLine()).intValue();
		Integer tNum = Double.valueOf(in.readLine()).intValue();
		Double sigma = Double.valueOf(in.readLine());
		Double e = Double.valueOf(in.readLine());
		Double r = Double.valueOf(in.readLine());
		Matrix<Double> exactSolutionForU = new DoubleArrayMatrix(arg[1], MatrixFileFormat.dense);

		in.close();

		ExplicitOptionCostAlgorithm efdm = new ExplicitOptionCostAlgorithm();
		efdm.init(new Input(sNum, tNum, xInt, tInt, sigma, e, exactSolutionForU));
		efdm.doAlgorithm();

		String fName;
		if (arg.length > 2) {
			fName = arg[2];
		} else {
			fName = "result_" + ExplicitOptionCostAlgorithm.class.toString() + ".txt";
		}

		MatrixUtils.saveMatrixInMatlabRepresentation(efdm.getResult(), fName);
	}
}
