package controllers;

import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class TreeSearchController {



    @FXML
    private TextField treeBarcode;

    private IModel model = null;

    public void setModel(IModel newModel) {
        model = newModel;
    }

    public void onSearch(ActionEvent actionEvent) {
        Properties search = new Properties();
        if(treeBarcode.getText().length() == 9){
            System.out.println("onsubmitt");
            search.setProperty("Barcode",treeBarcode.getText());
            try {
                System.out.println("onTry");
                model.stateChangeRequest("TreeSelectionScreenView", search);
            } catch (IOException e) {
                System.err.println("Error in calling TreeSelectionScreenView");
            }
        }else{
            System.out.println("please enter barcode to search");
        }

    }

    public void onGoBack(ActionEvent actionEvent) throws IOException, SQLException {
        model.stateChangeRequest("TreeLotCoordinatorView", null);
    }
}
