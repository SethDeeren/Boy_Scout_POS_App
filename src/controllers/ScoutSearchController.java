package controllers;

import database.DB;
import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class ScoutSearchController {

    private IModel model = null;

    @FXML
    private TextField scoutFirstName;

    @FXML
	private Button submit;

    @FXML
    private TextField scoutLastName;

    @FXML
    private TextField scoutEmail;

    public void setModel(IModel newModel) {
        model = newModel;
    }
    
    @FXML
    void onSubmit(ActionEvent event) {
    	if(!(scoutFirstName.getText().equals("")) || !(scoutLastName.getText().equals("")) || !(scoutEmail.getText().equals(""))) {
    		System.out.println("onSubmit()");
	    	Properties search = new Properties();
	    	if(!scoutFirstName.getText().equals("")) {
	    		search.setProperty("firstName", scoutFirstName.getText());
	    	}
	    	if(!scoutLastName.getText().equals("")) {
	    		search.setProperty("lastName", scoutLastName.getText());
	    	}
	    	if(!scoutEmail.getText().equals("")) {
	    		search.setProperty("email", scoutEmail.getText());
	    	}
	    	
	    	try {
				model.stateChangeRequest("ScoutSelectionScreenView", search);
			} catch (IOException e) {
				System.err.println("Error in calling ScoutSelectionScreenView");
			}
    	}
    	else {
    		System.out.println("You must input a value");
    	}
    }

    @FXML
    void onCancel(ActionEvent event) throws IOException, SQLException {
        //ViewFactoryController.createView("TreeLotCoordinatorView.fxml", event);
        model.stateChangeRequest("TreeLotCoordinatorView", null);
    }


	private boolean isSubmitReady() {
		return (scoutFirstName.getText().length() == 0 && scoutLastName.getText().length() == 0 && scoutEmail.getText().length() == 0);
	}

	@FXML
	private void initialize(){
		DB db = DB.getInstance();

		// listens and checks for any text field to be filled out
		submit.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady()));

		scoutFirstName.setOnKeyReleased(new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				initialize();
			}
		});

		scoutLastName.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				initialize();
			}
		});

		scoutEmail.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				initialize();
			}
		});
	}


}
