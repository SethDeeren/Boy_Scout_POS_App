package controllers;

import database.DB;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.PopupControl;
import javafx.scene.text.Text;
import model.Tree;
import javafx.scene.layout.AnchorPane;
import model.Tree;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class TreeSelectionScreenController {

    private IModel model = null;

    @FXML
    private ListView<Tree> treeListView;
    private ObservableList<Tree> treeListObservable;

    @FXML
    private Text deleteText;

    @FXML
    private Button deleteButton;



    @FXML
    private AnchorPane popup;


    @FXML
    private Button editButton;

    @FXML
    private Button menuButton;

    @FXML
    private Button backButton;

    private Tree getSelectedItem() {
        Tree selectedTree = treeListView.getSelectionModel().getSelectedItem();
        return selectedTree;
    }


    @FXML
    void goMainMenu(ActionEvent event) {
        try {
            model.stateChangeRequest("TreeLotCoordinatorView", null);
        } catch (IOException e) {
            System.err.println("Unable to call TreeLotCoordinatorView.");
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        try {
            model.stateChangeRequest("TreeSearchView", null);
        } catch (IOException e) {
            System.err.println("Unable to call TreeLotCoordinatorView.");
        }
    }

    @FXML
    void editThis(ActionEvent event) {
        //Tree tree =null;
        Tree tree = getSelectedItem();
        try {
            model.stateChangeRequest("UpdateTreeView", tree);
        } catch (IOException e) {
            System.err.println("Unable to call UpdateScoutView.");
        }
    }
    @FXML
    public void tryDelete(ActionEvent actionEvent) {
        popup.setVisible(true);
    }

    @FXML
    public void deleteThis(ActionEvent actionEvent) {
        Tree tree = getSelectedItem();
        DB db = DB.getInstance();

        //Don't actually delete, update to inactive.
        String sql = "DELETE FROM Tree WHERE Barcode = '" + tree.getBarcode() + "';";
        if(db.update(sql)) {
            treeListObservable.remove(tree);
            deleteText.setVisible(true);
            popup.setVisible(false);
            populateTreeListView();
        }


//        Tree tree = getSelectedItem();
//        DB db = new DB("test.shearhomeautomation.com", "CSC429", "csc429", "MUtNBY0oP8PJikmh");
//
//        String sql = "DELETE FROM Tree WHERE Barcode=" + tree.getBarcode() + ";";
//        if(db.update(sql)) {
//            treeListObservable.remove(tree);
//            deleteText.setVisible(true);
//            populateTreeListView();
//        }
    }


    @FXML
    void sellThis(ActionEvent event) {

    }

    public void setModel(IModel newModel) {
        model = newModel;
    }

    private void populateTreeListView() {
        treeListView.setItems(treeListObservable);
    }

    public void doSearch(Properties thisSearch) {
        String Barcode = null;

        String sql = null;
        DB db = DB.getInstance();

        if(thisSearch.containsKey("Barcode")) {
            Barcode = thisSearch.getProperty("Barcode");
        }


        //Build SQL statement
        sql = "SELECT * FROM Tree WHERE Barcode = '" + Barcode +"';";


        System.out.println(sql);

        //Query database
        //System.out.println(db.query(sql).toString());
        treeListObservable = FXCollections.observableArrayList();
        treeListObservable.setAll((ArrayList<Tree>)db.query(sql, "tree"));
        populateTreeListView();
    }


    public void onNo(ActionEvent actionEvent) {
    }

    public void onYes(ActionEvent actionEvent) {
    }


    public void cancelDelete(ActionEvent actionEvent) {
    }
}
