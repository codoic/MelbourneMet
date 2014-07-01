/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * MetMap: Display all the trains and stations This has got paintComponent
 * method for drawing all the trains
 *
 * @author Harjot and Nitesh
 *
 */
public class MetMap extends JPanel {

    private Image img;
    private JViewport viewport;//used for fixing the viewport if zoomed in  
    private Rectangle vpRequest;  // pending request, null if none
    private ArrayList<Station> stations;
    private ArrayList<Train> trains;// list of all trains  
    private Melbourne melbourne;
    private ArrayList<RailLine> lines;//to all the rail lines 
    private AffineTransform t;

    public MetMap(Melbourne melbourne) {
        RailLines railLines = new RailLines();
        lines = railLines.getLines();
        this.melbourne = melbourne;
        stations = melbourne.getStations();
        trains = new ArrayList<Train>();
        t = new AffineTransform();//tranformation for zoomin + zoom out 
        //read the map data 
        ImageIcon icon = new ImageIcon("data/Melbourne_railways_map.gif");
        img = icon.getImage();
        setSize(500, 500);
        setNormalSize();
    }

    /**
     * Paint Method for the graphics
     *
     * @param g is the Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //code for filtering the rendereing 
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                RenderingHints.VALUE_STROKE_PURE);
        g2.drawImage(img, 0, 0, getWidth(), getHeight(), this);//new changes 
        g2.transform(t);
        g2.setColor(Color.red);
        for (Station s : stations) {
            g2.fill(s.getShape());
        }
        drawLines(g2);//draw the lines for each railway line 
        //show the train. If train is passing to city then show blue color, if coming to city show green   
        if (!(trains.isEmpty())) {
            for (Train t : trains) {
                if (t == null) {
                } else {
                    if (t.getName().contains("ToCity")) {
                        g2.setColor(Color.BLUE);
                        g2.fill(t.getShape());
                    } else {
                        g2.setColor(Color.GREEN);
                        g2.fill(t.getShape());
                    }
                }
            }
        }
    }
        /**
     * Get all the stations
     *
     * @return ArrayList<Station>
     */
    public ArrayList<Station> getStations() {
        return stations;
    }

    /**
     * Add train show it can be shown in map.
     *
     * @param t
     */
    public void add(Train t) {
        trains.add(t);
    }

    /**
     * Empty all trains
     */
    public void empty() {
        trains.removeAll(trains);
    }

    /**
     * Draw the lines connecting all the station
     *
     * @param g2 is Graphics2D
     */
    public void drawLines(Graphics2D g2) {
        g2.setStroke(new BasicStroke(1.5f));
        for (RailLine r : lines) {
            ArrayList<Station> allstations = r.getStations();// get all the station for this line
            GeneralPath path = new GeneralPath();//create GeneralPath object
            path.moveTo(allstations.get(0).getXcordinate(), allstations.get(0).getYcordinate());
            for (Station s : allstations) {
                path.lineTo(s.getXcordinate(), s.getYcordinate());
            }
            g2.draw(path);
        }
    }


    /**
     * Code taken from lab Solution 6. Method called when map first
     * loads.PreferredSize set to size of Map image
     */
    public void setNormalSize() {
        setPreferredSize(new Dimension(img.getWidth(this), img.getHeight(this)));
        vpRequest = new Rectangle(
                0, 0, img.getWidth(this), img.getHeight(this));
        revalidate();
    }

    /**
     * Code taken from lab Solution 6. Controls the zoom functionality.
     *
     * @param factor is the amount by which to zoom.
     */
    public void setZoomSize(double factor) {
        setPreferredSize(new Dimension(
                (int) (factor * img.getWidth(this)), (int) (factor * img.getHeight(this))));
        //code for centering. This section can still be improved 
        Rectangle vpRect = viewport.getViewRect();  // pixels
        // maintain centre of vp in new pixel coords:
        int xc = 2 * vpRect.x + vpRect.width;  // new coords
        int yc = 2 * vpRect.y + vpRect.height;
        vpRequest = new Rectangle(
                xc - vpRect.width / 2,
                yc - vpRect.height / 2,
                vpRect.width,
                vpRect.height);
        t = AffineTransform.getScaleInstance(factor, factor);//get the current scaling 
        revalidate();  // repaint() is not enough - change is delayed
    }

    /**
     * Code taken from lab Solution 6. Sets the Viewport. This method is called
     * from Main.java
     *
     * @param vp
     */
    public void setViewport(JViewport vp) //***** added
    {
        viewport = vp;
        viewport.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                Dimension viewSize = getSize();   // **** changed
                Dimension viewPrefSize = getPreferredSize();   // **** changed
                if (viewSize.equals(viewPrefSize)) {
                    // zoom change has happened
                    if (vpRequest != null) {
                        Rectangle vpReq = vpRequest;
                        vpRequest = null;  // prevent doing it twice
                        scrollRectToVisible(vpReq);
                        revalidate();
                    }
                }
            }
        });
    }
}//end of MetMap.java
