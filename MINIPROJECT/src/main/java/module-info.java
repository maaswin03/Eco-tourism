module com.example.miniproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;


    opens com.example.miniproject to javafx.fxml;
    exports com.example.miniproject;
}