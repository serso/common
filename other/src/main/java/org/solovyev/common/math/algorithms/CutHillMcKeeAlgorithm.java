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

package org.solovyev.common.math.algorithms;


import com.mxgraph.view.mxGraph;
import org.jetbrains.annotations.NotNull;
import org.solovyev.common.math.graph.Graph;
import org.solovyev.common.math.graph.GraphFrame;
import org.solovyev.common.math.graph.Graphs;
import org.solovyev.common.math.graph.Node;
import org.solovyev.common.math.matrix.*;
import org.solovyev.common.datetime.Timer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * User: SerSo
 * Date: 05.04.2009
 * Time: 23:28:23
 */
public class CutHillMcKeeAlgorithm extends AbstractAlgorithm<CutHillMcKeeAlgorithm.Input, Graph<Object, Double>> {

	public static class Input {
		@NotNull
		private final Graph<Object, Double> g;

		private final boolean showIntermediateResults;

		private final boolean drawGraphs;

		public Input(@NotNull Graph<Object, Double> g, boolean showIntermediateResults, boolean drawGraphs) {
			this.g = g;
			this.showIntermediateResults = showIntermediateResults;
			this.drawGraphs = drawGraphs;
		}
	}

    private Graph<Object, Double> result = null;

    public Graph<Object, Double> doAlgorithm() {
        PrintWriter out = new PrintWriter(System.out, true);

        //adding new id to nodes in graph
        input.g.addNewId();

        //finding node with smallest degree
        Node<Object, Double> v = Graphs.findNodeWithSmallestDegree(input.g.getNodes());
        if (input.showIntermediateResults) {
            out.println();
            out.write("First node with max degree: ");
            out.println();
            v.textDisplay(out, false);
        }
        if (v != null) {
            //result graph
            result = new Graph<Object, Double>();

            //adding root node to result graph
            result.addNode(v);

            //list of nodes: consists of nodes which are related with
            //nodes constructed on the previous step
            List<Node<Object, Double>> list = new ArrayList<Node<Object, Double>>();

            //1. Adding first node
            list.add(v);

            //setting second ID as 0 and marking
            v.setId(0, 1);
            v.setMarked(true);

            //id
            int count = 1;

            if (input.showIntermediateResults) {
                out.println();
                out.write("Adj lists: ");
                out.println();
            }

            //2. While exists set of related nodes to current
            while ((list = input.g.getAdjSet(list)).size() != 0) {
                for (Node<Object, Double> node : list) {
                    //setting id and adding node to result graph
                    node.setId(count++, 1);
                    result.addNode(node);

                    if (input.showIntermediateResults) {
                        node.textDisplay(out, false);
                        out.write("; ");
                    }
                }
                if (input.showIntermediateResults) {
                    out.println();
                }
            }

            input.g.unmarkAll();
            //not to affect source graph while swapping
            result.setCurrentUsedId(1);
            result = result.clone();

            result.sortNodes(new Graphs.NodesComparator<Object, Double>(result.getCurrentUsedId(), 1));

            if (input.showIntermediateResults) {

                out.println();
                out.write("Original graph: ");
                out.println();
                input.g.textDisplay(out);
                out.println();
                out.write("Original graph as matrix: ");
                out.println();
                Matrix<Double> m = new DoubleArrayMatrix(input.g);
                m.textDisplay(out);
                out.println();
                out.write("Result graph: ");
                out.println();
                result.textDisplay(out);
                out.println();
                out.write("Result graph as matrix: ");
                out.println();
                m = new DoubleArrayMatrix(result);
                m.textDisplay(out);
            }
            if (input.drawGraphs) {
                GraphFrame graphFrame = new GraphFrame();
                mxGraph mx = Graphs.toMxGraph(input.g, 800, 600, 0, 100);
                Graphs.addGraphToMxGraph(mx, result, 800, 600, 0, 800);
                graphFrame.addGraphComponent(mx);
                graphFrame.draw();
            }
        }

		return this.result;
    }

    public static void main(String[] arg) {
        try {
            if (arg != null && arg.length > 0) {
                PrintWriter out = new PrintWriter(System.out, true);

                Timer.startTask("Doing task");
                Timer.writeStartTimeString(out, "Doing task");

                Timer.startTask("Initializing matrix");
                Timer.writeStartTimeString(out, "Initializing matrix");
                Matrix<Double> m = new DoubleArrayMatrix(arg[0], MatrixFileFormat.valueOf(arg[1].toUpperCase()));
                // Matrix<Double> m = new AbstractSparseMatrix(arg[0], MatrixFileFormat.valueOf(arg[1].toUpperCase()));
                Timer.writeTimeString(out, "Initializing matrix");


                Timer.startTask("Initializing algorithm");
                Timer.writeStartTimeString(out, "Initializing algorithm");

                final CutHillMcKeeAlgorithm kma = new CutHillMcKeeAlgorithm();
				kma.init(new Input(new Graph<Object, Double>(m), false, false));

				Timer.writeTimeString(out, "Initializing algorithm");

                Timer.startTask("Doing algorithm");
                Timer.writeStartTimeString(out, "Doing algorithm");
                kma.doAlgorithm();
                Timer.writeTimeString(out, "Doing algorithm");


                Timer.writeTimeString(out, "Doing task");

                out.write("Band width of original matrix: " + MatrixUtils.getBandWidth(m));
                out.println();

                Matrix<Double> result = new DoubleSparseMatrix(kma.getResult());
                out.write("Band width of matrix after algorithm: " + MatrixUtils.getBandWidth(result));
                out.println();

                out.write("Profile of original matrix: " + MatrixUtils.getProfile(m));
                out.println();

                out.write("Profile of matrix after algorithm: " + MatrixUtils.getProfile(result));
                out.println();
            }
        } catch (IOException e) {
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("log.txt"));
                out.write("Internal error while opening file.");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
