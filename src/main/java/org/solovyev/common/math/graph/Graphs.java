package org.solovyev.common.math.graph;

import com.mxgraph.view.mxGraph;

import java.util.*;

/**
 * User: serso
 * Date: 06.04.2009
 * Time: 2:55:14
 */
public class Graphs {
    public static mxGraph toMxGraph(Graph<?, ?> g, int displayWidth, int displayHeight, int xStartPos, int yStartPos) {
        mxGraph graph = new mxGraph();
        setMxGraph(g, displayWidth, displayHeight, xStartPos, yStartPos, graph);
        return graph;
    }

    private static void setMxGraph(Graph<?, ?> g, int displayWidth, int displayHeight, int xStartPos, int yStartPos, mxGraph graph) {
        Map<Integer, Object> map = new HashMap<Integer, Object>();
        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try {
            int i = 0;
            double width = 20d;
            double height = 20d;
            Object v;
            for (Node<?, ?> node : g.getNodes()) {
                v = graph.insertVertex(parent,
                        node.getId().toString(), node.getId(),
                        (Math.sin(2 * Math.PI * i / g.getNodes().size()) + 1) * displayWidth / 2 + xStartPos,
                        (Math.cos(2 * Math.PI * i / g.getNodes().size()) + 1) * displayHeight / 2 + yStartPos, width, height);
                map.put(node.getId(), v);
                i++;
            }
            for (Node<?, ?> node : g.getNodes()) {
                v = map.get(node.getId());
                for (LinkedNode<?, ?> linkedNode : node.getLinkedNodes()) {
                    graph.insertEdge(parent,
                            node.getId().toString() + linkedNode.getNode().getId().toString(),
                          //  linkedNode.getArc().toString(),
                            "",
                            v, map.get(linkedNode.getNode().getId()));
                }
            }
        }
        finally {
            graph.getModel().endUpdate();
        }
    }

    public static void addGraphToMxGraph( mxGraph mx, Graph<?, ?> g, int displayWidth, int displayHeight, int xStartPos, int yStartPos) {
        setMxGraph(g, displayWidth, displayHeight, xStartPos,yStartPos, mx);
    }



    public static <T, N> Node<T, N> findNodeWithSmallestDegree( List<Node<T, N>> nodes ) {
        Node<T, N> result = null;
        if ( nodes != null && nodes.size() > 0 ) {
            Collections.sort( nodes, new NodesComparatorByDegree<T, N>() );
            result = nodes.get(0);
        }
        return result;
    }

    public static class NodesComparator<T, N> implements Comparator<Node<T, N>> {
        private  int index = 0;
        private  int sortOrder = 0;

        public NodesComparator( int index, int sortOrder ) {
            this.index = index;
            this.sortOrder = sortOrder;
        }

        public int compare(Node<T, N> o1, Node<T, N> o2) {
            int result = 0;
            if ( o1 == null ) {
                result = 1;
            } else if ( o2 == null ) {
                result = -1;
            } else {
                if ( o1.getId(index) < o2.getId(index) ) {
                    result = -1;
                } else if ( o1.getId(index) > o2.getId(index) ) {
                    result = 1;
                }
            }
            return sortOrder * result;
        }
    }

    public static class NodesComparatorByDegree<T, N> implements Comparator<Node<T, N>> {

        public NodesComparatorByDegree(  ) {
        }

        public int compare(Node o1, Node o2) {
            int result = 0;
            if ( o1 == null ) {
                result = 1;
            } else if ( o2 == null ) {
                result = -1;
            } else if ( o1.getLinkedNodes() == null ){
                result = 1;
            } else if ( o2.getLinkedNodes() == null ) {
                result = -1;
            } else {
                if ( o1.getLinkedNodes().size() < o2.getLinkedNodes().size() ) {
                    result = -1;
                } else if ( o1.getLinkedNodes().size() > o2.getLinkedNodes().size() ) {
                    result = 1;
                }
            }
            return result;
        }
    }

    public static <T, N> void sortNodes ( Graph<T, N> g, Integer currentUsedId, Integer sortOrder ) {
        Collections.sort( g.getNodes(), new NodesComparator<T, N>( currentUsedId, sortOrder ) );
    }

    public static <T, N> void sortNodes ( Graph<T, N> g, Integer currentUsedId ) {
        sortNodes( g, currentUsedId, 1 );
    }

    public  static <T, N> void sortNodes ( Graph<T, N> g ) {
        sortNodes( g, g.getCurrentUsedId() );
    }

    /**
 * User: serso
     * Date: 30.03.2009
     * Time: 12:10:07
     */
        public static class ArcNodesComparator<T, N> implements Comparator<LinkedNode<T, N>> {

            private  int index = 0;

            public ArcNodesComparator( int index ) {
                this.index = index;
            }

            public int compare(LinkedNode<T, N> o1, LinkedNode<T, N> o2) {
                int result;
                if ( o1 == null ) {
                    result = 1;
                } else if ( o2 == null ) {
                    result = -1;
                } else {
                    if ( o1.getNode().getId(index) < o2.getNode().getId(index) ) {
                        result = -1;
                    } else if ( o1.getNode().getId(index) > o2.getNode().getId(index) ) {
                        result = 1;
                    } else {
                        result = 0;
                    }
                }
                return result;
            }
        }
}
