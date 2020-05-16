package controllers;

import database.DB;
import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import model.Scout;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RegisterScoutController {

    private IModel model = null;
    private Scout currentScout;
    private ArrayList<Scout> scoutArray;
    private boolean isValidTroopID = false;
    private boolean isValidPhoneNumber = false;
    private boolean isValidEmail = false;

    @FXML
    private TextField tf_firstName;

    @FXML
    private TextField tf_middleName;

    @FXML
    private TextField tf_lastName;

    @FXML
    private TextField tf_phoneNumber1;

    @FXML
    private TextField tf_phoneNumber2;

    @FXML
    private TextField tf_phoneNumber3;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_troopID;

    @FXML
    private DatePicker dp_DOB;

    @FXML
    private Text troopIDMessage;

    @FXML
    private Text phoneMessage1;

    @FXML
    private Text phoneMessage2;

    @FXML
    private Text phoneMessage3;

    @FXML
    private Text emailMessage;

    @FXML
    private Text successMessage;

    @FXML
    private Button submit;

    String pattern = "yyyy-MM-dd";

    public void setModel(IModel newModel) { model = newModel; }

    private boolean isSubmitReady() {
        checkEmail();
        checkTroopID();
        checkPhoneNumber1();
        checkPhoneNumber2();
        checkPhoneNumber3();
        return (!isValidTroopID || !isValidPhoneNumber || !isValidEmail || tf_firstName.getText().isEmpty() || tf_middleName.getText().isEmpty()
                || tf_lastName.getText().isEmpty() || tf_middleName.getText().isEmpty() || tf_email.getText().isEmpty()
                || dp_DOB.getValue() == null || tf_phoneNumber1.getText().length() != 3 || tf_phoneNumber2.getText().length() != 3
                || tf_phoneNumber3.getText().length() != 4 || tf_troopID.getText().length() != 9);
    }

    @FXML
    private void initialize(){
        DB db = DB.getInstance();
        currentScout = new Scout();

        //Disables the submit button until all user input is valid.
        //Make sure you include the Observables for all fields you want to check.
        submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), tf_firstName.textProperty(), tf_middleName.textProperty(),
                tf_lastName.textProperty(), tf_email.textProperty(), tf_troopID.textProperty(), tf_phoneNumber1.textProperty(),
                tf_phoneNumber2.textProperty(), tf_phoneNumber3.textProperty(), dp_DOB.valueProperty()));

        scoutArray = db.query("SELECT * FROM Scout;", "scout");
    }

    private void checkTroopID(){
        if(tf_troopID.getText().isEmpty()) {
            troopIDMessage.setVisible(false);
            return;
        }
        if(!tf_troopID.getText().matches("[0-9]+")) {
            troopIDMessage.setText("Troop ID must be an integer");
            troopIDMessage.setVisible(true);
            isValidTroopID = false;
            return;
        }
        if(tf_troopID.getText().length() == 9) {
            troopIDMessage.setVisible(false);
            for(Scout type : scoutArray) {
                if(type.getTroopID().equals(tf_troopID.getText())){
                    troopIDMessage.setText("Troop ID already exists");
                    troopIDMessage.setVisible(true);
                    isValidTroopID = false;
                    return;
                }
                else {
                    isValidTroopID = true;
                }
            }
        }
        else {
            troopIDMessage.setText("Troop ID must be 9 digits");
            troopIDMessage.setVisible(true);
            isValidTroopID = false;
        }
    }

    private void checkPhoneNumber1(){
        if(tf_phoneNumber1.getText().isEmpty()) {
            phoneMessage1.setVisible(false);
            return;
        }
        if(tf_phoneNumber1.getText().matches("[0-9]+")) {
            if(tf_phoneNumber1.getText().length() == 3) {
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
        if(tf_phoneNumber2.getText().isEmpty()) {
            phoneMessage2.setVisible(false);
            return;
        }
        if(tf_phoneNumber2.getText().matches("[0-9]+")) {
            if(tf_phoneNumber2.getText().length() == 3) {
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
        if(tf_phoneNumber3.getText().isEmpty()) {
            phoneMessage3.setVisible(false);
            return;
        }
        if(tf_phoneNumber3.getText().matches("[0-9]+")) {
            if(tf_phoneNumber3.getText().length() == 4) {
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
        if(tf_email.getText().isEmpty()) {
            emailMessage.setVisible(false);
            return;
        }
        if(tf_email.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+")) {
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
    void cancelToPreviousView(ActionEvent event) throws IOException {
        model.stateChangeRequest("TreeLotCoordinatorView", null);
    }

    @FXML
    void onSubmitClick(ActionEvent event) {
        String fName = tf_firstName.getText();
        String mName = tf_middleName.getText();
        String lName = tf_lastName.getText();
        String DOB = "";
        if(dp_DOB.getValue() != null) { DOB = dp_DOB.getValue().toString(); }
        dp_DOB.setPromptText(pattern.toLowerCase());
        dp_DOB.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });
        String phoneNumber1 = tf_phoneNumber1.getText();
        String phoneNumber2 = tf_phoneNumber2.getText();
        String phoneNumber3 = tf_phoneNumber3.getText();
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(phoneNumber1, 0, 3).append(")").append(phoneNumber2, 0, 3).append("-")
                .append(phoneNumber3, 0, 4);
        String phone = sb.toString();
        String email = tf_email.getText();
        String troopID = tf_troopID.getText();

        currentScout.setFirstName(fName);
        currentScout.setMiddleName(mName);
        currentScout.setLastName(lName);
        currentScout.setDateOfBirth(DOB);
        currentScout.setPhoneNumber(phone);
        currentScout.setEmail(email);
        currentScout.setTroopID(troopID);

        if (currentScout.save()){
            successMessage.setText("Scout has been successfully registered");
            successMessage.setVisible(true);
        }
    }
}
