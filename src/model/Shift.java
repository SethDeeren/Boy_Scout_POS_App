package model;

import java.time.LocalTime;

import database.DB;

public class Shift {
	private int ID = -1, sessionID = -1, scoutID = -1, hours = 0;
	private String name = null;
	private LocalTime startTime, endTime;
	
	public Shift() {
		
	}
	
	public boolean save() {
		DB db = DB.getInstance();
		String sql = "";
		
		if(ID < 0) {
			sql = "INSERT INTO Shift (SessionID, ScoutID, CompanionName, StartTime, EndTime, CompanionHours)";
			sql += " VALUES ('" + sessionID + "',";
			sql += " '" + scoutID + "',";
			sql += " '" + name + "',";
			sql += " '" + startTime.toString() + "',";
			sql += " '" + endTime.toString() + "',";
			sql += " '" + hours + "');";
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
			sql += " SessionID='" + sessionID + "',";
			sql += " ScoutID='" + scoutID + "',";
			sql += " CompanionName='" + name + "',";
			sql += " StartTime='" + startTime.toString() + "',";
			sql += " EndTime='" + endTime.toString() + "',";
			sql += " CompanionHours='" + hours + "'";
			sql += " WHERE ID='" + ID + "';";
			return db.update(sql);
		}
	}
	
	public String toString() {
		return "ID: " + ID + "\tsessionID: " + sessionID + "\tscoutID:" + scoutID;
	}
	
	public boolean delete() {
		DB db = DB.getInstance();
		return db.update("DELETE FROM Shift WHERE ID='" + ID + "';");
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
	 * @return the sessionID
	 */
	public int getSessionID() {
		return sessionID;
	}
	/**
	 * @param sessionID the sessionID to set
	 */
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	/**
	 * @return the scoutID
	 */
	public int getScoutID() {
		return scoutID;
	}
	/**
	 * @param scoutID the scoutID to set
	 */
	public void setScoutID(int scoutID) {
		this.scoutID = scoutID;
	}
	/**
	 * @return the hours
	 */
	public int getHours() {
		return hours;
	}
	/**
	 * @param hours the hours to set
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
}
