MelbourneMet
============

Melbourne Met is a JAVA application to animate trains in Melbourne’s map. Using this application you are able to enter the time and locate the overall location of trains in map. It was developed to learn Java Swing API, Java2D, Threads, Collection API and ANT.    

Running the application. 
The jar file can be executed by simply double clicking in Assignment2.jar located inside Assignment2/MebourneMet folder or using “java -jar "..\Assignment2\MelbourneMet\Assignment2.jar from command line. Please change the path to your own path. Please make sure you have the data folder in the same directory as Assignment2.jar.The javaDoc is located inside MelbourneMet. Please double click index.html inside javadoc folder.  
If you clean and build from Netbeans then dist folder gets deleted and re-created. Please copy the data folder from Assignment2 folder and paste in dist folder and choose to replace the folder.    
**********************************************************************************
build.xml file contains all the targets,which you can use to clean,build,run and to generate java documentation for this project.To run this project from command line using ant,plz place Ant and Assignment2 folders in the same folder. Make Assignment2 as current directory and set the AntHome and path. Please don't set CLASSPATH,if you'll do so then java documentation will not be generated and gives Illegal Argument type exception. Usage is the default target.It'll show the list of all the available targets for this project.you can execute these targets by typing ant [targetName] on command line.
*******************************************************************************************************************************************************
List of all implemented items and where in the source code to look. 
1.  Display a portion of map in at most using appropriate techniques (eg. zoom, scroll and viewport) : setNormalSize(),setZoomSize() and setViewport() in MetMap.java and TopPanel.java  
2.  JTable on side with station name and x,y coordinates recorded after clicking the relevant station in the visible area: TablePanel.java for JTable logic and some codes inside Main.java to add the JPanel.   
3.  Build new position data-TablePanel.java  
4.  Allow user to save a file of the new position data- FileChooser.java for JFileChooser logic, FileReader.java for FileReading logic, and actionPerformed() method inside Main.java.
5.  Animation as in Assignment 1 works-Trains.java, Train.java, PointMap.java, MetMap.java, BottomPanel.java to set the time and for time increment, FileReader.java Melbourne.java for Fail Reading purpose.
Trains appear 1 minute before the first time in the timetable-Could not be implemented in Assignment.
******************************************************************************************************************************************************** ]

#List of known bugs
Please enter time of day as 5:40 or 21:30 all in 24 hour format. No validation has been implemented to check the date provided.
##Other Discussion
####STEPS TO CHANGE THE POSITION DATA
Please open a position data. File>Open>Select the file. It is modified to show only .txt files. 
 Select the row i.e station and click once in map. This will update the x,y coordinates value. It is compulsory to select a row in Table. Continue the steps for all stations. 
Save the file. File>Save. Please make sure you save it as a same FileName or you will have to change the link fredsmet.ini  
After save operation you can run the project again to move the trains with new position data.
##OTHER
The trains are Blue color if going to the City and Green if running from the City. 
We have done minor changes in csv files. We have changed all the record which appeared as row to appear as column. This makes the file more readable as each train is mapping station in a new line. This was done after permission from the Lecturer. 
We have implemented Menu Bar to change specific day of week. By default, it will read all timetables for Mon-Fri. However, if you would like to change choose Saturday or Sunday respectively. Please make sure you have schedule (.csv) files for those days.  
**********************************************************************************************************************************************************
	
