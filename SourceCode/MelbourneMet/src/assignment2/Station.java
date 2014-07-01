/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.geom.Ellipse2D;
import java.io.Serializable;

/**
 * Station: Responsible for mapping between station name and its X and Y
 * coordinates.
 *
 * @author Nitesh and Harjot
 */
public class Station implements Serializable {

    private String name;
    private int xcordinate;
    private int ycordinate;
    private static final int XSIZE = 8;
    private static final int YSIZE = 8;

    /**
     * Initalize Station name and its x,y coordinates
     *
     * @param name for the name of the station
     * @param xcordinate for X coordinate
     * @param ycordinate for Y coordinate
     */
    public Station(String name, int xcordinate, int ycordinate) {
        this.name = name;
        this.xcordinate = xcordinate;
        this.ycordinate = ycordinate;
    }

    /**
     * to get the name of a station
     *
     * @return String name of a station
     */
    public String getName() {
        return name;
    }

    /**
     * To set the name of a station
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * To retrieve the x coordinate
     *
     * @return int : position of x
     */
    public int getXcordinate() {
        return xcordinate;
    }

    /**
     * set the x position of a station
     *
     * @param xcordinate : value of x cordinate
     */
    public void setXcordinate(int xcordinate) {
        this.xcordinate = xcordinate;
    }

    /**
     * To retrieve the y coordinate
     *
     * @return int : position of y
     */
    public int getYcordinate() {
        return ycordinate;
    }

    /**
     * set the x position of a station
     *
     * @param ycordinate : value of y cordinate
     */
    public void setYcordinate(int ycordinate) {
        this.ycordinate = ycordinate;
    }

    /**
     * Gets the shape of the ball at its current position.
     *
     * @return Ellipse2D object represent the shape of a station on a map
     */
    public Ellipse2D getShape() {
        return new Ellipse2D.Double(xcordinate, ycordinate, XSIZE, YSIZE);
    }

    /**
     * ToString for testing purpose only
     *
     * @return class information
     */
    @Override
    public String toString() {
        return name + ", " + "X-Cordinate:" + xcordinate + ", " + "Y-Cordinate:" + ycordinate;
    }
}
