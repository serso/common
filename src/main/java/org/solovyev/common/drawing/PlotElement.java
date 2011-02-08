package org.solovyev.common.drawing;

import org.solovyev.common.math.matrix.Matrix;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * User: serso
 * Date: 10.05.2009
 * Time: 23:52:10
 */
public class PlotElement {
    private Double[] x;
    private Double[] y;
    private Color[] color;
    private Integer size;

    public PlotElement(Double[] x, Double[] y, Color[] color, Integer size) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.size = size;
    }

    public Double[] getX() {
        return x;
    }

    public void setX(Double[] x) {
        this.x = x;
    }

    public Double[] getY() {
        return y;
    }

    public void setY(Double[] y) {
        this.y = y;
    }

    public Color[] getColor() {
        return color;
    }

    public void setColor(Color[] color) {
        this.color = color;
    }

    public PreparedPlotElement prepare(int startX, int startY, int width, int height) {
        double maxX = Double.MIN_VALUE;
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;

        for (int i = 0; i < x.length; i++) {
            if (x[i] > maxX) {
                maxX = x[i];
            }
            if (x[i] < minX) {
                minX = x[i];
            }
            if (y[i] > maxY) {
                maxY = y[i];
            }
            if (y[i] < minY) {
                minY = y[i];
            }
        }

        double xDist = maxX - minX;
        double yDist = maxY - minY;
        double xScale = width / xDist;
        double yScale = height / yDist;

        java.util.List<Integer> xInt = new ArrayList<Integer>();
        java.util.List<Integer> yInt = new ArrayList<Integer>();
        java.util.List<Integer> xDistInt = new ArrayList<Integer>();
        java.util.List<Integer> yDistInt = new ArrayList<Integer>();


        int j = 0;
        for (int i = 0; i < x.length; i++) {
            if (i > 0) {
                if (xInt.get(j - 1) != startX + (int) (x[i] * xScale) || yInt.get(j - 1) != startY + (int) (y[i] * yScale)) {
                    xInt.add(j, startX + (int) (x[i] * xScale));
                    yInt.add(j, startY + (int) (y[i] * yScale));
                    xDistInt.add(j, width / size > 0 ? width / size : 1);
                    yDistInt.add(j, height / size > 0 ? height / size : 1);
                    j++;
                }
            } else {
                xInt.add(startX + (int) (x[i] * xScale));
                yInt.add(startY + (int) (y[i] * yScale));
                xDistInt.add(width / size > 0 ? width / size : 1);
                yDistInt.add(height / size > 0 ? height / size : 1);
                j++;
            }
        }
        //todo serso: color must be cloned!!!
        return new PreparedPlotElement(xInt.toArray(new Integer[xInt.size()]), yInt.toArray(new Integer[yInt.size()]), xDistInt.toArray(new Integer[xDistInt.size()]), yDistInt.toArray(new Integer[yDistInt.size()]),  Arrays.copyOfRange(this.color, 0, xInt.size()));
    }

    public static <T extends Number> PlotElement create(Matrix<T> m, Color color) {
        java.util.List<Double> xs = new ArrayList<Double>();
        java.util.List<Double> ys = new ArrayList<Double>();
        java.util.List<Color> colors = new ArrayList<Color>();

        for (int i = 0; i < m.getNumberOfRows(); i++) {
            for (int j = 0; j < m.getNumberOfColumns(); j++) {
                if (m.getIJ(i, j).doubleValue() > 0) {
                    xs.add((double) j);
                    ys.add((double) i);
                    colors.add(color);
                }
            }
        }

        Double[] xArray = xs.toArray(new Double[xs.size()]);
        Double[] yArray = ys.toArray(new Double[ys.size()]);
        Color[] colorArray = colors.toArray(new Color[colors.size()]);

        return new PlotElement(xArray, yArray, colorArray, m.getNumberOfColumns());
    }
}
