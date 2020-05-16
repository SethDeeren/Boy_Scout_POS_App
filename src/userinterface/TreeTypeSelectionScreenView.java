package userinterface;

import java.io.IOException;
import java.util.Properties;

import controllers.TreeTypeSelectionScreenController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class TreeTypeSelectionScreenView extends View {
	
	private IModel model = null;
	private FXMLLoader loader = null;
	private Pane mainPane = null;
	private MessageView statusLog;
	private TreeTypeSelectionScreenController controller;
	
	
	public TreeTypeSelectionScreenView(IModel treeTypeSelectionScreen) {
		model = treeTypeSelectionScreen;
		
		try {
			loader = new FXMLLoader(getClass().getResource("../views/TreeTypeSelectionScreen.fxml"));
			mainPane = (Pane) loader.load();
			controller = (TreeTypeSelectionScreenController)loader.getController();
			controller.setModel(model);
		} catch (IOException e) {
			System.err.println("Unable to load TreeTypeSelectionScreen.fxml");
			e.printStackTrace();
		}
        getChildren().add(mainPane);
	}
	
	public void search(Properties thisSearch) {
		controller.doSearch(thisSearch);
	}

	@Override
	public void updateState(String key, Object value) {
        if(key.equals("TreeTypeSelectionScreenView")) {
        	search((Properties)value);
        }
    }

    public void displayErrorMessage(String message)
    {
		statusLog.displayErrorMessage(message);
    }

    /**
     * Clear error message
     */
    //----------------------------------------------------------
    public void clearErrorMessage()
    {
        statusLog.clearErrorMessage();
    }

	@Override
	public void subscribe(String key, IModel subscriber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unSubscribe(String key, IModel subscriber) {
		// TODO Auto-generated method stub
		
	}

}
