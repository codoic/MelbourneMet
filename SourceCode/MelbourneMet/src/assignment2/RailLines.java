/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * RailLines: Collection of RailLine needed to draw in MetMap.java  
 * 
 * @author Nitesh and Harjot.
 * 
 */
public class RailLines {

    private ArrayList<String> files;
    private ArrayList<String> records;
    private ArrayList<RailLine> lines;

    public RailLines() {
        files = new ArrayList<String>();
        records = new ArrayList<String>();
        lines = new ArrayList<RailLine>();
        readfiles();
        readStaion();
    }

    /**
     * read data from fredsmet.ini and add to array list
     */
    public final void readfiles() {
        String path;
        path = new File("data/fredsmet.ini").getPath();//read all coordinates to create station
        files = FileReader.readFile(path);

    }

    /**
     * read each file and add station name and location to ArrayLIst
     */
    public final void readStaion() {
        String path;
        //System.out.println(System.getProperty("user.dir"));

        for (String f : files) {
            if (f.contains(".txt")) {
                RailLine railLine = new RailLine();
                path = new File(f).getPath();
                records = FileReader.readFile(path);
                records.remove(0);//this line is not needed
                records.remove(0);//this line is not needed
                for (String r : records) {
                    StringTokenizer st2 = new StringTokenizer(r, ",");
                    String name = null;//name of the location
                    int xcordinate = 0;//xcordinate of location
                    int ycordinate = 0;//ycordinate of location 
                    name = (String) st2.nextToken();
                    xcordinate = Integer.parseInt(st2.nextToken());
                    ycordinate = Integer.parseInt(st2.nextToken());
                    railLine.add(name, xcordinate, ycordinate);
                }
                lines.add(railLine);
            }
        }
    }

    public ArrayList<RailLine> getLines() {
        return lines;
    }
}
