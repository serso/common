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

package org.solovyev.common.text;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ListMapper<E> extends CollectionMapper<List<E>, E> {

	/*
	**********************************************************************
	*
	*                           CONSTRUCTORS
	*
	**********************************************************************
	*/

	private ListMapper(@Nonnull Parser<E> parser, @Nonnull Formatter<E> formatter, @Nonnull String delimiter) {
		super(parser, formatter, delimiter);
	}

	private ListMapper(@Nonnull Parser<E> parser, @Nonnull Formatter<E> formatter) {
		super(parser, formatter);
	}

	private ListMapper(@Nonnull Mapper<E> mapper, @Nonnull String delimiter) {
		super(mapper, delimiter);
	}

	private ListMapper(@Nonnull Mapper<E> mapper) {
		super(mapper);
	}

	@Nonnull
	public static <E> ListMapper<E> newInstance(@Nonnull Parser<E> parser, @Nonnull Formatter<E> formatter, @Nonnull String delimiter) {
		return new ListMapper<E>(parser, formatter, delimiter);
	}

	@Nonnull
	public static <E> ListMapper<E> newInstance(@Nonnull Parser<E> parser, @Nonnull Formatter<E> formatter) {
		return new ListMapper<E>(parser, formatter);
	}

	@Nonnull
	public static <E> ListMapper<E> newInstance(@Nonnull Mapper<E> mapper, @Nonnull String delimiter) {
		return new ListMapper<E>(mapper, delimiter);
	}

	@Nonnull
	public static <E> ListMapper<E> newInstance(@Nonnull Mapper<E> mapper) {
		return new ListMapper<E>(mapper);
	}

	/*
	**********************************************************************
	*
	*                           METHODS
	*
	**********************************************************************
	*/

	@Nonnull
	@Override
	protected List<E> newCollection() {
		return new ArrayList<E>();
	}
}
