/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.Serializable;

/**
 * PointMap:This is class is designed to do the mapping between 
 * given time and station name. from the train schedule data
 * @author Nitesh and Harjot.
 * 
 */
public class PointMap implements Serializable {

    private String station;
    private String time;

    /**
     * to initialize PointMap for a train
     *
     * @param station sets the train station
     * @param time sets the time
     */
    public PointMap(String station, String time) {
        this.station = station;
        this.time = time;
    }

    /**
     * to get the current station
     *
     * @return String name of the station
     */
    public String getStation() {
        return station;
    }

    /**
     * to set the current station
     *
     * @param station name of the station
     */
    public void setStation(String station) {
        this.station = station;
    }

    /**
     * to get the current time
     *
     * @return String: time of a train at particular station
     */
    public String getTime() {
        return time;
    }

    /**
     * to set the time
     *
     * @param time initial time for a train at station
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * For testing purpose
     *
     * @return String
     */
    @Override
    public String toString() {
        return "PointMap{" + "station=" + station + ", time=" + time + '}';
    }
}
