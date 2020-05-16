package controllers;

import database.DB;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import impresario.IModel;
import model.Session;
import model.Tree;

import java.io.IOException;
import java.util.ArrayList;

public class TreeLotCoordinatorViewController {

    private IModel model = null;
    private Session session = Session.getInstance();

    public void viewFactory(String view, ActionEvent event) throws IOException {
        System.out.println("VIEW FACTORY FOR TLC");
        Parent parent = FXMLLoader.load(TreeLotCoordinatorViewController.class.getResource(view));
        Scene sceneTwo = new Scene(parent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(sceneTwo);
        window.show();
    }


    public void setModel(IModel newModel) {
        model = newModel;
    }

    @FXML
    private Button openShift;

    @FXML
    private Button editCloseShift;

    @FXML
    private Button sellTreeButton;



    @FXML
    private Text sellTreeMessage;

    @FXML
    private Label caption;

    @FXML
    private Label title;

    @FXML
    private TextField sellTreeBarcode;

    public void goToRegisterScout(javafx.event.ActionEvent event)  {
        System.out.println("RegisterScoutView");
        try {
            System.out.println("before state change request");
            //viewFactory("RegisterScoutView.fxml", event);
            model.stateChangeRequest("RegisterScoutView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSearchScouts(javafx.event.ActionEvent event) {
        System.out.println("ScoutSearchView");
        try {
            System.out.println("before state change request");
            //viewFactory("ScoutSearchView.fxml", event);
            model.stateChangeRequest("ScoutSearchView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }
    }

    public void goToRegisterTreeType(javafx.event.ActionEvent event)  {
        System.out.println("RegisterTreeTypeView");
        try {
            System.out.println("before state change request");
            model.stateChangeRequest("RegisterTreeTypeView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }
    }
    public void goToRegisterTree(javafx.event.ActionEvent event)  {
        System.out.println("RegisterTreeView");
        try {
            System.out.println("before state change request");
            model.stateChangeRequest("RegisterTreeView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }
    }

    @FXML
    public void goToSearchTreeTypes(javafx.event.ActionEvent event) {
        System.out.println("TreeTypeSearchView");
        try {
            System.out.println("before state change request");
            model.stateChangeRequest("TreeTypeSearchView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }
    }
    @FXML
    public void goToSearchTrees(javafx.event.ActionEvent event) {
        System.out.println("TreeSearchView");
        try {
            System.out.println("before state change request");
            model.stateChangeRequest("TreeSearchView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }
    }
    @FXML
    public void goToDeleteTrees(javafx.event.ActionEvent event) {
        System.out.println("DeleteATreeView");
        try {
            System.out.println("before state change request");
            model.stateChangeRequest("DeleteATreeView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }
    }

    @FXML
    public void goToDeleteScouts(javafx.event.ActionEvent event) {

    }

    @FXML
    public void onExit(javafx.event.ActionEvent event) {

    }


    public void openShift(ActionEvent actionEvent) {
        System.out.println("OpenShiftView");
        try {
            System.out.println("before state change request");
            model.stateChangeRequest("OpenShiftView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }
    }

    @FXML
    public void closeShift(ActionEvent actionEvent) {
        System.out.println("CloseAShiftView");
        try {
            System.out.println("before state change request");
            model.stateChangeRequest("CloseAShiftView", null);
        } catch (IOException e) {
            System.out.println("in catch block");
            e.printStackTrace();
        }

//        System.out.println("after delete session id is " + session.getID());
//        initialize();
    }

    public void onSellTree(ActionEvent actionEvent) {
        System.out.println("on Sell a tree action");
        String barcode = sellTreeBarcode.getText();

        try{
            Tree tree = getTree(barcode);
            System.out.println("this tree's status is " + tree.getStatus());
            if(!tree.getStatus().equals("Sold")){
                try{
                    model.stateChangeRequest("SellingConfirmationView", tree);
                }catch (IOException e){
                    System.out.println("caught an exception in sell a tree");
                    e.printStackTrace();
                }
            }else{
                System.out.println("invalid barcode");
                sellTreeMessage.setText("This tree has already been sold");
            }
        }catch(Exception e){
            //System.out.println("invalid barcode");
            sellTreeMessage.setText("Invalid barcode try again");

        }


    }

    public Tree getTree(String barcode){
        DB db = DB.getInstance();
        String sql = "Select * From Tree WHERE barcode = '" + barcode + "';";
        ArrayList<Tree> trees = db.query(sql, "tree");
        Tree tree = trees.get(0);
        return tree;
    }




    //This checks for all values entered by user to be valid
    private boolean isOpenShiftReady() {
        if(session.getID() > 0 ){
            sellTreeMessage.setText("");
        } else {
            sellTreeMessage.setText("A session must be open to sell a Tree.");
        }
        return (session.getID() < 0);
    }


    @FXML
    private void initialize(){

        // disable open shift until a shift is closed
        openShift.disableProperty().bind(Bindings.createBooleanBinding(() -> !isOpenShiftReady()));
        editCloseShift.disableProperty().bind(Bindings.createBooleanBinding(()-> isOpenShiftReady()));
        sellTreeButton.disableProperty().bind(Bindings.createBooleanBinding(()-> isOpenShiftReady()));
        sellTreeBarcode.promptTextProperty().set("Enter Tree Barcode");
    }

}
