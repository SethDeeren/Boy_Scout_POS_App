package userinterface;

import controllers.RegisterTreeTypeController;
import impresario.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class RegisterTreeTypeView extends View {

    private FXMLLoader loader;
    private IModel model;
    private Pane mainPane = null;

    private MessageView statusLog;

    public RegisterTreeTypeView(IModel RegisterTreeType) throws IOException {
        super(RegisterTreeType, "RegisterTreeTypeView");
        model = RegisterTreeType;

        myModel.subscribe("TransactionError", this);

        loader = new FXMLLoader(getClass().getResource("../views/RegisterTreeTypeView.fxml"));
        mainPane = (Pane) loader.load();
        RegisterTreeTypeController controller = (RegisterTreeTypeController)loader.getController();
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
