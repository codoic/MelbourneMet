/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSlider;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * BottomPanel: allows user to set the current time of the day and view all the
 * trains.
 *
 * @author Nitesh and Harjot
 *
 */
public class BottomPanel extends JPanel {

    private JLabel journey;
    private JComboBox jList;
    private JLabel time;
    private JTextField timeField;
    private JLabel timeInc;
    private JSlider timeSlider;
    private JButton button;
    private MetMap panel;
    private Trains trains;

    public BottomPanel(MetMap panel,final Trains trains) {

        setLayout(new FlowLayout());
        journey = new JLabel("Journey");
        this.panel = panel;
        this.trains = trains;
        time = new JLabel("Enter Time");
        timeField = new JTextField("00:00", 8);

        button = new JButton("View Trains");
        //create new ButtonHandler for button event handling
        ButtonHandler handler = new ButtonHandler();
        button.addActionListener(handler);

        timeInc = new JLabel("Time Increment");
        timeSlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 0);

        Dimension d = timeSlider.getPreferredSize();
        timeSlider.setPreferredSize(new Dimension(d.width + 250, d.height + 100));
        timeSlider.setMajorTickSpacing(1);
        timeSlider.setPaintTicks(true);
        timeSlider.setPaintLabels(true);
        //register JSlider event listener 
        //Anonymous inner class 
        timeSlider.addChangeListener(
                new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
               trains.timeIncrement(timeSlider.getValue());
            }
        });

        add(time);
        add(timeField);
        add(button);
        add(timeInc);
        add(timeSlider);

    }

    private class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button) {
                String date = timeField.getText();
                diplayTrains(date);

            }
        }

        /**
         * Display all the trains for given date
         */
        public void diplayTrains(String date) {
            trains.findTrains(date);
        }
    }
}
