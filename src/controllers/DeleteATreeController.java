package controllers;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Tree;

import java.io.IOException;
import java.sql.SQLException;

public class DeleteATreeController {

    private IModel model = null;

    @FXML
    private TextField tf_barcode;

    public void setModel(IModel newModel) { model = newModel; }

    @FXML
    void cancelToPreviousView(ActionEvent event) throws IOException, SQLException {
        model.stateChangeRequest("TreeLotCoordinatorView", null);
    }

    @FXML
    void onSubmitClick(ActionEvent event) throws IOException, SQLException {
        String treeBarcode = tf_barcode.getText();

        if ((treeBarcode.length() == 0)) {
            System.out.println("Please fill all fields!");
        }
        else {
            model.stateChangeRequest("DeleteATreePopupView", null);
        }
    }
}
