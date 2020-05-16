package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import database.DB;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import model.TreeType;

public class TreeTypeSelectionScreenController {
	
	private IModel model = null;
	@FXML
	private ListView<TreeType> treeTypeListView;
	private ObservableList<TreeType> treeTypeListObservable;
	
	@FXML
	private Text deleteText;
	
	public TreeTypeSelectionScreenController() {
		
	}
	
	public void setModel(IModel newModel) {
		model = newModel;
	}
	
	private void populateTreeTypeListView() {
		treeTypeListView.setItems(treeTypeListObservable);
	}
	
	private TreeType getSelectedItem() {
		TreeType selectedTreeType = treeTypeListView.getSelectionModel().getSelectedItem();
		return selectedTreeType;
	}
	
	public void doSearch(Properties thisSearch) {
		String barcode = null;
		String sql = null;
		DB db = DB.getInstance();
		
		if(thisSearch.containsKey("barcode")) {
			barcode = thisSearch.getProperty("barcode");
		}
		
		//Build SQL statement
		sql = "SELECT * FROM TreeType WHERE";
		if(barcode != null) {
			sql += " BarcodePrefix='" + barcode + "'";
		}
		
		sql += ";";
		System.out.println(sql);
		
		//Query database
		//System.out.println(db.query(sql).toString());
		treeTypeListObservable = FXCollections.observableArrayList();
		treeTypeListObservable.setAll((ArrayList<TreeType>)db.query(sql, "treeType"));
		populateTreeTypeListView();
	}
	
	@FXML
	public void goMainMenu() {
		try {
			model.stateChangeRequest("TreeLotCoordinatorView", null);
		} catch (IOException e) {
			System.err.println("Unable to call TreeLotCoordinatorView.");
		}
	}
	
	@FXML 
	public void goBack() {
		try {
			model.stateChangeRequest("TreeTypeSearchView", null);
		} catch (IOException e) {
			System.err.println("Unable to call TreeTypeSearchView.");
		}
	}
	
	@FXML
	public void editThis() {
		TreeType treeType = getSelectedItem();
		try {
			model.stateChangeRequest("UpdateTreeTypeView", treeType);
		} catch (IOException e) {
			System.err.println("Unable to call UpdateTreeTypeView.");
		}
	}
}
