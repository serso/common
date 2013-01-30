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

package org.solovyev.common.math;

/**
 * User: serso
 * Date: 9/19/11
 * Time: 9:31 PM
 */
public class LinearNormalizer implements Normalizer {

    private final double min;
    private final double max;

    public LinearNormalizer(double min, double max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public double normalize(double value) {
        if ((max - min) != 0d) {
            return (value - min) / (max - min);
        } else {
            return 1d;
        }
    }

    @Override
    public double denormalize(double value) {
        return min + value * (max - min);
    }

}
