package com.app.thuvienlichsu.base;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NhanVatModel extends Model
{
    private List<List<String>> thongTin;
    private Set<String> cacNhanVatLienQuan;
    private Set<String> cacDiTichLienQuan;
    private Set<String> cacThoiKyLienQuan;


    public NhanVatModel(String tenModel, List<String> moTa, String code, List<List<String>> thongTin
            , Set<String> cacNhanVatLienQuan, Set<String> cacDiTichLienQuan, Set<String> cacThoiKyLienQuan)
    {
        super(tenModel, moTa);
        setCode(code);
        setThongTin(thongTin);
        setCacNhanVatLienQuan(cacNhanVatLienQuan);
        setCacDiTichLienQuan(cacDiTichLienQuan);
        setCacThoiKyLienQuan(cacThoiKyLienQuan);
    }
    public NhanVatModel(String tenModel)
    {
        super(tenModel);
        setCode(new String());
        setMoTa(new ArrayList<>());
        setThongTin(new ArrayList<>());
        setCacNhanVatLienQuan(new HashSet<>());
        setCacDiTichLienQuan(new HashSet<>());
        setCacThoiKyLienQuan(new HashSet<>());
    }

    public List<List<String>> getThongTin() {
        return thongTin;
    }
    public Set<String> getCacNhanVatLienQuan() {
        return cacNhanVatLienQuan;
    }
    public Set<String> getCacThoiKyLienQuan() {
        return cacThoiKyLienQuan;
    }
    public Set<String> getCacDiTichLienQuan() {
        return cacDiTichLienQuan;
    }
    public void setThongTin(List<List<String>> thongTin) {
        this.thongTin = thongTin;
    }

    public void setCacNhanVatLienQuan(Set<String> cacNhanVatLienQuan) {
        this.cacNhanVatLienQuan = cacNhanVatLienQuan;
    }

    public void setCacDiTichLienQuan(Set<String> cacDiTichLienQuan) {
        this.cacDiTichLienQuan = cacDiTichLienQuan;
    }

    public void setCacThoiKyLienQuan(Set<String> cacThoiKyLienQuan) {
        this.cacThoiKyLienQuan = cacThoiKyLienQuan;
    }

    @Override
    public String toHTML() {
        StringBuilder htmlBuilder = new StringBuilder();

        // Start the HTML structure
        htmlBuilder.append("<html>");
        htmlBuilder.append("<i>").append(this.tenModel).append("</i>");
        htmlBuilder.append("<head>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body contenteditable=\"true\">");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        // htmlBuilder.append("<title>").append(getName()).append("</title>");
        htmlBuilder.append("<style>");
        htmlBuilder.append("body { font-family:'lucida grande', tahoma, verdana, arial, sans-serif;font-size:14px; }");
        htmlBuilder.append("table { font-family:'lucida grande', tahoma, verdana, arial, sans-serif;font-size:14px; }");
        htmlBuilder.append(".table-container { text-align: left; }");
        htmlBuilder.append("</style>");

        htmlBuilder.append("<h2>Description</h2>");
        if (this.moTa != null) {
            for (String desc : this.moTa) {
                htmlBuilder.append("<p>").append(desc).append("</p>");
            }
        }
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }
    public GridPane getAnotherInfoTable(){
        if (this.thongTin == null) return null;
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(20);
        gridPane.setVgap(10);
        // Create column constraints
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(80);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(160);


        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPrefWidth(160);


        // Apply column constraints to the GridPane
        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        int rowCnt = 0;
        for (List<String> row : thongTin){
            Label fieldName = null, sourceNKS = null, sourceWiki = null;
            if (row.size() >= 1) {
                fieldName = createWrappedLabel(row.get(0));
                fieldName.setStyle("-fx-font-weight: bold;");
            }
            if (row.size() >= 2)
                sourceNKS = createWrappedLabel(row.get(1));
            if (row.size() >= 3)
                sourceWiki = createWrappedLabel(row.get(2));
            if (row.size() == 1) GridPane.setColumnSpan(fieldName, 3);
            if (row.size() == 2) GridPane.setColumnSpan(sourceNKS, 2);
            if (fieldName != null) gridPane.add(fieldName, 0, rowCnt);
            if (sourceNKS != null) gridPane.add(sourceNKS, 1, rowCnt);
            if (sourceWiki != null) gridPane.add(sourceWiki, 2, rowCnt);
            System.out.println(row);
            System.out.println(fieldName);
            System.out.println(sourceNKS);
            System.out.println(sourceWiki);
            rowCnt += 1;
        }
        return gridPane;

    }

    private Label createWrappedLabel(String text) {
        Label label = new Label();
        label.setTextAlignment(TextAlignment.JUSTIFY);
        label.setWrapText(true);

        TextFlow textFlow = new TextFlow();
        Text textNode = new Text(text);
        textFlow.getChildren().add(textNode);
        label.setGraphic(textFlow);

        return label;
    }
}
