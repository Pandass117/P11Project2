module com.project2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project2 to javafx.fxml;
    exports com.project2;
}