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
        Assert.assertEquals(createMockTree().toString(), "1\n" +
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
                "    14\n");
    }

    @Test
    public void testHasNext() throws Exception {
        createMockTree();
    }

    /*

1
├──── 2
│     ├──── 3
│     │     ├──── 4
│     │     └──── 5
│     └───── 6
├──── 7
├──── 8
│     ├──── 9
│     └──── 10
└──── 11
      └──── 12
            └──── 13
                  └──── 14
     */
    private Tree<Integer> createMockTree() {
        final MutableTree<Integer> result = SimpleTree.newInstance(1);

        MutableTreeNode<Integer> root = result.getRoot();
        MutableTreeNode<Integer> child2 = root.addChild(2);
        MutableTreeNode<Integer> child3 = child2.addChild(3);
        child3.addChild(4);
        child3.addChild(5);
        child2.addChild(6);
        root.addChild(7);
        MutableTreeNode<Integer> child8 = root.addChild(8);
        child8.addChild(9);
        child8.addChild(10);
        root.addChild(11).addChild(12).addChild(13).addChild(14);


        return result;
    }

    @Test
    public void testNext() throws Exception {

    }

    @Test
    public void testRemove() throws Exception {

    }
}
