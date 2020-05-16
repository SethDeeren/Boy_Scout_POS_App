package userinterface;

import java.io.IOException;

import controllers.UpdateScoutController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import model.Scout;

public class UpdateScoutView extends View {
	
	private IModel model = null;
	private FXMLLoader loader = null;
	private Pane mainPane = null;
	private MessageView statusLog;
	private UpdateScoutController controller;
	
	
	public UpdateScoutView(IModel updateScoutView) {
		model = updateScoutView;
		
		try {
			loader = new FXMLLoader(getClass().getResource("../views/UpdateScoutView.fxml"));
			mainPane = (Pane) loader.load();
			controller = (UpdateScoutController)loader.getController();
			controller.setModel(model);
		} catch (IOException e) {
			System.err.println("Unable to load UpdateScoutView.fxml");
			e.printStackTrace();
		}
        getChildren().add(mainPane);
	}

	@Override
	public void updateState(String key, Object value) {
        if(key.equals("UpdateScoutView")) {
        	controller.populateFields((Scout)value);
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
