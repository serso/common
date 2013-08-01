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
import java.util.List;

import javax.annotation.Nonnull;

public final class Graphs {

	private Graphs() {
		throw new AssertionError();
	}

	static UnsupportedOperationException newNodeValueIsNotSupportedException() {
		return new UnsupportedOperationException("Node value is not supported");
	}

	static UnsupportedOperationException newEdgeValueIsNotSupportedException() {
		return new UnsupportedOperationException("Edge value is not supported");
	}

	static <N> GraphEdge<N> newGraphEdge(@Nonnull N from, @Nonnull N to) {
		return new GraphEdge<N>(from, to);
	}

	static <N> Collection<GraphEdge<N>> getGraphEdges(@Nonnull Graph<N, ?, ?, ?> graph, @Nonnull N node) {
		final Collection<N> neighbours = graph.getNeighbours(node);
		final List<GraphEdge<N>> edges = new ArrayList<GraphEdge<N>>(neighbours.size());
		for (N neighbour : neighbours) {
			edges.add(new GraphEdge<N>(node, neighbour));
		}
		return edges;
	}

	@Nonnull
	public static <NV> MutableGraph<AdjacencyListNode<NV>, GraphEdge<AdjacencyListNode<NV>>, NV, Void> newAdjacencyListGraph() {
		return new AdjacencyListGraph<NV>();
	}

	@Nonnull
	public static <V> AdjacencyListNode<V> newAdjacencyListNode() {
		return new AdjacencyListNode<V>();
	}

	@Nonnull
	public static <V> AdjacencyListNode<V> newAdjacencyListNode(@Nonnull V value) {
		final AdjacencyListNode<V> result = newAdjacencyListNode();
		result.setValue(value);
		return result;
	}

	@Nonnull
	public static MutableGraph<Integer, GraphEdge<Integer>, Integer, Integer> newDenseIntAdjacencyListGraph() {
		return new DenseIntAdjacencyListGraph();
	}

	@Nonnull
	public static MutableGraph<SparseIntAdjacencyListGraph.Node, GraphEdge<SparseIntAdjacencyListGraph.Node>, Integer, Integer> newSparseIntAdjacencyListGraph() {
		return new SparseIntAdjacencyListGraph();
	}

	@Nonnull
	public static SparseIntAdjacencyListGraph.Node newSparseIntAdjacencyListNode(int value) {
		return new SparseIntAdjacencyListGraph.Node(value);
	}
}
