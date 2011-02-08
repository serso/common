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
