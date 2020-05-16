package controllers;


import database.DB;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import model.Scout;
import model.Tree;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DeleteATreePopupController {

    @FXML
    private ListView<Tree> treeListView;
    private ObservableList<Tree> treeListObservable;

    public DeleteATreePopupController(ListView<Tree> treeListView, ObservableList<Tree> treeListObservable) {
        this.treeListView = treeListView;
        this.treeListObservable = treeListObservable;
    }

    private void populateTreeListView() { treeListView.setItems(treeListObservable); }

    private Tree getSelectedItem() {
        Tree selectedTree = treeListView.getSelectionModel().getSelectedItem();
        return selectedTree;
    }

    @FXML
    void deleteThis(ActionEvent event) {
        Tree tree = getSelectedItem();
        DB db = DB.getInstance();

        String sql = "DELETE FROM Scout WHERE ID=" + tree.getBarcode() + ";";
        if(db.update(sql)) {
            treeListObservable.remove(tree);
            populateTreeListView();
        }
    }

    private IModel model = null;

    public void setModel(IModel newModel) { model = newModel; }

    @FXML
    void cancelToPreviousView(ActionEvent event) throws IOException{
        model.stateChangeRequest("DeleteATreeView", null);
    }
}
