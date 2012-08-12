package org.solovyev.common.compare;

import org.jetbrains.annotations.Nullable;
import org.solovyev.common.definitions.Identifiable;
import org.solovyev.common.equals.EqualsTool;

import java.util.Date;
import java.util.List;

public final class CompareTools {

    public static int comparePreparedObjects(Object value1, Object value2) {
        Integer result = compareObjectsOnNullness(value1, value2);

        if (result == null) {
            if (value1 instanceof Comparable && value2 instanceof Comparable) {
                //noinspection unchecked
                result = ((Comparable) value1).compareTo(value2);
            } else {
                result = 0;
            }
        }
        return result;
    }

    public static <T extends Comparable<T>> int comparePreparedObjects(@Nullable T l,
                                                                       @Nullable T r) {
        Integer result = compareObjectsOnNullness(l, r);

        if (result == null) {
            assert l != null;
            result = l.compareTo(r);

        }
        return result;
    }

    public static int comparePreparedObjects(List list1, List list2) {
        Integer result = compareObjectsOnNullness(list1, list2);

        if (result == null) {
            result = list1.size() - list2.size();
            if (result == 0) {
                for (int i = 0; i < list1.size(); i++) {
                    result = comparePreparedObjects(list1.get(i), list2.get(i));
                    if (result != 0) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    public static int comparePreparedObjects(Number value1, Number value2) {
        Integer result = compareObjectsOnNullness(value1, value2);

        if (result == null) {
            if (value1 instanceof Comparable && value2 instanceof Comparable) {
                //noinspection unchecked
                result = ((Comparable) value1).compareTo(value2);
            } else {
                result = 0;
            }
        }

        return result;
    }

    public static int comparePreparedObjects(Date value1, Date value2) {
        Integer result = compareObjectsOnNullness(value1, value2);
        if (result == null) {
            if (value1.before(value2)) {
                result = -1;
            } else if (value1.after(value2)) {
                result = 1;
            } else {
                result = 0;
            }
        }
        return result;
    }

    public static int comparePreparedObjects(int value1, int value2) {
        if (value1 > value2) {
            return 1;
        } else if (value1 == value2) {
            return 0;
        } else {
            return -1;
        }
    }

    public static int comparePreparedObjects(String value1, String value2, boolean ignoreCase) {
        Integer result = compareObjectsOnNullness(value1, value2);

        if (result == null) {
            if (ignoreCase) {
                result = value1.toLowerCase().compareTo(value2.toLowerCase());
            } else {
                result = value1.compareTo(value2);
            }
        }

        return result;
    }

    public static int comparePreparedObjects(Boolean value1, Boolean value2) {
        Integer result = compareObjectsOnNullness(value1, value2);

        if (result == null) {
            result = value1.compareTo(value2);
        }

        return result;
    }

    public static <T extends Comparable<T>> int compareIdentifiableObjects(@Nullable Identifiable<T> i1,
                                                                           @Nullable Identifiable<T> i2) {
        Integer result = compareObjectsOnNullness(i1, i2);

        if (result == null) {
            result = compareObjectsOnNullness(i1.getId(), i2.getId());
            if (result == null) {
                result = i1.getId().compareTo(i2.getId());
            }
        }
        return result;
    }


    /**
     * Method compares two object with equals() method. Null checking is provided.
     *
     * @param o1 first object for check
     * @param o2 second object for check
     * @return 'true' if object are equal by equals() method of if they are both nulls, 'false' otherwise
     */
    public static boolean equals(Object o1, Object o2) {
        // first compare on nullness
        Integer compareResult = compareObjectsOnNullness(o1, o2);

        // by default objects are unequal
        boolean result = false;

        if (compareResult == null) {
            // both objects are not nulls -> let's compare with equals
            result = o1.equals(o2);
        } else if (compareResult.equals(0)) {
            // both nulls -> they are equal
            result = true;
        }

        return result;
    }

    /**
     * Method compares objects according their nullness property
     *
     * @param o1 first compared object
     * @param o2 second compared object
     * @return if both objects are nulls then 0 (they are equal), if first is null then -1, if second is null then 1, otherwise - null
     */
    @Nullable
    public static Integer compareObjectsOnNullness(Object o1, Object o2) {
        Integer result;

        if (o1 == null && o2 == null) {
            result = EqualsTool.Result.BOTH_NULLS_CONST;
        } else if (o1 == null) {
            result = -1;
        } else if (o2 == null) {
            result = 1;
        } else {
            //both not nulls
            result = null;
        }

        return result;
    }

    /**
     * Method compares objects according their nullness property
     *
     * @param o1 first compared object
     * @param o2 second compared object
     * @return if both objects are nulls then 0 (they are equal), if first is null then -1, if second is null then 1, otherwise - null
     */
    public static EqualsTool.Result compareObjectsOnNullnessWithResult(Object o1, Object o2) {
        return EqualsTool.getEqualsResult(o1, o2);
    }
}
