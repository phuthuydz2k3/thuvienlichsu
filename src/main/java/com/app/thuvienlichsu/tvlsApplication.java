package com.app.thuvienlichsu;
import com.app.thuvienlichsu.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class tvlsApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        FXMLLoader fxmlLoader = new FXMLLoader(tvlsApplication.class.getResource("tvls-main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 857, 600);
        MainController controller = fxmlLoader.getController();

        FXMLLoader fxmlSplashLoader = new FXMLLoader(getClass().getResource("splash_screen.fxml"));
        Scene splashScene = new Scene(fxmlSplashLoader.load(), 857, 600);

        controller.setScenes(stage, scene, splashScene);
        stage.setTitle("Thư Viện Lịch Sử");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
