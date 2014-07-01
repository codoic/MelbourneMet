/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PointMapData : SubClass of PointMap to convert the String time into date
 * Format
 *
 * @author Nitesh and Harjot
 */
public class PointMapDate extends PointMap {

    public PointMapDate(String station, String time) {
        super(station, time);

    }

    /**
     * To get the date
     */
    public Date getDate() {

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        //provided time 
        Date date = null;
        try {
            date = (Date) formatter.parse(super.getTime());
        } catch (ParseException ex) {
            Logger.getLogger(PointMapDate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    @Override
    public String toString() {
        return "PointMap{" + "station=" + super.getStation() + ", String time=" + super.getTime() + ", time=" + getDate() + '}';
    }
}
