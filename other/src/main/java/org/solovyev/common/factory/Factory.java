/*
 * Copyright 2013 serso aka se.solovyev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

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
