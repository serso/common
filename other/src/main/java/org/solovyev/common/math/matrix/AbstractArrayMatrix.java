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

import java.io.IOException;
import java.util.Arrays;

/**
 * User: serso
 * Date: 30.04.2009
 * Time: 0:15:02
 */
public abstract class AbstractArrayMatrix<T> extends AbstractMatrix<T> implements ArrayMatrix <T> {

    protected Object[] array;

	protected AbstractArrayMatrix() {
	}

	protected AbstractArrayMatrix(int m) {
        super(m);
    }

    protected AbstractArrayMatrix(int m, int n) {
		this(m, n, null);
    }

    protected AbstractArrayMatrix(int m, int n, T defaultValue) {
        super(m, n, defaultValue);
    }

    protected AbstractArrayMatrix(Graph<?, T> g) {
        super(g);
    }

    protected AbstractArrayMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
        super(fName, fileFormat);
    }

    public void init(int m, int n, T defaultValue) {
        this.m = m;
        this.n = n;
        this.array = new Object[m * n];
        for (int i = 0; i < this.array.length; i++) {
            if (defaultValue == null) {
                this.array[i] = this.getEmptyValue();
            } else {
                this.array[i] = defaultValue;
            }
        }
    }

    public T getChecked(int i, int j) {
		return MatrixUtils.getElement(this.getArray(), i, j, this.getNumberOfColumns());
    }

    public void setChecked(int i, int j, T value) {
        MatrixUtils.setElement(this.getArray(), value, i, j, this.getNumberOfColumns());
    }

    public void transpose() {
        //todo serso: hint: use just boolean stored in Matrix (transposed) and use it proper in each method working with matrix
        Object[] array = new Object[this.getNumberOfColumns() * this.getNumberOfRows()];
        for (int i = 0; i < this.getNumberOfColumns(); i++) {
            for (int j = 0; j < this.getNumberOfRows(); j++) {
                array[i * this.getNumberOfRows() + j] = this.get(j, i);
            }
        }
        Integer tmpInt = this.m;
        this.m = this.n;
        this.n = tmpInt;
        this.array = array;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public Matrix<T> clone() {
        final AbstractArrayMatrix<T> clone = (AbstractArrayMatrix<T>) super.clone();

        //todo serso: only for simple types
        clone.array = Arrays.copyOf(this.array, this.array.length);

        return clone;
    }

	@Override
	public T[] getArray() {
		//noinspection unchecked
		return (T[])array;
	}
}
