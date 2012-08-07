package org.solovyev.common.utils;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.JCloneable;

import java.util.List;
import java.util.ArrayList;

/**
 * User: serso
 * Date: 13.04.2009
 * Time: 11:26:54
 */

/**
 * Class contains utility methods for cloning
 */
public final class CloneUtils {

    private CloneUtils() {
        throw new AssertionError("Not intended for instantiation!");
    }

    @NotNull
    public static <T extends JCloneable<T>> List<T> deepListCloning( @NotNull List<T> source ) {
        final List<T> result = new ArrayList<T>(source.size());

        // clone all elements in the list
        for ( T element: source ) {
            result.add( element.clone() );
        }

        return result;
    }

    @NotNull
    public static <T extends JCloneable<T>> T[] deepArrayCloning( @NotNull T[] array ) {
        final T[] result = (T[]) new Object[array.length];

        // clone all elements of array
        for ( int i = 0; i < array.length; i++ ) {
            result[i] = array[i].clone();
        }

        return result;
    }
}
