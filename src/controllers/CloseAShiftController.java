package controllers;

import database.DB;
import impresario.IModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Session;
import model.Transaction;

import java.io.IOException;
import java.util.ArrayList;

public class CloseAShiftController {
    private IModel model;

    @FXML
    private Button close;

    @FXML
    private Button cancel;

    private Session session = null;

    @FXML
    private Text confirmationMessage;

    @FXML
    private Text noTransactionMessage;
    @FXML
    public void initialize() {
        confirmationMessage.setVisible(false);
        noTransactionMessage.setVisible(false);
    }
    @FXML
    void onCancel(ActionEvent event) {
        try{
            model.stateChangeRequest("TreeLotCoordinatorView", null);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void onClose(ActionEvent event) {
        DB db = DB.getInstance();
        session = Session.getInstance();
        String sessionID = ""+session.getID();
        ArrayList<Transaction> transactionList;
        String sql = "SELECT * FROM Transaction WHERE SessionID = '" + sessionID + "';";
        transactionList =  db.query(sql, "Transaction");
        System.out.println(sql);
        if(transactionList != null)
        {
            for(int i = 0; i < transactionList.size(); i++)
            {
                if(transactionList.get(i).getPaymentMethod().equals("Cash"))
                {
                    int transactionAmount = transactionList.get(i).getTransactionAmount();
                    int currentEndingCash = session.getEndingCash();
                    int finalAmount = transactionAmount + currentEndingCash;
                    session.setEndingCash(finalAmount);


                }
                else
                {
                    int transactionAmount = transactionList.get(i).getTransactionAmount();
                    int totalCheckTransactionAmount = session.getTotalCheckTransactionsAmount();
                    int finalAmount = transactionAmount + totalCheckTransactionAmount;
                    session.setTotalCheckTransactionsAmount(finalAmount);

                }
            }
            int endingCash = session.getEndingCash();
            int startingCash = session.getStartingCash();
            session.setEndingCash(endingCash + startingCash);
            confirmationMessage.setVisible(true);
            session.save();
        }
        else
        {
            noTransactionMessage.setVisible(true);
        }
        session.delete();
        close.setDisable(true);


    }

    public void setModel(IModel newModel) {
        model = newModel;
    }
}
