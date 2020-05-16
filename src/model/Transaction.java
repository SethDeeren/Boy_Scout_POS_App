package model;

import database.DB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Properties;

public class Transaction {

    private int sessionID = -1;
    private String transactionType = "Tree Type"; //because we only have trees now can be changed later
    private String treeBarcode = null;
    private int transactionAmount = 0;
    private String paymentMethod = null;
    private String customerName = null;
    private String customerPhone = null;
    private String customerEmail = null;
    private LocalDate transactionDate = null;
    private LocalTime transactionTime = null;
    private LocalDate dateStatusUpdated = null;

    // put values for parameters above will be in props object
    public Transaction(Properties prop){
        if(prop.containsKey("sessionID")) {
            setSessionID(Integer.parseInt(prop.getProperty("sessionID")));
        }
        if(prop.containsKey("transactionType")) {
            setTransactionType(prop.getProperty("transactionType"));
        }
        if(prop.containsKey("barcode")) {
            setTreeBarcode(prop.getProperty("barcode"));
        }
        if(prop.containsKey("transactionAmount")) {
            setTransactionAmount(Integer.parseInt(prop.getProperty("transactionAmount")));
        }
        if(prop.containsKey("paymentMethod")) {
            setPaymentMethod(prop.getProperty("paymentMethod"));
        }
        if(prop.containsKey("customerName")) {
            setCustomerName(prop.getProperty("customerName"));
        }
        if(prop.containsKey("customerPhone")) {
            setCustomerPhone(prop.getProperty("customerPhone"));
        }
        if(prop.containsKey("customerEmail")) {
            setCustomerEmail(prop.getProperty("customerEmail"));
        }
        if(prop.containsKey("transactionDate")) {
            setTransactionDate(LocalDate.parse(prop.getProperty("transactionDate")));
        }
        if(prop.containsKey("transactionTime")) {
            setTransactionTime(LocalTime.parse(prop.getProperty("transactionTime")));
        }
        if(prop.containsKey("dateStatusUpdated")){
            setDateStatusUpdated(LocalDate.parse(prop.getProperty("dateStatusUpdated")));

        }
    }

    //this will update database
    public boolean save() {
        String sql = "";
        DB db = DB.getInstance();

        //change sql to match this class the method was copied and pasted from another
        //Build SQL statement
        sql += "INSERT INTO Transaction ";
        sql += "(SessionID, TransactionType,Barcode,TransactionAmount,PaymentMethod,CustomerName,";
        sql += "CustomerPhone,CustomerEmail,TransactionDate,TransactionTime,DateStatusUpdated )";
        sql += " VALUES (" + sessionID + ", '" + transactionType + "', '" + treeBarcode + "', '"
                + transactionAmount + "', '" + paymentMethod + "', '" + customerName + "', '"
                + customerPhone + "', '" + customerEmail + "', '" + transactionDate + "', '" + transactionTime + "', '" + dateStatusUpdated + "');";

        System.out.println(sql);
        return db.update(sql);
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
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the treeBarcode
	 */
	public String getTreeBarcode() {
		return treeBarcode;
	}

	/**
	 * @param treeBarcode the treeBarcode to set
	 */
	public void setTreeBarcode(String treeBarcode) {
		this.treeBarcode = treeBarcode;
	}

	/**
	 * @return the transactionAmount
	 */
	public int getTransactionAmount() {
		return transactionAmount;
	}

	/**
	 * @param transactionAmount the transactionAmount to set
	 */
	public void setTransactionAmount(int transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the transactionDate
	 */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionTime
	 */
	public LocalTime getTransactionTime() {
		return transactionTime;
	}

	/**
	 * @param transactionTime the transactionTime to set
	 */
	public void setTransactionTime(LocalTime transactionTime) {
		this.transactionTime = transactionTime;
	}

	/**
	 * @return the dateStatusUpdated
	 */
	public LocalDate getDateStatusUpdated() {
		return dateStatusUpdated;
	}

	/**
	 * @param dateStatusUpdated the dateStatusUpdated to set
	 */
	public void setDateStatusUpdated(LocalDate dateStatusUpdated) {
		this.dateStatusUpdated = dateStatusUpdated;
	}
}
