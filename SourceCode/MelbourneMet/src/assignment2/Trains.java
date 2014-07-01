/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;
import java.io.File;

/**
 * Trains: Responsible for reading timetable data for each train and store each
 * Train into arrayList of Train
 *
 * @author Nitesh and Harjot
 */
public final class Trains {

    private MetMap m;
    private String constraint;
    private ArrayList<Train> trains = null;
    private ArrayList<Train> records = null;
    private ArrayList<String> files;

    /**
     * initialization for Trains Class
     *
     * @param m : MetMap to store it with each trains data
     */
    public Trains(MetMap m) {
        this.m = m;
        this.constraint = "Weekdays";
        files = new ArrayList<String>();
        trains = new ArrayList<Train>();
        readfiles();
        readAllTrains();
    }

    /**
     * to read all the files from fredsmet.ini file
     */
    public void readfiles() {

        // String path = getClass().getResource("data/fredsmet.ini").getPath();
        String path;
        path = new File("data/fredsmet.ini").getPath();//read all coordinates to create station
        files = FileReader.readFile(path);
    }

    /**
     * to read all the trains data from file and store each train into
     * collection
     */
    public void readAllTrains() {
        String path;
        records = new ArrayList<Train>();
        for (String f : files) {
            if (f.contains(constraint + ".csv")) {

                // System.out.println(f);
                path = new File(f).getPath();
                records = FileReader.readTrains(m, path);
            }

            for (Train t : records) {
                trains.add(t);
            }
        }
        if (trains.isEmpty()) {
            System.out.print("No train schedule found for this line.");
        }

    }

    /**
     * to get all the Trains
     *
     * @return ArrayList<Train>
     */
    public ArrayList<Train> getTrains() {
        return trains;
    }

    // for testing purpose only
    public void showTrains() {
        int c = 1;
        for (Train t : trains) {
            System.out.print("" + c + "   ");
            System.out.println(t);
            c = c + 1;
        }
    }

    /*
     * Method to find the trains 
     * Allows user to set the current time of day 
     */
    public void findTrains(String date) {
        System.out.println("*********************");
        ArrayList<Train> movingTrains = new ArrayList<Train>();
        for (Train t : trains) {
            t.setPosition(date);
            if (t.getX() != 0 || t.getY() != 0) {
                movingTrains.add(t);
                m.add(t);
            }

        }
        //call thread for moveable trains 
        runThreads(movingTrains);

    }

    /*
     * Run each train whose timetable is found as one thread 
     */
    public void runThreads(ArrayList<Train> ts) {
        Thread[] thread = new Thread[trains.size() - 1];
        int i = 1;
        for (Train t : ts) {
            m.add(t);
            Runnable r = new TrainRunnable(t, m);
            thread[i] = new Thread(r);
            thread[i].start();
            i++;
        }
        System.out.println("");
    }

    /**
     * Getter for the constraint
     *
     * @return String constraint
     */
    public String getConstraint() {
        return constraint;
    }

    /**
     * Setter for constraint Please set only Weekdays, Saturday or Sunday
     *
     * @param constraint the constraint.
     */
    public void setConstraint(String constraint) {
        this.constraint = constraint;
        files.removeAll(files);//remove all 
        records.removeAll(records);
        trains.removeAll(trains);
        readfiles();
        readAllTrains();
        m.empty();
        m.repaint();
    }

    /*
     * Code for JSlider 
     */
    public void timeIncrement(int time) {
        for (Train t : trains) {
            t.timeIncrement(time);
        }
    }
}
