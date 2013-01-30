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

package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 6/9/12
 * Time: 8:40 PM
 */

/**
 * Common interface for clonable objects. Suppresses {@link CloneNotSupportedException}, has a explicit
 * {@link #clone()} method declaration and use type T for it's result
 * @param <T> type of cloned object
 */
public interface JCloneable<T extends JCloneable<T>> extends Cloneable {

    @SuppressWarnings("CloneDoesntDeclareCloneNotSupportedException")
    @NotNull
    T clone();
}
