package userinterface;

import controllers.ScoutSearchController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class ScoutSearchView extends View {


    private FXMLLoader loader;
    private IModel model;
    private Pane mainPane = null;
    private MessageView statusLog;

    public ScoutSearchView(IModel TreeLotCoordinator) throws IOException {
        super(TreeLotCoordinator, "ScoutSearchView");
        model = TreeLotCoordinator;

        myModel.subscribe("TransactionError", this);

        loader = new FXMLLoader(getClass().getResource("../views/ScoutSearchView.fxml"));
        mainPane = (Pane) loader.load();
        ScoutSearchController controller = (ScoutSearchController) loader.getController();
        controller.setModel(model);
        getChildren().add(mainPane);
    }






    @Override
    public void updateState(String key, Object value) {
        if (key.equals("TransactionError") == true)
        {
            // display the passed text
            displayErrorMessage((String)value);
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








}
