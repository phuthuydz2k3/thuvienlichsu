package com.app.thuvienlichsu.base;

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

        // Add the infobox
        if (this.thongTin != null)
        {
            htmlBuilder.append("<h2>Thông tin nhân vật</h2>");
            htmlBuilder.append("<table class=\"table-container\">");
            for (List<String> info : this.thongTin) {
                htmlBuilder.append("<tr>");
                if (info.size() == 1) htmlBuilder.append("<th colspan=\"2\">");
                else htmlBuilder.append("<th scope=\"row\">");
                htmlBuilder.append(info.get(0)).append("</th>");
                htmlBuilder.append("<td>");
                for (int i = 1; i < info.size(); i++) {
                    if (i > 1) htmlBuilder.append("<br>");
                    htmlBuilder.append(info.get(i));
                }
                htmlBuilder.append("</td>");
                htmlBuilder.append("</tr>");
            }
            htmlBuilder.append("</table>");
        }

//        htmlBuilder.append("<h2>Related Periods</h2>");
//        htmlBuilder.append("<ul>");
//        for (String figure : this.cacThoiKyLienQuan) {
//            htmlBuilder.append("<li>").append(figure).append("</li>");
//        }
//        htmlBuilder.append("</ul>");

        // Add the description
        htmlBuilder.append("<h2>Description</h2>");
        if (this.moTa != null) {
            for (String desc : this.moTa) {
                htmlBuilder.append("<p>").append(desc).append("</p>");
            }
        }


//        // Add the related figures
//        htmlBuilder.append("<h2>Related Figures</h2>");
//        if (this.cacNhanVatLienQuan != null) {
//            htmlBuilder.append("<ul>");
//            for (String figure : this.cacNhanVatLienQuan) {
//                htmlBuilder.append("<li>").append(figure).append("</li>");
//            }
//            htmlBuilder.append("</ul>");
//        }
//
//
//        // Add the related places
//        htmlBuilder.append("<h2>Related Places</h2>");
//        if (this.cacDiTichLienQuan != null) {
//            htmlBuilder.append("<ul>");
//            for (String place : this.cacDiTichLienQuan) {
//                htmlBuilder.append("<li>").append(place).append("</li>");
//            }
//            htmlBuilder.append("</ul>");
//        }
//
//
//        // Add the related time periods
//        htmlBuilder.append("<h2>Related Time Periods</h2>");
//        if (this.cacThoiKyLienQuan != null) {
//            htmlBuilder.append("<ul>");
//            for (String timePeriod : this.cacNhanVatLienQuan) {
//                htmlBuilder.append("<li>").append(timePeriod).append("</li>");
//            }
//            htmlBuilder.append("</ul>");
//        }


        // Close the HTML structure
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }
}
