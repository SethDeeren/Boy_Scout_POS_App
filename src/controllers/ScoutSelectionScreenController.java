package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import database.DB;
import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Scout;

public class ScoutSelectionScreenController {
	
	private IModel model = null;
	@FXML
	private ListView<Scout> scoutListView;
	private ObservableList<Scout> scoutListObservable;
	@FXML
	private AnchorPane popup;
	
	@FXML
	private Text deleteText;
	
	public ScoutSelectionScreenController() {
		
	}
	
	public void setModel(IModel newModel) {
		model = newModel;
	}
	
	private void populateScoutListView() {
		scoutListView.setItems(scoutListObservable);
	}
	
	private Scout getSelectedItem() {
		Scout selectedScout = scoutListView.getSelectionModel().getSelectedItem();
		return selectedScout;
	}
	
	public void doSearch(Properties thisSearch) {
		String firstName = null;
		String lastName = null;
		String email = null;
		String sql = null;
		DB db = DB.getInstance();
		
		if(thisSearch.containsKey("firstName")) {
			firstName = thisSearch.getProperty("firstName");
		}
		if(thisSearch.containsKey("lastName")) {
			lastName = thisSearch.getProperty("lastName");
		}
		if(thisSearch.containsKey("email")) {
			email = thisSearch.getProperty("email");
		}
		
		//Build SQL statement
		ArrayList<String> sqlString = new ArrayList<String>();
		sql = "SELECT * FROM Scout WHERE";
		if(firstName != null) {
			sqlString.add(" FirstName='" + firstName + "'");
		}
		if(lastName != null) {
			sqlString.add(" LastName='" + lastName + "'");
		}
		if(email != null) {
			sqlString.add(" Email='" + email + "'");
		}
		sqlString.add(" Status!='Inactive'");
		for(int i=0; i < sqlString.size(); i++) {
			sql += sqlString.get(i);
			if(i < (sqlString.size() - 1)) {
				sql += " AND";
			}
		}
		
		sql += ";";
		System.out.println(sql);
		
		//Query database
		//System.out.println(db.query(sql).toString());
		scoutListObservable = FXCollections.observableArrayList();
		scoutListObservable.setAll((ArrayList<Scout>)db.query(sql, "scout"));
		populateScoutListView();
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
			model.stateChangeRequest("ScoutSearchView", null);
		} catch (IOException e) {
			System.err.println("Unable to call ScoutSearchView.");
		}
	}
	
	@FXML
	public void tryDelete() {
		popup.setVisible(true);
	}
	
	@FXML
	public void deleteThis() {
		Scout scout = getSelectedItem();
		DB db = DB.getInstance();
		
		//Don't actually delete, update to inactive.
		String sql = "UPDATE Scout SET Status='Inactive' WHERE ID = '" + scout.getId() + "';";
		if(db.update(sql)) {
			scoutListObservable.remove(scout);
			deleteText.setVisible(true);
			popup.setVisible(false);
			populateScoutListView();
		}
	}
	
	@FXML
	public void cancelDelete() {
		popup.setVisible(false);
	}
	
	@FXML
	public void editThis() {
		Scout scout = getSelectedItem();
		try {
			model.stateChangeRequest("UpdateScoutView", scout);
		} catch (IOException e) {
			System.err.println("Unable to call UpdateScoutView.");
		}
	}
}
