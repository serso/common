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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface MutableGraph<Node, Edge, NodeValue, EdgeValue> extends Graph<Node, Edge, NodeValue, EdgeValue> {

	void addNode(@Nonnull Node node);

	void addNeighbour(@Nonnull Node node, @Nonnull Node neighbour);

	void setNodeValue(@Nonnull Node node, @Nullable NodeValue value);

	void setEdgeValue(@Nonnull Edge edge, @Nullable EdgeValue value);
}
