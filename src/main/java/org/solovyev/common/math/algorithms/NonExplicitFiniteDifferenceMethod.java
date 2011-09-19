package org.solovyev.common.math.algorithms;


import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.Conditions;
import org.solovyev.common.math.Function;
import org.solovyev.common.math.matrix.DoubleArrayMatrix;
import org.solovyev.common.math.matrix.Matrix;
import org.solovyev.common.math.matrix.MatrixUtils;
import org.solovyev.common.utils.SimpleInterval;

import java.io.*;

/**
 * User: SerSo
 * Date: 20.05.2009
 * Time: 2:35:38
 */

/**
 * brief:   solving equation:   dU/dt = d^2U/dx^2 + f (x, t) where U(x, t) , f(x, t) - functions, U - unknown, f - known
 * with conditions:    1. at x0 = a, x1 = b: U(x0, t) = U(x1, t) = 0
 * 2. at t0 : U(x, t0) = exp(t0) * sin(PI *x)
 */
public class NonExplicitFiniteDifferenceMethod extends AbstractAlgorithm<NonExplicitFiniteDifferenceMethod.Input, Matrix<Double>> {

	public static class Input {
		private final int xNum;
		private final int tNum;
		private final SimpleInterval xInterval;
		private final SimpleInterval tInterval;
		private final boolean showIntermediateResults;
		private final Double xStep;
		private final Double tStep;
		private final Conditions conditions;
		private final Function addFunction;
		private final Matrix<Double> exactSolution;

		public Input(int xNum, int tNum, SimpleInterval xInterval, SimpleInterval tInterval, Conditions conditions, Function addFunction, boolean showIntermediateResults, Matrix<Double> exactSolution) {
			this.exactSolution = exactSolution;
			this.xNum = xNum;
			this.tNum = tNum;
			this.xInterval = xInterval;
			this.tInterval = tInterval;
			this.xStep = xInterval.dist() / (xNum - 1);
			this.tStep = tInterval.dist() / (tNum - 1);
			this.showIntermediateResults = showIntermediateResults;
			this.conditions = conditions;
			this.addFunction = addFunction;
		}
	}

	@Override
	public NonExplicitFiniteDifferenceMethod init(@NotNull Input input) {
		super.init(input);

		this.result = new DoubleArrayMatrix(input.xNum, input.tNum);


		if (input.conditions != null) {
			for (int i = 0; i < input.xNum; i++) {
				result.set(i, 0, input.conditions.getEntryCondition().getValue(input.tInterval.getStart(), i * input.xStep + input.xInterval.getStart()));
			}

			for (int j = 1; j < input.tNum; j++) {
				result.set(0, j, input.conditions.getStartCondition0().getValue(j * input.tStep + input.tInterval.getStart(), input.xInterval.getStart()));
				result.set(input.xNum - 1, j, input.conditions.getStartCondition1().getValue(j * input.tStep + input.tInterval.getStart(), input.xInterval.getEnd()));
			}
		} else {
			//using exact solution
			//this.exactSolution.transpose();

			for (int i = 0; i < input.xNum; i++) {
				result.set(i, 0, this.input.exactSolution.get(i, 0));
			}

			for (int j = 1; j < input.tNum; j++) {
				result.set(0, j, this.input.exactSolution.get(0, j));
				result.set(input.xNum - 1, j, this.input.exactSolution.get(input.xNum - 1, j));
			}
		}
		/*PrintWriter out = new PrintWriter(System.out, true);
				this.exactSolution.textDisplay(out);
				out.println();
				out.println();
				m.textDisplay(out);*/

		return this;
	}


