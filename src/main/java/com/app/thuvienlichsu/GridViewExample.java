package com.app.thuvienlichsu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GridViewExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        // Add column headers
        gridPane.add(new Label("Name"), 0, 0);
        gridPane.add(new Label("Age"), 1, 0);
        gridPane.add(new Label("Email"), 2, 0);

        // Add data rows
        gridPane.add(new Label("John Doe"), 0, 1);
        gridPane.add(new Label("25"), 1, 1);
        gridPane.add(new Label("john.doe@example.com"), 2, 1);

        gridPane.add(new Label("Jane Smith"), 0, 2);
        gridPane.add(new Label("30"), 1, 2);
        gridPane.add(new Label("jane.smith@example.com"), 2, 2);

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
