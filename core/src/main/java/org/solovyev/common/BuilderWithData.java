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

import javax.annotation.Nonnull;

/**
 * User: serso
 * Date: 4/28/12
 * Time: 1:42 AM
 */

/**
 * Builder with data - creates object of specified type T with some data needed on the time of creation
 * NOTE: if no data is needed use {@link org.solovyev.common.Builder}
 *
 * @see org.solovyev.common.Builder
 */
public interface BuilderWithData<T, D> {

	@Nonnull
	T build(D data);
}
