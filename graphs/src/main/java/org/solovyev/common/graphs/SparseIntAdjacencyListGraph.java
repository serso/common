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
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Collections.binarySearch;
import static java.util.Collections.unmodifiableList;
import static org.solovyev.common.graphs.Graphs.getGraphEdges;
import static org.solovyev.common.graphs.Graphs.newEdgeValueIsNotSupportedException;
import static org.solovyev.common.graphs.Graphs.newNodeValueIsNotSupportedException;

public class SparseIntAdjacencyListGraph implements MutableGraph<SparseIntAdjacencyListGraph.Node, GraphEdge<SparseIntAdjacencyListGraph.Node>, Integer, Integer> {

	private final List<Node> nodes = new ArrayList<Node>();

	@Override
	public void addNode(@Nonnull Node node) {
		int i = 0;
		for (; i < nodes.size(); i++) {
			if(node.index < nodes.get(i).index) {
				break;
			}
		}

		if(i < nodes.size()) {
			nodes.add(i, node);
		} else {
			nodes.add(node);
		}
	}

	@Override
	public void addNeighbour(@Nonnull Node node, @Nonnull Node neighbour) {
		if (findNodeIndexInNodes(neighbour)  < 0) {
			addNode(neighbour);
		}

		getMutableNeighbours(node).add(neighbour);
		getMutableNeighbours(neighbour).add(node);
	}

	@Override
	public void setNodeValue(@Nonnull Node node, @Nullable Integer value) {
		throw newNodeValueIsNotSupportedException();
	}

	@Override
	public void setEdgeValue(@Nonnull GraphEdge<Node> edge, @Nullable Integer value) {
		throw newEdgeValueIsNotSupportedException();
	}

	@Override
	public boolean isAdjacent(@Nonnull Node n1, @Nonnull Node n2) {
		return getMutableNeighbours(n1).contains(n2);
	}

	@Nonnull
	@Override
	public Collection<Node> getNodes() {
		return Collections.unmodifiableList(nodes);
	}

	@Nonnull
	@Override
	public Collection<Node> getNeighbours(@Nonnull Node node) {
		return unmodifiableList(getMutableNeighbours(node));
	}

	@Nonnull
	private List<Node> getMutableNeighbours(@Nonnull Node node) {
		final int i = findNodeIndexInNodes(node);

		if (i >= 0) {
			return nodes.get(i).neighbours;
		} else {
			return new ArrayList<Node>(1);
		}
	}

	private int findNodeIndexInNodes(@Nonnull Node node) {
		return binarySearch(nodes, node, new NodeComparator());
	}

	@Nonnull
	@Override
	public Collection<GraphEdge<Node>> getEdges(@Nonnull Node node) {
		return getGraphEdges(this, node);
	}

	@Nullable
	@Override
	public Integer getNodeValue(@Nonnull Node node) {
		return node.index;
	}

	@Nullable
	@Override
	public Integer getEdgeValue(@Nonnull GraphEdge<Node> edge) {
		throw newEdgeValueIsNotSupportedException();
	}

	public static final class Node {
		private final List<Node> neighbours = new ArrayList<Node>();
		private final int index;

		Node(int index) {
			this.index = index;
		}
	}

	private static class NodeComparator implements Comparator<Node> {
		@Override
		public int compare(Node n1, Node n2) {
			if (n1.index < n2.index) {
				return -1;
			} else if (n1.index > n2.index) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
