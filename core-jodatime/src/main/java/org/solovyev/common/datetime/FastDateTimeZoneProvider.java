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

package org.solovyev.common.datetime;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.joda.time.DateTimeZone;
import org.joda.time.tz.Provider;

import java.util.*;

/**
 * User: serso
 * Date: 4/29/12
 * Time: 9:36 PM
 */
public class FastDateTimeZoneProvider implements Provider {

    @Nonnull
    public static final Set<String> availableIds = new HashSet<String>();

    static {
        availableIds.addAll(Arrays.asList(TimeZone.getAvailableIDs()));
    }

    @Nonnull
    public DateTimeZone getZone(@Nullable String id) {
        if (id == null) {
            return DateTimeZone.UTC;
        }

        final TimeZone tz = TimeZone.getTimeZone(id);
        if (tz == null) {
            return DateTimeZone.UTC;
        }

        int rawOffset = tz.getRawOffset();

        // sub-optimal. could be improved to only create a new Date every few minutes
        if (tz.inDaylightTime(new Date())) {
            rawOffset += tz.getDSTSavings();
        }

        return DateTimeZone.forOffsetMillis(rawOffset);
    }

    @Nonnull
    public Set<String> getAvailableIDs() {
        return availableIds;
    }
}

