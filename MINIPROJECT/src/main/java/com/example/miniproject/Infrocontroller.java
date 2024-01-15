package com.example.miniproject;

import com.mongodb.client.FindIterable;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import org.bson.Document;
import org.bson.types.ObjectId;


import static com.mongodb.client.MongoClients.*;

public class Infrocontroller {

    @FXML
    private TextField label3;

    @FXML
    private TextField label4;

    @FXML
    private TextField label5;

    @FXML
    private Label label7;

    @FXML
    private CheckBox checkbox1;

    @FXML
    private CheckBox checkbox2;

    String email;


    @FXML
    protected void profilepage() {
        email = UserController.getemail();
        try (var mongoClient = create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("tourism");
            var collection = database.getCollection("information");


            Document query = new Document("email", email);
            FindIterable<Document> documents = collection.find(query);

            for (Document document : documents) {
                String profilename = document.getString("name");
                String profileemail = document.getString("email");
                String profilenumber = document.getString("phonenumber");
                System.out.println(profilename);
                System.out.println(profileemail);
                System.out.println(profilenumber);

                label3.setText(profilename);
                label4.setText(profileemail);
                label5.setText(profilenumber);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void saveinfo() {
        String name = label3.getText();
        String email1 = label4.getText();
        String phonenumber = label5.getText();
        String gender;

        if (checkbox1.isSelected()) {
            gender = "male";
        } else {
            gender = "female";
        }

        if (!email1.equals(email)) {
            label7.setText("Kindly enter the correct email");
            label7.setAlignment(Pos.CENTER);
        } else if (name.isEmpty()) {
            label7.setText("Kindly enter the name");
            label7.setAlignment(Pos.CENTER);
        } else if (phonenumber.isEmpty()) {
            label7.setText("Kindly enter the phone number");
            label7.setAlignment(Pos.CENTER);
        } else if (!(checkbox1.isSelected() || checkbox2.isSelected())) {
            label7.setText("Kindly select gender");
            label7.setAlignment(Pos.CENTER);
        } else {
            String userid = UserController.getid();
            try (var mongoClient = create("mongodb://localhost:27017")) {
                var database = mongoClient.getDatabase("tourism");
                var collection = database.getCollection("information");

                ObjectId objectId = new ObjectId(userid);

                Document query = new Document("_id", objectId);

                // Use the $set operator to update specific fields
                Document update = new Document("$set", new Document()
                        .append("name", name)
                        .append("email", email1)
                        .append("phonenumber", phonenumber)
                        .append("gender", gender));

                collection.updateOne(query, update);

                label7.setText("Information updated successfully");
                label7.setAlignment(Pos.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
                label7.setText("Error during update. Please try again.");
                label7.setAlignment(Pos.CENTER);
            }
        }
    }
}