package org.solovyev.common.math.graph;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;


/**
 * User: serso
 * Date: 06.04.2009
 * Time: 1:55:51
 */
public class GraphFrame extends JFrame {
    public GraphFrame(){
	}

    public void addGraphComponent(mxGraph graph) {
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
		getContentPane().add(graphComponent);
    }

	public void draw(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setVisible(true);
	}
}
