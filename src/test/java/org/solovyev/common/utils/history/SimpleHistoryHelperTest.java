package org.solovyev.common.utils.history;

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
		final HistoryHelper<Integer> history = new SimpleHistoryHelper<Integer>(5);
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
}
