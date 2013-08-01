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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static org.solovyev.common.graphs.Graphs.getGraphEdges;
import static org.solovyev.common.graphs.Graphs.newEdgeValueIsNotSupportedException;

class AdjacencyListGraph<NodeValue> implements MutableGraph<AdjacencyListNode<NodeValue>, GraphEdge<AdjacencyListNode<NodeValue>>, NodeValue, Void> {

	private final List<AdjacencyListNode<NodeValue>> nodes = new ArrayList<AdjacencyListNode<NodeValue>>();

	@Override
	public void addNode(@Nonnull AdjacencyListNode<NodeValue> node) {
		nodes.add(node);
	}

	@Override
	public void addNeighbour(@Nonnull AdjacencyListNode<NodeValue> node, @Nonnull AdjacencyListNode<NodeValue> neighbour) {
		if (!nodes.contains(neighbour)) {
			addNode(neighbour);
		}

		node.addNeighbour(neighbour);
		neighbour.addNeighbour(node);
	}

	@Override
	public void setNodeValue(@Nonnull AdjacencyListNode<NodeValue> node, @Nullable NodeValue value) {
		node.setValue(value);
	}

	@Nonnull
	@Override
	public Collection<AdjacencyListNode<NodeValue>> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	@Override
	public void setEdgeValue(@Nonnull GraphEdge<AdjacencyListNode<NodeValue>> edge, @Nullable Void edgeValue) {
		throw newEdgeValueIsNotSupportedException();
	}

	@Override
	public boolean isAdjacent(@Nonnull AdjacencyListNode<NodeValue> n1, @Nonnull AdjacencyListNode<NodeValue> n2) {
		return n1.getNeighbours().contains(n2);
	}

	@Nonnull
	@Override
	public Collection<AdjacencyListNode<NodeValue>> getNeighbours(@Nonnull AdjacencyListNode<NodeValue> node) {
		return node.getNeighbours();
	}

	@Nonnull
	@Override
	public Collection<GraphEdge<AdjacencyListNode<NodeValue>>> getEdges(@Nonnull AdjacencyListNode<NodeValue> node) {
		return getGraphEdges(this, node);
	}

	@Nullable
	@Override
	public NodeValue getNodeValue(@Nonnull AdjacencyListNode<NodeValue> node) {
		return node.getValue();
	}

	@Nullable
	@Override
	public Void getEdgeValue(@Nonnull GraphEdge<AdjacencyListNode<NodeValue>> edge) {
		throw newEdgeValueIsNotSupportedException();
	}
}
