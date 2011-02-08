package org.solovyev.common.utils;

/**
 * User: serso
 * Date: 18.05.2009
 * Time: 0:54:37
 */
public class Interval {

    private double start;
    private double end;

    public Interval(double start, double end) {
        this.start = start;
        this.end = end;
    }

    public double getEnd() {
        return end;
    }

    public double getStart() {
        return start;
    }

    public double dist() {
        return this.end - this.start;
    }
}
