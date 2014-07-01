/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.ArrayList;

/**
 * RailLine: Represent Each rail line of Melbourne. eg, Belgrave Line.  
 * 
 * @author Nitesh and Harjot.
 * 
 */
public class RailLine {

    private ArrayList<Station> stations;

    public RailLine() {
        this.stations = new ArrayList<Station>();
    }

    /**
     * Add station for this RailLine
     *
     * @param name is the name
     * @param xcordinate is the xcordinate
     * @param ycordinate is the ycordinate
     */
    public void add(String name, int xcordinate, int ycordinate) {
        stations.add(new Station(name, xcordinate, ycordinate));
    }

    /*
     * Get all the station for this line. 
     */
    public ArrayList<Station> getStations() {
        return stations;
    }
}
