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

package org.solovyev.common.math.calculators;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.equals.Equalizer;

/**
 * User: serso
 * Date: 3/6/11
 * Time: 11:30 AM
 */
public interface Calculator<T> extends Equalizer<T>{

	@NotNull
	Class<T> getObjectClass();

	@NotNull
	T multiply(@NotNull T l, @NotNull T r);

	@NotNull
	T divide(@NotNull T l, @NotNull T r);

	@NotNull
	T summarize(@NotNull T l, @NotNull T r);

	@NotNull
	T subtract (@NotNull T l, @NotNull  T r);

	@NotNull
	T getZero();

}
