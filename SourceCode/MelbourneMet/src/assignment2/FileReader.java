
package assignment2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FileReader: Class to read the file Seperate method for individual data files and spatial data file.
 * @author Harjot and Nitesh.
 * 
 */
public class FileReader {

    /**
     * Static method for file reading
     *
     * @param filename the path for the file
     * @return ArrayList<String> for each line as a string
     */
    public static ArrayList<String> readFile(String filename) {
        ArrayList<String> records = new ArrayList<String>();
        String line = null;
        File file = new File(filename);
        //System.out.println(file.getPath());
        try {
            InputStreamReader in = new InputStreamReader(new FileInputStream(file));
            BufferedReader reader = new BufferedReader(in);
            line = reader.readLine();
            while (line != null) {
                records.add(line);
                line = reader.readLine();
            }
            reader.close();//close after reading is sone 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;

    }

    /**
     * FileReading method especially designed for read train schedule data
     *
     * @param m is for MetMap panel
     * @param filename is the name
     * @return ArrayList of Train and each element represent a train object
     */
    public static ArrayList<Train> readTrains(MetMap m, String filename) {
        //this area is to identify train name 
        String file = new File(filename).getName();
        StringTokenizer st = new StringTokenizer(file, ".");//only need the part before .  
        file = st.nextToken();
        System.out.println(file);
        ArrayList<Train> trains = new ArrayList<>();
        BufferedReader br = null;
        int lineCount = 0;//count the number of lines 
        String lines = "";
        String[] station = null;// string array to store all given stations name  
        try {
            br = new BufferedReader(new java.io.FileReader(filename));

            while ((lines = br.readLine()) != null) {
                lineCount++;
                //line for the stations for mapping 
                if (lineCount == 3) {
                    station = lines.split(",");// line has got array of station name  
                }

                //for each train object which is line 3 
                if (lineCount > 3) {
                    //format to store the trainname i.e filename and concatenate with line number 
                    //will give us new train name every time. 
                    String trainName = new StringBuilder(file).append(lineCount - 3).toString();
                    ArrayList<PointMap> pointmaps = new ArrayList<PointMap>();

                    //  Train train=new Train(m,trainName,stations);// create new train object  
                    String[] time = lines.split(",");// array of time in the file  

                    for (int i = 1; i < time.length; i++) {
                        // train.addMap(station[i], time[i]);//map the time with station name 
                        //System.out.println(station[i]+":"+station[i]);
                        pointmaps.add(new PointMap(station[i], time[i]));
                    }
                    trains.add(new Train(m, trainName, pointmaps));// add this train in ArrayList  
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileReader.class.getName()).log(Level.SEVERE, null, ex);
        }

        return trains;
    }
}
