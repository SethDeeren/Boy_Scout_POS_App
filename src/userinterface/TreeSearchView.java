package userinterface;

import controllers.TreeSearchViewController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TreeSearchView extends View {


    private FXMLLoader loader;
    private IModel model;
    private Pane mainPane = null;
    private MessageView statusLog;

    public TreeSearchView(IModel TreeLotCoordinator) throws IOException {
        super(TreeLotCoordinator, "TreeSearchView");
        model = TreeLotCoordinator;

        myModel.subscribe("TransactionError", this);

        loader = new FXMLLoader(getClass().getResource("../views/TreeSearchView.fxml"));
        mainPane = (Pane) loader.load();
        TreeSearchViewController controller = (TreeSearchViewController) loader.getController();
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
