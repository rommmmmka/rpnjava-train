module com.example.rpnjava3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kravets.rpnjava3 to javafx.fxml;
    exports com.kravets.rpnjava3;
}