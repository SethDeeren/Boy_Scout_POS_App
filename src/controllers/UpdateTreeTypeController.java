package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DB;
import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.TreeType;

public class UpdateTreeTypeController {
	
	private IModel model = null;
    private boolean isValidCost = false;
    private TreeType currentTreeType;
    
	@FXML
	private TextField typeDescription;
	@FXML
	private TextField cost;
	@FXML
	private Text costMessage;
	@FXML
	private TextField barcode;
	@FXML
	private Text barcodeMessage;
	@FXML
	private Text editText;
	@FXML
    private Button submit;
	
	public UpdateTreeTypeController() {
		
	}
	
	public void setModel(IModel newModel) {
		model = newModel;
	}
	
	//This checks for all values entered by user to be valid
    private boolean isSubmitReady() {
    	checkCost();
    	return (typeDescription.getText().isEmpty() || !isValidCost);
    }
    
    @FXML
    private void initialize(){
    	currentTreeType = new TreeType();
        //Disables the submit button until all user input is valid.
        //Make sure you include the Observables for all fields you want to check.
        submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), typeDescription.textProperty(), cost.textProperty()));
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
	
	public void populateFields(TreeType thisTreeType) {
		currentTreeType = thisTreeType;
		
		typeDescription.setText(currentTreeType.getTypeDescription());
		cost.setText(currentTreeType.getCost());
		barcode.setText(currentTreeType.getBarcodePrefix());
		barcode.setDisable(true);
	}
	
	@FXML
	public void onSubmit() {
		String sql = null;
		DB db = DB.getInstance();
		ArrayList<String> sqlString = new ArrayList<String>();
		
		//Build SQL statement
		sql = "UPDATE TreeType SET";
		if(!currentTreeType.getTypeDescription().equals(typeDescription.getText())) {
			sqlString.add(" TypeDescription=" + "'" + typeDescription.getText() + "'");
		}
		if(!currentTreeType.getCost().equals(cost.getText())) {
			sqlString.add(" Cost=" + "'" + cost.getText() + "'");
		}
		if(!currentTreeType.getBarcodePrefix().equals(barcode.getText())) {
			sqlString.add(" BarcodePrefix=" + "'" + barcode.getText() + "'");
		}
		
		for(int i=0; i < sqlString.size(); i++) {
			sql += sqlString.get(i);
			if(i < (sqlString.size() - 1)) {
				sql += " ,";
			}
		}
		
		sql += " WHERE ID=" + currentTreeType.getID() + ";";
		System.out.println(sql);
		
		if(!sqlString.isEmpty()) {
			if(db.update(sql)) {
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
