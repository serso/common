package org.solovyev.common.collections.tree;

import org.junit.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 4/1/12
 * Time: 5:39 PM
 */
public class DepthTreeIteratorTest {

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals("1\n" +
                " 2\n" +
                "  3\n" +
                "   4\n" +
                "   5\n" +
                "  6\n" +
                " 7\n" +
                " 8\n" +
                "  9\n" +
                "  10\n" +
                " 11\n" +
                "  12\n" +
                "   13\n" +
                "    14\n", SimpleTreeTest.createMockTree().toString());
    }

    @Test
    public void testHasNext() throws Exception {
        SimpleTreeTest.createMockTree();
    }

    @Test
    public void testNext() throws Exception {

    }

    @Test
    public void testRemove() throws Exception {

    }
}
