package userinterface;

import java.io.IOException;

import controllers.ThankYouController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class ThankYouView extends View {

    private IModel model = null;
    private FXMLLoader loader = null;
    private Pane mainPane = null;
    private MessageView statusLog;
    private ThankYouController controller;


    public ThankYouView(IModel updateScoutView) {
        model = updateScoutView;

        try {
            loader = new FXMLLoader(getClass().getResource("../views/ThankYou.fxml"));
            mainPane = (Pane) loader.load();
            controller = (ThankYouController) loader.getController();
            controller.setModel(model);
        } catch (IOException e) {
            System.err.println("Unable to load UpdateScoutView.fxml");
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
