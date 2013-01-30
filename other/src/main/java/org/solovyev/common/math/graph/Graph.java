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
 * ---------------------------------------------------------------------
 * Contact details
 *
 * Email: se.solovyev@gmail.com
 * Site:  http://se.solovyev.org
 */

package org.solovyev.common.math.graph;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.JCloneable;
import org.solovyev.common.clone.Cloneables;
import org.solovyev.common.definitions.Empty;
import org.solovyev.common.definitions.MultiIdentity;
import org.solovyev.common.definitions.Property;
import org.solovyev.common.math.matrix.AbstractSparseMatrix;
import org.solovyev.common.math.matrix.Matrix;
import org.solovyev.common.text.TextDisplay;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: serso
 * Date: 28.03.2009
 * Time: 15:55:54
 */
public class Graph<T, N> implements TextDisplay, JCloneable<Graph<T, N>> {

    private List<Node<T, N>> nodes = new ArrayList<Node<T, N>>();
    private Integer currentUsedId = MultiIdentity.defaulUsedId;

    public Graph() {
    }

    public Graph(List<Node<T, N>> nodes) {
        this.nodes = nodes;
    }

    public List<Node<T, N>> getNodes() {
        return new ArrayList<Node<T, N>>(nodes);
    }

    public void setNodes(List<Node<T, N>> nodes) {
        this.nodes = nodes;
    }

    public Graph(Matrix<N> m) {
        if (m != null && !m.isEmpty() && m.getNumberOfColumns() == m.getNumberOfRows()) {
            Node<T, N> node;

            //creating nodes without linked nodes
            for (int i = 0; i < m.getNumberOfRows(); i++) {
                nodes.add(new Node<T, N>(null, i));
            }
            N tmp;
            //adding linked nodes

            if (m instanceof AbstractSparseMatrix) {
                List<List<Property<Double, Integer>>> rows = ((AbstractSparseMatrix)m).getRows();
                List<Property<Double, Integer>> row;
                for ( int i = 0; i < rows.size(); i++ ) {
                    row = rows.get(i);
                    if ( row != null ) {
                        node = this.nodes.get(i);
                        for ( Property<Double, Integer> el: row ) {
                            node.addLinkedNode(this.nodes.get(el.getId()), (N)el.getValue());
                        }
                    }
                }

            } else {
                for (int i = 0; i < m.getNumberOfRows(); i++) {
                    node = this.nodes.get(i);
                    for (int j = 0; j < m.getNumberOfColumns(); j++) {
                        tmp = m.get(i, j);
                        if (tmp instanceof Empty) {
                            if (!((Empty) tmp).isEmpty()) {
                                node.addLinkedNode(this.nodes.get(j), tmp);
                            }
                        } else if (tmp instanceof Number) {
                            if (Math.abs(((Number) tmp).doubleValue()) > 0) {
                                node.addLinkedNode(this.nodes.get(j), tmp);
                            }
                        }
                    }
                }
            }
        }
    }

    public Object[][] toMatrix() {
        Object[][] result = new Object[this.nodes.size()][this.nodes.size()];
        for (int i = 0; i < this.nodes.size(); i++) {
            for (int j = 0; j < this.nodes.size(); j++) {
                result[i][j] = 0d;
            }
        }
        for (Node<T, N> node : this.nodes) {
            for (LinkedNode<T, N> linkedNode : node.getLinkedNodes()) {
                result[node.getId()][linkedNode.getNode().getId()] = linkedNode.getArc();
            }
        }
        return result;
    }

    public void addNode(Node<T, N> node) {
        this.nodes.add(node);
    }

    public Node<T, N> getNode(Integer id) {
        Node<T, N> node = null;
        for (Node<T, N> n : this.nodes) {
            if (n.getId().equals(id)) {
                node = n;
                break;
            }
        }
        return node;
    }

    public void deleteNode(Integer id) {
        int i;
        for (i = 0; i < this.nodes.size(); i++) {
            if (this.nodes.get(i).getId().equals(id)) {
                this.nodes.remove(i);
                break;
            }
        }

        //if node was found and deleted
        if (i != this.nodes.size() - 1) {
            //deleting links
            for (Node n : this.nodes) {
                n.deleteLinkedNode(id);
            }
        }
    }

    public void textDisplay(PrintWriter out) {
        if (this.nodes.size() > 0) {
            for (Node node : this.nodes) {
                node.textDisplay(out);
                out.println();
            }
        } else {
            out.write("Graph is empty.");
            out.println();
        }
    }

    public List<Node<T, N>> getAdjSet(List<Node<T, N>> l) {
        List<Node<T, N>> result = new ArrayList<Node<T, N>>();
        for (Node<T, N> node : l) {
            for (LinkedNode<T, N> n1 : node.getLinkedNodes()) {
                if (!n1.getNode().isMarked()) {
                    n1.getNode().setMarked(true);
                    result.add(n1.getNode());
                }
            }
        }
        Collections.sort(result, new Graphs.NodesComparatorByDegree<T, N>());
        return result;
    }

    public void unmarkAll() {
        for (Node<T, N> node : this.getNodes()) {
            node.setMarked(false);
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public Graph<T, N> clone() {
        Graph<T, N> result = null;
        try {
            result = (Graph<T, N>) super.clone();

            //nodes list
            result.setNodes(Cloneables.deepListCloning(this.getNodes()));

            //adding linked nodes
            for (Node<T, N> node : this.nodes) {
                for (LinkedNode<T, N> linkedNode : node.getLinkedNodes()) {
                    result.nodes.get(node.getId()).addLinkedNode(result.nodes.get(linkedNode.getNode().getId()), linkedNode.getArc());
                }
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void addNodeId() {
        for (Node<T, N> node : this.nodes) {
            node.addNewId();
        }
    }

    public void addNewId() {
        //adding new id
        for (Node<T, N> node : this.nodes) {
            //new id is the same as current
            node.addNewId(node.getId());
        }
    }

    public void setCurrentUsedId(Integer currentUsedId) {
        this.currentUsedId = currentUsedId;
        for (Node<T, N> node : this.nodes) {
            node.setCurrentUsedId(currentUsedId);
        }
    }

    public Integer getCurrentUsedId() {
        return currentUsedId;
    }

    public void sortNodes(Comparator<Node<T, N>> c) {
        Collections.sort(this.nodes, c);
    }

}
