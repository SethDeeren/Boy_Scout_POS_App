package userinterface;

import java.io.IOException;

import controllers.UpdateTreeViewController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import model.Tree;

public class UpdateTreeView extends View {

    private IModel model = null;
    private FXMLLoader loader = null;
    private Pane mainPane = null;
    private MessageView statusLog;
    private UpdateTreeViewController controller;


    public UpdateTreeView(IModel updateScoutView) {
        model = updateScoutView;

        try {
            loader = new FXMLLoader(getClass().getResource("../views/UpdateTreeView.fxml"));
            mainPane = (Pane) loader.load();
            controller = (UpdateTreeViewController)loader.getController();
            controller.setModel(model);
        } catch (IOException e) {
            System.err.println("Unable to load UpdateScoutView.fxml");
            e.printStackTrace();
        }
        getChildren().add(mainPane);
    }

    @Override
    public void updateState(String key, Object value) {
        if(key.equals("UpdateTreeView")) {
            controller.populateFields((Tree)value);
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
