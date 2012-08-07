package org.solovyev.common.drawing;

import org.solovyev.common.math.matrix.Matrix;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * User: serso
 * Date: 10.05.2009
 * Time: 22:00:46
 */
public class Plot {
    public static void plot(PreparedPlotElement p, Graphics g) {
        for (int i = 0; i < p.getX().length; i++) {
            g.setColor(p.getColor()[i]);
            g.fillRect(p.getX()[i], p.getY()[i], p.getXDist()[i], p.getYDist()[i]);
        }
    }

    public static <T extends Number> JInternalFrame drawMatrix(int startX, int startY, int width, int height, Matrix<T>... matrixes) {
        return Plot.drawMatrix(startX, startY, width, height, "", Color.black, matrixes);
    }


    public static <T extends Number> JInternalFrame drawMatrix(int startX, int startY, int width, int height, String title, Color color, Matrix<T>... matrixes) {
        JInternalFrame jInternalFrame = new JInternalFrame(title, true, true, true, true);
        jInternalFrame.setBackground(Color.white);
        jInternalFrame.getContentPane().add(new PaintPanel<T>(startX, startY, width, height, color, matrixes));
        jInternalFrame.setBounds(0, 0,
                width + startX * 2 + 20,
                height + startY * 2 + 40);
        jInternalFrame.setVisible(true);
        return jInternalFrame;
    }

    private static class PaintPanel<T extends Number> extends JPanel {

        private java.util.List<PreparedPlotElement> preparedPlotElements = new ArrayList<PreparedPlotElement>();

        public PaintPanel(int startX, int startY, int width, int height, Color color, Matrix<T>... matrices) {
            ColorBundle.init();
            for ( Matrix<T> matrix: matrices ) {
                preparedPlotElements.add(  PlotElement.create(matrix, color == null ? ColorBundle.getNextColor() : color).prepare(startX, startY, width, height) );
            }
            System.gc();
        }

        protected void paintComponent(Graphics g) {
            super.paintComponents(g);
            for (PreparedPlotElement preparedPlotElement : preparedPlotElements) {
                Plot.plot(preparedPlotElement, g);
            }
        }
    }

}
