package org.solovyev.common.collections.tree;

import junit.framework.Assert;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import org.solovyev.common.JPredicate;


/**
 * User: serso
 * Date: 4/1/12
 * Time: 11:08 PM
 */
public class TreeImplTest {
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
    @NotNull
    static MutableTree<Integer> createMockTree() {
        final MutableTree<Integer> result = Trees.newMutableTree(1);

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
    public void testRemoveNodeIf() throws Exception {
        // remove all even elements
        MutableTree<Integer> tree = createMockTree();
        tree.removeNodeIf(new JPredicate<TreeNode<Integer>>() {
            @Override
            public boolean apply(@Nullable TreeNode<Integer> input) {
                return input.getData() % 2 == 0;
            }
        });

        Assert.assertEquals(3, tree.getSize());
        for (TreeNode<Integer> node : tree) {
            Assert.assertTrue(node.getData() % 2 != 0);
        }

        // remove all elements
        tree = createMockTree();
        tree.removeNodeIf(new JPredicate<TreeNode<Integer>>() {
            @Override
            public boolean apply(@Nullable TreeNode<Integer> input) {
                return true;
            }
        });
        Assert.assertEquals(1, tree.getSize());



        // remove all odd elements
        tree = createMockTree();
        tree.removeNodeIf(new JPredicate<TreeNode<Integer>>() {
            @Override
            public boolean apply(@Nullable TreeNode<Integer> input) {
                return input.getData() % 2 != 0;
            }
        });

        Assert.assertEquals(5, tree.getSize());
        for (TreeNode<Integer> node : tree) {
            if (node != tree.getRoot()) {
                Assert.assertTrue(node.getData() % 2 == 0);
            }
        }
    }
}
