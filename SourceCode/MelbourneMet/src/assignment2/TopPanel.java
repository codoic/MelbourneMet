/**
 * TopPanel : Responsible for zooming in and zooming out MetMap Panel
 *
 * @author Nitesh and Harjot
 */
package assignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * TopPanel : Responsible for zooming in and zooming out MetMap Panel
 *
 * @author Nitesh and Harjot
 */
public class TopPanel extends JPanel {

    private JButton zoomIn;
    private JButton zoomOut;
    private MetMap m;
    private int count = 1;

    public TopPanel(MetMap pane) {
        zoomIn = new JButton("ZoomIn+");
        zoomOut = new JButton("ZoomOut-");
        this.m = pane;

        ButtonListerner buttonListener = new ButtonListerner();

        zoomIn.addActionListener(buttonListener);
        zoomOut.addActionListener(buttonListener);

        add(zoomIn);
        add(zoomOut);
    }

    class ButtonListerner implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == zoomIn) {
                count++;
                m.setZoomSize(count);
            }
            if (e.getSource() == zoomOut) {
                count--;
                if (count < 1) {
                    m.setNormalSize();
                    count = 1;
                    JOptionPane.showMessageDialog(new JFrame(), "This is the Minimum zoom out available!");
                } else {
                    m.setZoomSize(count);
                }

            }
        }
    }
}
