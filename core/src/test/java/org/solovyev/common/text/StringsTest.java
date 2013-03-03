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

import junit.framework.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * User: serso
 * Date: 9/13/12
 * Time: 8:48 PM
 */
public class StringsTest {

    @Test
    public void testGenerateRandomString() throws Exception {
        final Map<String, String> generatedStrings = new HashMap<String, String>(100);

        for (int i = 0; i < 100; i++ ) {
            final String newString = Strings.generateRandomString(100);
            final String oldString = generatedStrings.get(newString);
            Assert.assertNull("Iteration " + i + "\nNew string: " + newString + "\nOld string: " + oldString, oldString);
            generatedStrings.put(newString, newString);
        }
    }

    @Test
    public void testToCharacterObjects() throws Exception {
        char[] chars = new char[]{'2', '3', 'a'};
        Character[] characters = Strings.toObjects(chars);
        Assert.assertTrue(chars.length == characters.length);
        for (int i = 0; i < characters.length; i++) {
            Character character = characters[i];
            char ch = chars[i];
            Assert.assertTrue(character.equals(ch));

        }

    }
}
