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
import javax.annotation.Nullable;

import static java.util.Collections.unmodifiableList;
import static org.solovyev.common.graphs.Graphs.getGraphEdges;
import static org.solovyev.common.graphs.Graphs.newEdgeValueIsNotSupportedException;
import static org.solovyev.common.graphs.Graphs.newNodeValueIsNotSupportedException;

class DenseIntAdjacencyListGraph implements MutableGraph<Integer, GraphEdge<Integer>, Integer, Integer> {

	private final List<List<Integer>> nodes = new ArrayList<List<Integer>>();

	@Override
	public void addNode(@Nonnull Integer node) {
		int nodesToAdd = node - nodes.size() + 1;
		if (nodesToAdd >= 0) {
			while (nodesToAdd >= 0) {
				nodes.add(null);
				nodesToAdd--;
			}
			nodes.set(node, new ArrayList<Integer>());
		} else {
			if (nodes.get(node) == null) {
				nodes.set(node, new ArrayList<Integer>());
			}
		}
	}

	@Override
	public void addNeighbour(@Nonnull Integer node, @Nonnull Integer neighbour) {
		addNode(neighbour);

		getMutableNeighbours(node).add(neighbour);
		getMutableNeighbours(neighbour).add(node);
	}

	@Override
	public void setNodeValue(@Nonnull Integer node, @Nullable Integer value) {
		throw newNodeValueIsNotSupportedException();
	}

	@Override
	public void setEdgeValue(@Nonnull GraphEdge<Integer> edge, @Nullable Integer value) {
		throw newEdgeValueIsNotSupportedException();
	}

	@Override
	public boolean isAdjacent(@Nonnull Integer n1, @Nonnull Integer n2) {
		return getMutableNeighbours(n1).contains(n2);
	}

	@Nonnull
	@Override
	public Collection<Integer> getNodes() {
		final List<Integer> result = new ArrayList<Integer>(nodes.size());

		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i) != null) {
				result.add(i);
			}
		}

		return result;
	}

	@Nonnull
	@Override
	public Collection<Integer> getNeighbours(@Nonnull Integer node) {
		return unmodifiableList(getMutableNeighbours(node));
	}

	@Nonnull
	private List<Integer> getMutableNeighbours(@Nonnull Integer node) {
		final List<Integer> neighbours = nodes.get(node);
		if (neighbours == null) {
			return new ArrayList<Integer>(1);
		} else {
			return neighbours;
		}
	}

	@Nonnull
	@Override
	public Collection<GraphEdge<Integer>> getEdges(@Nonnull Integer node) {
		return getGraphEdges(this, node);
	}

	@Nullable
	@Override
	public Integer getNodeValue(@Nonnull Integer node) {
		return node;
	}

	@Nullable
	@Override
	public Integer getEdgeValue(@Nonnull GraphEdge<Integer> edge) {
		throw newEdgeValueIsNotSupportedException();
	}
}
