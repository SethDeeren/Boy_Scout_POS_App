package main;

// specify the package
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;

// project imports
import event.Event;
import model.TreeLotCoordinator;
import userinterface.MainStageContainer;
import userinterface.WindowPosition;


/** The class containing the main program  for the ATM application */
//==============================================================
public class TreeLotStart extends Application
{

    private TreeLotCoordinator myTLC;		// the main behavior for the application

    /** Main frame of the application */
    private Stage mainStage;


    // start method for this class, the main application object
    //----------------------------------------------------------
    public void start(Stage primaryStage)
    {
        System.out.println("TreeLot Start Version 1.00");
        System.out.println("Copyright Zack Tuttle");

        // Create the top-level container (main frame) and add contents to it.
        MainStageContainer.setStage(primaryStage, "TreeLotCoordinator Version 3.00");
        mainStage = MainStageContainer.getInstance();

        // Finish setting up the stage (ENABLE THE GUI TO BE CLOSED USING THE TOP RIGHT
        // 'X' IN THE WINDOW), and show it.
        mainStage.setOnCloseRequest(new EventHandler <javafx.stage.WindowEvent>() {
            @Override
            public void handle(javafx.stage.WindowEvent event) {
                System.exit(0);
            }
        });

        try
        {
            myTLC = new TreeLotCoordinator();
        }
        catch(Exception exc)
        {
            System.err.println("Could not create TreeLotCoordinator!");
            new Event(Event.getLeafLevelClassName(this), "TreeLotStart.<init>", "Unable to create TreeLotCoordinator object", Event.ERROR);
            exc.printStackTrace();
        }


        WindowPosition.placeCenter(mainStage);

        mainStage.show();
    }


    /**
     * The "main" entry point for the application. Carries out actions to
     * set up the application
     */
    //----------------------------------------------------------
    public static void main(String[] args)
    {
        launch(args);
    }

}