	public Matrix<Double> doAlgorithm() {

		Matrix<Double> a;
		Matrix<Double> b;
		Matrix<Double> x;

		MatrixSweepMethod msm;
		PrintWriter out = new PrintWriter(System.out, true);

		if (input.showIntermediateResults) {
			out.println("Matrix M:");
			result.textDisplay(out);
			out.println();
		}

		Double lambda = input.tStep / Math.pow(input.xStep, 2);

		a = new DoubleArrayMatrix(input.xNum - 2, input.xNum - 2);
		for (int i = 1; i < input.xNum - 1; i++) {
			//diagonal elements
			a.set(i - 1, i - 1, 2 * lambda + 1);

			//elements above diagonal except last
			if (i < input.xNum - 2) {
				a.set(i - 1, i, -lambda);
			}

			//elements below diagonal except first
			if (i > 1) {
				a.set(i - 1, i - 2, -lambda);
			}
		}

		b = new DoubleArrayMatrix(input.xNum - 2, 1);
		for (int j = 1; j < input.tNum; j++) {
			for (int i = 1; i < input.xNum - 1; i++) {
				if (i == 1) {
					b.set(i - 1, 0, result.get(1, j - 1) + lambda * result.get(0, j - 1));
				} else if (i == input.xNum - 2) {
					b.set(i - 1, 0, lambda * result.get(input.xNum - 1, j - 1) + result.get(input.xNum - 2, j - 1));
				} else {
					b.set(i - 1, 0, result.get(i, j - 1));
				}

				b.set(i - 1, 0, b.get(i - 1, 0) + input.tStep * this.input.addFunction.getValue(getT(j - 1), getX(i)));
			}
			msm = new MatrixSweepMethod();
			msm.init(new MatrixSweepMethod.Input(a, b));
			msm.doAlgorithm();
			x = msm.getResult();

			if (input.showIntermediateResults) {
				out.println("Matrix A:");
				a.textDisplay(out);
				out.println();
				out.println("Matrix b:");
				b.textDisplay(out);
				out.println();
				out.println("Matrix x:");
				x.textDisplay(out);
				out.println();
				out.println("Matrix A*x-b:");
				MatrixUtils.difference(MatrixUtils.multiply(a, x), b).textDisplay(out);
				out.println();
			}

			for (int k = 0; k < x.getNumberOfRows(); k++) {
				this.result.set(k + 1, j, x.get(k, 0));
			}

			if (input.showIntermediateResults) {
				out.println("Matrix m:");
				result.textDisplay(out);
				out.println();
			}
		}

		return this.result;
	}

	private double getT(int j) {
		return input.tStep * j + input.tInterval.getStart();
	}

	private double getX(int i) {
		return input.xStep * i + input.xInterval.getStart();
	}

	@SuppressWarnings({"UnusedDeclaration"})
	public static void mainMatlab(String paramsFile, String fileToSave) throws IOException {
		String[] str = new String[2];
		str[0] = paramsFile;
		str[1] = fileToSave;
		main(str);
	}


	public static void main(String[] arg) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader(arg[0]));

		SimpleInterval xInt = new SimpleInterval(Double.valueOf(in.readLine()), Double.valueOf(in.readLine()));
		SimpleInterval tInt = new SimpleInterval(Double.valueOf(in.readLine()), Double.valueOf(in.readLine()));

		NonExplicitFiniteDifferenceMethod efdm = new NonExplicitFiniteDifferenceMethod();
		efdm.init(new Input(Double.valueOf(in.readLine()).intValue(), Double.valueOf(in.readLine()).intValue(), xInt, tInt,
				new Conditions(new StartCondition0(), new StartCondition1(), new EntryCondition()), new AddFunction(), false, null));
		in.close();
		efdm.doAlgorithm();

		String fName;
		if (arg.length > 1) {
			fName = arg[1];
		} else {
			fName = "result_" + NonExplicitFiniteDifferenceMethod.class.toString() + ".txt";
		}

		MatrixUtils.saveMatrixInMatlabRepresentation(efdm.getResult(), fName);
	}

	private static class AddFunction implements Function {
		public double getValue(double... params) {
			return Math.exp(-params[0]) * Math.sin(Math.PI * params[1]) * (Math.PI * Math.PI - 1);
		}
	}

	private static class EntryCondition implements Function {
		public double getValue(double... params) {
			return Math.exp(-params[0]) * Math.sin(Math.PI * params[1]);
		}
	}

	private static class StartCondition0 implements Function {
		public double getValue(double... params) {
			return 0d;
		}
	}

	private static class StartCondition1 implements Function {
		public double getValue(double... params) {
			return 0d;
		}
	}
}
