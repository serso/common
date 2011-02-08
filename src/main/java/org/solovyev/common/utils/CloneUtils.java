package org.solovyev.common.utils;

import org.solovyev.common.definitions.SimpleCloneable;

import java.util.List;
import java.util.ArrayList;

/**
 * User: serso
 * Date: 13.04.2009
 * Time: 11:26:54
 */
public class CloneUtils {
    public static <T extends SimpleCloneable<T>> List<T> deepListCloning( List<T> source ) {
        List<T> result = new ArrayList<T>();
        for ( T element: source ) {
            result.add( element.clone() );
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <T extends SimpleCloneable<T>> T[] deepArrayCloning( T[] array ) {
        Object[] result = new Object[array.length];
        for ( int i = 0; i < array.length; i++ ) {
            result[i] = array[i].clone();
        }
        return (T[])result;
    }
}
