package com.example.miniproject;

import com.mongodb.client.FindIterable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.bson.Document;

import java.io.IOException;

import static com.mongodb.client.MongoClients.create;

public class maincontroller {

    @FXML
    private TextField label5;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;

    @FXML
    private TextField phonenumber;

    @FXML
    private DatePicker dateofbirth;

    @FXML
    private TextField email;

    @FXML
    private TextField packagename;

    @FXML
    private TextField totalperson;

    @FXML
    private DatePicker fromdate;

    @FXML
    private Label label6;

    @FXML
    private Label label7;

    @FXML
    private Label label8;

    @FXML
    private Label label9;

    @FXML
    private Label label10;


    @FXML
    protected void onclickkumari(){
        onclickbooknow();
        UserController.setplace("KanyaKumari");
    }

    @FXML
    protected void onclickmadurai(){
        onclickbooknow();
        UserController.setplace("Madurai");
    }


    @FXML
    protected void onclickchennai(){
        onclickbooknow();
        UserController.setplace("Chennai");
    }

    public void onclickbooknow(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
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
    protected void onclickconfirm() {
        String fname = firstname.getText();
        String lname = lastname.getText();
        String number = phonenumber.getText();
        String dob = dateofbirth.getValue() != null ? dateofbirth.getValue().toString() : null;
        String emailid = email.getText();
        String person = totalperson.getText();
        String sdate = fromdate.getValue() != null ? fromdate.getValue().toString() : null;

        String retrievedValue = UserController.getplace();
        label5.setText(retrievedValue);

        if (fname.isEmpty()) {
            label6.setText("Kindly enter first name");
            label6.setAlignment(Pos.CENTER);
        } else if (lname.isEmpty()) {
            label6.setText("Kindly enter Last name");
            label6.setAlignment(Pos.CENTER);
        } else if (number.isEmpty()) {
            label6.setText("Kindly enter phone number");
            label6.setAlignment(Pos.CENTER);
        } else if (emailid.isEmpty()) {
            label6.setText("Kindly enter email id");
            label6.setAlignment(Pos.CENTER);
        } else if (person.isEmpty()) {
            label6.setText("Kindly enter total members");
            label6.setAlignment(Pos.CENTER);
        } else if (dob == null || sdate == null) {
            label6.setText("Kindly enter both date of birth and start date");
            label6.setAlignment(Pos.CENTER);
        } else {
            Integer person1 = Integer.parseInt(person);
            String id = UserController.getid();
            try (var mongoClient = create("mongodb://localhost:27017")) {
                var database = mongoClient.getDatabase("tourism");

                var customerDetailsCollection = database.getCollection("customer_details");
                customerDetailsCollection.insertOne(new Document("customer_id", id)
                        .append("firstname", fname)
                        .append("lastname", lname)
                        .append("phonenumber", number)
                        .append("dateofbirth", dob)
                        .append("email", emailid)
                        .append("cityname", retrievedValue)
                        .append("totalperson", person1)
                        .append("StartDate", sdate)
                );
                var placesCollection = database.getCollection("places");
                Document query = new Document("name", retrievedValue);
                FindIterable<Document> documents = placesCollection.find(query);

                for (Document document : documents) {
                    Integer price = document.getInteger("price");
                    Integer discount = document.getInteger("Discount");


                    UserController.setamount(price);
                    UserController.setdiscount(discount);
                    UserController.setperson(person1);



                    if (price != null && discount != null) {
                        float totalprice = (price - (price * ((float) discount / 100))) * person1;
                        UserController.settotalamount(totalprice);
                    }
                    else if(discount == null){
                        UserController.settotalamount((price * person1));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error");
            }
            gotopayment();
        }
    }

    protected void gotopayment(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("payment.fxml"));
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
    protected void gotohelp(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("contactus.fxml"));
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
    protected void gotoinfo(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
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
