package org.solovyev.common.math.matrix;

import org.solovyev.common.math.graph.Graph;

import java.io.IOException;
import java.util.Arrays;

/**
 * User: serso
 * Date: 30.04.2009
 * Time: 0:15:02
 */
public abstract class ArrayMatrix<T> extends AbstractMatrix<T> {

    protected Object[] array;

    protected ArrayMatrix(int m) {
        super(m);
    }

    protected ArrayMatrix(int m, int n) {
        this(m, n, null);
    }

    protected ArrayMatrix(int m, int n, T defaultValue) {
        super(m, n, defaultValue);
    }

    protected ArrayMatrix(Graph<?, T> g) {
        super(g);
    }

    protected ArrayMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
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

    @SuppressWarnings("unchecked")
    public T getCheckedIJ(int i, int j) {
        return (T) array[i * this.getNumberOfColumns() + j];
    }

    public void setCheckedIJ(int i, int j, T value) {
        array[i * this.getNumberOfColumns() + j] = value;
    }

    public void transpose() {
        //todo serso: hint: use just boolean stored in Matrix (transposed) and use it proper in each method working with matrix
        Object[] array = new Object[this.getNumberOfColumns() * this.getNumberOfRows()];
        for (int i = 0; i < this.getNumberOfColumns(); i++) {
            for (int j = 0; j < this.getNumberOfRows(); j++) {
                array[i * this.getNumberOfRows() + j] = this.getIJ(j, i);
            }
        }
        Integer tmpInt = this.m;
        this.m = this.n;
        this.n = tmpInt;
        this.array = array;
    }

    @SuppressWarnings("unchecked")
    public Matrix<T> clone() {
        final ArrayMatrix<T> clone = (ArrayMatrix<T>) super.clone();

        //todo serso: only for simple types
        clone.array = Arrays.copyOf(this.array, this.array.length);

        return clone;
    }
}
