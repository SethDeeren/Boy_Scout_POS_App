package controllers;

import database.DB;
import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Session;
import model.Transaction;
import model.Tree;
import model.TreeType;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Properties;

public class SellTreeViewController {

    private IModel model;
    private String treeCost;
    private String barcodeToUpdate;
    private Session session = Session.getInstance();

    @FXML
    private ComboBox<String> paymentType;

    @FXML
    private TextField nameTF;

    @FXML
    private Text treeTypeText;

    @FXML
    private Text treeCostText;

    @FXML
    private TextField phoneTF1;

    @FXML
    private TextField phoneTF2;

    @FXML
    private TextField phoneTF3;

    @FXML
    private TextField emailTF;

    @FXML
    private Button purchaseButton;

    @FXML
    private Button cancellButton;

    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "Cash",
                    "Check"
            );

    @FXML
    private void initialize(){

        paymentType.setItems(options);
        paymentType.setValue("Cash");

        purchaseButton.disableProperty().bind(Bindings.createBooleanBinding(() -> isSubmitReady(), emailTF.textProperty(), nameTF.textProperty(),
                phoneTF1.textProperty(), phoneTF2.textProperty(), phoneTF3.textProperty()));

    }

    private boolean isSubmitReady() {

        boolean check;

        if(nameTF.getText().isEmpty() && (emailTF.getText().isEmpty() || !emailTF.getText().matches("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+"))
               && ((phoneTF1.getText().length() != 3 || !phoneTF1.getText().matches("[0-9]+"))
                && (phoneTF2.getText().length() != 3 || !phoneTF2.getText().matches("[0-9]+"))
                && (phoneTF2.getText().length() != 3 || !phoneTF2.getText().matches("[0-9]+"))))
        {
            check = true;
        }
        else
        {
            check = false;
        }
        return check;

    }


    public void setTransActionInfo(Properties transActionInfo){
        barcodeToUpdate = transActionInfo.getProperty("Barcode");
        treeCost = transActionInfo.getProperty("TreeCost");
        treeCostText.setText("Cost: $"+ treeCost +".00");
        treeTypeText.setText("Transaction Summary");
    }


    @FXML
    void onCancellButton(ActionEvent event) {
        try{
            model.stateChangeRequest("TreeLotCoordinatorView", null);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onPurchaseButton(ActionEvent event) {
        String sql = "UPDATE Tree set Status = 'Sold' WHERE Barcode = '" + barcodeToUpdate + "';";
        DB db = DB.getInstance();

        createTransAction();
        db.query(sql);

        try{
            model.stateChangeRequest("ThankYouView", null);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void createTransAction(){
        Properties props = new Properties();
        String phone1 = phoneTF1.getText();
        String phone2 = phoneTF2.getText();
        String phone3 = phoneTF3.getText();
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(phone1, 0, 3).append(")").append(phone2, 0, 3).append("-")
                .append(phone3, 0, 4);
        String phone = sb.toString();

//        SimpleDateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss z");

//        Date date = new Date(System.currentTimeMillis());
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        System.out.println(date.toString());
        String sessionID = "" + session.getID();
        System.out.println("this is session id " + session.getID());
        props.setProperty("sessionID", "" + session.getID());
        props.setProperty("barcode", barcodeToUpdate);
        props.setProperty("transactionAmount", treeCost);
        props.setProperty("paymentMethod", paymentType.getValue().toString());
        props.setProperty("customerName", nameTF.getText());
        props.setProperty("customerPhone", phone);
        props.setProperty("customerEmail", emailTF.getText());
        props.setProperty("transactionDate", date.toString());
        props.setProperty("transactionTime", time.toString());
        props.setProperty("dateStatusUpdated", date.toString());

        Transaction transaction = new Transaction(props);
        transaction.save();

    }


    public void setModel(IModel newModel) {
        model = newModel;
    }

    public String toString(){
        return "";
    }


}
