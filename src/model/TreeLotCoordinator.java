// specify the package
package model;

// system imports
import java.io.IOException;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Properties;

import database.DB;
import javafx.stage.Stage;
import javafx.scene.Scene;

// project imports
import impresario.IModel;
import impresario.ISlideShow;
import impresario.IView;
import impresario.ModelRegistry;
import model.*;
import exception.InvalidPrimaryKeyException;
import exception.PasswordMismatchException;
import event.Event;
import userinterface.MainStageContainer;
import userinterface.View;
import userinterface.ViewFactory;
import userinterface.WindowPosition;

/** The class containing the Teller  for the ATM application */
//==============================================================
public class TreeLotCoordinator implements IView, IModel
// This class implements all these interfaces (and does NOT extend 'EntityBase')
// because it does NOT play the role of accessing the back-end database tables.
// It only plays a front-end role. 'EntityBase' objects play both roles.
{
    // For Impresario
    private Properties dependencies;
    private ModelRegistry myRegistry;

    // GUI Components
    private Hashtable<String, Scene> myViews;
    private Stage	  	myStage;
    private String transactionErrorMessage = "";

    // constructor for this class
    //----------------------------------------------------------
    public TreeLotCoordinator() throws IOException {
        myStage = MainStageContainer.getInstance();
        DB db = DB.getInstance();
        myViews = new Hashtable<String, Scene>();

        // STEP 3.1: Create the Registry object - if you inherit from
        // EntityBase, this is done for you. Otherwise, you do it yourself
        myRegistry = new ModelRegistry("TreeLotCoordinator");
        if(myRegistry == null)
        {
            new Event(Event.getLeafLevelClassName(this), "TreeLotCoordinator",
                    "Could not instantiate Registry", Event.ERROR);
        }

        // STEP 3.2: Be sure to set the dependencies correctly
        setDependencies();

        // Set up the initial view
        createAndShowView("TreeLotCoordinatorView", null);
    }

    //-------------------------------------------------------------------------------
    public TreeLotCoordinator(String view) throws IOException {
        createAndShowView(view, null);
    }

    //-----------------------------------------------------------------------------------
    private void setDependencies()
    {
        dependencies = new Properties();
        dependencies.setProperty("InsertBook", "TransactionError");
        dependencies.setProperty("InsertPatron", "TransactionError");
        dependencies.setProperty("SearchBooks", "TransactionError");
        dependencies.setProperty("SearchPatrons", "TransactionError");
        //dependencies.setProperty("") possible error

        myRegistry.setDependencies(dependencies);
    }

    /**
     * Method called from client to get the value of a particular field
     * held by the objects encapsulated by this object.
     *
     * @param	key	Name of database column (field) for which the client wants the value
     *
     * @return	Value associated with the field
     */
    //----------------------------------------------------------
    public Object getState(String key) {
        if (key.equals("TransactionError"))
        {
            return transactionErrorMessage;
        }
        else
            return "";
    }

    //----------------------------------------------------------------
    public void stateChangeRequest(String key, Object value) throws IOException {
        // STEP 4: Write the sCR method component for the key you
        // just set up dependencies for
        // DEBUG System.out.println("Teller.sCR: key = " + key);
        if (key.equals("Exit") == true)
        {
            System.exit(0);
        }
        else if (key.equals("TreeLotCoordinatorView") || key.equals("ScoutSearchView")  || key.equals("TreeTypeSearchView") || key.equals("TreeSearchView"))
        {
            createAndShowView(key, value);
        }
        else if (key.equals("UpdateScoutView") || key.equals("UpdateTreeTypeView") || key.equals("UpdateTreeView")) {
        	createAndShowView(key, value);
        }
        else if (key.equals("RegisterTreeTypeView") || key.equals("RegisterScoutView") || key.equals("RegisterTreeView")) {
            createAndShowView(key, value);
        }
        else if (key.equals("ScoutSelectionScreenView") || key.equals("TreeTypeSelectionScreenView") || key.equals("TreeSelectionScreenView")) {
            createAndShowView(key, value);
        }
        else if (key.equals("DeleteATreeView") || key.equals("DeleteATreePopupView") || key.equals("OpenShiftView") || key.equals("CloseAShiftView")) {
            createAndShowView(key, value);
        }
        else if(key.equals("OpenShiftSelectScoutsView") || key.equals("SellTreeView") || key.equals("SellingConfirmationView") || key.equals("ThankYouView")){
            createAndShowView(key, value);
        }
        myRegistry.updateSubscribers(key, this);
    }

    /** Called via the IView relationship */
    //----------------------------------------------------------
    public void updateState(String key, Object value) throws IOException {
        // DEBUG System.out.println("Teller.updateState: key: " + key);

        stateChangeRequest(key, value);
    }

    /**
     * Create a Transaction depending on the Transaction type (deposit,
     * withdraw, transfer, etc.). Use the AccountHolder holder data to do the
     * create.
     */
    //----------------------------------------------------------

    public void doAction(String thingToDo) {

    }

    /*------------------------------------------------------------
    public void createAndShowView(String view, Object value) throws IOException {
        Scene currentScene = (Scene)myViews.get(view);

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView(view, this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put(view, currentScene);
            newView.updateState(view, value);
        }

        swapToView(currentScene);

    }
    */
    
    public void createAndShowView(String view, Object value) throws IOException {
        Scene currentScene = null;

        if (currentScene == null)
        {
            // create our initial view
            View newView = ViewFactory.createView(view, this); // USE VIEW FACTORY
            currentScene = new Scene(newView);
            myViews.put(view, currentScene);
            newView.updateState(view, value);
        }

        swapToView(currentScene);

    }

    public Hashtable<String, Scene> getViews() {
        return myViews;
    }


    /** Register objects to receive state updates. */
    //----------------------------------------------------------
    public void subscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager[" + myTableName + "].subscribe");
        // forward to our registry
        myRegistry.subscribe(key, subscriber);
    }

    /** Unregister previously registered objects. */
    //----------------------------------------------------------
    public void unSubscribe(String key, IView subscriber)
    {
        // DEBUG: System.out.println("Cager.unSubscribe");
        // forward to our registry
        myRegistry.unSubscribe(key, subscriber);
    }



    //-----------------------------------------------------------------------------
    public void swapToView(Scene newScene)
    {


        if (newScene == null)
        {
            System.out.println("Librarian.swapToView(): Missing view for display");
            new Event(Event.getLeafLevelClassName(this), "swapToView",
                    "Missing view for display ", Event.ERROR);
            return;
        }



        myStage.setScene(newScene);
        myStage.sizeToScene();


        //Place in center
        WindowPosition.placeCenter(myStage);

    }

}

