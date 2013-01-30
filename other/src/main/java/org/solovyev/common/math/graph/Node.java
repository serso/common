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
import org.solovyev.common.definitions.IsMarked;
import org.solovyev.common.definitions.MultiIdentity;
import org.solovyev.common.text.TextDisplay;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: serso
 * Date: 28.03.2009
 * Time: 15:57:32
 */
public class Node<T, N> extends MultiIdentity<Integer> implements  Comparable<Node<T, N>>, TextDisplay,
        IsMarked, JCloneable<Node<T, N>> {

    private List<LinkedNode<T, N>> linkedNodes = new ArrayList<LinkedNode<T, N>>();
    private Object value;
    private boolean isMarked = false;

    public Node( T value, Integer id ) {
        this( value, null, id );
    }

    public Node( T value, List<LinkedNode<T, N>> linkedNodes, List<Integer> ids, Integer currentUsedId ) {
        super (ids, currentUsedId);
        this.value = value;
        if ( linkedNodes != null ) {
            this.linkedNodes.addAll(linkedNodes);
        }

    }

    public Node(  T value, List<LinkedNode<T, N>> linkedNodes, List<Integer> ids ) {
        this ( value, linkedNodes, ids, 0 );
    }

    public Node ( T value, List<LinkedNode<T, N>> linkedNodes, Integer id ) {
        super(id);
        this.value = value;
        if ( linkedNodes != null ) {
            this.linkedNodes.addAll(linkedNodes);
        }
    }

    public List<LinkedNode<T, N>> getLinkedNodes() {
        return linkedNodes;
    }

    public void setLinkedNodes(List<LinkedNode<T, N>> linkedNodes) {
        this.linkedNodes = linkedNodes;
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T)value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void addLinkedNode(Node<T, N> node, N value) {
        int i;

        for ( i = this.linkedNodes.size() - 1; i >= 0; i-- ) {
            if ( this.linkedNodes.get(i).getNode().getId() - node.getId() > 0 ) {
                break;
            }
        }

        if ( i == -1 ) {
            this.linkedNodes.add(0, new LinkedNode<T, N>(node, value));
        } else {
            this.linkedNodes.add(i, new LinkedNode<T, N>(node, value));
        }

        //todo serso: check this place
        //this really works faster

        this.linkedNodes.add( new LinkedNode<T, N>(node, value) );
        Collections.sort( this.linkedNodes );
    }

    public int compareTo(Node<T, N> o) {
        int result = 0;
        if ( o != null ) {
            if ( this.getId() < o.getId() ) {
                result = -1;
            } else if ( this.getId() > o.getId() ) {
                result = 1;
            }
        }
        return result;
    }

    public void deleteLinkedNode( Integer id ) {
        for ( int i = this.linkedNodes.size() - 1;  i >= 0; i-- ) {
            if ( this.linkedNodes.get( i ).getNode().getId().equals( id ) ) {
                linkedNodes.remove( i );
                break;
            }
        }
    }

    public void textDisplay(PrintWriter out, Boolean showLinkedNodes) {
        out.write("Node: id=" + this.getId().toString() + ", value=" + ((this.value == null) ? "null" : value.toString()));
        if ( showLinkedNodes ) {
            out.write(" Linked Node Ids: ");
            for (LinkedNode<T, N> linkedNode : this.linkedNodes) {
                out.write("(id: " + linkedNode.getNode().getId().toString() + ", dist: " + linkedNode.getArc().toString() + " )");
                if (this.linkedNodes.indexOf(linkedNode) != this.linkedNodes.size() - 1) {
                    out.write(", ");
                }
            }
        }
    }

    public void textDisplay(PrintWriter out) {
        this.textDisplay(out, true);
    }

    public boolean isMarked() {
        return this.isMarked;
    }

    public void setMarked( boolean isMarked) {
        this.isMarked = isMarked;
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public Node<T, N> clone() {
        Node<T, N> result = null;
        try {
            result = (Node<T, N>)super.clone();
            result.setIds( new ArrayList<Integer>( this.getIds() ) );
            result.setLinkedNodes( new ArrayList<LinkedNode<T, N>>() );
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
