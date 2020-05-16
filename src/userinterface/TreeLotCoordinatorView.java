package userinterface;

import controllers.TreeLotCoordinatorViewController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TreeLotCoordinatorView extends View {

    private FXMLLoader loader;
    private IModel model;
    private Pane mainPane = null;
    private MessageView statusLog;

    public TreeLotCoordinatorView(IModel TreeLotCoordinator) throws IOException {
        super(TreeLotCoordinator, "TreeLotCoordinatorView");
        model = TreeLotCoordinator;
        myModel.subscribe("TransactionError", this);

        loader = new FXMLLoader(getClass().getResource("../views/TreeLotCoordinatorView.fxml"));
        mainPane = (Pane) loader.load();
        TreeLotCoordinatorViewController controller = (TreeLotCoordinatorViewController)loader.getController();
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
