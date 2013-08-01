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
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.graphs;


import java.util.Collection;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class MutableGraphTest<N, E> {

	@Nonnull
	private MutableGraph<N, E, Integer, ?> graph;

	@Before
	public void setUp() throws Exception {
		graph = newGraph();
	}

	@Nonnull
	protected abstract MutableGraph<N, E, Integer, ?> newGraph();

	@Nonnull
	protected abstract N newNode(int value);

	@Test
	public void testNodeShouldBeAdded() throws Exception {
		addAndCheckNode(2, 1);
	}

	private void addAndCheckNode(Integer value, int graphSize) {
		final N node = newNode(value);
		graph.addNode(node);

		final Collection<N> nodes = graph.getNodes();
		assertEquals(graphSize, nodes.size());
		assertEquals(value, graph.getNodeValue(node));
	}

	@Test
	public void testNodesShouldBeAdded() throws Exception {
		addAndCheckNode(10, 1);
		addAndCheckNode(0, 2);
		addAndCheckNode(3, 3);
		addAndCheckNode(16, 4);
		addAndCheckNode(22, 5);
		addAndCheckNode(1, 6);
	}

	@Test
	public void testShouldAddNeighbour() throws Exception {
		final N node = newNode(1);
		graph.addNode(node);
		final N neighbour = newNode(10);
		graph.addNeighbour(node, neighbour);

		assertEquals(Integer.valueOf(2), (Integer)graph.getNodes().size());
		assertTrue(graph.getNeighbours(node).contains(neighbour));
		assertTrue(graph.getNeighbours(neighbour).contains(node));
	}
}
