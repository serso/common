package org.solovyev.common.factory;

/**
 * User: serso
 * Date: 31.03.2009
 * Time: 20:21:42
 */
public class Factory {

    @SuppressWarnings("unchecked")
    public static <T> T[] getArrayOf(Class<T> klass, int length){
        Object[] array = null;
        try {
            if ( length > 0 ) {
                array = new Object[length];
                for ( int i = 0; i < array.length; i++ ) {
                    array[i] = klass.newInstance();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T[])array;
    }

    public static <T> T newInstance(Class<T> klass) {
        T result = null;
        try {
            result = klass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
