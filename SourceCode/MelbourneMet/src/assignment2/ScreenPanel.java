/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.BorderLayout;
import java.awt.Point;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * ScreenPanel: is the Screen for the Map. This controls the ViewPort 
 * @author Nitesh and Harjot
 */
public class ScreenPanel extends JPanel {

    private JScrollPane scroll;

    public ScreenPanel(TopPanel top, MetMap map, BottomPanel bottomPane) {
        setLayout(new BorderLayout());

        //code for scroll bar
        scroll = new JScrollPane(map, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JViewport viewport = scroll.getViewport();
        map.setViewport(viewport);

        //setting position of ViewPort 
        //this line of codes ensures that the screen is focused in centre of screen 
        Point vp = viewport.getViewPosition();
        vp.x = 500;
        vp.y = 500;
        viewport.setViewPosition(vp);

        add(top, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(bottomPane, BorderLayout.SOUTH);
    }
}
