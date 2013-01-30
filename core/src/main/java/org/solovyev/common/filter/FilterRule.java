
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

package org.solovyev.common.filter;

/**
 * User: serso
 * Date: Jan 18, 2010
 * Time: 10:53:37 AM
 */

/**
 * Class using in list filtration
 * @param <T> type of object in list
 */
public interface FilterRule<T> {

	/**
	 * Method indicates filtration of element
	 * @param object current element in list
	 * @return 'true' if element is filtered of 'false' otherwise
	 */
	public boolean isFiltered(T object) ;
}

