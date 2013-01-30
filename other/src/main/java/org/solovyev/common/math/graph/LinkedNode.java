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

/**
 * User: serso
 * Date: 30.03.2009
 * Time: 11:56:31
 */
public class LinkedNode<T, N> implements Comparable<LinkedNode<T, N>>{
    private N arc;
    private Node<T, N> node;

    public LinkedNode(Node<T, N> node, N value) {
        this.node = node;
        this.arc = value;
    }

    public N getArc() {
        return arc;
    }

    public void setArc(N arc) {
        this.arc = arc;
    }

    public Node<T, N> getNode() {
        return node;
    }

    public void setNode(Node<T, N> node) {
        this.node = node;
    }

    public int compareTo(LinkedNode<T, N> o) {
        int result = 0;
        if ( o != null ) {
            if ( this.getNode().getId() < o.getNode( ).getId() ) {
                result = -1;
            } else if ( this.getNode().getId() > o.getNode().getId() ) {
                result = 1;
            }
        }
        return result;
    }
}
