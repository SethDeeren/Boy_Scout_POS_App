package model;

import java.util.Properties;

import database.DB;

public class Tree {
    private String barcode = null;
    private String treeType = null;
    private String notes = null;
    private String dateStatusUpdated = null;
    private String status = "Available";

    public Tree() {

    }

    public Tree(Properties prop) {
        if(prop.containsKey("Barcode")) {
            setBarcode(prop.getProperty("Barcode"));
        }
        if(prop.containsKey("TreeType")) {
            setTreeType(prop.getProperty("TreeType"));
        }
        if(prop.containsKey("Notes")) {
            setNotes(prop.getProperty("Notes"));
        }
        if(prop.containsKey("DateStatusUpdated")) {
            setDateStatusUpdated(prop.getProperty("DateStatusUpdated"));
        }
        if(prop.containsKey("Status")) {
            setStatus(prop.getProperty("Status"));
        }
    }

    public boolean save() {
        String sql = null;
        DB db = DB.getInstance();

        //Build SQL statement
        sql = "INSERT INTO Tree (Barcode, TreeType, Notes, DateStatusUpdated, Status)";
        sql += " VALUES ('" + barcode + "', '" + treeType + "', '" + notes + "', '" + dateStatusUpdated + "', '" + status + "');";

        System.out.println(sql);
        return db.update(sql);
    }

    public Properties getProperties() {
        Properties prop = new Properties();

        prop.setProperty("Barcode", barcode);
        prop.setProperty("TreeType", treeType);
        prop.setProperty("Notes", notes);
        prop.setProperty("DateStatusUpdated", dateStatusUpdated);
        prop.setProperty("Status", status);

        return prop;
    }

    public String toString() {
        String output = "Barcode: " + barcode + "    TreeType: " + treeType + "    Notes: " + notes + "    DateStatusUpdated: " + dateStatusUpdated + "    Status: " + status;

        return output;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTreeType() {
        return treeType;
    }

    public void setTreeType(String treeType) {
        this.treeType = treeType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDateStatusUpdated() {
        return dateStatusUpdated;
    }

    public void setDateStatusUpdated(String dateStatusUpdated) {
        this.dateStatusUpdated = dateStatusUpdated;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
