package org.solovyev.common.math.matrix;

import org.solovyev.common.definitions.Property;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import java.util.ArrayList;

import org.solovyev.common.math.graph.Graph;
import org.solovyev.common.math.graph.Node;
import org.solovyev.common.math.graph.LinkedNode;

/**
 * User: serso
 * Date: 29.04.2009
 * Time: 23:35:53
 */
public class SparseMatrix extends AbstractMatrix<Double> {

    List<List<Property<Double, Integer>>> rows;

    protected final static int DEFAULT_M_SIZE = 2;
    protected final static int DEFAULT_N_SIZE = 2;

    public SparseMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
        super(fName, fileFormat);
    }

    public SparseMatrix(Graph<?, Double> g) {
        this.init(g.getNodes().size(), g.getNodes().size());
        for (Node<?, Double> node : g.getNodes()) {
            for (LinkedNode<?, Double> linkedNode : node.getLinkedNodes()) {
                this.setIJ(node.getId(), linkedNode.getNode().getId(), linkedNode.getArc());
            }
        }
    }

    public SparseMatrix(int m, int n) {
        super(m, n, null);
    }

    public SparseMatrix(int m) {
        super(m);
    }

    public SparseMatrix() {
    }

    public Double getIJ(int i, int j) {
        Double result = null;
        if (i >= 0 && i < this.m && j >= 0 && j < this.n) {
            result = 0d;
            List<Property<Double, Integer>> iRow = this.rows.get(i);

            if (iRow != null) {
                for (Property<Double, Integer> element : iRow) {
                    if (element.getId() == j) {
                        result = element.getValue();
                        break;
                    } else if (element.getId() > j) {
                        break;
                    }
                }
            }
        }
        return result;
    }

    //todo serso: implement
    public boolean isEmpty() {
        return false;
    }

    public int getNumberOfRows() {
        return this.m;
    }

    public int getNumberOfColumns() {
        return this.n;
    }

    public void transpose() {

    }

    public boolean isSymmetric() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean equals(Matrix<Double> r) {
        return false;
    }

    public void setIJ(int i, int j, Double value) {
        if (i >= 0 && i < this.m && j >= 0 && j < this.n) {
            List<Property<Double, Integer>> iRow = this.rows.get(i);
            Property<Double, Integer> element;

            if (iRow == null) {
                iRow = new ArrayList<Property<Double, Integer>>();
                element = new Property<Double, Integer>(value, j);
                iRow.add(element);
                this.rows.set(i, iRow);
            } else {
                int index;
                boolean isFound = false;
                for (index = iRow.size() - 1; index >= 0; index--) {
                    if (iRow.get(index).getId().equals(j)) {
                        isFound = true;
                        break;
                    } else if (iRow.get(index).getId() - j < 0) {
                        break;
                    }
                }

                if (isFound) {
                    iRow.get(index).setValue(value);
                } else {
                    element = new Property<Double, Integer>(value, j);
                    iRow.add(index + 1, element);
                }
            }
        }
    }

    public void textDisplay(PrintWriter out) {
        List<Property<Double, Integer>> iRow;
        int k;
        int prev;
        for (int i = 0; i < this.m; i++) {
            iRow = this.rows.get(i);
            if (iRow == null) {
                for (int j = 0; j < this.n; j++) {
                    out.write("0.0 ");
                }
            } else {
                prev = 0;
                for (Property<Double, Integer> e : iRow) {
                    if (prev == 0) {
                        k = 0;
                    } else {
                        k = prev;
                    }
                    for (; k < e.getId(); k++) {
                        out.write("0.0 ");
                    }
                    out.write(String.valueOf(e.getValue()));
                    prev = k + 1;
                }
            }
            out.println();
        }
    }

    public Matrix<Double> clone() {
        SparseMatrix result;
        result = (SparseMatrix) super.clone();
        result.init(this.m, this.n);
        for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                result.setIJ(i, j, this.getIJ(i, j));
            }
        }
        return result;
    }

    public void init(int m, int n, Double defaultValue) {
        this.m = m;
        this.n = n;
        rows = new ArrayList<List<Property<Double, Integer>>>();
        for (int i = 0; i < this.m; i++) {
            rows.add(null);
        }
    }

    protected Double getValueFromString(String value) throws InstantiationException, IllegalAccessException, ClassCastException {
        Double result = null;
        if (value != null) {
            result = Double.valueOf(value);
        }
        return result;
    }

    protected Double getEmptyValue() {
        return 0d;
    }

    public List<List<Property<Double, Integer>>> getRows() {
        return rows;
    }

    public void save(String fName, MatrixFileFormat matrixFileFormat) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(fName));

        out.write(String.valueOf(this.getNumberOfRows()));
        out.write(" ");
        out.write(String.valueOf(this.getNumberOfColumns()));
        out.newLine();

        if (matrixFileFormat.equals(MatrixFileFormat.SHORTED)) {
            for (int i = 0; i < this.getNumberOfRows(); i++) {
                List<Property<Double, Integer>> row = this.getRows().get(i);
                if (row != null) {
                    for (Property<Double, Integer> element : row) {
                        out.write((i+1) + " " + (element.getId()+1) + " " + element.getValue());
                        out.newLine();
                    }
                }
            }
        } else if (matrixFileFormat.equals(MatrixFileFormat.SIMPLE)) {
            int index;
            for (int i = 0; i < this.getNumberOfRows(); i++) {
                List<Property<Double, Integer>> row = this.getRows().get(i);
                if (row != null) {
                    index = 0;
                    for (Property<Double, Integer> element : row) {
                        while (index < element.getId()) {
                            out.write(0d + " ");
                            index++;
                        }
                        out.write(element.getValue() + " ");
                        index++;
                    }
                } else {
                    for (int j = 0; j < this.getNumberOfColumns(); j++) {
                        out.write(0d + " ");
                    }
                }
                out.newLine();
            }
        }

        out.close();
    }
}
