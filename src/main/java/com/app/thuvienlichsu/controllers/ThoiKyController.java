package com.app.thuvienlichsu.controllers;

import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.base.ThoiKyModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class ThoiKyController extends GeneralController implements Initializable {
    public VBox cacNhanVatLienQuan;
    public VBox cacDiTichLienQuan;
    public Label nhanVatLienQuanLabel;
    public Label diTichLienQuanLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reloadData();
        for (Model temp : database.getThoiKy()) {
            objectList.add(temp.getTenModel());
        }
        listView.setItems(objectList);
    }
    public void preloadThoiKy(String thoiKyName) {
        listView.getSelectionModel().select(thoiKyName);
        showThoiKyDetail(thoiKyName);
    }
    @FXML
    public void showThoiKyDetail() {
        ThoiKyModel item = (ThoiKyModel) showDetail((ArrayList<Model>) database.getThoiKy());
        showDanhSachLienQuan(item);
    }
    public void showThoiKyDetail(String thoiKyName) {
        ThoiKyModel item = (ThoiKyModel) showDetail((ArrayList<Model>) database.getThoiKy(), thoiKyName);
        showDanhSachLienQuan(item);
    }
    private void showDanhSachLienQuan(ThoiKyModel thoiKy){
//        nhanVatLienQuanLabel.setVisible(false);
//        diTichLienQuanLabel.setVisible(false);
//
//        cacNhanVatLienQuan.getChildren().clear();
//        cacDiTichLienQuan.getChildren().clear();

        resetDanhSachLienQuan();

        if (thoiKy == null) return;

        if (thoiKy.getCacNhanVatLienQuan().size() > 0) nhanVatLienQuanLabel.setVisible(true);
        cacNhanVatLienQuan.getChildren().addAll(GeneralController.nhanVatLienQuanButtons(thoiKy.getCacNhanVatLienQuan(), database.getNhanVat()));

        if (thoiKy.getcacDiTichLienQuan().size() > 0) diTichLienQuanLabel.setVisible(true);
        cacDiTichLienQuan.getChildren().addAll(GeneralController.diTichLienQuanButtons(thoiKy.getCacDiTichLienQuan(), database.getDiTich()));

    }
    @FXML
    public void thoiKySearchFieldAction() {
        searchFieldAction((ArrayList<Model>) database.getThoiKy());
    }

    private void resetDanhSachLienQuan(){
        nhanVatLienQuanLabel.setVisible(false);
        diTichLienQuanLabel.setVisible(false);

        cacNhanVatLienQuan.getChildren().clear();
        cacDiTichLienQuan.getChildren().clear();
    }
}
