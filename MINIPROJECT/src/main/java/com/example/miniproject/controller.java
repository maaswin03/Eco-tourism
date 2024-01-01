package com.example.miniproject;

import com.mongodb.client.FindIterable;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;


import java.io.IOException;

import static com.mongodb.client.MongoClients.*;

public class controller {

    @FXML
    private TextField label3;

    @FXML
    private TextField label4;

    @FXML
    private TextField label5;

    @FXML
    private TextField label6;

    @FXML
    private Label label7;

    String email;

    public void setEmail(String email) {
        this.email = email;
    }

    @FXML
    protected void profilepage() {
        System.out.println(email);
        try (var mongoClient = create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("tourism");
            var collection = database.getCollection("information");


            Document query = new Document("email", email);
            FindIterable<Document> documents = collection.find(query);

            for (Document document : documents) {
                String profilename = document.getString("name");
                String profileemail = document.getString("email");
                String profilenumber = document.getString("phonenumber");
                String profilegender = document.getString("gender");
                System.out.println(profilename);
                System.out.println(profileemail);
                System.out.println(profilenumber);
                System.out.println(profilegender);

                label3.setText(profilename);
                label4.setText(profileemail);
                label5.setText(profilenumber);
                label6.setText(profilegender);
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
        String gender = label6.getText();

        if (!email1.equals(email)) {
            label7.setText("Email cannot be changed");
            label7.setAlignment(Pos.CENTER);
        } else if (name.isEmpty()) {
            label7.setText("kindly enter name");
            label7.setAlignment(Pos.CENTER);
        } else if (phonenumber.isEmpty()) {
            label7.setText("kindly enter phonenumber");
            label7.setAlignment(Pos.CENTER);
        } else if (gender.isEmpty()) {
            label7.setText("kindly enter gender");
            label7.setAlignment(Pos.CENTER);
        } else {
            try (var mongoClient = create("mongodb://localhost:27017")) {
                var database = mongoClient.getDatabase("tourism");
                var collection = database.getCollection("information");

                Document query = new Document("email", email1);
                FindIterable<Document> documents = collection.find(query);
                for (Document document : documents) {
                    document.append("name", name)
                            .append("phonenumber", phonenumber)
                            .append("gender", gender);
                    collection.replaceOne(query, document);
                }
                label7.setText("Infromation updated Successfully");
                label7.setAlignment(Pos.CENTER);
            } catch (Exception e) {
                e.printStackTrace();
                label7.setText("Error during update. Please try again.");
                label7.setAlignment(Pos.CENTER);
            }
        }
    }

    @FXML
    protected  void gotomain(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Signup Page");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected  void gotofav(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fav.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Signup Page");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}