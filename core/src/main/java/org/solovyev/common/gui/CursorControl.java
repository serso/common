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

package org.solovyev.common.gui;

/**
 * User: serso
 * Date: 9/13/11
 * Time: 12:08 AM
 */

/**
 * Interface for controlling the cursor position
 */
public interface CursorControl {

    /**
     * Method sets the cursor to the beginning
     */
    public void setCursorOnStart();

    /**
     * Method sets the cursor to the end
     */
    public void setCursorOnEnd();

    /**
     * Method moves cursor to the left of current position
     */
    public void moveCursorLeft();

    /**
     * Method moves cursor to the right of current position
     */
    public void moveCursorRight();
}
