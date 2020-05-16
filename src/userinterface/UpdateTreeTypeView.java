package userinterface;

import java.io.IOException;

import controllers.UpdateTreeTypeController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import model.TreeType;

public class UpdateTreeTypeView extends View {
	
	private IModel model = null;
	private FXMLLoader loader = null;
	private Pane mainPane = null;
	private MessageView statusLog;
	private UpdateTreeTypeController controller;
	
	
	public UpdateTreeTypeView(IModel updateTreeTypeView) {
		model = updateTreeTypeView;
		
		try {
			loader = new FXMLLoader(getClass().getResource("../views/UpdateTreeTypeView.fxml"));
			mainPane = (Pane) loader.load();
			controller = (UpdateTreeTypeController)loader.getController();
			controller.setModel(model);
		} catch (IOException e) {
			System.err.println("Unable to load UpdateTreeTypeView.fxml");
			e.printStackTrace();
		}
        getChildren().add(mainPane);
	}

	@Override
	public void updateState(String key, Object value) {
        if(key.equals("UpdateTreeTypeView")) {
        	controller.populateFields((TreeType)value);
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
