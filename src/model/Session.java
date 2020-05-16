package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import database.DB;

public class Session {
	
	private static Session singleton = null;
	
	private int ID = -1;
	private LocalDate startDate;
	private LocalTime startTime, endTime;
	private int startingCash = 0, endingCash = 0, totalCheckTransactionsAmount = 0;
	private String notes = null;
	ArrayList<Scout> scoutList;
	
	public Session() {
		
	}
	
	public static Session getInstance() {
		if(singleton == null) {
    		singleton = new Session();
    	}
    	return singleton;
	}
	
	private void getScouts() {
		if(scoutList == null) {
			DB db = DB.getInstance();
			scoutList = db.query("SELECT * FROM Scout WHERE Status!='Inactive';", "scout");
		}
	}
	
	public boolean save() {
		DB db = DB.getInstance();
		String sql = "";
		
		if(ID < 0) {
			sql = "INSERT INTO Session (StartDate, StartTime, EndTime, StartingCash, EndingCash, TotalCheckTransactionsAmount)";
			sql += " VALUES ('" + startDate.toString() + "',";
			sql += " '" + startTime.toString() + "',";
			sql += " '" + endTime.toString() + "',";
			sql += " '" + startingCash + "',";
			sql += " '" + endingCash + "',";
			sql += " '" + totalCheckTransactionsAmount + "');";
			ID = db.insert(sql);
			if(ID > -1) {
				return true;
			}
			else {
				return false;
			}
			
		}
		else {
			sql = "UPDATE Session SET";
			sql += " StartDate='" + startDate.toString() + "',";
			sql += " StartTime='" + startTime.toString() + "',";
			sql += " EndTime='" + endTime.toString() + "',";
			sql += " StartingCash='" + startingCash + "',";
			sql += " EndingCash='" + endingCash + "',";
			sql += " TotalCheckTransactionsAmount='" + totalCheckTransactionsAmount + "'";
			sql += " WHERE ID='" + ID + "';";
			return db.update(sql);
		}
	}
	
	public boolean delete() {
		DB db = DB.getInstance();
		
		//This marks the object as not having a copy in the database.
		ID = -1;
		
		return db.update("DELETE FROM Session WHERE ID='" + ID + "';");
	}
	
	public String toString() {
		return "ID: " + ID + "\tStart Time: " + startTime.toString() + "\tEnd Time:" + endTime.toString();
	}



	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the totalCheckTransactionsAmount
	 */
	public int getTotalCheckTransactionsAmount() {
		return totalCheckTransactionsAmount;
	}

	/**
	 * @param totalCheckTransactionsAmount the totalCheckTransactionsAmount to set
	 */
	public void setTotalCheckTransactionsAmount(int totalCheckTransactionsAmount) {
		this.totalCheckTransactionsAmount = totalCheckTransactionsAmount;
	}

	/**
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startTime
	 */
	public LocalTime getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public LocalTime getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/**
	 * @return the scoutList
	 */
	public ArrayList<Scout> getScoutList() {
		getScouts();
		return scoutList;
	}

	/**
	 * @return the startingCash
	 */
	public int getStartingCash() {
		return startingCash;
	}

	/**
	 * @param startingCash the startingCash to set
	 */
	public void setStartingCash(int startingCash) {
		this.startingCash = startingCash;
	}

	/**
	 * @return the endingCash
	 */
	public int getEndingCash() {
		return endingCash;
	}

	/**
	 * @param endingCash the endingCash to set
	 */
	public void setEndingCash(int endingCash) {
		this.endingCash = endingCash;
	}

}
