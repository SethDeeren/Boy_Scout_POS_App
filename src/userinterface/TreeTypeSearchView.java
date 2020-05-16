package userinterface;

import controllers.TreeTypeSearchController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class TreeTypeSearchView extends View {


    private FXMLLoader loader;
    private IModel model;
    private Pane mainPane = null;
    private MessageView statusLog;

    public TreeTypeSearchView(IModel TreeTypeSearch) throws IOException {
        super(TreeTypeSearch, "TreeTypeSearchView");
        model = TreeTypeSearch;

        myModel.subscribe("TransactionError", this);

        loader = new FXMLLoader(getClass().getResource("../views/TreeTypeSearchView.fxml"));
        mainPane = (Pane) loader.load();
        TreeTypeSearchController controller = (TreeTypeSearchController) loader.getController();
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
