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
