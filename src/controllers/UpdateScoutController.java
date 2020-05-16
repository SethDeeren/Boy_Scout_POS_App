package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import database.DB;
import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.Scout;

public class UpdateScoutController {
	
	private IModel model = null;
	private Scout scout;
	private boolean isValidPhoneNumber = false;
	private boolean isValidEmail = false;

	@FXML
	private TextField firstName;
	@FXML
	private TextField middleName;
	@FXML
	private TextField lastName;
	@FXML
	private DatePicker DOB;
	@FXML
	private TextField phoneNumber1;
	@FXML
	private TextField phoneNumber2;
	@FXML
	private TextField phoneNumber3;
	@FXML
	private TextField email;
	@FXML
	private TextField troopID;
	@FXML
	private Text editText;

	@FXML
	private Button submit;

	@FXML
	private Text phoneMessage1;

	@FXML
	private Text phoneMessage2;

	@FXML
	private Text phoneMessage3;

	@FXML
	private Text emailMessage;
	
	public UpdateScoutController() {
		
	}
	
	public void setModel(IModel newModel) {
		model = newModel;
	}
	
	public void populateFields(Scout thisScout) {
		scout = thisScout;
		
		firstName.setText(scout.getFirstName());
		middleName.setText(scout.getMiddleName());
		lastName.setText(scout.getLastName());
		DOB.setValue(LocalDate.parse(scout.getDateOfBirth()));
		phoneNumber1.setText(scout.getPhoneNumber().substring(1, 4));
		phoneNumber2.setText(scout.getPhoneNumber().substring(5, 8));
		phoneNumber3.setText(scout.getPhoneNumber().substring(9));
		email.setText(scout.getEmail());
		troopID.setText(scout.getTroopID());
	}

	private boolean isSubmitReady() {
		checkEmail();
		checkPhoneNumber1();
		checkPhoneNumber2();
		checkPhoneNumber3();
		return (!isValidPhoneNumber || !isValidEmail || firstName.getText().isEmpty() || middleName.getText().isEmpty()
				|| lastName.getText().isEmpty() || middleName.getText().isEmpty() || email.getText().isEmpty()
				|| DOB.getValue() == null|| phoneNumber1.getText().length() != 3 ||  phoneNumber2.getText().length() != 3
				|| phoneNumber3.getText().length() != 4);
	}

