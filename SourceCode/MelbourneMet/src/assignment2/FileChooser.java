/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * FileChooser:Allows to choose a file using JFile Chooser
 *
 * @author Nitesh and Harjot.
 */
public class FileChooser {

    private Main main;
    private JFileChooser fileChooser;

    /**
     * Constructor of FileChooser
     * @param main is parent class to show Dialog
     */
    public FileChooser(Main main) {
        this.main = main;//needed to show Dialog 
        fileChooser = new JFileChooser();
         FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "TXT FILES", "txt");
         fileChooser.setFileFilter(filter);//shows only txt files
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setCurrentDirectory(new File("data"));
        
    }

    
    /**
     * Allow user to choose a File using JFileChooser
     * @return File is path to be read.  
     */
    public File getFile() {
        //display file dialog, so user can chooser file to open
        int result = fileChooser.showOpenDialog(main);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("FileChooser been cancelled!.Nothing to open");
        }
        return file;
    }
    
     /**
     * Allow user to choose a File using JFileChooser
     * @return File is path to be read.  
     */
    public File saveFile() {
        //display file dialog, so user can chooser file to open
        int result = fileChooser.showSaveDialog(main);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            //This is where a real application would open the file.
            System.out.println("Opening: " + file.getName() + ".");
        } else {
            System.out.println("FileChooser been cancelled!.Nothing to save");
        }
        return file;
    }
}
