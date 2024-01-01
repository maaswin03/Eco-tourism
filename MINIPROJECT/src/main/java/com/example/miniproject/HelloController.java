package com.example.miniproject;

import com.mongodb.client.FindIterable;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.BreakIterator;

import static com.mongodb.client.MongoClients.*;

public class HelloController {

    @FXML
    public TextField loginusername;

    @FXML
    private PasswordField loginpassword;

    @FXML
    private Label label1;

    @FXML
    private TextField signupname;

    @FXML
    private TextField signupemail;

    @FXML
    private TextField signupphonenumber;

    @FXML
    private TextField signuppassword;

    @FXML
    private CheckBox signupmale;

    @FXML
    private CheckBox signupfemale;

    @FXML
    private Label label2;

    public String email;


    @FXML
    public void onclickloginbutton() {
        email = loginusername.getText();
        String password = loginpassword.getText();


        if(email.isEmpty()){
            label1.setText("Kindly enter your email");
            label1.setAlignment(Pos.CENTER);
        }
        else if(password.isEmpty()){
            label1.setText("Kindly enter your password");
            label1.setAlignment(Pos.CENTER);
        }
        else if (authenticateUser(email, password)) {
            UserController.setemail(email);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
                Parent root = loader.load();

                getid();

                Stage stage = new Stage();
                stage.setTitle("main Page");
                stage.setScene(new Scene(root));

                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            label1.setText("Invalid username or password");
            label1.setAlignment(Pos.CENTER);
        }
    }


    private boolean authenticateUser(String username, String password) {
        try (var mongoClient = create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("tourism");
            var usersCollection = database.getCollection("information");

            var userDocument = usersCollection.find(new Document("email", username).append("password", password)).first();
            return userDocument != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    protected void onclicksignupbutton(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
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
    protected void onclicksignupbutton1(){
        String email1 = signupemail.getText();
        String password1 = signuppassword.getText();
        String phonenumber = signupphonenumber.getText();
        String name = signupname.getText();
        String gender;

        if(signupmale.isSelected()){
            gender = "male";
        }
        else{
            gender = "female";
        }
        if(email1.isEmpty()){
            label2.setText("Kindly enter your email");
            label2.setAlignment(Pos.CENTER);
        }
        else if(password1.isEmpty()){
            label2.setText("Kindly enter your password");
            label2.setAlignment(Pos.CENTER);
        }
        else if(name.isEmpty()){
            label2.setText("Kindly enter your name");
            label2.setAlignment(Pos.CENTER);
        }
        else if(phonenumber.isEmpty()){
            label2.setText("Kindly enter your phone number");
            label2.setAlignment(Pos.CENTER);
        }
        else if(signupmale.isSelected() && signupfemale.isSelected()){
            label2.setText("Kindly select one gender");
            label2.setAlignment(Pos.CENTER);
        }
        else{
            try {
                try (var mongoClient = create("mongodb://localhost:27017")) {
                    var database = mongoClient.getDatabase("tourism");
                    var collection = database.getCollection("information");

                    collection.insertOne(new Document("name", name)
                            .append("email", email1)
                            .append("phonenumber",phonenumber)
                            .append("gender",gender)
                            .append("password",password1)
                    );
                    label2.setText("Signup successful!");
                    label2.setAlignment(Pos.CENTER);
                }
            } catch (Exception e) {
                e.printStackTrace();
                label2.setText("Error during signup. Please try again.");
                label2.setAlignment(Pos.CENTER);
            }
        }
    }

    @FXML
    protected void gotologin(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Signup Page");
            stage.setScene(new Scene(root));

            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getid() {
        String retrievedemail = UserController.getemail();
        try (var mongoClient = create("mongodb://localhost:27017")) {
            var database = mongoClient.getDatabase("tourism");
            var collection = database.getCollection("information");
            Document query = new Document("email", retrievedemail);
            FindIterable<Document> documents = collection.find(query);

            for (Document document : documents) {
                ObjectId userId = document.getObjectId("_id");
                UserController.setid(userId.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}