	@FXML
	private void initialize(){
		//Disables the submit button until all user input is valid.
		//Make sure you include the Observables for all fields you want to check.
		submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), firstName.textProperty(), middleName.textProperty(),
				lastName.textProperty(), email.textProperty(), troopID.textProperty(), phoneNumber1.textProperty(), phoneNumber2.textProperty(),
				phoneNumber3.textProperty(), DOB.valueProperty()));
	}

	private void checkPhoneNumber1(){
		if(phoneNumber1.getText().isEmpty()) {
			phoneMessage1.setVisible(false);
			return;
		}
		if(phoneNumber1.getText().matches("[0-9]+")) {
			if(phoneNumber1.getText().length() == 3) {
				phoneMessage1.setVisible(false);
				isValidPhoneNumber = true;
			}
			else {
				phoneMessage1.setText("Must have digits in the form (###)###-####");
				phoneMessage1.setVisible(true);
				isValidPhoneNumber = false;
			}
		}
		else {
			phoneMessage1.setText("Must have digits in the form (###)###-####");
			phoneMessage1.setVisible(true);
			isValidPhoneNumber = false;
		}
	}

	private void checkPhoneNumber2(){
		if(phoneNumber2.getText().isEmpty()) {
			phoneMessage2.setVisible(false);
			return;
		}
		if(phoneNumber2.getText().matches("[0-9]+")) {
			if(phoneNumber2.getText().length() == 3) {
				phoneMessage2.setVisible(false);
				isValidPhoneNumber = true;
			}
			else {
				phoneMessage2.setText("Must have digits in the form (###)###-####");
				phoneMessage2.setVisible(true);
				isValidPhoneNumber = false;
			}
		}
		else {
			phoneMessage2.setText("Must have digits in the form (###)###-####");
			phoneMessage2.setVisible(true);
			isValidPhoneNumber = false;
		}
	}

	private void checkPhoneNumber3(){
		if(phoneNumber3.getText().isEmpty()) {
			phoneMessage3.setVisible(false);
			return;
		}
		if(phoneNumber3.getText().matches("[0-9]+")) {
			if(phoneNumber3.getText().length() == 4) {
				phoneMessage3.setVisible(false);
				isValidPhoneNumber = true;
			}
			else {
				phoneMessage3.setText("Must have digits in the form (###)###-####");
				phoneMessage3.setVisible(true);
				isValidPhoneNumber = false;
			}
		}
		else {
			phoneMessage3.setText("Must have digits in the form (###)###-####");
			phoneMessage3.setVisible(true);
			isValidPhoneNumber = false;
		}
	}

	private void checkEmail(){
		if(email.getText().isEmpty()) {
			emailMessage.setVisible(false);
			return;
		}
		if(email.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")) {
			emailMessage.setVisible(false);
			isValidEmail = true;
		}
		else {
			emailMessage.setText("Must be a valid Email");
			emailMessage.setVisible(true);
			isValidEmail = false;
		}
	}

	@FXML
	public void OnSubmit() {
		String phone1 = phoneNumber1.getText();
		String phone2 = phoneNumber2.getText();
		String phone3 = phoneNumber3.getText();
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(phone1, 0, 3).append(")").append(phone2, 0, 3).append("-")
				.append(phone3, 0, 4);
		String phone = sb.toString();
		String sql = null;
		DB db = DB.getInstance();
		ArrayList<String> sqlString = new ArrayList<String>();
		
		editText.setVisible(false);
		
		//Build SQL statement
		sql = "UPDATE Scout SET";
		if(!scout.getFirstName().equals(firstName.getText())) {
			sqlString.add(" FirstName=" + "'" + firstName.getText() + "'");
		}
		if(!scout.getMiddleName().equals(middleName.getText())) {
			sqlString.add(" MiddleName=" + "'" + middleName.getText() + "'");
		}
		if(!scout.getLastName().equals(lastName.getText())) {
			sqlString.add(" LastName=" + "'" + lastName.getText() + "'");
		}
		if(!scout.getDateOfBirth().equals(DOB.getValue().toString())) {
			sqlString.add(" DateOfBirth=" + "'" + DOB.getValue().toString() + "'");
		}
		if(!scout.getPhoneNumber().equals(phone)) {
			sqlString.add(" PhoneNumber=" + "'" + phone + "'");
		}
		if(!scout.getEmail().equals(email.getText())) {
			sqlString.add(" Email=" + "'" + email.getText() + "'");
		}
		if(!scout.getTroopID().equals(troopID.getText())) {
			if(troopID.getText().length() == 9) {
				sqlString.add(" TroopID=" + "'" + troopID.getText() + "'");
			}
			else {
				editText.setText("TroopID must be 9 digits.");
				editText.setVisible(true);
				return;
			}
		}
		
		for(int i=0; i < sqlString.size(); i++) {
			sql += sqlString.get(i);
			if(i < (sqlString.size() - 1)) {
				sql += " ,";
			}
		}
		
		sql += " WHERE ID=" + scout.getId() + ";";
		System.out.println(sql);
		
		if(!sqlString.isEmpty()) {
			if(db.update(sql)) {
				editText.setText("Scout has been successfully updated.");
				editText.setVisible(true);
			}
		}
	}
	
	@FXML
	public void goMainMenu() {
		try {
			model.stateChangeRequest("TreeLotCoordinatorView", null);
		} catch (IOException e) {
			System.err.println("This done broke!");
		}
	}
}
