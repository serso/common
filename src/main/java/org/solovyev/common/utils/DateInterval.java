/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.common.utils;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * User: serso
 * Date: May 11, 2010
 * Time: 11:34:00 PM
 */
public class DateInterval implements Cloneable {

	private Date leftBorder;
    private Date rightBorder;
    private boolean isLeftBorderIn;
    private boolean isRightBorderIn;
    public static final Date MIN_DATE = new Date(0);

    public DateInterval() {
        this.init();
    }

    public DateInterval(Date leftBorder, Date rightBorder) {
        this(leftBorder, true, rightBorder, true);
    }

    public DateInterval(Date leftBorder, boolean leftBorderIn, Date rightBorder, boolean rightBorderIn) {
        this.init(leftBorder, leftBorderIn, rightBorder, rightBorderIn);
    }

    public Date getLeftBorder() {
        return leftBorder;
    }

    public Date getRightBorder() {
        return rightBorder;
    }

    public void init() {
        this.init(MIN_DATE, true, MIN_DATE, true);
    }

    public void init(Date leftBorder, boolean isLeftBorderIn, Date rightBorder, boolean isRightBorderIn) {
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.isLeftBorderIn = isLeftBorderIn;
        this.isRightBorderIn = isRightBorderIn;
    }

    public void setLeftBorder(Date leftBorder) {
        this.leftBorder = leftBorder;
    }

    public void setRightBorder(Date rightBorder) {
        this.rightBorder = rightBorder;
    }

    public long getNumberOfDaysInInterval() {
        long delta = DateUtils.deltaDays(this.leftBorder, this.rightBorder);
        if (isReversed()) {
            delta *= -1;
        }
        if (!isLeftBorderIn && delta > 0) {
            delta -= 1;
        }

        if (!isRightBorderIn && delta > 0) {
            delta -= 1;
        }
        return delta;
    }

    public boolean isInInterval(Date value) {
        return isLater(leftBorder, value, !isLeftBorderIn) && isLater(value, rightBorder, !isRightBorderIn);
    }

    private static boolean isLater(Date v1, Date v2, boolean isStrict) {
        boolean result;
        if (isStrict) {
            result = DateUtils.deltaDays(v1, v2) > 0;
        } else {
            result = DateUtils.deltaDays(v1, v2) >= 0;
        }
        return result;
    }

    public boolean isEmptyInterval() {
        return DateUtils.deltaDays(leftBorder, rightBorder) == 0;
    }

    public boolean isFakeInterval() {
        return DateUtils.deltaDays(leftBorder, MIN_DATE) == 0 && DateUtils.deltaDays(rightBorder, MIN_DATE) == 0;
    }

    public long daysToRightBorder(Date value) {
        return DateUtils.deltaDays(value, rightBorder);
    }

    public long distToLeftBorder(Date value) {
        return DateUtils.deltaDays(leftBorder, value);
    }

    public boolean isReversed() {
        return DateUtils.deltaDays(leftBorder, rightBorder) < 0;
    }

    public boolean isSet() {
        return leftBorder != null && rightBorder != null;
    }

    public void normalReverse() {
        if (isReversed()) {
            reverse();
        }
    }

    private void reverse() {
        Date tmp = this.leftBorder;
        this.leftBorder = this.rightBorder;
        this.rightBorder = tmp;

        boolean tmp1 = this.isLeftBorderIn;
        this.isLeftBorderIn = this.isRightBorderIn;
        this.isRightBorderIn = tmp1;

    }

    public static DateInterval intersection(DateInterval interval1, DateInterval interval2) {
        //todo serso: create support for leftBorderIn, RightBorderIn
        DateInterval result = new DateInterval();
        DateInterval int1 = interval1.clone();
        DateInterval int2 = interval2.clone();
        int1.normalReverse();
        int2.normalReverse();
        if (DateUtils.deltaDays(int1.leftBorder, int2.leftBorder) >= 0) {
            if (DateUtils.deltaDays(int2.leftBorder, int1.rightBorder) >= 0) {
                result.setLeftBorder(int2.leftBorder);
                if (DateUtils.deltaDays(int1.rightBorder, int2.rightBorder) >= 0) {
                    result.setRightBorder(int1.rightBorder);
                } else {
                    result.setRightBorder(int2.rightBorder);
                }
            }
        } else {
            if (DateUtils.deltaDays(int1.leftBorder, int2.rightBorder) >= 0) {
                result.setLeftBorder(int1.leftBorder);
                if (DateUtils.deltaDays(int2.rightBorder, int1.rightBorder) >= 0) {
                    result.setRightBorder(int2.rightBorder);
                } else {
                    result.setRightBorder(int1.rightBorder);
                }
            }
        }
        return result;
    }

    @Override
    public DateInterval clone() {
        DateInterval clone = null;
        try {
            clone = (DateInterval) super.clone();
            if (this.leftBorder != null) {
                clone.leftBorder = (Date) this.leftBorder.clone();
            }
            if (this.rightBorder != null) {
                clone.rightBorder = (Date) this.rightBorder.clone();
            }
        } catch (CloneNotSupportedException e) {
            Logger.getLogger(this.getClass()).error(e.getMessage(), e);
        }
        return clone;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof DateInterval) {
            DateInterval int1 = this.clone();
            DateInterval int2 = ((DateInterval) obj).clone();
            int1.normalReverse();
            int2.normalReverse();
            if (DateUtils.deltaDays(int1.getLeftBorder(), int2.getLeftBorder()) == 0 && DateUtils.deltaDays(int1.getRightBorder(), int2.getRightBorder()) == 0
                    && int1.isLeftBorderIn == int2.isLeftBorderIn && int1.isRightBorderIn == int2.isRightBorderIn) {
                result = true;
            }
        }
        return result;
    }

    public void moveToNextPeriod(Date rightBorder) {
        this.setLeftBorder(this.getRightBorder());
        this.setRightBorder(rightBorder);
    }


    public void moveToPrevPeriod(Date leftBorder) {
        this.setRightBorder(this.getLeftBorder());
        this.setLeftBorder(leftBorder);
    }

    public boolean isLeftBorderIn() {
        return isLeftBorderIn;
    }

    public void setLeftBorderIn(boolean leftBorderIn) {
        isLeftBorderIn = leftBorderIn;
    }

    public boolean isRightBorderIn() {
        return isRightBorderIn;
    }

    public void setRightBorderIn(boolean rightBorderIn) {
        isRightBorderIn = rightBorderIn;
    }
}
