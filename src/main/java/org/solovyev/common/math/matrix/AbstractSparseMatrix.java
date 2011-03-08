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
public abstract class AbstractSparseMatrix<T> extends AbstractMatrix<T> {

    List<List<Property<T, Integer>>> rows;

    public AbstractSparseMatrix(String fName, MatrixFileFormat fileFormat) throws IOException, IllegalArgumentException {
        super(fName, fileFormat);
    }

    public AbstractSparseMatrix(Graph<?, T> g) {
		this.init(g.getNodes().size(), g.getNodes().size());
        for (Node<?, T> node : g.getNodes()) {
            for (LinkedNode<?, T> linkedNode : node.getLinkedNodes()) {
                this.set(node.getId(), linkedNode.getNode().getId(), linkedNode.getArc());
            }
        }
    }

    public AbstractSparseMatrix(int m, int n) {
        super(m, n, null);
    }

    public AbstractSparseMatrix(int m) {
        super(m);
    }

    public AbstractSparseMatrix() {
	}

    public T getChecked(int i, int j) {
        T result = getEmptyValue();
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

    public void transpose() {
    }

	public void setChecked(int i, int j, T value) {
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
        final AbstractSparseMatrix<T> result= (AbstractSparseMatrix<T>) super.clone();

		result.init(this.m, this.n);

		for (int i = 0; i < this.m; i++) {
            for (int j = 0; j < this.n; j++) {
                result.set(i, j, this.get(i, j));
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

    public List<List<Property<T, Integer>>> getRows() {
        return rows;
    }
}
