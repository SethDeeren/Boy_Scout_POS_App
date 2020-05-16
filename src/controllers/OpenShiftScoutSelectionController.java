package controllers;

import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.Scout;
import model.Session;
import model.Shift;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;

public class OpenShiftScoutSelectionController {
    private IModel model;
    private Session session;
    private Scout selected;
    private ArrayList<Shift> shiftList;
    
    @FXML
    private TextField companionName;
    
    @FXML
    private TextField companionHourTime;
    
    @FXML
    private TextField companionMinTime;
    
    @FXML
    private ComboBox<String> companionTime;
    
    @FXML
    private TextField companionHours;

    @FXML
    private AnchorPane popup;

    @FXML
    private Text editText;
    
    @FXML
    private Text hoursText;
    
    @FXML
    private Text timeText;

    @FXML
    private Button menuButton;

    @FXML
    private ListView<Scout> scoutListView;
    
    @FXML
    private ListView<Scout> selectedListView;

    @FXML
    private Button addButton;

    @FXML
    private Button finishButton;

    @FXML
    private Button popupSubmit;

    @FXML
    private Button popupCancel;
    
    @FXML
    public void initialize() {
    	session = Session.getInstance();
    	shiftList = new ArrayList<Shift>();
    	
    	companionTime.setItems(FXCollections.observableArrayList("AM", "PM"));
    	companionTime.setValue("AM");
    	
    	finishButton.disableProperty().bind(Bindings.createBooleanBinding(() -> selectedListView.getItems().isEmpty(), selectedListView.getItems()));
    	popupSubmit.disableProperty().bind(Bindings.createBooleanBinding(() -> isPopupReady(), companionName.textProperty(), companionHourTime.textProperty(), companionMinTime.textProperty(), companionHours.textProperty()));
    }
    
    private boolean isPopupReady() {
    	return (checkName() | checkHours() | checkTime());
    }

	private boolean checkTime() {
		if(companionHourTime.getText().isEmpty() || companionMinTime.getText().isEmpty()) {
    		timeText.setText("Must be HH:MM");
    		timeText.setVisible(true);
    		return true;
    	}
    	
    	if((!companionHourTime.getText().matches("[0-9]+")) || (!companionMinTime.getText().matches("[0-9]+"))) {
    		timeText.setVisible(true);
    		timeText.setText("Must be HH:MM");
    		return true;
    	}
    	
    	if((companionHourTime.getText().length() == 2) && (companionMinTime.getText().length() == 2)) {
    		if(Integer.parseInt(companionHourTime.getText()) <= 12 && Integer.parseInt(companionMinTime.getText()) <= 59) {
    			timeText.setVisible(false);
    	    	return false;
        	}
    		else {
    			return true;
    		}
    	}
    	else {
    		timeText.setText("Must be HH:MM");
    		timeText.setVisible(true);
    		return true;
    	}
	}

	private boolean checkHours() {
		if(companionHours.getText().isEmpty()) {
    		hoursText.setVisible(false);
    		return true;
    	}
    	
    	if(!companionHours.getText().matches("[0-9]+")) {
    		hoursText.setVisible(true);
    		hoursText.setText("Must be an integer");
    		return true;
    	}
    	
    	hoursText.setVisible(false);
    	return false;
	}

	private boolean checkName() {
		if(companionName.getText().isEmpty()) {
    		return true;
    	}
		else {
			return false;
		}
	}

	@FXML
    void goMainMenu(ActionEvent event) {
		try {
			model.stateChangeRequest("TreeLotCoordinatorView", null);
		} catch (IOException e) {
			System.err.println("Unable to open TreeLotCoordinatorView");
		}
    }

    @FXML
    void onFinish(ActionEvent event) throws IOException {
    	boolean savedShifts = true;
    	for(Shift shift : shiftList) {
    		if(!shift.save()) {
    			savedShifts = false;
    		}
    	}
    	if(savedShifts) {
    		model.stateChangeRequest("TreeLotCoordinatorView", null);
    	}
    	else {
    		editText.setText("Unable to save all the shifts");
    		editText.setVisible(true);
    	}
    }

    @FXML
    void addScout(ActionEvent event) {
    	selected = scoutListView.getSelectionModel().getSelectedItem();
    	companionName.setText("");
    	companionHours.setText("");
    	companionHourTime.setText("");
    	companionMinTime.setText("");
    	companionTime.setValue("AM");
    	popup.setVisible(true);
    }



    @FXML
    void onSubmitCompanionName(ActionEvent event) {
    	selectedListView.getItems().add(selected);
    	scoutListView.getItems().remove(selected);
    	
    	Shift thisShift = new Shift();
    	thisShift.setSessionID(session.getID());
    	thisShift.setScoutID(Integer.parseInt(selected.getId()));
    	thisShift.setName(companionName.getText());
    	thisShift.setStartTime(session.getStartTime());
    	thisShift.setEndTime(LocalTime.of(Integer.parseInt(companionHourTime.getText()), Integer.parseInt(companionMinTime.getText())));
    	thisShift.setHours(Integer.parseInt(companionHours.getText()));
    	shiftList.add(thisShift);
    	
    	System.out.println(selected.toString());
    	System.out.println(thisShift.toString());
    	System.out.println(session.toString());
    	
    	popup.setVisible(false);
    }

    @FXML
    void onCancellCompanionName(ActionEvent event) {
    	popup.setVisible(false);
    }

    public void setModel(IModel newModel) { model = newModel; }


    public void doSearch() {
        scoutListView.setItems(FXCollections.observableArrayList(session.getScoutList()));
    }
}
