package com.app.thuvienlichsu.controllers;

import com.app.thuvienlichsu.base.DiTichModel;
import com.app.thuvienlichsu.base.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class DiTichController extends GeneralController implements Initializable {
    public VBox cacNhanVatLienQuan;
    public Label nhanVatLienQuanLabel;
    public Label thoiKyLienQuanLabel;
    public Label leHoiLienQuanLabel;
    public VBox cacThoiKyLienQuan;
    public VBox cacLeHoiLienQuan;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Model temp : database.getDiTich()) {
            objectList.add(temp.getTenModel());
        }
        listView.setItems(objectList);
    }
    public void preloadDiTich(String diTichName) {
        listView.getSelectionModel().select(diTichName);
        showDiTichDetail(diTichName);
    }
    @FXML
    public void showDiTichDetail() {
        DiTichModel item = (DiTichModel) showDetail((ArrayList<Model>) database.getDiTich());
        showDanhSachLienQuan(item);
    }
    public void showDiTichDetail(String diTichName) {
        DiTichModel item = (DiTichModel) showDetail((ArrayList<Model>) database.getDiTich(), diTichName);
        showDanhSachLienQuan(item);
    }
    @FXML
    public void diTichSearchFieldAction(){
        searchFieldAction((ArrayList<Model>) database.getDiTich());
    }
    @FXML
    private void showDanhSachLienQuan(DiTichModel item){
        resetDanhSachLienQuan();
        if (item == null) return;
        if (item.getCacThoiKyLienQuan().size() > 0) thoiKyLienQuanLabel.setVisible(true);
        cacThoiKyLienQuan.getChildren().addAll(GeneralController.thoiKyLienQuanButtons(item.getCacThoiKyLienQuan(), database.getThoiKy()));
        if (item.getCacNhanVatLienQuan().size() > 0) nhanVatLienQuanLabel.setVisible(true);
        cacNhanVatLienQuan.getChildren().addAll(GeneralController.nhanVatLienQuanButtons(item.getCacNhanVatLienQuan(), database.getNhanVat()));
//        if (item.getCacLeHoiLienQuan().size() > 0) leHoiLienQuanLabel.setVisible(true);
//        cacLeHoiLienQuan.getChildren().addAll(GeneralController.leHoiLienQuanButtons((Set<String>) item.getCacLeHoiLienQuan(), database.getLeHoi()));
    }
    private void resetDanhSachLienQuan(){
        nhanVatLienQuanLabel.setVisible(false);
        cacNhanVatLienQuan.getChildren().clear();
        thoiKyLienQuanLabel.setVisible(false);
        cacThoiKyLienQuan.getChildren().clear();
        leHoiLienQuanLabel.setVisible(false);
        cacLeHoiLienQuan.getChildren().clear();
    }
}
