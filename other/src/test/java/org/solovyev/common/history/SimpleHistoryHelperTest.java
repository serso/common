package org.solovyev.common.history;

import junit.framework.Assert;
import org.junit.Test;

/**
 * User: serso
 * Date: 10/16/11
 * Time: 10:53 AM
 */
public class SimpleHistoryHelperTest {

	@Test
	public void testAddState() throws Exception {
		final HistoryHelper<Integer> history = SimpleHistoryHelper.newInstance(5);
		history.addState(1);
		history.addState(2);
		history.addState(3);
		history.addState(4);
		Assert.assertEquals("[1, 2, 3, 4]", history.getStates().toString());
		history.addState(5);
		Assert.assertEquals("[1, 2, 3, 4, 5]", history.getStates().toString());
		history.addState(6);
		Assert.assertEquals("[2, 3, 4, 5, 6]", history.getStates().toString());
		history.addState(6);
		Assert.assertEquals("[2, 3, 4, 5, 6]", history.getStates().toString());
		history.addState(7);
		Assert.assertEquals("[3, 4, 5, 6, 7]", history.getStates().toString());
		history.undo(7);
		Assert.assertEquals("[3, 4, 5, 6, 7]", history.getStates().toString());
		history.addState(8);
		Assert.assertEquals("[3, 4, 5, 6, 8]", history.getStates().toString());
		history.addState(9);
		Assert.assertEquals("[4, 5, 6, 8, 9]", history.getStates().toString());
	}

	@Test
	public void testClear() throws Exception {
		final HistoryHelper<Integer> history = SimpleHistoryHelper.newInstance(4);
		history.addState(1);
		history.addState(2);
		history.addState(3);
		history.addState(4);
		Assert.assertEquals("[1, 2, 3, 4]", history.getStates().toString());
		history.clear();
		Assert.assertFalse(history.isRedoAvailable());
		Assert.assertFalse(history.isUndoAvailable());
		history.addState(1);
		history.addState(2);
		history.addState(3);
		history.addState(4);
		Assert.assertEquals("[1, 2, 3, 4]", history.getStates().toString());
	}

	@Test
	public void testGetLastHistoryState() throws Exception {
		final HistoryHelper<Integer> history = SimpleHistoryHelper.newInstance(4);
		history.addState(1);
		history.addState(2);
		history.addState(3);
		history.addState(4);

		Assert.assertEquals(new Integer(4), history.getLastHistoryState());
		history.undo(4);
		Assert.assertEquals(new Integer(3), history.getLastHistoryState());
		history.redo(4);
		Assert.assertEquals(new Integer(4), history.getLastHistoryState());

		history.undo(4);
		Assert.assertEquals(new Integer(3), history.getLastHistoryState());
		history.undo(3);
		Assert.assertEquals(new Integer(2), history.getLastHistoryState());
		history.undo(2);
		Assert.assertEquals(new Integer(1), history.getLastHistoryState());
	}
}
