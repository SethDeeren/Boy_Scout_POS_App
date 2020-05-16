package model;

import java.util.Properties;

import database.DB;

public class Scout {
	private String id = null;
	private String lastName = null;
	private String firstName = null;
	private String middleName = null;
	private String dateOfBirth = null;
	private String phoneNumber = null;
	private String email = null;
	private String troopID = null;
	private String Status = "Active";
	
	public Scout() {
		
	}
	
	public Scout(Properties prop) {
		if(prop.containsKey("id")) {
			setId(prop.getProperty("id"));
		}
		if(prop.containsKey("lastName")) {
			setLastName(prop.getProperty("lastName"));
		}
		if(prop.containsKey("firstName")) {
			setFirstName(prop.getProperty("firstName"));
		}
		if(prop.containsKey("middleName")) {
			setMiddleName(prop.getProperty("middleName"));
		}
		if(prop.containsKey("dateOfBirth")) {
			setDateOfBirth(prop.getProperty("dateOfBirth"));
		}
		if(prop.containsKey("phoneNumber")) {
			setPhoneNumber(prop.getProperty("phoneNumber"));
		}
		if(prop.containsKey("email")) {
			setEmail(prop.getProperty("email"));
		}
		if(prop.containsKey("troopID")) {
			setTroopID(prop.getProperty("troopID"));
		}
	}
	
	public boolean save() {
		String sql = null;
		DB db = DB.getInstance();
		
		//Build SQL statement
		sql = "INSERT INTO Scout (FirstName, MiddleName, LastName, DateOfBirth, PhoneNumber, Email, TroopID, Status)";
		sql += " VALUES ('" + firstName + "', '" + middleName + "', '" + lastName + "', '" + dateOfBirth + "', '" + phoneNumber + "', '" + email + "', '" + troopID;
		sql += "', 'Active');";
		
		System.out.println(sql);
		return db.update(sql);
	}
	
	public Properties getProperties() {
		Properties prop = new Properties();
		
		prop.setProperty("lastName", lastName);
		prop.setProperty("firstName", firstName);
		prop.setProperty("middleName", middleName);
		prop.setProperty("dateOfBirth", dateOfBirth);
		prop.setProperty("phoneNumber", String.valueOf(phoneNumber));
		prop.setProperty("email", email);
		prop.setProperty("troopID", troopID);
		
		return prop;
	}
	
	public String toString() {
		String output = "ID: " + id + "    First Name: " + firstName + "    Last Name: " + lastName + "    Email: " + email;
		
		return output;
	}
	
	/*
	@Override
	public boolean equals(Object scout) {
		Scout otherScout = (Scout)scout;
		if(this.getId() != null && otherScout.getId() != null) {
			System.out.println(getId() + " , " + otherScout.getId());
			return this.getId().equals(otherScout.getId());
		}
		return false;
	}
	*/

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the troopID
	 */
	public String getTroopID() {
		return troopID;
	}

	/**
	 * @param troopID the troopID to set
	 */
	public void setTroopID(String troopID) {
		this.troopID = troopID;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return Status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		Status = status;
	}
}
