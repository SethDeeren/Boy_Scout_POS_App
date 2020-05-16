package controllers;

import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import database.DB;

public class TreeSearchViewController {

    @FXML
    private TextField treeBarcode;
    
    @FXML
    private Text barcodeMessage;
    
    @FXML
    private Button submit;

    private IModel model = null;

    public void setModel(IModel newModel) {
        model = newModel;
    }
    
    private boolean isSubmitReady() {
    	return (treeBarcode.getText().length() != 6);
    }
    
    @FXML
    private void initialize(){
        DB db = DB.getInstance();
        
        //Disables the submit button until all user input is valid.
        //Make sure you include the Observables for all fields you want to check.
        submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), treeBarcode.textProperty()));
        
        treeBarcode.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
            	if(treeBarcode.getText().length() == 6) {
            		barcodeMessage.setVisible(false);
            	}
            	else {
            		barcodeMessage.setText("Barcode must be 6 digits");
            		barcodeMessage.setVisible(true);
            	}
            }
        });
    }

    public void onSearch(ActionEvent actionEvent) throws IOException {
        Properties search = new Properties();
        search.setProperty("Barcode",treeBarcode.getText());
        model.stateChangeRequest("TreeSelectionScreenView", search);
    }

    public void onGoBack(ActionEvent actionEvent) throws IOException, SQLException {
        model.stateChangeRequest("TreeLotCoordinatorView", null);
    }
}
