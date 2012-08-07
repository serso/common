package org.solovyev.common;

import org.jetbrains.annotations.NotNull;

/**
 * User: serso
 * Date: 8/7/12
 * Time: 8:42 PM
 */

// MUST COMPILE =)
public class TestJObject extends JObject implements JCloneable<TestJObject> {

    @NotNull
    @Override
    public TestJObject clone() {
        return (TestJObject) super.clone();
    }

    private static class TestJObject2 extends TestJObject {

        @NotNull
        @Override
        public TestJObject2 clone() {
            return (TestJObject2) super.clone();
        }
    }
}
