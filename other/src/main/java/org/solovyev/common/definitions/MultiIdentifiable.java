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

package org.solovyev.common.definitions;

import org.solovyev.common.Identifiable;

/**
 * User: serso
 * Date: 28.03.2009
 * Time: 15:59:27
 */
public interface MultiIdentifiable<T> extends Identifiable<T> {
    public T getId(Integer i);
    public int getNumberOfIds();
    public void addNewId(T id);
    public void addNewId();
    public Integer getCurrentUsedId();
}
