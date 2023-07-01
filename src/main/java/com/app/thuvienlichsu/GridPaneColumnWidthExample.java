package com.app.thuvienlichsu;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class GridPaneColumnWidthExample extends Application {
    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        // Create column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40); // Set width as a percentage of the grid's width

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(100); // Set preferred width in pixels

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setMaxWidth(200); // Set maximum width in pixels

        // Apply column constraints to the GridPane
        gridPane.getColumnConstraints().addAll(column1, column2, column3);

        // Add labels to the grid
        Label l = createWrappedLabel("Column 1 Column 1 Column 1 Column 1Column 1Column 1Column 1Column 1Column 1Column 1Column 1Column 1Column 1Column 1", 12);
        gridPane.add(l, 0, 0);
        gridPane.add(new Label("Column 2"), 1, 0);
        gridPane.add(new Label("Column 3"), 2, 0);

        Scene scene = new Scene(gridPane, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private Label createWrappedLabel(String text, double fontSize) {
        Label label = new Label();
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setWrapText(true);

        TextFlow textFlow = new TextFlow();
        Text textNode = new Text(text);
        textNode.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        textFlow.getChildren().add(textNode);

        label.setGraphic(textFlow);

        return label;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
