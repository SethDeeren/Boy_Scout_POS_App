package controllers;

import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.TreeType;

import java.io.IOException;
import java.util.ArrayList;

import database.DB;

public class RegisterTreeTypeController {

    private IModel model = null;
    private boolean isValidPrefix = false;
    private boolean isValidCost = false;
    private ArrayList<TreeType> treeTypeArray;
    private TreeType currentTreeType;

    @FXML
    private TextField description;
    @FXML
    private TextField cost;
    @FXML
	private Text costMessage;
    @FXML
	private Text editText;
    @FXML
    private TextField barcode;
    @FXML
	private Text barcodeMessage;
    @FXML
    private Button submit;

    public void setModel(IModel newModel) { model = newModel; }
    
    //This checks for all values entered by user to be valid
    private boolean isSubmitReady() {
    	checkPrefix();
    	checkCost();
    	return (!isValidPrefix || description.getText().isEmpty() || !isValidCost);
    }
    
    @FXML
    private void initialize(){
        DB db = DB.getInstance();
        currentTreeType = new TreeType();
        
        //Disables the submit button until all user input is valid.
        //Make sure you include the Observables for all fields you want to check.
        submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), description.textProperty(), cost.textProperty(), barcode.textProperty()));
        
        treeTypeArray = db.query("SELECT * FROM TreeType;", "treeType");
    }
    
    private void checkCost() {
    	if(cost.getText().isEmpty()) {
    		costMessage.setVisible(false);
    		return;
    	}
    	if(cost.getText().matches("[0-9]+")) {
    		costMessage.setVisible(false);
    		isValidCost = true;
    	}
    	else {
    		costMessage.setText("The cost must be an integer");
    		costMessage.setVisible(true);
    		isValidCost = false;
    	}
    }
    
    private void checkPrefix() {
    	if(barcode.getText().isEmpty()) {
    		barcodeMessage.setVisible(false);
    		return;
    	}
    	
    	if(!barcode.getText().matches("[0-9]+")) {
    		barcodeMessage.setVisible(true);
    		barcodeMessage.setText("Prefix must be an integer");
    		isValidPrefix = false;
    		return;
    	}
    	
    	if(barcode.getText().length() == 2) {
    		barcodeMessage.setVisible(false);
	    	for(TreeType type : treeTypeArray) {
	    		if(type.getBarcodePrefix().equals(barcode.getText())) {
	    			barcodeMessage.setText("Prefix already exists");
	    			barcodeMessage.setVisible(true);
	    			isValidPrefix = false;
	    			return;
	    		}
	    		else {
	    			isValidPrefix = true;
	    		}
	    	}
    	}
    	else {
    		barcodeMessage.setText("Prefix must be 2 digits");
    		barcodeMessage.setVisible(true);
    		isValidPrefix = false;
    	}
    }
    
    @FXML
    public void goMainMenu() {
        try {
            model.stateChangeRequest("TreeLotCoordinatorView", null);
        } catch (IOException e) {
            System.err.println("Unable to call TreeLotCoordinatorView.");
        }
    }

    @FXML
    void onSubmit(ActionEvent event) {
        currentTreeType.setTypeDescription(description.getText());
        currentTreeType.setCost(cost.getText());
        currentTreeType.setBarcodePrefix(barcode.getText());

        if(currentTreeType.save()) {
    		editText.setVisible(true);
    	}
    }
}