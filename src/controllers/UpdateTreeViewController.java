package controllers;

import database.DB;
import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.Tree;
import model.TreeType;

import java.util.Date;
import java.util.HashMap;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.LocalDate.parse;

public class UpdateTreeViewController {
    private IModel model;
    private ArrayList<TreeType> treeTypeArray;
    private Tree currentTree;
    private boolean isValidPrefix = false;
    
    @FXML
    private DatePicker lastUpdated;

    @FXML
    private TextArea notes;

    @FXML
    private TextField Barcode;
    
    @FXML
    private Text barcodeMessage;

    @FXML
    private TextField treeType;

    @FXML
    private Text editText;
    
    @FXML
    private Button submit;


     ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Available",
                    "Sold",
                    "Damaged"
            );

    @FXML
    private ComboBox<String> status;

    @FXML
    private void initialize(){
        status.setItems(options);
        DB db = DB.getInstance();
        currentTree = new Tree();
        
        //Disables the submit button until all user input is valid.
        //Make sure you include the Observables for all fields you want to check.
        submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), Barcode.textProperty(), notes.textProperty()));
        
        treeTypeArray = db.query("SELECT * FROM TreeType;", "treeType");
        lastUpdated.setDisable(true);
        lastUpdated.setValue(LocalDate.now());
        
        Barcode.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
            	if(Barcode.getText().length() >= 2) {
                	findTreeType();
                }
            	
            	if(Barcode.getText().length() == 6) {
            		barcodeMessage.setVisible(false);
            	}
            	else {
            		barcodeMessage.setText("Barcode must be 6 digits");
            		barcodeMessage.setVisible(true);
            	}
            }
        });
    }
    
  //This checks for all values entered by user to be valid
    private boolean isSubmitReady() {
    	return (!isValidPrefix || notes.getText().isEmpty() || Barcode.getText().length() != 6);
    }
    
    private void findTreeType() {
    	String prefix = Barcode.getText();
    	prefix = prefix.substring(0, 2);
    	for(int i = 0; i < treeTypeArray.size(); i++) {
    		if(treeTypeArray.get(i).getBarcodePrefix().equals(prefix)) {
    			treeType.setText(treeTypeArray.get(i).getTypeDescription());
    			currentTree.setTreeType(treeTypeArray.get(i).getID());
    			isValidPrefix = true;
    			return;
    		}
    	}
    	treeType.setText("Invalid Tree Type");
    	isValidPrefix = false;
    }

    public void onSubmit(ActionEvent actionEvent) {
        String sql = null;
        DB db = DB.getInstance();
        ArrayList<String> sqlString = new ArrayList<String>();
        editText.setVisible(false);
        //Build SQL statement
        sql = "UPDATE Tree SET";
        if (!currentTree.getBarcode().equals(Barcode.getText())) {
            sqlString.add(" Barcode=" + "'" + Barcode.getText() + "'");
            sqlString.add(" TreeType=" + "'" + currentTree.getTreeType() + "'");
        }
        if (!currentTree.getNotes().equals(notes.getText())) {
            sqlString.add(" Notes=" + "'" + notes.getText() + "'");
        }
        if (!currentTree.getStatus().equals(status.getValue())) {
            sqlString.add(" Status=" + "'" + status.getValue() + "'");
        }
        if (!currentTree.getDateStatusUpdated().equals(lastUpdated.getValue().toString())) {
            sqlString.add(" DateStatusUpdated=" + "'" + lastUpdated.getValue().toString() + "'");
        }
        
        for(int i=0; i < sqlString.size(); i++) {
            sql += sqlString.get(i);
            if(i < (sqlString.size() - 1)) {
                sql += " ,";
            }
        }

        sql += " WHERE Barcode='" + currentTree.getBarcode() + "';";
        System.out.println(sql);

        if(!sqlString.isEmpty()) {
            if(db.update(sql)) {
                editText.setText("Tree has been successfully updated.");
                editText.setVisible(true);
            }
        }
    }
    
    public void onCancel(ActionEvent actionEvent) {
        try {
            model.stateChangeRequest("TreeLotCoordinatorView", null);
        } catch (IOException e) {
            System.err.println("This done broke!");
        }
    }

    public void setModel(IModel newModel) {
        model = newModel;
    }

    public void populateFields(Tree thisTree)  {
        currentTree = thisTree;

        Barcode.setText(currentTree.getBarcode());
        findTreeType();
        notes.setText(currentTree.getNotes());
        lastUpdated.setDisable(true);
        lastUpdated.setValue(LocalDate.now());
        status.setValue(currentTree.getStatus());
    }
}