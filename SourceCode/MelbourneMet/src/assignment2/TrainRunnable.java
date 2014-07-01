package assignment2;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TrainRunnable: implements Thread  functionality 
 * @author Nitesh and Harjot
 */
public class TrainRunnable implements Runnable {

    private Train train;
    private Component component;
    public static final int STEPS = 2000;
    public static final int DELAY = 50;
    private ArrayList<PointMapDate> specialMap;
    private double x;
    private double y;

    public TrainRunnable(Train train, Component aComponent) {
        this.train = train;
        component = aComponent;
        specialMap = train.getSpecial();
        x = train.getX();
        y = train.getY();
    }

    @Override
    public void run() {

        train.move();

    }
}
