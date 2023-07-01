module com.app.thuvienlichsu {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.web;
    requires org.jsoup;


    opens com.app.thuvienlichsu.controllers to javafx.fxml;
    opens com.app.thuvienlichsu.base to com.google.gson, javafx.base;
    exports com.app.thuvienlichsu;
}