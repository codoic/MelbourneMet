/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * TablePanel: is the core to creating JTable
 *
 * @author Harjot and Nitesh
 */
public class TablePanel extends JPanel {

    private boolean DEBUG = false;
    private MetMap panel;
    private MyTableModel model;
    private JTable table;
    private JLabel path;

    public TablePanel(ArrayList<Station> stations, MetMap panel) {
        super(new GridLayout(1, 0));

        model = new MyTableModel(stations);
        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        path = new JLabel("File Name:");
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        //added this to show the current path. 
        JPanel labelPanel = new JPanel();
        labelPanel.add(path);
        add(labelPanel, BorderLayout.SOUTH);
        //Anonomous MouseListener to capture X,Y coordinate in MetMap 
        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = e.getX();
                int j = e.getY();
                System.out.println(i + "," + j);
                final int row = table.getSelectedRow();
                model.setValues(i, j, row);

            }
        });

    }

    /**
     * Change row data in JTable
     *
     * @param data is the new row data.This means FileChooser is been used to
     * change data
     */
    public void changeData(ArrayList<Station> data) {
        model.setData(data);
        model.fireTableDataChanged();
    }

    /**
     * Returns the new datas. This can be saved into textfile
     *
     * @return ArrayList<Station> is the list of station for these lines.
     */
    public ArrayList<Station> getData() {
        return model.getData();
    }

    /**
     * Shows the current path of choosed file
     *
     * @param text is the path
     */
    public void setPath(String text) {
        path.setText(text);
    }
    
    /**
     * Inner class for Table Model.
     * This class extends AbstractTableModel 
     */
    class MyTableModel extends AbstractTableModel {

        //name of column which remains unchanged  
        private String[] columnNames = {"Station Name",
            "X-Cordinate",
            "Y-Cordinate"};
        private ArrayList<Station> data;

        /**
         * Constructor of Table model
         */
        public MyTableModel(ArrayList<Station> data) {
            this.data = data;
        }

        /**
         * Change row data in JTable
         *
         * @param data is the new row data.This means FileChooser is been used
         * to change data
         */
        public void setData(ArrayList<Station> data) {
            this.data = data;
        }

        /**
         * Return changed data.
         *
         * @return data of an ArrayList of New Data.
         */
        public ArrayList<Station> getData() {
            return data;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            if (col == 0) {
                return data.get(row).getName();
            } else if (col == 1) {
                return data.get(row).getXcordinate();
            } else if (col == 2) {
                return data.get(row).getYcordinate();
            }
            return null;
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col > 2) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * sets the new value of x and y for row
         *
         * @param i new value for x coordinate
         * @param j new value for y coordinate
         * @param row
         */
        public void setValues(int i, int j, int row) {
            data.get(row).setXcordinate(i);
            data.get(row).setYcordinate(j);
            fireTableCellUpdated(row, 1);
            fireTableCellUpdated(row, 2);
        }
    }
}
