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

package org.solovyev.common.math.algorithms;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.Conditions;
import org.solovyev.common.math.Function;
import org.solovyev.common.math.matrix.DoubleArrayMatrix;
import org.solovyev.common.math.matrix.Matrix;
import org.solovyev.common.math.matrix.MatrixUtils;
import org.solovyev.common.interval.SimpleInterval;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

/**
 * User: SerSo
 * Date: 18.05.2009
 * Time: 0:18:29
 */

/**
 * brief:   solving equation:   dU/dt = d^2U/dx^2 + f (x, t) where U(x, t) , f(x, t) - functions, U - unknown, f - known
 * with conditions:    1. at x0 = a, x1 = b: U(x0, t) = U(x1, t) = 0
 * 2. at t0 : U(x, t0) = exp(t0) * sin(PI *x)
 */
public class ExplicitFiniteDifferenceMethod extends AbstractAlgorithm<ExplicitFiniteDifferenceMethod.Input, Matrix<Double>> {

	public static class Input {

		private final int xNum;
		private final int tNum;
		private final SimpleInterval xInterval;
		private final SimpleInterval tInterval;
		private final Double xStep;
		private final Double tStep;
		private final Conditions conditions;
		private final Function addFunction;
		private final Matrix<Double> exactSolutionForU;

		public Input(int xNum, int tNum, SimpleInterval xInterval, SimpleInterval tInterval, Conditions conditions, Function addFunction, Matrix<Double> exactSolutionForU) {
			this.xNum = xNum;
			this.tNum = tNum;
			this.xInterval = xInterval;
			this.tInterval = tInterval;
			this.xStep = xInterval.dist() / (xNum - 1);
			this.tStep = tInterval.dist() / (tNum - 1);
			this.conditions = conditions;
			this.addFunction = addFunction;
			this.exactSolutionForU = exactSolutionForU;
		}
	}

	@Override
	public ExplicitFiniteDifferenceMethod init(@NotNull Input input) {
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
				result.set(i, 0, this.input.exactSolutionForU.get(i, 0));
			}

			for (int j = 1; j < input.tNum; j++) {
				result.set(0, j, this.input.exactSolutionForU.get(0, j));
				result.set(input.xNum - 1, j, this.input.exactSolutionForU.get(input.xNum - 1, j));
			}
		}


		return this;
	}

	public Matrix<Double> doAlgorithm() {
		//finding solution by explicit difference method

		double lambda = input.tStep / Math.pow(input.xStep, 2);
		for (int j = 1; j < this.input.tNum; j++) {
			for (int i = 1; i < this.input.xNum - 1; i++) {
				result.set(i, j, lambda * result.get(i + 1, j - 1) + (1 - 2 * lambda) * result.get(i, j - 1) + lambda * result.get(i - 1, j - 1) + input.tStep * this.input.addFunction.getValue(getT(j - 1), getX(i)));
			}
		}

		return result;
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

		ExplicitFiniteDifferenceMethod efdm = new ExplicitFiniteDifferenceMethod();
		efdm.init(new Input(Double.valueOf(in.readLine()).intValue(), Double.valueOf(in.readLine()).intValue(), xInt, tInt,
				new Conditions(new StartCondition0(), new StartCondition1(), new EntryCondition()), new AddFunction(), null));
		in.close();
		efdm.doAlgorithm();

		String fName;
		if (arg.length > 1) {
			fName = arg[1];
		} else {
			fName = "result_" + ExplicitFiniteDifferenceMethod.class.toString() + ".txt";
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
