package database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import event.Event;
import model.Scout;
import model.Transaction;
import model.Tree;
import model.TreeType;

//Thomas Shear

public class DB {
	private static DB singleton = null;
	
	//Creating the connection 
    String server = "test.shearhomeautomation.com"; 
    String dbName = "CSC429"; 
    String username = "csc429";
    String password = "MUtNBY0oP8PJikmh";
    Driver driver = null;
    Connection connection;
    
    public static DB getInstance() {
    	if(singleton == null) {
    		singleton = new DB();
    	}
    	return singleton;
    }
    
    private DB() {
    	connect();
    }
    
    private void connect() {
    	String url = "jdbc:mysql://"+server+":3306/"+dbName;
    	
    	try {
    		driver = (Driver)Class.forName("org.mariadb.jdbc.Driver").cast(driver);
    		connection = DriverManager.getConnection(url,username,password);
    	}
    	catch(ClassNotFoundException e)
		{
			System.err.println("Tom's database extravaganza - Could not load driver class: ClassNotFoundException");
		}	
			catch(SQLException e)
		{
			System.err.println("Tom's database extravaganza - SQLException");
		}
    }
    
    public void connect(String address, String tableName, String user, String pass) {
    	server = address;
    	dbName = tableName;
    	username = user;
    	password = pass;
    	
    	String url = "jdbc:mysql://"+server+":3306/"+dbName;
    	
    	try {
    		driver = (Driver)Class.forName("org.mariadb.jdbc.Driver").cast(driver);
    		connection = DriverManager.getConnection(url,username,password);
    	}
    	catch(ClassNotFoundException e)
		{
			System.err.println("Tom's database extravaganza - Could not load driver class: ClassNotFoundException");
		}	
			catch(SQLException e)
		{
			System.err.println("Tom's database extravaganza - SQLException");
		}
    }
    
    public int insert(String sql) {
    	try {
			Statement statement = connection.createStatement();
			System.out.println(sql);
			statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			ResultSet keys = statement.getGeneratedKeys();
			if(keys != null && keys.next()) {
				return keys.getInt(1);
			}
			else {
				return -1;
			}
		} catch (SQLException e) {
			System.err.println("Tom's database extravaganza - Error in creating SQL statement.");
    		return -1;
		}
    }
    
    //Runs UPDATE,DELETE, and INSERT on DB
    public boolean update(String sql) {
    	if(sql.contains("UPDATE")) {
    		if(!sql.contains("WHERE")) {
    			return false;
    		}
    	}
    	
    	//Make the change in the database
    	try {
    		Statement statement = connection.createStatement();
    		int success = statement.executeUpdate(sql);
    		if(success >= 1) {
    			return true;
    		}
    		else {
    			return false;
    		}
    	}
    	catch (SQLException e) {
    		System.err.println("Tom's database extravaganza - Error in creating SQL statement.");
    		return false;
    	}
    }
    
    //Generic query returning a ResultSet
    public ResultSet query(String sql) {
    	ResultSet results = null;
    	
    	try {
    		Statement statement = connection.createStatement();
    		results = statement.executeQuery(sql);
    	}
    	catch (SQLException e) {
    		System.err.println("Tom's database extravaganza - Error in creating SQL statement.");
    	}
    	
    	return results;
    }
    
    //Queries the DB then parses the results into a ArrayList depending on type
    public ArrayList query(String sql, String type) {
    	ResultSet results = null;
    	ArrayList resultList = null;
    	
    	//Make the request in the database
    	try {
    		Statement statement = connection.createStatement();
    		results = statement.executeQuery(sql);
    	}
    	catch (SQLException e) {
    		System.err.println("Tom's database extravaganza - Error in creating SQL statement.");
    	}
    	
    	//Work with returned data
    	if(type.toLowerCase().equals("scout")) {
    		resultList = new ArrayList<Scout>();
            try {
				while (results.next()) {
					Properties prop = new Properties();
					prop.setProperty("id", results.getString("ID"));
					prop.setProperty("lastName", results.getString("LastName"));
					prop.setProperty("firstName", results.getString("FirstName"));
					prop.setProperty("middleName", results.getString("MiddleName"));
					prop.setProperty("dateOfBirth", results.getString("DateOfBirth"));
					prop.setProperty("phoneNumber", results.getString("PhoneNumber"));
					prop.setProperty("email", results.getString("Email"));
					prop.setProperty("troopID", results.getString("TroopID"));
					Scout thisScout = new Scout(prop);
					resultList.add(thisScout);
				}
			} catch (SQLException e) {
				System.err.println("Tom's database extravaganza - Error in parsing ResultSet.");
			}
    	}
    	else if(type.toLowerCase().equals("tree")) {
    		resultList = new ArrayList<Tree>();
            try {
				while (results.next()) {
					Properties prop = new Properties();
					prop.setProperty("Barcode", results.getString("Barcode"));
					prop.setProperty("TreeType", results.getString("TreeType"));
					prop.setProperty("Notes", results.getString("Notes"));
					prop.setProperty("DateStatusUpdated", results.getString("DateStatusUpdated"));
					prop.setProperty("Status", results.getString("Status"));
					Tree thisTree = new Tree(prop);
					resultList.add(thisTree);
				}
			} catch (SQLException e) {
				System.err.println("Tom's database extravaganza - Error in parsing ResultSet.");
			}
    	}
    	else if(type.toLowerCase().equals("treetype")) {
    		resultList = new ArrayList<TreeType>();
            try {
				while (results.next()) {
					Properties prop = new Properties();
					prop.setProperty("ID", results.getString("ID"));
					prop.setProperty("TypeDescription", results.getString("TypeDescription"));
					prop.setProperty("Cost", results.getString("Cost"));
					prop.setProperty("BarcodePrefix", results.getString("BarcodePrefix"));
					TreeType thisTreeType = new TreeType(prop);
					resultList.add(thisTreeType);
				}
			} catch (SQLException e) {
				System.err.println("Tom's database extravaganza - Error in parsing ResultSet.");
			}
    	}

		else if(type.toLowerCase().equals("transaction")){
			resultList = new ArrayList<Transaction>();
			try{
				while(results.next()) {
					Properties prop = new Properties();
					//prop.setProperty("ID", results.getString("ID"));
					prop.setProperty("sessionID", results.getString("SessionID"));
					prop.setProperty("transactionType", results.getString("TransactionType"));
					prop.setProperty("barcode", results.getString("Barcode"));
					prop.setProperty("transactionAmount", results.getString("TRansactionAmount"));
					prop.setProperty("paymentMethod", results.getString("PaymentMethod"));
					prop.setProperty("customerName", results.getString("CustomerName"));
					prop.setProperty("customerEmail", results.getString("CustomerEmail"));
					prop.setProperty("transactionDate",results.getString("TransactionDate"));
					prop.setProperty("transactionTime", results.getString("TransactionTime"));
					prop.setProperty("dateStatusUpdated", results.getString("DateStatusUpdated"));
					Transaction transaction = new Transaction(prop);

					resultList.add(transaction);
				}
			} catch (SQLException e){
				System.err.println("Tom's database extravaganza - Error in parsing ResultSet.");
			}
		}

    	return resultList;
    }
}
