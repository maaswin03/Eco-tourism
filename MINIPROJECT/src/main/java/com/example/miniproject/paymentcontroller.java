package com.example.miniproject;

import com.mongodb.client.FindIterable;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.bson.Document;
import org.bson.types.ObjectId;

import static com.mongodb.client.MongoClients.create;

public class paymentcontroller {

    @FXML
    private CheckBox checkbox1;
    @FXML
    private CheckBox checkbox2;
    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField cardnumber;

    @FXML
    private TextField cvvno;

    @FXML
    private TextField email;

    @FXML
    private DatePicker expiriydate;

    @FXML
    private Label label1;

    @FXML
    private Label label2;


    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    private Label label5;

    @FXML
    protected void onclickdone(){
        if(checkbox1.isSelected()){
            String fname = firstname.getText();
            String lname = lastname.getText();
            String cnumber = cardnumber.getText();
            String cvv = cvvno.getText();
            String emailid = email.getText();

            if(fname.isEmpty()){
                label1.setText("kindly enter first name");
            }
            else if(lname.isEmpty()){
                label1.setText("kindly enter last name");
            }
            else if(cnumber.isEmpty()){
                label1.setText("kindly enter card number");
            }
            else if(cvv.isEmpty()){
                label1.setText("kindly enter cvv");
            }
            else if(emailid.isEmpty()){
                label1.setText("kindly enter emailid");
            }
            else if (expiriydate.getValue() == null) {
                label1.setText("kindly enter expirydate");
            }
            else{
                String edate = expiriydate.getValue().toString();
                String id = UserController.getid();
                try (var mongoClient = create("mongodb://localhost:27017")) {
                    var database = mongoClient.getDatabase("tourism");

                    var customerDetailsCollection = database.getCollection("card_details");
                    customerDetailsCollection.insertOne(new Document("customer_id", id)
                            .append("firstname", fname)
                            .append("lastname", lname)
                            .append("cardnumber", cnumber)
                            .append("cvv", cvv)
                            .append("expirydate", edate)
                            .append("email", emailid)
                    );
                }
                cashdetails(1);
            }
        }
        else if(checkbox2.isSelected()){
            cashdetails(2);
        }
        else if (checkbox1.isSelected() && checkbox2.isSelected()) {
            label1.setText("kindly select one payment method");
        }
        else{
            label1.setText("kindly select payment method");
        }
    }


    protected void cashdetails(int i){
        int discount = UserController.getdiscount();
        int amount = UserController.getamount();
        double totalamount = UserController.gettotalamount();
        int person = UserController.getperson();
        String id = UserController.getid();


        try (var mongoClient = create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("tourism");

            var placesCollection = database.getCollection("customer_details");
            Document query = new Document("customer_id", id);
            FindIterable<Document> documents = placesCollection.find(query);

            for (Document document : documents) {
                ObjectId userId = document.getObjectId("_id");
                UserController.setbid(userId.toString());
            }
        }

        label2.setText("₹ " + amount);

        label3.setText(discount + " %");

        label4.setText(String.valueOf(person));

        label5.setText("₹ " +totalamount);

        String bid = UserController.getbid();

        if(i == 1){
            try (var mongoClient = create("mongodb://localhost:27017")) {
                var database = mongoClient.getDatabase("tourism");

                var customerDetails = database.getCollection("payment_details");
                customerDetails.insertOne(new Document("customer_id", id)
                        .append("booking_id", bid)
                        .append("payment_method", "card")
                        .append("payment_status", "completed")
                        .append("amount", amount)
                        .append("totalamount", totalamount)
                        .append("discount", discount)
                );
            }
        }
        else if (i == 2) {
            try (var mongoClient = create("mongodb://localhost:27017")) {
                var database = mongoClient.getDatabase("tourism");

                var customerDetails = database.getCollection("payment_details");
                customerDetails.insertOne(new Document("customer_id", id)
                        .append("booking_id", bid)
                        .append("payment_method", "cashondelivery")
                        .append("payment_status", "pending")
                        .append("amount", amount)
                        .append("totalamount", totalamount)
                        .append("discount", discount)
                );
            }
        }
    }
}
