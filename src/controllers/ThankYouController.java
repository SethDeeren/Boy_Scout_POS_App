package controllers;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class ThankYouController {
    private IModel model;
    @FXML
    private Button done;

    @FXML
    void onDone(ActionEvent event) {
        try{
            model.stateChangeRequest("TreeLotCoordinatorView", null);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void setModel(IModel newModel) {
        model = newModel;
    }
}
