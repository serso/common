package org.solovyev.common.math.algorithms;

import org.solovyev.common.math.Function;
import org.solovyev.common.math.matrix.*;
import org.solovyev.common.utils.SimpleInterval;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * User: SerSo
 * Date: 31.05.2009
 * Time: 2:34:59
 */
public class NonExplicitOptionCostAlgorithm extends AbstractAlgorithm<NonExplicitOptionCostAlgorithm.Input, Matrix<Double>> {

	public static class Input {
		private final int sNum;
		private final int tNum;
		private final SimpleInterval sInterval;
		private final SimpleInterval tInterval;
		private final Double sigma;
		private final Double e;
		private final Matrix<Double> exactSolutionForU;

		public Input(int sNum, int tNum, SimpleInterval sInterval, SimpleInterval tInterval, Double sigma, Double e, Matrix<Double> exactSolutionForU) {
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
		int xNum = input.sNum;
		int tauNum = input.tNum;
		SimpleInterval xInterval = new SimpleInterval(Math.log(input.sInterval.getStart() / input.e), Math.log(input.sInterval.getEnd()) / input.e);
		SimpleInterval tauInterval = new SimpleInterval(0d, (input.tInterval.getEnd() - input.tInterval.getStart()) * Math.pow(input.sigma, 2) / 2d);
		//Conditions conditions = new Conditions(new EntryCondition(k), new StartCondition0(), new StartCondition1());

		NonExplicitFiniteDifferenceMethod nefdm = new NonExplicitFiniteDifferenceMethod();
		nefdm.init(new NonExplicitFiniteDifferenceMethod.Input(xNum, tauNum, xInterval, tauInterval, null, new AddFunction(), false, input.exactSolutionForU));
		nefdm.doAlgorithm();

		return nefdm.getResult();
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

		SimpleInterval xInt = new SimpleInterval(Double.valueOf(in.readLine()), Double.valueOf(in.readLine()));
		SimpleInterval tInt = new SimpleInterval(Double.valueOf(in.readLine()), Double.valueOf(in.readLine()));
		Integer sNum = Double.valueOf(in.readLine()).intValue();
		Integer tNum = Double.valueOf(in.readLine()).intValue();
		Double sigma = Double.valueOf(in.readLine());
		Double e = Double.valueOf(in.readLine());
		Double r = Double.valueOf(in.readLine());
		Matrix<Double> exactSolutionForU = new DoubleArrayMatrix(arg[1], MatrixFileFormat.dense);


		NonExplicitOptionCostAlgorithm efdm = new NonExplicitOptionCostAlgorithm();
		efdm.init(new Input(sNum, tNum, xInt, tInt, sigma, e, exactSolutionForU));
		in.close();
		efdm.doAlgorithm();

		String fName;
		if (arg.length > 2) {
			fName = arg[2];
		} else {
			fName = "result_" + NonExplicitOptionCostAlgorithm.class.toString() + ".txt";
		}

		MatrixUtils.saveMatrixInMatlabRepresentation(efdm.getResult(), fName);
	}
}
