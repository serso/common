package org.solovyev.common.math.matrix;

import org.solovyev.common.utils.TextDisplay;
import org.solovyev.common.definitions.SimpleCloneable;

import java.io.IOException;

/**
 * User: serso
 * Date: 31.03.2009
 * Time: 20:19:09
 */
public interface Matrix<T> extends TextDisplay, SimpleCloneable<Matrix<T>>{
    public T getIJ(int i, int j);
    public void init(int m, int n);
    public void init(int m, int n, T defaultValue);
    public void setIJ(int i, int j, T value );
    public boolean isEmpty();
    public int getNumberOfRows();
    public int getNumberOfColumns();
    public void transpose();
    public boolean isSymmetric();
    public boolean equals( Matrix<T> r );
    public void save( String fName, MatrixFileFormat matrixFileFormat) throws IOException;
}
