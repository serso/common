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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org/java/jcl
 */

package java.lang.ref;

import org.jetbrains.annotations.Nullable;

/**
* User: serso
* Date: 2/1/13
* Time: 8:52 PM
*/

/**
 * This class represents reference which never will be cleared by GC.
 *
 * NOTE: located in java.lang.ref in order to access package local constructor
 * @param <R> referent type
 */
public class HardReference<R> extends Reference<R> {

    // field is used in order to have reference to the referent object => this object will never be freed
    @SuppressWarnings("FieldCanBeLocal")
    @Nullable
    private final R hardReferent;

    public HardReference(@Nullable R referent) {
        super(referent);
        this.hardReferent = referent;
    }
}
