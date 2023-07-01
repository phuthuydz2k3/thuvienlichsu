package com.app.thuvienlichsu.controllers;

import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.base.SuKienModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SuKienController extends GeneralController implements Initializable {
    public VBox cacNhanVatLienQuan;
    public VBox cacDiTichLienQuan;
    public Label nhanVatLienQuanLabel;
    public Label diTichLienQuanLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Model temp : database.getSuKien()) {
            objectList.add(temp.getTenModel());
        }
        listView.setItems(objectList);
    }
    public void preloadSuKien(String suKienName) {
        listView.getSelectionModel().select(suKienName);
        showSuKienDetail(suKienName);
    }
    @FXML
    public void showSuKienDetail() {
        SuKienModel item = (SuKienModel) showDetail((ArrayList<Model>) database.getSuKien());
        showDanhSachLienQuan(item);
    }
    public void showSuKienDetail(String suKienName) {
        SuKienModel item = (SuKienModel) showDetail((ArrayList<Model>) database.getSuKien(), suKienName);
        showDanhSachLienQuan(item);
    }
    @FXML
    public void suKienSearchFieldAction(){
        searchFieldAction((ArrayList<Model>) database.getSuKien());
    }
    private void showDanhSachLienQuan(SuKienModel suKien){
//        nhanVatLienQuanLabel.setVisible(true);
//        diTichLienQuanLabel.setVisible(true);
//
//        cacNhanVatLienQuan.getChildren().clear();
//        cacDiTichLienQuan.getChildren().clear();
//        if (item == null) return;
//        if (item.getCacNhanVatLienQuan().size() > 0) nhanVatLienQuanLabel.setVisible(true);
//        cacNhanVatLienQuan.getChildren().addAll(GeneralController.nhanVatLienQuanButtons(item.getCacNhanVatLienQuan(), database.getNhanVat()));
//        for (String diTich : item.getcacDiTichLienQuan()) {
//            List<Model> dtL = database.getDiTich();
//            int idx = database.binaryLookupByCode(0, dtL.size() - 1, diTich, (ArrayList<Model>) dtL);
//            if (idx < 0) continue;
//            Button btn = new Button(dtL.get(idx).getTenModel());
//            btn.setOnAction(this::handleDiTichLienQuanButton);
//            cacDiTichLienQuan.getChildren().add(btn);
//        }
        resetDanhSachLienQuan();

        if (suKien == null) return;

        if (suKien.getCacNhanVatLienQuan().size() > 0) nhanVatLienQuanLabel.setVisible(true);
        cacNhanVatLienQuan.getChildren().addAll(GeneralController.nhanVatLienQuanButtons(suKien.getCacNhanVatLienQuan(), database.getNhanVat()));

        if (suKien.getCacDiTichLienQuan().size() > 0) diTichLienQuanLabel.setVisible(true);
        cacDiTichLienQuan.getChildren().addAll(GeneralController.diTichLienQuanButtons(suKien.getCacDiTichLienQuan(), database.getDiTich()));
    }
    private void resetDanhSachLienQuan(){
        nhanVatLienQuanLabel.setVisible(false);
        diTichLienQuanLabel.setVisible(false);

        cacNhanVatLienQuan.getChildren().clear();
        cacDiTichLienQuan.getChildren().clear();
    }
}
