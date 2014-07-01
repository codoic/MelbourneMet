package assignment2;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This is responsible for reading data files i.e fresmet.ini and its contains
 *
 * @author Harjot and Nitesh
 */
public class Melbourne {

    private ArrayList<String> files;
    private ArrayList<String> records;
    private ArrayList<Station> stations;

    public Melbourne() {
        files = new ArrayList<String>();
        records = new ArrayList<String>();
        stations = new ArrayList<Station>();
        readfiles();
        readStaion();
        //printRecords();//Only intended for testing purpose 
    }

    /**
     * read data from fredsmet.ini and add to array list
     */
    public void readfiles() {

        // String path = getClass().getResource("data/fredsmet.ini").getPath();

        String path;
        path = new File("data/fredsmet.ini").getPath();//read all coordinates to create station
        files = FileReader.readFile(path);

    }

    /**
     * read each file and add station name and location to ArrayLIst
     */
    public void readStaion() {
        String path;
        //System.out.println(System.getProperty("user.dir"));

        for (String f : files) {
            if (f.contains(".txt")) {

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

                    //check for any redudancy inside ArrayList not completed yet  
                    stations.add(new Station(name, xcordinate, ycordinate));
                }
            }
        }
    }

    /**
     * Get all the stations for Melbourne
     *
     * @return ArrayList<Stations> which contains collection of station as
     * objects.
     */
    public ArrayList<Station> getStations() {
        return stations;
    }
}
