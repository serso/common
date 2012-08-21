package org.solovyev.common.clone;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.JCloneable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public static <T extends JCloneable<T>> List<T> cloneList(@NotNull List<T> source) {
        return deepListCloning(source, new ArrayList<T>(source.size()));
    }

    @NotNull
    public static <T extends JCloneable<T>> List<T> cloneList(@NotNull List<T> source,
                                                              @NotNull List<T> destination) {
        return deepListCloning(source, destination);
    }

    @NotNull
    public static <T extends JCloneable<T>> List<T> deepListCloning(@NotNull List<T> source) {
        return deepListCloning(source, new ArrayList<T>(source.size()));
    }

    @NotNull
    public static <T extends JCloneable<T>> List<T> deepListCloning(@NotNull List<T> source,
                                                                    @NotNull List<T> destination) {
        // clone all elements in the list
        for (T element : source) {
            destination.add(element.clone());
        }

        return destination;
    }

    @NotNull
    public static <K, V extends JCloneable<V>> Map<K, V> cloneMap(@NotNull Map<K, V> source) {
        return cloneMap(source, new HashMap<K, V>(source.size()));
    }

    @NotNull
    public static <K, V extends JCloneable<V>> Map<K, V> cloneMap(@NotNull Map<K, V> source, @NotNull Map<K, V> destination) {
        for (Map.Entry<K, V> entry : source.entrySet()) {
            final V value = entry.getValue();
            if (value != null) {
                destination.put(entry.getKey(), value.clone());
            } else {
                destination.put(entry.getKey(), null);
            }
        }

        return destination;
    }

    @NotNull
    public static <K extends JCloneable<K>, V extends JCloneable<V>> Map<K, V> cloneMapWithKeys(@NotNull Map<K, V> source) {
        final Map<K, V> result = new HashMap<K, V>(source.size());

        for (Map.Entry<K, V> entry : source.entrySet()) {
            final V value = entry.getValue();
            final K key = entry.getKey();
            if (value != null) {
                result.put(key.clone(), value.clone());
            } else {
                result.put(key.clone(), null);
            }
        }

        return result;
    }

    @NotNull
    public static <T extends JCloneable<T>> T[] deepArrayCloning(@NotNull T[] array) {
        final T[] result = (T[]) new Object[array.length];

        // clone all elements of array
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].clone();
        }

        return result;
    }
}
