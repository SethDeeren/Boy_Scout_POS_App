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
import java.util.Properties;

public class TreeTypeSearchController {

	@FXML
    private TextField barcode;
    
    @FXML
    private Text barcodeMessage;
    
    @FXML
    private Button submit;

    private IModel model = null;

    public void setModel(IModel newModel) {
        model = newModel;
    }
    
    private boolean isSubmitReady() {
    	return (barcode.getText().length() != 2);
    }
    
    @FXML
    private void initialize(){
        //Disables the submit button until all user input is valid.
        //Make sure you include the Observables for all fields you want to check.
        submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), barcode.textProperty()));
        
        barcode.setOnKeyReleased(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
            	if(barcode.getText().length() == 2) {
            		barcodeMessage.setVisible(false);
            	}
            	else {
            		barcodeMessage.setText("Barcode must be 2 digits");
            		barcodeMessage.setVisible(true);
            	}
            }
        });
    }
    
    @FXML
    void onSubmit(ActionEvent event) {
    	Properties search = new Properties();
	    search.setProperty("barcode", barcode.getText());
	    try {
			model.stateChangeRequest("TreeTypeSelectionScreenView", search);
		} catch (IOException e) {
			System.err.println("Error in calling TreeTypeSelectionScreenView");
		}
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException {
        model.stateChangeRequest("TreeLotCoordinatorView", null);
    }


}
