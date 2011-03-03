package org.solovyev.common.math.matrix;

import org.solovyev.common.math.graph.Graph;

import java.io.IOException;

/**
 * User: serso
 * Date: 31.03.2009
 * Time: 23:20:30
 */
public class MathArrayMatrix<T extends Number> extends ArrayMatrix<T> {

    public MathArrayMatrix() {
        this(DEFAULT_M_SIZE, DEFAULT_N_SIZE);
    }

    public MathArrayMatrix(int m) {
        this(m, m);
    }

    public MathArrayMatrix(int m, int n) {
        this(m, n, null);
    }

    public MathArrayMatrix(int m, int n, T defaultValue) {
        super(m, n, defaultValue);    
    }

    public MathArrayMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
        super(fName, fileFormat);
    }

    public MathArrayMatrix(Graph<?, T> g) {
        super(g);
    }

    @SuppressWarnings("unchecked")
    protected T getValueFromString(String value) throws InstantiationException, IllegalAccessException, ClassCastException {
        T result = null;
        if ( value != null ) {
            result = (T)Double.valueOf(value);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    protected T getEmptyValue() {
        return (T)new Double(0d); 
    }
}
