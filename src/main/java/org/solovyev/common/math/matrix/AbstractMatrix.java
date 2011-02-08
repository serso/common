package org.solovyev.common.math.matrix;

import org.solovyev.common.math.graph.Graph;
import org.solovyev.common.math.graph.Node;
import org.solovyev.common.math.graph.LinkedNode;

import java.io.*;

import org.solovyev.common.utils.StringsUtils;

/**
 * User: serso
 * Date: 31.03.2009
 * Time: 20:19:46
 */
public abstract class AbstractMatrix<T> implements Matrix<T> {

    protected int m = 0; //number of rows
    protected int n = 0; //number of columns

    protected final static int DEFAULT_M_SIZE = 2;
    protected final static int DEFAULT_N_SIZE = 2;

    public AbstractMatrix() {
        this(DEFAULT_M_SIZE, DEFAULT_N_SIZE, null);
    }

    public AbstractMatrix(int m) {
        this(m, m, null);
    }

    public boolean isEmpty() {
        return (this.m * this.n == 0d);
    }

    public AbstractMatrix(int m, int n, T defaultValue) {
        this.init(m, n, defaultValue);
    }

    public void init(int m, int n) {
        this.init(m, n, null);
    }

    public AbstractMatrix(Graph<?, T> g) {
        this.init(g.getNodes().size(), g.getNodes().size());
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                this.setIJ(i, j, this.getEmptyValue());
            }
        }
        for (Node<?, T> node : g.getNodes()) {
            for (LinkedNode<?, T> linkedNode : node.getLinkedNodes()) {
                this.setIJ(node.getId(), linkedNode.getNode().getId(), linkedNode.getArc());
            }
        }
    }

    public AbstractMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
        if (fName != null) {
            BufferedReader in = new BufferedReader(new FileReader(fName));
            String s = in.readLine();
            String[] params = StringsUtils.getParams(s, " ");
            try {
                if (params != null && params.length > 0) {
                    if (params.length == 1) {
                        Integer size = Integer.valueOf(params[0]);
                        this.init(size, size);
                    } else {
                        Integer m = Integer.valueOf(params[0]);
                        Integer n = Integer.valueOf(params[1]);
                        this.init(m, n);
                    }
                    switch (fileFormat) {
                        case SIMPLE:
                            for (int i = 0; i < this.getNumberOfRows(); i++) {
                                s = in.readLine();
                                params = StringsUtils.getParams(s, " ");
                                if (params != null && params.length == this.getNumberOfColumns()) {
                                    for (int j = 0; j < this.getNumberOfColumns(); j++) {
                                        this.setIJ(i, j, this.getValueFromString(params[j]));
                                    }
                                } else {
                                    throw new IllegalArgumentException();
                                }
                            }
                            break;
                        case SHORTED:
                            Integer param0;
                            Integer param1;
                            T param2;
                            while ((s = in.readLine()) != null) {
                                params = StringsUtils.getParams(s, " ");
                                if (params.length > 2) {
                                    param0 = Integer.valueOf(params[0]) - 1;
                                    param1 = Integer.valueOf(params[1]) - 1;
                                    param2 = this.getValueFromString(params[2]);
                                    this.setIJ(param0, param1, param2);
                                    this.setIJ(param1, param0, param2);
                                }
                            }
                            break;
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            in.close();
        }
    }


    public void save(String fName, MatrixFileFormat matrixFileFormat) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(fName));

        out.write(String.valueOf(this.getNumberOfRows()));
        out.write(" ");
        out.write(String.valueOf(this.getNumberOfColumns()));
        out.newLine();

        T value;
        if (matrixFileFormat.equals(MatrixFileFormat.SHORTED)) {
            for (int i = 0; i < this.getNumberOfRows(); i++) {
                for (int j = 0; j < this.getNumberOfColumns(); j++) {
                    value = this.getIJ(i, j);
                    if (value != null) {
                        if (value instanceof Number) {
                            if (((Number) value).doubleValue() > 0) {
                                out.write((i+1) + " " + (j+1) + " " + value.toString());
                            }
                        } else {
                            if (!value.toString().equals("")) {
                                out.write((i+1) + " " + (j+1) + " " + value.toString());
                            }
                        }
                        out.newLine();
                    }
                }
            }
        } else if (matrixFileFormat.equals(MatrixFileFormat.SIMPLE)) {
            for (int i = 0; i < this.getNumberOfRows(); i++) {
                for (int j = 0; j < this.getNumberOfColumns(); j++) {
                    value =  this.getIJ(i, j);
                    if ( value != null ) {
                        out.write(value.toString() + " ");                                                
                    }
                }
                out.newLine();
            }
        }
        
        out.close();
    }

    protected abstract T getValueFromString(String value) throws InstantiationException, IllegalAccessException;

    protected abstract T getEmptyValue();

    public int getNumberOfRows() {
        return this.m;
    }

    public int getNumberOfColumns() {
        return this.n;
    }

    @Override
    public String toString() {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < this.getNumberOfColumns(); j++) {
                result.append(this.getIJ(i, j).toString());
                result.append(" ");
            }
            result.append("/");
        }
        return result.toString();
    }

    public void textDisplay(PrintWriter out) {
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < this.getNumberOfColumns(); j++) {
                out.write(this.getIJ(i, j).toString() + " ");
            }
            out.println();
        }
    }

    public boolean isSymmetric() {
        boolean result = true;
        for (int i = 0; i < this.getNumberOfRows(); i++) {
            for (int j = 0; j < i; j++) {
                if (!getIJ(i, j).equals(getIJ(j, i))) {
                    result = false;
                    break;
                }
            }
            if (!result) {
                break;
            }
        }
        return result;
    }

    public boolean equals(Matrix<T> r) {
        boolean result = true;
        if (this.getNumberOfRows() != r.getNumberOfRows()) {
            result = false;
        } else if (this.getNumberOfColumns() != r.getNumberOfColumns()) {
            result = false;
        }

        if (result) {
            for (int i = 0; i < this.getNumberOfRows(); i++) {
                for (int j = 0; j < this.getNumberOfColumns(); j++) {
                    if (!this.getIJ(i, j).equals(r.getIJ(i, j))) {
                        result = false;
                        break;
                    }
                }
                if (!result) {
                    break;
                }
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public Matrix<T> clone() {
        Matrix<T> result = null;
        try {
            result = (Matrix<T>) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
