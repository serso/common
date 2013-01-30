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
public class JStringsTest {

    @Test
    public void testGenerateRandomString() throws Exception {
        final Map<String, String> generatedStrings = new HashMap<String, String>(100);

        for (int i = 0; i < 100; i++ ) {
            final String newString = JStrings.generateRandomString(100);
            final String oldString = generatedStrings.get(newString);
            Assert.assertNull("Iteration " + i + "\nNew string: " + newString + "\nOld string: " + oldString, oldString);
            generatedStrings.put(newString, newString);
        }
    }
}
