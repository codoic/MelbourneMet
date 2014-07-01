/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Train: Stores all the information about a particular train like name,
 * currentPosition coordinates, time when reaches at a particular station and
 * its shape.
 *
 * @author Nitesh and Harjot
 */
public class Train {
    // instance variables

    private String name;
    private MetMap pane;
    private ArrayList<PointMap> map;
    private double x = 0;//
    private double y = 0;//
    private double vx = 0;//
    private double vy = 0;//
    private static final int XSIZE = 8;
    private static final int YSIZE = 8;
    private Ellipse2D.Double ellipse2d;
    private ArrayList<Station> stations;
    private String timeState;//implemented to save state of current time to work with JSlider 
    private ArrayList<PointMapDate> specialMap;
    private int dt = 1;

    /**
     * Constructor for objects of class Train.
     *
     * @param m for panel on which train is to be displayed
     * @param name refers to the name of train
     * @param map represents ArrayList containing information about all the
     * station and corresponding time for train's journey
     */
    public Train(MetMap m, String name, ArrayList<PointMap> map) {

        this.pane = m;
        this.name = name;
        this.map = map;
        stations = m.getStations();
        specialMap = getSpecial();
        this.ellipse2d = new Ellipse2D.Double(x, y, XSIZE, YSIZE);
        timeState = null;
    }

    /**
     * creates an arraylist, which is required to show train's movement
     *
     * @return special an ArrayList of PointMapDate excluding all extra
     * information
     */
    public ArrayList<PointMapDate> getSpecial() {
        ArrayList<PointMapDate> special = new ArrayList<PointMapDate>();
        for (PointMap p : map) {

            //check if any of the records exactly matches in the csv files 
            if ((!"-".equals(p.getTime())) && (!"".equals(p.getTime())) && (!"�".equals(p.getTime())) && (!"|".equals(p.getTime())) && (!"Ê".equals(p.getTime()))) {
                special.add(new PointMapDate(p.getStation(), p.getTime()));
            }

        }
        return special;
    }

    /**
     * Getters for train name
     *
     * @return name of the train
     */
    public String getName() {
        return name;
    }

    /**
     * Setters for train name
     *
     * @param name refers to the name of the train
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the position of this train for given time timeState provided is saved
     * to be used for JSlider.
     *
     * @param time for setting the simulation start time
     */
    public void setPosition(String time) {
        this.timeState = time;// save the instance of this time  
        simpleSearch(time); // perform search for this time 
    }

