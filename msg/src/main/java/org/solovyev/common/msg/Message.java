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

package org.solovyev.common.msg;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 10:45:56 PM
 */

/**
 * Interface represents translatable user message.
 * Implementation of this class will likely contains
 * some logic for translation message according to
 * it's message code and list of parameters.
 */
public interface Message {

    /**
     * @return message code
     */
	@NotNull
	public String getMessageCode();

    /**
     * @return list of message parameters
     */
	@NotNull
	public List<Object> getParameters();

    /**
     * @return message level
     */
	@NotNull
	public MessageLevel getMessageLevel();

    /**
     *
     * @param locale locate to which current message should be translated
     *
     * @return message string translated to specified locale
     */
	@NotNull
	public String getLocalizedMessage(@NotNull Locale locale);

    /**
     *
     * @return message string translated to deault locale (Locale.getDefault())
     */
	@NotNull
	public String getLocalizedMessage();
}
