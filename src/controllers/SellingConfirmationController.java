package controllers;

import database.DB;
import impresario.IModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Tree;
import model.TreeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class SellingConfirmationController {
    private IModel model = null;

   private Properties transactionInfo;

    @FXML
    private Button yes;

    @FXML
    private Text treeTypeText;

    @FXML
    private Text costText;

    @FXML
    private Button no;

    @FXML
    void onNo(ActionEvent event) {
        try{

            model.stateChangeRequest("TreeLotCoordinatorView", null);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onYes(ActionEvent event) {
        try{
            model.stateChangeRequest("SellTreeView", transactionInfo);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setModel(IModel newModel) {
        model = newModel;
    }

    public void getTreeInfo(Tree tree){

        System.out.println("This is the inputed tree to tree info and its tree type " + tree.getTreeType());
        transactionInfo = new Properties();
        DB db = DB.getInstance();
        String sql = "SELECT * FROM TreeType WHERE BarcodePrefix = '" + tree.getBarcode().substring(0,2) + "';";
        ArrayList<TreeType> t = db.query(sql, "treeType");
        TreeType treeType = t.get(0);

        //setting transaction information
        transactionInfo.setProperty("Barcode", tree.getBarcode());
        transactionInfo.setProperty("TreeDescription", treeType.getTypeDescription());
        transactionInfo.setProperty("TreeCost", treeType.getCost());
        System.out.println("set transaction info successfully");

        //displaying confirmation text to the user
        treeTypeText.setText(treeType.getTypeDescription());
        costText.setText("$" + treeType.getCost() + ".00");
    }

}
