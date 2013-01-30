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

package org.solovyev.common.drawing;

import java.awt.*;
import java.util.Arrays;

/**
 * User: serso
 * Date: 10.05.2009
 * Time: 23:39:22
 */
public class ColorBundle {


    private final static java.util.List<Color> colors = Arrays.asList(Color.black,
            Color.blue,
            Color.cyan,
            Color.darkGray,
            Color.gray,
            Color.green,
            Color.lightGray,
            Color.magenta,
            Color.orange,
            Color.pink,
            Color.red,
            Color.yellow);

    private static int index = 0;

    public static void init() {
        index = 0;
    }

    public static Color getNextColor() {
        if (index >= colors.size()) {
            index = 0;
        }
        return colors.get(index++);
    }
}
