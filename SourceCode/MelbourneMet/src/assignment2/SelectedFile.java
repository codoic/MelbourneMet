package assignment2;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This is responsible for selecting the File availble by FileChooser (when JFileChooser popups). 
 *
 * @author Harjot and Nitesh
 */
public class SelectedFile {

 
    private ArrayList<String> records;
    private ArrayList<Station> stations;

    public SelectedFile(String path) {
        records = new ArrayList<String>();
        stations = new ArrayList<Station>();
        readStaion(path);
        //printRecords();//Only intended for testing purpose 
    }

    /**
     * read each file and add station name and location to ArrayLIst
     */
    public void readStaion(String path) {
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
