package com.example.miniproject;

import com.mongodb.client.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.IOException;


public class Planscontroller {

    @FXML
    private Label label1;
    
    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label label4;

    @FXML
    protected void onclickthisbutton() {
        String place = UserController.getplace();
        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = mongoClient.getDatabase("tourism");

            MongoCollection<Document> placesCollection = database.getCollection("places");
            Document query = new Document("name", place);
            FindIterable<Document> documents = placesCollection.find(query);

            for (Document document : documents) {
                ObjectId objectId = document.getObjectId("_id");
                String name = document.getString("name");
                String description = document.getString("description");
                String activities = document.getString("activities");
                int price = document.getInteger("price");
                int discount = document.getInteger("Discount");

                label1.setText(name);
                label2.setText(description);
                label2.setWrapText(true);
                label3.setText(activities);

                label4.setText("₹" + (price - discount));

                System.out.println(place);
                System.out.println("Object ID: " + objectId);
                System.out.println("Name: " + name);
                System.out.println("Description: " + description);
                System.out.println("Activities: " + activities);
                System.out.println("Price: ₹" + price);
                System.out.println("Discount: " + discount);
            }
        }
    }

    @FXML
    protected void gotonextpage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
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