    /**
     * Method to do a simpleSearch based in Linear Search Algorithm looks for
     * any matching incrementally
     *
     * @param time given for searching the this train
     */
    public void simpleSearch(String time) {
        int count = 0;//need to check if we have found the result i.e it gets incremented if exactly matched in csv
        String position = "", station = "";
        for (PointMapDate p : specialMap) {
            try {

                DateFormat formatter = new SimpleDateFormat("HH:mm");
                //provided time 
                Date date = (Date) formatter.parse(time);
                Date compareDate = (Date) formatter.parse(p.getTime());

                if (date.compareTo(compareDate) == 0) {
                    count++;// if count gets incremented means we have found something matching  
                    station = p.getStation();

                    x = getStationX(station);
                    y = getStationY(station);

                    ellipse2d = new Ellipse2D.Double(x, y, XSIZE, YSIZE);
                    position = name + " is at " + station + "," + "X-Corinate" + x + "," + "Y-Corinate" + y + " with X-Cordinate:" + x + ", Y-Cordinate:" + y + " at " + time;
                    System.out.println(position);
                }
            } catch (ParseException ex) {
                Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//if this section is finised  no matching found so need to something special 
        if (count == 0) {
            specialSearch(specialMap, time);
        }
    }

    /**
     * if train's current position is in between two stations then this this
     * method calculates the exact x and y coordinates to represent trains
     * position.
     *
     * @param specialMaps ArrayList of mapping but without symbol like -, |, " "
     */
    public void specialSearch(ArrayList<PointMapDate> specialMaps, String time) {
        try {
            ArrayList<PointMapDate> smap = specialMaps;
            double x1 = 0, x2 = 0, y1 = 0, y2 = 0;

            DateFormat formatter = new SimpleDateFormat("HH:mm");
            Date date = (Date) formatter.parse(time);

            if (smap.size() >= 2) {//check if we have at least two mapping points for calculation
                Date lowerbound = smap.get(0).getDate();//
                Date upperbound = smap.get(1).getDate();

                for (int i = 1; i < smap.size() - 1; i++) {
                    if ((date.after(lowerbound)) && (date.before(upperbound))) {

                        PointMapDate leftpoint = smap.get(i - 1);//create the lowerbound object 
                        PointMapDate rightpoint = smap.get(i);//create the upperbound object


                        String leftStation = leftpoint.getStation();
                        x1 = getStationX(leftStation);
                        y1 = getStationY(leftStation);


                        Date t1 = leftpoint.getDate();

                        String rightStation = rightpoint.getStation();
                        x2 = getStationX(rightStation);
                        y2 = getStationY(rightStation);


                        Date t2 = rightpoint.getDate();
                        double timeinc = date.getTime() - t1.getTime();
                        //  double timeincMin = (timeinc / (1000*60))%60;

                        double timeincMin = (timeinc / 1000);
                        x = determineX(x1, y1, x2, y2, t1, t2, timeincMin);//calculated value of x. see the method below
                        y = determineY(x1, y1, x2, y2, t1, t2, timeincMin); //calculated value of x. see the method below

                        ellipse2d = new Ellipse2D.Double(x, y, XSIZE, YSIZE);
                        String position = name + " is  between " + leftStation + " and " + rightStation
                                + " with X-Cordinate:" + x + ", Y-Cordinate:" + y + " at " + time;
                        System.out.println(position);

                        break;
                    }
                    lowerbound = smap.get(i).getDate();
                    upperbound = smap.get(i + 1).getDate();
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Train.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * set the value for x and y coordinates
     *
     * @param x sets the x coordinate value
     * @param y sets the y coordinate value
     */
    public void setPos(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * to get train name and its current position
     *
     * @return string containing train name with x,y values
     */
    public String currentPosition() {
        return name + " X-Corinate" + x + "," + "Y-Corinate" + y;
    }

    /**
     * sets the new x and y coordinate values for tains next movement by just
     * adding unit velocity
     */
    public void moveOneStep() {
        x = x + vx * dt;   // in every case value of dt = 1 because we are showing the trains movement in 1 min
        y = y + vy * dt;
        System.out.println("vx=" + vx);
        System.out.println("vy=" + vy);
        System.out.println("x=" + x);
        System.out.println("y=" + y);
        System.out.println("*************");
    }

    /**
     * sets the x and y position coordinates of a train for next move
     */
    public void move() {
           try {
            for (int i = 0; i < specialMap.size() - 1; i++) //repeats untill train reaches at its destination
            {
                int size = specialMap.size();
                double stationX1 = 0, stationX2 = 0, stationY1 = 0, stationY2 = 0;
                double speed = 0.0;//speed of train
                double distance = 0.0;//is the distance travelled 
                double timeDiff = 0.0;//time difference in milliseconds
                double timeDiffMin = 0.0;  // time difference in minutes

                stationX1 = getStationX(specialMap.get(i).getStation());     //to get the x coordinate of stationA
                stationY1 = getStationY(specialMap.get(i).getStation());     //to get the y coordinate of stationA

                System.out.println("stationx" + stationX1);
                System.out.println("stationx" + stationY1);

                stationX2 = getStationX(specialMap.get(i + 1).getStation());   //to get the x coordinate of stationB
                stationY2 = getStationY(specialMap.get(i + 1).getStation());   //to get the y coordinate of stationB
                Date t1 = specialMap.get(i).getDate();                      // time at stationA
                Date t2 = specialMap.get(i + 1).getDate();                    // time at stationB

               if( (stationX1 == stationX2) && (stationY1 == stationY2) &&(x== stationX1) && (y == stationY1))  // to check if train is at the same station
               {
                                    Thread.sleep(t2.getTime()-t1.getTime());         // it'll stop the train for t2-t1 min at a station befor moving 
               }
                else
                {

                            if (x == stationX1 && y == stationY1) {

                                // train is at a particular station. so calculate new unit velocities because current vx and vy is equal to 0
                                distance = Math.sqrt(((stationX2 - stationX1) * (stationX2 - stationX1)) + ((stationY2 - stationY1) * (stationY2 - stationY1)));

                                timeDiff = t2.getTime() - t1.getTime() - (30 * 1000);      // timediff'll be t2-t1-30 sec  because train'll spend 30sec at each station

                                timeDiffMin = (timeDiff / 1000);
                                speed = distance / timeDiffMin;                               // calculating speed
                                vx = (((stationX2 - stationX1) / distance) * speed);           // unit velocity for x
                                vy = (((stationY2 - stationY1) / distance) * speed);           // unit velocity for y
                                System.out.println("vx=" + vx);
                                System.out.println("vy=" + vy);

                                if (x <= stationX2) {             //   if train goes in forward direction
                                    while (x <= stationX2 ) //  it'll repeats untill train reaches at stationB
                                    {
                                        moveOneStep();        // to calculate new x and y coordinates
                                        pane.repaint();        // repainting met map
                                        Thread.sleep(1000);   // for pausing a thread after one move
                                    }
                                    x = stationX2; // added just to perform valid calculations
                                    y = stationY2;  // added just to perform valid calculations  
                                    Thread.sleep(30000);              // to stop the train at a station for 30 sec.
                                } else {                   //   if train goes in backward direction
                                    while (x >= stationX2 ) //  
                                    {
                                        moveOneStep();
                                        pane.repaint();             // repainting met map
                                        Thread.sleep(1000);        // for pausing a thread after one move
                                    }
                                    x = stationX2; // added just to perform valid calculations
                                    y = stationY2;  // added just to perform valid calculations  
                                    Thread.sleep(30000);              // to stop the train at a station for 30 sec.
                                }

                            } else if(x ==   stationX2 && y ==   stationY2){
                                        pane.paint(pane.getGraphics());
                            }
                            else {   // train is in between two stations

                                if ((x > stationX1 && x < stationX2)) {                                        //   for forward direction
                                    while (x < stationX2 ) // repeats untill the train reaches at stationB
                                    {

                                        moveOneStep();
                                        pane.repaint();         // repainting met map
                                        Thread.sleep(1000);   // for pausing a thread after one move
                                    }
                                    x = stationX2; // added just to perform valid calculations
                                    y = stationY2;  // added just to perform valid calculations
                                    Thread.sleep(30000);              // to stop the train at a station for 30 sec.
                                } else if ((x < stationX1 && x > stationX2)) {                           // for backward direction
                                    while (x > stationX2) {
                                        moveOneStep();
                                        pane.repaint();
                                        Thread.sleep(1000);   // for pausing a thread after one move

                                    }
                                    x = stationX2; // added just to perform valid calculations
                                    y = stationY2;  // added just to perform valid calculations
                                    Thread.sleep(30000);              // to stop the train at a station for 30 sec.
                                }

                            }
               }
            }

        } catch (InterruptedException e) {
        }
    }

    /**
     * Calculated x coordinate based on Matmatical formula if unkown in points
     * Formula for speed, unit velocity use
     *
     * @param x1 is x coordinate of first position
     * @param y1 is y coordinate of first position
     * @param x2 is x coordinate of second position
     * @param y2 is y coordinate of second position
     * @param t1 is the time for first position
     * @param t2 is the time for the second position
     * @param t3 represents time increment
     * @return x3 which is estimated coordinate
     */
    public double determineX(double x1, double y1, double x2, double y2, Date t1, Date t2, double t3) {
        double x3 = 0;
        double speed = 0.0;//speed to be returned 
        double distance = 0.0;//is the distance travelled 
        double timeDiff = 0.0;//time difference
        //  double vx = 0.0;//unit velocity
        //distance travelled    
        distance = Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));//mathmatical formula 
        //time difference between two points 
        timeDiff = t2.getTime() - t1.getTime();                         // changedt1.getTime() - t2.getTime();
        //speed of the train 
        // double timeDiffMin = (timeDiff / (1000*60))%60;
        double timeDiffMin = (timeDiff / 1000);
        speed = distance / timeDiffMin;

        vx = (((x2 - x1) / distance) * speed);//calculate unit velocity 
        x3 = (x1 + vx * t3);//x3=x1+vx 
        return x3;
    }

    /**
     * Calculated x coordinate based on Matmatical formula if unkown in points
     * Formula for speed, unit velocity use
     *
     * @param x1 is x coordinate of first position
     * @param y1 is y coordinate of first position
     * @param x2 is x coordinate of second position
     * @param y2 is y coordinate of second position
     * @param t1 is the time for first position
     * @param t2 is the time for the second position
     * @param t3 represents time increment
     * @return y3 which is estimated coordinate
     */
    public double determineY(double x1, double y1, double x2, double y2, Date t1, Date t2, double t3) {
        double y3 = 0;
        double speed = 0.0;//speed to be returned 
        double distance = 0.0;//is the distance travelled 
        double timeDiff = 0.0;//time difference
        // double vy = 0.0;//unit velocity
        //distance travelled 
        distance = Math.sqrt(((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)));//mathmatical formula 
        //time difference between two points 
        timeDiff = t2.getTime() - t1.getTime();
        //speed of the train 
        // double timeDiffMin = (timeDiff / (1000*60))%60;
        double timeDiffMin = (timeDiff / 1000);
        speed = distance / timeDiffMin;

        vy = (((y2 - y1) / distance) * speed);//calculate unit velocity 
        //Note: need to decrease by time increment of 30 sec
        y3 = (y1 + vy * t3);//x3=x1+vx 
        return y3;
    }

    /**
     * Getters for time state
     */
    public String getTimeState() {
        return timeState;
    }

    /**
     * finds the x coordinate for a given station
     *
     * @param station represents the name of a station
     * @return x coordinate value for a station
     */
    public double getStationX(String station) {
        double X = 0;
        for (Station s : stations) {


            String name = s.getName();
            if (station.contains("Flinders")) {   //to remove street also from the flinders street                         
                station = "Flinders";
            } else if (station.contains("Upper")) {
                station = "Upper Ferntree Gully";
            } else if (station.contains("Ferntree")) {
                station = "Ferntree Gully";
            }else if(station.contains("Upwey")){
                station ="Upwey";
            } else if ((station.contains("(ARR)")) || station.contains("(DEP)")) {   // to remove ARR and DEP from name
                station = station.substring(0, name.length() - 5);
            }

            if (station.equalsIgnoreCase(name)) {
                X = s.getXcordinate();
                break;
            }
        }
        return X;
    }

    /**
     * finds the y coordinate for a given station
     *
     * @param station represents the name of a station
     * @return y coordinate value for a station
     */
    public double getStationY(String station) {
        double Y = 0;
        for (Station s : stations) {

            String name = s.getName();
            if (station.contains("Flinders")) {   //to remove street also from the flinders street                         
                station = "Flinders";
            } else if (station.contains("Upper")) {
                station = "Upper Ferntree Gully";
            } else if (station.contains("Ferntree")) {
                station = "Ferntree Gully";
            }else if(station.contains("Upwey")){
                station ="Upwey";
            } else if ((station.contains("(ARR)")) || station.contains("(DEP)")) {    // to remove ARR and DEP from name
                station = station.substring(0, name.length() - 5);
            }

            if (station.equalsIgnoreCase(name)) {
                Y = s.getYcordinate();
                break;
            }
        }
        return Y;
    }

    /*
     * Code for JSlider
     */
    public void timeIncrement(int time) {
        dt = 60 * time;
    }

    /**
     * getter to get the value of x coordinate
     *
     * @return x value
     */
    public double getX() {
        return x;
    }

    /**
     * getter to get the value of y coordinate
     *
     * @return y value
     */
    public double getY() {
        return y;
    }

    /**
     * get the current position of this train
     *
     * @return ellipse2D object for the shape of train
     */
    public Ellipse2D getShape() {
        return new Ellipse2D.Double(x, y, XSIZE, YSIZE);

    }
}
