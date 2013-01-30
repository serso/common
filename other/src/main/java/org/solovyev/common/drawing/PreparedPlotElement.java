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

/**
 * User: serso
 * Date: 11.05.2009
 * Time: 0:10:03
 */
public class PreparedPlotElement {
    private Integer[] x;
    private Integer[] y;
    private Integer[] xDist;
    private Integer[] yDist;
    private Color[] color;

    public PreparedPlotElement(Integer[] x, Integer[] y,Integer[] xDist, Integer[] yDist, Color[] color) {
        this.x = x;
        this.y = y;
        this.xDist = xDist;
        this.yDist = yDist;
        this.color = color;
    }

    public Integer[] getX() {
        return x;
    }

    public Integer[] getY() {
        return y;
    }

    public Color[] getColor() {
        return color;
    }

    public Integer[] getXDist() {
        return xDist;
    }

    public Integer[] getYDist() {
        return yDist;
    }
}
