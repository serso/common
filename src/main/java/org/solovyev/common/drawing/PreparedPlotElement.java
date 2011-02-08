package org.solovyev.common.drawing;

import java.awt.*;

/**
 * User: serso
 * Date: 11.05.2009
 * Time: 0:10:03
 */
public class PreparedPlotElement {
    private Integer[] x;
    private Integer[] y;
    private Integer[] xDist;
    private Integer[] yDist;
    private Color[] color;

    public PreparedPlotElement(Integer[] x, Integer[] y,Integer[] xDist, Integer[] yDist, Color[] color) {
        this.x = x;
        this.y = y;
        this.xDist = xDist;
        this.yDist = yDist;
        this.color = color;
    }

    public Integer[] getX() {
        return x;
    }

    public Integer[] getY() {
        return y;
    }

    public Color[] getColor() {
        return color;
    }

    public Integer[] getXDist() {
        return xDist;
    }

    public Integer[] getYDist() {
        return yDist;
    }
}
