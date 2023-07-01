package com.app.thuvienlichsu.controllers;

import com.app.thuvienlichsu.crawl.CrawlAll;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class MainController implements Initializable {
    private static MainController instance;
    public MainController(){
        instance = this;
    }
    public static MainController getInstance(){
        return instance;
    }

    public Stage primaryStage;
    public Scene dictMainScene;
    public Scene splashScene;
    public AnchorPane mainContent;
    @FXML
    private Button thoiKyBtn;
    @FXML
    private Button nhanVatBtn;
    @FXML
    private Button suKienBtn;
    @FXML
    private Button diTichBtn;
    @FXML
    private Button leHoiBtn;
    @FXML
    private Button crawlingBtn;

    @FXML
    private AnchorPane thoiKyPane;
    @FXML
    private AnchorPane nhanVatPane;
    @FXML
    private AnchorPane diTichPane;
    @FXML
    private AnchorPane suKienPane;
    @FXML
    private AnchorPane leHoiPane;
    @FXML
    private ThoiKyController thoiKyController;
    @FXML
    private NhanVatController nhanVatController;
    @FXML
    private SuKienController suKienController;
    @FXML
    private DiTichController diTichController;
    @FXML
    private LeHoiController leHoiController;
    @FXML
    public ProgressIndicator loadingCircle;


    private void setMainContent(AnchorPane anchorPane) {
        mainContent.getChildren().setAll(anchorPane);
    }

    public void resetStyleNav() {
        thoiKyBtn.getStyleClass().removeAll("active");
        nhanVatBtn.getStyleClass().removeAll("active");
        suKienBtn.getStyleClass().removeAll("active");
        diTichBtn.getStyleClass().removeAll("active");
        leHoiBtn.getStyleClass().removeAll("active");
    }

    public void showThoiKyPane() {
        resetStyleNav();
        thoiKyBtn.getStyleClass().add("active");
        setMainContent(thoiKyPane);

    }
    public void linkThoiKyPane(String thoiKyName) {
        showThoiKyPane();
        thoiKyController.preloadThoiKy(thoiKyName);
    }
    public void showNhanVatPane() {
        resetStyleNav();
        nhanVatBtn.getStyleClass().add("active");
        setMainContent(nhanVatPane);
    }
    public void linkNhanVatPane(String nhanVatName) {
        showNhanVatPane();
        nhanVatController.preloadNhanVat(nhanVatName);
    }

    public void showSuKienPane() {
        resetStyleNav();
        suKienBtn.getStyleClass().add("active");
        setMainContent(suKienPane);
    }
    public void linkSuKienPane(String suKienName) {
        showSuKienPane();
        suKienController.preloadSuKien(suKienName);
    }

    public void linkDiTichPane(String diTichName) {
        showDiTichPane();
        diTichController.preloadDiTich(diTichName);
    }
    public void showDiTichPane() {
        resetStyleNav();
        diTichBtn.getStyleClass().add("active");
        setMainContent(diTichPane);
    }

    public void showLeHoiPane() {
        resetStyleNav();
        leHoiBtn.getStyleClass().add("active");
        setMainContent(leHoiPane);
    }
    public void linkLeHoiPane(String leHoiName) {
        showLeHoiPane();
        leHoiController.preloadLeHoi(leHoiName);
    }
    private void loadPanes(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/thuvienlichsu/thoi-ky.fxml"));
            thoiKyPane = loader.load();
            thoiKyController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/thuvienlichsu/nhan-vat.fxml"));
            nhanVatPane = loader.load();
            nhanVatController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/thuvienlichsu/su-kien.fxml"));
            suKienPane = loader.load();
            suKienController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/thuvienlichsu/di-tich.fxml"));
            diTichPane = loader.load();
            diTichController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/app/thuvienlichsu/le-hoi.fxml"));
            leHoiPane = loader.load();
            leHoiController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadPanes();
        thoiKyBtn.getStyleClass().add("active");
        setMainContent(thoiKyPane);
    }

    public void setScenes(Stage primaryStage, Scene scene, Scene splashScene) {
        this.primaryStage = primaryStage;
        this.dictMainScene = scene;
        this.splashScene = splashScene;
    }

    public void showSplashScreenAndRunMethod() {
        primaryStage.setScene(splashScene);
        primaryStage.show();
        Task<Void> crawlTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                CrawlAll crawlAll = new CrawlAll();
                boolean crawlSuccess = false;
                boolean linkSuccess = false;
                TimeUnit.SECONDS.sleep(3);


//                try {
//                    crawlAll.crawl();
//                    crawlSuccess = true;
//                    crawlAll.link();
//                    linkSuccess = true;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (crawlSuccess && linkSuccess) {
//                    crawlAll.overwriteDatabase();
//                }

                return null;
            }
        };

        crawlTask.setOnSucceeded(e -> {
            primaryStage.setScene(dictMainScene);
            loadPanes();
            primaryStage.show();
            showRelaunchMessage();
        });

        primaryStage.setOnCloseRequest(event -> {
            // Terminate the crawling process if it's still running
            if (crawlTask != null && crawlTask.isRunning()) {
                crawlTask.cancel();
            }
        });

        // Run the task in a separate thread

        Thread crawlThread = new Thread(crawlTask);
        crawlThread.start();
    }
    private void showRelaunchMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Application Restart Required");
        alert.setHeaderText(null);
        alert.setContentText("The application has finished recrawling the data.\nPlease relaunch the application.");
        alert.showAndWait();
    }
}
