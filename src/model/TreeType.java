package model;

import database.DB;

import java.util.Properties;

public class TreeType {
    private String ID = null;
    private String TypeDescription = null;
    private String Cost = null;
    private String BarcodePrefix = null;

    public TreeType() {

    }

    public TreeType(Properties prop) {
        if(prop.containsKey("ID")) {
            setID(prop.getProperty("ID"));
        }
        if(prop.containsKey("TypeDescription")) {
            setTypeDescription(prop.getProperty("TypeDescription"));
        }
        if(prop.containsKey("Cost")) {
            setCost(prop.getProperty("Cost"));
        }
        if(prop.containsKey("BarcodePrefix")) {
            setBarcodePrefix(prop.getProperty("BarcodePrefix"));
        }
    }

    public boolean save() {
        String sql = null;
        DB db = DB.getInstance();

        //Build SQL statement
        sql = "INSERT INTO TreeType (TypeDescription, Cost, BarcodePrefix)";
        sql += " VALUES ('" + TypeDescription + "', '" + Cost + "', '" + BarcodePrefix+ "');";

        System.out.println(sql);
        return db.update(sql);
    }

    public Properties getProperties() {
        Properties prop = new Properties();

        prop.setProperty("ID", ID);
        prop.setProperty("TypeDescription", TypeDescription);
        prop.setProperty("Cost", Cost);
        prop.setProperty("BarcodePrefix", BarcodePrefix);

        return prop;
    }

    public String toString() {
        String output = "ID: " + ID + "    TypeDescription: " + TypeDescription + "    Cost: " + Cost + "    BarcodePrefix: " + BarcodePrefix;

        return output;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTypeDescription() {
        return TypeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        TypeDescription = typeDescription;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getBarcodePrefix() {
        return BarcodePrefix;
    }

    public void setBarcodePrefix(String barcodePrefix) {
        BarcodePrefix = barcodePrefix;
    }

}
