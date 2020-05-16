package userinterface;

import controllers.RegisterTreeController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RegisterTreeView extends View {

    private FXMLLoader loader;
    private IModel model;
    private Pane mainPane = null;
    private RegisterTreeController controller;

    private MessageView statusLog;

    public RegisterTreeView(IModel TreeLotCoordinator) throws IOException {
        super(TreeLotCoordinator, "TreeLotCoordinatorView");
        model = TreeLotCoordinator;

        myModel.subscribe("TransactionError", this);

        loader = new FXMLLoader(getClass().getResource("../views/RegisterTreeView.fxml"));
        mainPane = (Pane) loader.load();
        controller = (RegisterTreeController)loader.getController();
        controller.setModel(model);
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








}
