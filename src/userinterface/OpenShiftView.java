package userinterface;

import controllers.OpenShiftController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class OpenShiftView extends View {

    private FXMLLoader loader;
    private IModel model;
    private Pane mainPane = null;

    private MessageView statusLog;

    public OpenShiftView(IModel TreeLotCoordinator) throws IOException {
        super(TreeLotCoordinator, "TreeLotCoordinatorView");
        model = TreeLotCoordinator;

        myModel.subscribe("TransactionError", this);

        loader = new FXMLLoader(getClass().getResource("../views/OpenShiftView.fxml"));
        mainPane = (Pane) loader.load();
        OpenShiftController controller = (OpenShiftController)loader.getController();
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
