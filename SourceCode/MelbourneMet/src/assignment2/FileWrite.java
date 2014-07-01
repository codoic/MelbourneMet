/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FileWriter: Class to the file. Static method to write the X,Y coordinates.
 *
 * @author Harjot and Nitesh.
 *
 */
public class FileWrite {

    public static boolean writeFile(String filename, ArrayList<Station> stations) {
        boolean result = false;

        try {
            File file = new File(filename);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintWriter fileWriter = new PrintWriter(file);

            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(file.getName() + " Line");
            bw.newLine();
            bw.write("#station,px,py");
            bw.newLine();
            for (Station s : stations) {
                bw.write(s.getName() + "," + s.getXcordinate() + "," + s.getYcordinate());
                bw.newLine();
            }
            bw.close();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return result;
    }
}
