package com.app.thuvienlichsu.controllers;

import com.app.thuvienlichsu.base.LoadData;
import com.app.thuvienlichsu.base.Model;
import com.app.thuvienlichsu.util.StringUtility;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.*;

public class GeneralController implements Initializable {
    protected final ObservableList<String> objectList = FXCollections.observableArrayList();
    protected static LoadData database = new LoadData();

    @FXML
    protected ListView<String> listView;
    @FXML
    protected TextField searchField;
    @FXML
    private WebView definitionView;
    private final ArrayList<Model> searchTemp = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Initialize");
    }
    public void setListViewItem(ArrayList<Model> resource) {
        objectList.clear();
        if (searchField.getText().equals("")) {
            searchTemp.clear();
            searchTemp.addAll(resource);
        }
        for (Model temp : searchTemp) {
            objectList.add(temp.getTenModel());
        }
        listView.setItems(objectList);
    }

    public void searchFieldAction(ArrayList<Model> resource) {
        searchTemp.clear();
        objectList.clear();
        String word = searchField.getText();
        int index = database.binaryLookup(0, resource.size() - 1, word, resource);
        updateWordInListView(word, index, resource, searchTemp);
        setListViewItem(resource);
    }
    public Model showDetail(ArrayList<Model> resource) {
        return showDetail(resource, listView.getSelectionModel().getSelectedItem());
    }
    public Model showDetail(ArrayList<Model> resource, String spelling) {
        if (spelling == null) {
            return null;
        }
        int index = Collections.binarySearch(resource, new Model(spelling, ""));
        String meaning = resource.get(index).getHTML();
        definitionView.getEngine().loadContent(meaning, "text/html");
        return resource.get(index);
    }
    public void updateWordInListView(String word, int index, ArrayList<Model> res, ArrayList<Model> des) {
        if (index < 0) {
            return;
        }
        int j = index;
        while (j >= 0) {
            if (StringUtility.isContain(word, res.get(j).getTenModel()) == 0) {
                j--;
            } else {
                break;
            }
        }
        for (int i = j + 1; i <= index; i++) {
            Model temp = new Model(res.get(i).getTenModel(), res.get(i).getHTML());
            des.add(temp);
        }
        for (int i = index + 1; i < res.size(); i++) {
            if (StringUtility.isContain(word, res.get(i).getTenModel()) == 0) {
                Model temp = new Model(res.get(i).getTenModel(), res.get(i).getHTML());
                des.add(temp);
            } else {
                break;
            }
        }
    }
    protected static List<Button> nhanVatLienQuanButtons(Set<String> nhanVatCodes, List<Model> nvL){
        List<Button> allBtns = new ArrayList<>();
        for (String nhanVat : nhanVatCodes) {
            int idx = LoadData.binaryLookupByCode(0, nvL.size() - 1, nhanVat, (ArrayList<Model>) nvL);
            if (idx < 0) continue;
            Button btn = new Button(nvL.get(idx).getTenModel());
            btn.setOnAction(event -> {
                MainController.getInstance().linkNhanVatPane(((Button) event.getSource()).getText());
            });
            allBtns.add(btn);
        }
        return allBtns;
    }
    protected static List<Button> thoiKyLienQuanButtons(Set<String> thoiKyCodes, List<Model> tkL){
        List<Button> allBtns = new ArrayList<>();
        for (String thoiKy : thoiKyCodes) {
            int idx = LoadData.binaryLookupByCode(0, tkL.size() - 1, thoiKy, (ArrayList<Model>) tkL);
            if (idx < 0) continue;
            Button btn = new Button(tkL.get(idx).getTenModel());
            btn.setOnAction(event -> {
                MainController.getInstance().linkThoiKyPane(((Button) event.getSource()).getText());
            });
            allBtns.add(btn);
        }
        return allBtns;
    }
    protected static List<Button> suKienLienQuanButtons(Set<String> suKienCodes, List<Model> skL){
        List<Button> allBtns = new ArrayList<>();
        for (String suKien : suKienCodes) {
            int idx = LoadData.binaryLookupByCode(0, skL.size() - 1, suKien, (ArrayList<Model>) skL);
            if (idx < 0) continue;
            Button btn = new Button(skL.get(idx).getTenModel());
            btn.setOnAction(event -> {
                MainController.getInstance().linkNhanVatPane(((Button) event.getSource()).getText());
            });
            allBtns.add(btn);
        }
        return allBtns;
    }
    protected static List<Button> diTichLienQuanButtons(Set<String> diTichCodes, List<Model> dtL){
        List<Button> allBtns = new ArrayList<>();
        for (String diTich : diTichCodes) {
            int idx = LoadData.binaryLookupByCode(0, dtL.size() - 1, diTich, (ArrayList<Model>) dtL);
            if (idx < 0) continue;
            Button btn = new Button(dtL.get(idx).getTenModel());
            btn.setOnAction(event -> {
                MainController.getInstance().linkDiTichPane(((Button) event.getSource()).getText());
            });
            allBtns.add(btn);
        }
        return allBtns;
    }
    protected static List<Button> leHoiLienQuanButtons(Set<String> leHoiCodes, List<Model> lhL){
        List<Button> allBtns = new ArrayList<>();
        for (String leHoi : leHoiCodes) {
            int idx = LoadData.binaryLookupByCode(0, lhL.size() - 1, leHoi, (ArrayList<Model>) lhL);
            if (idx < 0) continue;
            Button btn = new Button(lhL.get(idx).getTenModel());
            btn.setOnAction(event -> {
                MainController.getInstance().linkLeHoiPane(((Button) event.getSource()).getText());
            });
            allBtns.add(btn);
        }
        return allBtns;
    }
    protected static void reloadData(){
        database = new LoadData();
    }
}