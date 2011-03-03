package org.solovyev.common.math.matrix;

import org.apache.log4j.Logger;
import org.solovyev.common.definitions.Property;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * User: serso
 * Date: 15.04.2009
 * Time: 12:36:52
 */
public class MatrixUtils {

	public static <T> Matrix<T> getTransposeMatrix(Matrix<T> m) {
        Matrix<T> result = m.clone();
        result.transpose();
        return result;
    }

    public static Matrix<Double> multiply(Matrix<Double> l, Matrix<Double> r) {
        Matrix<Double> result = null;
        try {
            result = initMatrix(l.getClass(), l.getNumberOfRows(), l.getNumberOfColumns());
            Double value;
            for (int i = 0; i < result.getNumberOfRows(); i++) {
                for (int j = 0; j < result.getNumberOfColumns(); j++) {
                    value = 0d;
                    for (int k = 0; k < l.getNumberOfColumns(); k++) {
                        value += l.getIJ(i, k) * r.getIJ(k, j);
                    }
                    result.setIJ(i, j, value);
                }
            }
        } catch (InstantiationException e) {
            Logger.getLogger(MatrixUtils.class).error(e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(MatrixUtils.class).error(e);
        }
        return result;
    }

    public static Matrix<Double> copyMatrix(Matrix<Double> m, int startRow, int startCol, int endRow, int endCol) {
        Matrix<Double> result = null;
        try {
            result = initMatrix(m.getClass(), endRow - startRow, endCol - startCol);


            for (int i = startRow; i < endRow; i++) {
                for (int j = startCol; j < endCol; j++) {
                    result.setIJ(i, j, m.getIJ(i, j));
                }
            }
        } catch (InstantiationException e) {
            Logger.getLogger(MatrixUtils.class).error(e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(MatrixUtils.class).error(e);
        }

        return result;
    }


    public static Matrix<Double> difference(Matrix<Double> l, Matrix<Double> r) {
        Matrix<Double> result = null;
        try {
            result = initMatrix(l.getClass(), l.getNumberOfRows(), l.getNumberOfColumns());
            for (int i = 0; i < result.getNumberOfRows(); i++) {
                for (int j = 0; j < result.getNumberOfColumns(); j++) {
                    result.setIJ(i, j, l.getIJ(i, j) - r.getIJ(i, j));
                }
            }
        } catch (InstantiationException e) {
            Logger.getLogger(MatrixUtils.class).error(e);
        } catch (IllegalAccessException e) {
            Logger.getLogger(MatrixUtils.class).error(e);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static Matrix<Double> initMatrix(Class klass, int numberOfRows, int numberOfColumns) throws InstantiationException, IllegalAccessException {
        Matrix<Double> result = (Matrix<Double>) klass.newInstance();
        result.init(numberOfRows, numberOfColumns);
        return result;
    }

    public static <T extends Number> boolean isEMatrix(Matrix<T> m, Double error) {
        boolean isE = true;
        for (int i = 0; i < m.getNumberOfRows(); i++) {
            for (int j = 0; j < m.getNumberOfColumns(); j++) {
                if (i == j) {
                    if (Math.abs(m.getIJ(i, j).doubleValue() - 1d) > error) {
                        isE = false;
                        break;
                    }
                } else {
                    if (Math.abs(m.getIJ(i, j).doubleValue()) > error) {
                        isE = false;
                        break;
                    }
                }
            }
            if (!isE) {
                break;
            }
        }
        return isE;
    }

    public static <T extends Number> boolean isSame(Matrix<T> m1, Matrix<T> m2, Double error) {
        boolean result = true;
        if (m1.getNumberOfRows() != m2.getNumberOfRows()) {
            result = false;
        } else if (m1.getNumberOfColumns() != m2.getNumberOfColumns()) {
            result = false;
        }

        if (result) {
            for (int i = 0; i < m1.getNumberOfRows(); i++) {
                for (int j = 0; j < m1.getNumberOfColumns(); j++) {
                    if (Math.abs(m1.getIJ(i, j).doubleValue() - m2.getIJ(i, j).doubleValue()) > error) {
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

    public static void saveMatrixInMatlabRepresentation(Matrix<Double> m, String fName) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(fName));

        Double value;
        if (m instanceof SparseMatrix) {
            int index;
            for (int i = 0; i < m.getNumberOfRows(); i++) {
                List<Property<Double, Integer>> row = ((SparseMatrix) m).getRows().get(i);
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
                    for (int j = 0; j < m.getNumberOfColumns(); j++) {
                        out.write(0d + " ");
                    }
                }
                out.newLine();
            }
        } else {
            for (int i = 0; i < m.getNumberOfRows(); i++) {
                for (int j = 0; j < m.getNumberOfColumns(); j++) {
                    value = m.getIJ(i, j);
                    if (value != null) {
                        out.write(value.toString() + " ");
                    }
                }
                out.newLine();
            }
        }

        out.close();
    }


    public static <T extends Number> int getBandWidth(Matrix<T> m) {
        int result = 0;
        int bandWidth;
        for (int i = 0; i < m.getNumberOfRows(); i++) {
            bandWidth = 0;
            for (int j = 0; j < m.getNumberOfColumns(); j++) {
                if (Math.abs(m.getIJ(i, j).doubleValue()) > 0) {
                    bandWidth = m.getNumberOfColumns() / 2 - j;
                    break;
                }
            }
            if (bandWidth > result) {
                result = bandWidth;
            }
        }
        return result;
    }

    public static <T extends Number> int getProfile(Matrix<T> m) {
        int result = 0;
        int bandWidth;
        for (int i = 0; i < m.getNumberOfRows(); i++) {
            bandWidth = 0;
            for (int j = 0; j < m.getNumberOfColumns(); j++) {
                if (Math.abs(m.getIJ(i, j).doubleValue()) > 0) {
                    bandWidth = m.getNumberOfColumns() / 2 - j;
                    break;
                }
            }
            result += bandWidth;
        }
        return result;
    }

    public static Double getEuclidianMetric(Matrix<Double> m) {
        Double sum = 0d;
        for (int i = 0; i < m.getNumberOfRows(); i++) {
            for (int j = 0; j < m.getNumberOfColumns(); j++) {
                sum = m.getIJ(i, j) * m.getIJ(i, j);
            }
        }
        return Math.pow(sum, 0.5d);
    }

    public static Double getEuclidianDistance(Matrix<Double> m1, Matrix<Double> m2) {
        return getEuclidianMetric(difference(m1, m2));
    }

    public static double maxInColumn(Matrix<Double> m, int j) {
        double result = Double.MIN_VALUE;

        for (int i = 0; i < m.getNumberOfRows(); i++) {
            if (m.getIJ(i, j) > result) {
                result = m.getIJ(i, j);
            }
        }

        return result;
    }

    public static double minInColumn(Matrix<Double> m, int j) {
        double result = Double.MAX_VALUE;

        for (int i = 0; i < m.getNumberOfRows(); i++) {
            if (m.getIJ(i, j) < result) {
                result = m.getIJ(i, j);
            }
        }

        return result;
    }
}
