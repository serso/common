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

package org.solovyev.common.math.visualisation;


import org.solovyev.common.math.algorithms.CutHillMcKeeAlgorithm;
import org.solovyev.common.drawing.Plot;
import org.solovyev.common.math.graph.Graph;
import org.solovyev.common.math.matrix.*;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.*;


/**
 * User: SerSo
 * Date: 27.04.2009
 * Time: 13:54:39
 */
public class CutHillMcKeeAlgorithmApp extends JFrame {

    public CutHillMcKeeAlgorithmApp() {
        super("visualisation.CutHillMcKeeAlgorithmApp");

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width - inset * 2,
                screenSize.height - inset * 2);

        JDesktopPane desktop = new JDesktopPane();
        this.setContentPane(desktop);
    }

    public static void main(final String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(args);
            }
        });
    }

    private static void createAndShowGUI(String[] args) {

        //Create and set up the window.
        CutHillMcKeeAlgorithmApp frame = new CutHillMcKeeAlgorithmApp();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.WHITE);
        //Display the window.
        frame.setVisible(true);
        try {
            PrintWriter out = new PrintWriter(System.out, true);

            Matrix<Double> m = new DoubleSparseMatrix(args[0], MatrixFileFormat.valueOf(args[1].toUpperCase()));

            CutHillMcKeeAlgorithm kma = new CutHillMcKeeAlgorithm();
			kma.init(new CutHillMcKeeAlgorithm.Input(new Graph<Object, Double>(m), false, false));

            kma.doAlgorithm();

            Matrix<Double> resultMatrix = new DoubleSparseMatrix(kma.getResult());

            frame.add(Plot.drawMatrix(10, 10, 400, 400, "Matrix after algorithm", Color.BLACK, resultMatrix));
            frame.add(Plot.drawMatrix(10, 10, 400, 400, "Both MatrixUtils", null, resultMatrix, m));
            frame.add(Plot.drawMatrix(10, 10, 400, 400, "Matrix before algorithm", Color.blue, m));

            out.write("Band width of original matrix: " + MatrixUtils.getBandWidth(m));
            out.println();

            out.write("Band width of matrix after algorithm: " + MatrixUtils.getBandWidth(resultMatrix));
            out.println();

            out.write("Profile of original matrix: " + MatrixUtils.getProfile(m));
            out.println();

            out.write("Profile of matrix after algorithm: " + MatrixUtils.getProfile(resultMatrix));
            //resultMatrix.save( args[0].substring(0, args[0].length()-4) + "_result.txt", MatrixFileFormat.SHORTED);
            out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
