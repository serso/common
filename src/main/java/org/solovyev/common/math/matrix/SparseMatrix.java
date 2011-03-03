package org.solovyev.common.math.matrix;

import org.solovyev.common.definitions.Property;
import org.solovyev.common.math.graph.Graph;
import org.solovyev.common.math.graph.LinkedNode;
import org.solovyev.common.math.graph.Node;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * User: serso
 * Date: 29.04.2009
 * Time: 23:35:53
 */
public abstract class SparseMatrix<T> extends AbstractMatrix<T> {

    List<List<Property<T, Integer>>> rows;

    protected final static int DEFAULT_M_SIZE = 2;
    protected final static int DEFAULT_N_SIZE = 2;

    public SparseMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
        super(fName, fileFormat);
    }

    public SparseMatrix(Graph<?, T> g) {
        this.init(g.getNodes().size(), g.getNodes().size());
        for (Node<?, T> node : g.getNodes()) {
            for (LinkedNode<?, T> linkedNode : node.getLinkedNodes()) {
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

    public T getCheckedIJ(int i, int j) {
		// todo serso : get default value
        T result = null;
		final List<Property<T, Integer>> iRow = this.rows.get(i);

		if (iRow != null) {
			for (Property<T, Integer> element : iRow) {
				if (element.getId() == j) {
					result = element.getValue();
					break;
				} else if (element.getId() > j) {
					break;
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

    public boolean equals(Matrix<T> that) {
		// todo serso: implement
        return false;
    }

	public void setCheckedIJ(int i, int j, T value) {
		List<Property<T, Integer>> iRow = this.rows.get(i);

		if (iRow == null) {
			iRow = new ArrayList<Property<T, Integer>>();
			iRow.add(new Property<T, Integer>(value, j));
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
				iRow.add(index + 1, new Property<T, Integer>(value, j));
			}
		}
	}

    public void textDisplay(PrintWriter out) {
        List<Property<T, Integer>> iRow;
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
                for (Property<T, Integer> e : iRow) {
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

    public Matrix<T> clone() {
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

    public void init(int m, int n, T defaultValue) {
        this.m = m;
        this.n = n;
        rows = new ArrayList<List<Property<T, Integer>>>();
        for (int i = 0; i < this.m; i++) {
            rows.add(null);
        }
    }

/*    protected T getValueFromString(String value) throws InstantiationException, IllegalAccessException, ClassCastException {
        T result = null;
        if (value != null) {
            result = Double.valueOf(value);
        }
        return result;
    }*/

/*    protected Double getEmptyValue() {
        return 0d;
    }*/

    public List<List<Property<T, Integer>>> getRows() {
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
                List<Property<T, Integer>> row = this.getRows().get(i);
                if (row != null) {
                    for (Property<T, Integer> element : row) {
                        out.write((i+1) + " " + (element.getId()+1) + " " + element.getValue());
                        out.newLine();
                    }
                }
            }
        } else if (matrixFileFormat.equals(MatrixFileFormat.SIMPLE)) {
            int index;
            for (int i = 0; i < this.getNumberOfRows(); i++) {
                List<Property<T, Integer>> row = this.getRows().get(i);
                if (row != null) {
                    index = 0;
                    for (Property<T, Integer> element : row) {
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
