package com.example.miniproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.bson.Document;

import static com.mongodb.client.MongoClients.create;

public class Contactcontroller {

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField email;

    @FXML
    private TextField query;

    @FXML
    private Label label;

    @FXML
    protected void onclicksubmit(){
        String fname = firstname.getText();
        String lname = lastname.getText();
        String emailid = email.getText();
        String hquery = query.getText();

        if(fname.isEmpty()){
            label.setText("kindly Enter first Name");
        }

        else if(lname.isEmpty()){
            label.setText("kindly Enter Last Name");
        }

        else if(emailid.isEmpty()){
            label.setText("kindly Enter  emailid");
        }

        else if(hquery.isEmpty()){
            label.setText("kindly Enter query");
        }

        else{
            String id = UserController.getid();
            try (var mongoClient = create("mongodb://localhost:27017")) {
                var database = mongoClient.getDatabase("tourism");

                var customerDetailsCollection = database.getCollection("helpus_details");
                customerDetailsCollection.insertOne(new Document("customer_id", id)
                        .append("firstname", fname)
                        .append("lastname", lname)
                        .append("email", emailid)
                        .append("query", hquery)
                );
            }
        }
    }
}
