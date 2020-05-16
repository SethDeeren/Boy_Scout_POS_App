package controllers;

import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.Tree;
import model.TreeType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import database.DB;

public class RegisterTreeController {

    private IModel model = null;
    private ArrayList<TreeType> treeTypeArray;
    private Tree currentTree;
    private boolean isValidPrefix = false;
    
    @FXML
    private Text succesMessage;
    
    @FXML
    private Text barcodeMessage;

    @FXML
    private TextField tf_barcode;

    @FXML
    private TextField tf_notes;

    @FXML
    private TextField tf_treeType;

    @FXML
    private DatePicker dp_DateStatus;
    
    @FXML
    private Button submit;

    public void setModel(IModel newModel) {
    	model = newModel;
    }
    
    //This checks for all values entered by user to be valid
    private boolean isSubmitReady() {



    	return (!isValidPrefix || tf_notes.getText().isEmpty() || (tf_barcode.getText().length() != 6));
    }
    
    @FXML
    private void initialize(){
        DB db = DB.getInstance();
        currentTree = new Tree();
        
        //Disables the submit button until all user input is valid.
        //Make sure you include the Observables for all fields you want to check.
        submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), tf_barcode.textProperty(), tf_notes.textProperty()));
        
        treeTypeArray = db.query("SELECT * FROM TreeType;", "treeType");
        dp_DateStatus.setDisable(true);
        dp_DateStatus.setValue(LocalDate.now());
        
        tf_barcode.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
            	if(tf_barcode.getText().length() >= 2) {
                	findTreeType();
                }
            	
            	if(tf_barcode.getText().length() == 6) {
            		barcodeMessage.setVisible(false);
            	}
            	else {
            		barcodeMessage.setText("Barcode must be 6 digits");
            		barcodeMessage.setVisible(true);
            	}
            }
        });
    }

    @FXML
    void cancelToPreviousView(ActionEvent event) throws IOException {
        model.stateChangeRequest("TreeLotCoordinatorView", null);
    }
    
    private void findTreeType() {
    	String prefix = tf_barcode.getText();
    	prefix = prefix.substring(0, 2);
    	for(int i = 0; i < treeTypeArray.size(); i++) {
    		if(treeTypeArray.get(i).getBarcodePrefix().equals(prefix)) {
    			tf_treeType.setText(treeTypeArray.get(i).getTypeDescription());
    			currentTree.setTreeType(treeTypeArray.get(i).getID());
    			isValidPrefix = true;
    			return;
    		}
    	}
    	tf_treeType.setText("Invalid Tree Type");
    	isValidPrefix = false;
    }

    @FXML
    void onSubmitClick(ActionEvent event) {
        String barcode = tf_barcode.getText();
        String notes = tf_notes.getText();
        String status = "Available";
        String dateStatus = "";
        if(dp_DateStatus.getValue() != null) {
            dateStatus = dp_DateStatus.getValue().toString();
        }
        currentTree.setBarcode(barcode);
        currentTree.setNotes(notes);
        currentTree.setDateStatusUpdated(dateStatus);
        currentTree.setStatus(status);

        if(currentTree.save()) {
            succesMessage.setVisible(true);
        }
    }
}