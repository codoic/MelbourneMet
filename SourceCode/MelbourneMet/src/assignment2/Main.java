/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;

/**
 * Main: setting the JFrame and all its components Instantiate all the objects
 * required for project. Creates Menu bar
 *
 * @author Nitesh and Harjot
 */
public class Main extends JFrame implements ActionListener {

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event-dispatching thread.
     */
    private Melbourne melbourne;
    private TopPanel topPane;
    private Trains trains;
    private MetMap panel;
    private BottomPanel bottomPane;
    private ScreenPanel screenPanel;
    private TablePanel tablePanel;
    private JScrollPane scroll;
    //Menubar 
    private JMenuBar bar;//main bar 
    private JMenu file, settings;//menu option available 
    private JMenuItem open, save;//menu items for File
    private JRadioButtonMenuItem weekdays;
    private JRadioButtonMenuItem saturday;
    private JRadioButtonMenuItem sunday;
    private ButtonGroup buttonGroup;//manages group
    //components for file chooser 
    private FileChooser fileChooser;//to be used with menubar
    private SelectedFile selected;//currently selected file  
    private ArrayList<Station> data;//all stations of currently selected file 
    private File selfile;//path of file loaded in JTable  

    public Main() {
        super("Melbourne Met 2.0");
        melbourne = new Melbourne();
        panel = new MetMap(melbourne);
        trains = new Trains(panel);
        topPane = new TopPanel(panel);
        bottomPane = new BottomPanel(panel, trains);
        screenPanel = new ScreenPanel(topPane, panel, bottomPane);
        fileChooser = new FileChooser(this);//to open/retore coordinate points  
        //by default this will be selected 
        selfile = new File("data/metro-stations-lilydale.txt");
        selected = new SelectedFile(selfile.getPath());
        data = selected.getStations();
        tablePanel = new TablePanel(data, panel);
        tablePanel.setPath(selfile.getAbsolutePath());;


        // add a MenuBar.  
        //For File Menu 
        file = new JMenu("File");
        file.setMnemonic('F');//set mnemonic to F.

        //Open menu Item 
        open = new JMenuItem("Open");
        open.setMnemonic('O');
        open.addActionListener(this);
        file.add(open);

        //Save menu Item 
        save = new JMenuItem("Save");
        save.setMnemonic('S');
        save.addActionListener(this);
        file.add(save);

        //create a menu so that settings can be changes
        //as Weekdays, Saturday or Sunday Trains
        //create a menu so that settings can be changes
        //as Weekdays, Saturday or Sunday Trains
        JMenu settings = new JMenu("Settings");
        settings.setMnemonic('S');//set mnemonic to S.

        //initialize all setting items
        buttonGroup = new ButtonGroup();
        weekdays = new JRadioButtonMenuItem("Weekdays");
        weekdays.setEnabled(true);
        saturday = new JRadioButtonMenuItem("Saturday");
        sunday = new JRadioButtonMenuItem("Sunday");

        buttonGroup.add(weekdays);
        buttonGroup.add(saturday);
        buttonGroup.add(sunday);

        settings.add(weekdays);
        settings.add(saturday);
        settings.add(sunday);



        //add JMenu to JMenubar
        bar = new JMenuBar();
        setJMenuBar(bar);
        bar.add(file);
        bar.add(settings);

        weekdays.addActionListener(this);
        saturday.addActionListener(this);
        sunday.addActionListener(this);

        add(tablePanel, BorderLayout.WEST);
        add(screenPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // setBounds(0, 0, screenSize.width-50, screenSize.height-50);

        setVisible(true);
    }

    /**
     * ActionListener implemented Menubar
     *
     * @param e is the ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == open) {
            selfile = fileChooser.getFile();//method to open JFile Chooser  
            selected = new SelectedFile(selfile.getPath());
            data = selected.getStations();
            tablePanel.changeData(data);
            tablePanel.setPath(selfile.getPath());
        }
        if (e.getSource() == save) {
            selfile = fileChooser.saveFile();//method to save JFile Chooser 
            ArrayList<Station> newData = tablePanel.getData();
            System.out.println(selfile);
            FileWrite.writeFile(selfile.getPath(), newData);
        }
        //for settings Menu 
        if (e.getSource() == weekdays) {
            trains.setConstraint("Weekdays");
            JOptionPane.showMessageDialog(this, "Mon-Fri timetable selected!");
        }
        if (e.getSource() == saturday) {
            trains.setConstraint("Saturday");
            JOptionPane.showMessageDialog(this, "Saturday timetable selected!");
        }
        if (e.getSource() == sunday) {
            trains.setConstraint("Sunday");
            JOptionPane.showMessageDialog(this, "Sunday timetable selected!");
        }
    }

    /**
     * responsible for simulation of Melbourne Met Train Display Application
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main main = new Main();
            }
        });
    }
}
