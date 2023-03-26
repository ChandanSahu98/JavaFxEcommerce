module com.example.ecommercemarch {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ecommercemarch to javafx.fxml;
    exports com.example.ecommercemarch;
}