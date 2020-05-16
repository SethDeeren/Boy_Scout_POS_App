package userinterface;

import java.io.IOException;

import controllers.OpenShiftScoutSelectionController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class OpenShiftSelectScoutsView extends View {

    private IModel model = null;
    private FXMLLoader loader = null;
    private Pane mainPane = null;
    private MessageView statusLog;
    private OpenShiftScoutSelectionController controller;


    public OpenShiftSelectScoutsView(IModel treeSelectionScreen) {
        model = treeSelectionScreen;

        try {
            loader = new FXMLLoader(getClass().getResource("../views/OpenShiftScoutSelectionScreen.fxml"));
            mainPane = (Pane) loader.load();
            controller = (OpenShiftScoutSelectionController) loader.getController();
            controller.setModel(model);
        } catch (IOException e) {
            System.err.println("Unable to load TreeSelectionScreen.fxml");
            e.printStackTrace();
        }
        getChildren().add(mainPane);
    }

    @Override
    public void updateState(String key, Object value) {
        if(key.equals("OpenShiftSelectScoutsView")) {
            controller.doSearch();
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
