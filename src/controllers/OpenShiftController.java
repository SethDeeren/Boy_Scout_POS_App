package controllers;

import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.text.Text;
import javafx.util.converter.DateTimeStringConverter;
import model.Session;
import model.TreeType;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class OpenShiftController {

    private IModel model;
    private Session session;

    @FXML
    private TextField endHourTime;
    
    @FXML
    private TextField endMinTime;
    
    @FXML
    private Text endTimeMessage;

    @FXML
    private TextField startHourTime;
    
    @FXML
    private TextField startMinTime;
    
    @FXML
    private Text startTimeMessage;

    @FXML
    private Button submitButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField startCash;
    
    @FXML
    private ComboBox<String> startCombo;
    
    @FXML
    private ComboBox<String> endCombo;
    
    @FXML
    private Text cashMessage;
    
    @FXML
    public void initialize() {
    	session = Session.getInstance();
    	
    	startCombo.setItems(FXCollections.observableArrayList("AM", "PM"));
    	endCombo.setItems(FXCollections.observableArrayList("AM", "PM"));
    	startCombo.setValue("AM");
    	endCombo.setValue("AM");
    	
    	submitButton.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), startCash.textProperty(), startHourTime.textProperty(), startMinTime.textProperty(), endHourTime.textProperty(), endMinTime.textProperty(), endCombo.valueProperty(), startCombo.valueProperty()));
    	cashMessage.setVisible(false);
    }
    
    //This checks for all values entered by user to be valid
    private boolean isSubmitReady() {
    	boolean checks = ((checkStart()) | (checkEnd()));
    	if(!checks) {
    		//Dealing with AM/PM to 24-hour conversions.
    		int startHour = Integer.parseInt(startHourTime.getText());
        	if(startCombo.getValue().equals("PM") && startHour != 12) {
        		startHour += 12;
        	}
        	else if(startCombo.getValue().equals("AM") && startHour == 12) {
        		startHour = 0;
        	}
        	int endHour = Integer.parseInt(endHourTime.getText());
        	if(endCombo.getValue().equals("PM") && endHour != 12) {
        		endHour += 12;
        	}
        	if(endCombo.getValue().equals("AM") && endHour == 12) {
        		endHour = 0;
        	}
        	
        	session.setStartTime(LocalTime.of(startHour, Integer.parseInt(startMinTime.getText())));
        	session.setEndTime(LocalTime.of(endHour, Integer.parseInt(endMinTime.getText())));
        	
        	if(session.getEndTime().isBefore(session.getStartTime())) {
        		endTimeMessage.setText("End time is before start time.");
        		endTimeMessage.setVisible(true);
        		checks = true;
        	}
        	else {
        		endTimeMessage.setVisible(false);
        		checks = false;
        	}
    	}
    	checks = checkCash();
    	return checks;
    }
    
    private boolean checkCash() {
    	if(startCash.getText().isEmpty()) {
    		cashMessage.setVisible(false);
    		return true;
    	}
    	
    	if(!startCash.getText().matches("[0-9]+")) {
    		cashMessage.setVisible(true);
    		cashMessage.setText("Must be an integer");
    		return true;
    	}
    	
    	cashMessage.setVisible(false);
    	return false;
    }
    
    private boolean checkStart() {
    	if(startHourTime.getText().isEmpty() || startMinTime.getText().isEmpty()) {
    		startTimeMessage.setText("Must be HH:MM");
    		startTimeMessage.setVisible(true);
    		return true;
    	}
    	
    	if((!startHourTime.getText().matches("[0-9]+")) || (!startMinTime.getText().matches("[0-9]+"))) {
    		startTimeMessage.setVisible(true);
    		startTimeMessage.setText("Must be HH:MM");
    		return true;
    	}
    	
    	if((startHourTime.getText().length() <= 2) && (startMinTime.getText().length() <= 2)) {
    		if(Integer.parseInt(startHourTime.getText()) <= 12 && Integer.parseInt(startMinTime.getText()) <= 59) {
    			startTimeMessage.setVisible(false);
    	    	return false;
        	}
    		else {
    			return true;
    		}
    	}
    	else {
    		startTimeMessage.setText("Must be HH:MM");
    		startTimeMessage.setVisible(true);
    		return true;
    	}
    }
    
    private boolean checkEnd() {
    	if(endHourTime.getText().isEmpty() || endMinTime.getText().isEmpty()) {
    		endTimeMessage.setText("Must be HH:MM");
    		endTimeMessage.setVisible(true);
    		return true;
    	}
    	
    	if((!endHourTime.getText().matches("[0-9]+")) || (!endMinTime.getText().matches("[0-9]+"))) {
    		endTimeMessage.setVisible(true);
    		endTimeMessage.setText("Must be HH:MM");
    		return true;
    	}
    	
    	if((endHourTime.getText().length() <= 2) && (endMinTime.getText().length() <= 2)) {
    		if(Integer.parseInt(endHourTime.getText()) <= 12 && Integer.parseInt(endMinTime.getText()) <= 59) {
    			endTimeMessage.setVisible(false);
    	    	return false;
        	}
    		else {
    			return true;
    		}
    	}
    	else {
    		endTimeMessage.setText("Must be HH:MM");
    		endTimeMessage.setVisible(true);
    		return true;
    	}
    }

    @FXML
    void onSubmit(ActionEvent event) {
    	
    	session.setStartingCash(Integer.parseInt(startCash.getText()));
    	session.setStartDate(LocalDate.now());
    	
    	if(session.save()) {
	        try {
				model.stateChangeRequest("OpenShiftSelectScoutsView", null);
			} catch (IOException e) {
				System.err.println("Unable to open OpenShiftSelectScoutsView");
			}
    	}
    }

    @FXML
    void onCancel(ActionEvent event) {
        try {
			model.stateChangeRequest("TreeLotCoordinatorView", null);
		} catch (IOException e) {
			System.err.println("Unable to open TreeLotCoordinatorView");
		}
    }

    public void setModel(IModel newModel) {
        model = newModel;
    }

}
