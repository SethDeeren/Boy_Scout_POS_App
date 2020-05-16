package userinterface;

import controllers.CloseAShiftController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class CloseAShiftView extends View {

    private IModel model = null;
    private FXMLLoader loader = null;
    private Pane mainPane = null;
    private MessageView statusLog;
    private CloseAShiftController controller;


    public CloseAShiftView(IModel updateScoutView) {
        model = updateScoutView;

        try {
            loader = new FXMLLoader(getClass().getResource("../views/CloseAShiftView.fxml"));
            mainPane = (Pane) loader.load();
            controller = (CloseAShiftController) loader.getController();
            controller.setModel(model);
        } catch (IOException e) {
            System.err.println("Unable to load CloseAShiftView.fxml");
            e.printStackTrace();
        }
        getChildren().add(mainPane);
    }

    @Override
    public void updateState(String key, Object value) {

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
