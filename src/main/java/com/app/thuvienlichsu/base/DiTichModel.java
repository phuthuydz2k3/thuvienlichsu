package com.app.thuvienlichsu.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiTichModel extends Model{
    private Set<String> cacNhanVatLienQuan;
    private Set<String> cacThoiKyLienQuan;
    private List<String> cacLeHoiLienQuan;

    public DiTichModel(String tenModel, List<String> moTa, String code, Set<String> cacNhanVatLienQuan)
    {
        super(tenModel, moTa);
        setCode(code);
        setCacNhanVatLienQuan(cacNhanVatLienQuan);
        setCacThoiKyLienQuan(new HashSet<>());
        setCacLeHoiLienQuan(new ArrayList<>());
    }

    public void setCacLeHoiLienQuan(List<String> cacLeHoiLienQuan) {
        this.cacLeHoiLienQuan = cacLeHoiLienQuan;
    }

    public Set<String> getCacNhanVatLienQuan() {
        return cacNhanVatLienQuan;
    }

    public void setCacNhanVatLienQuan(Set<String> cacNhanVatLienQuan) {
        this.cacNhanVatLienQuan = cacNhanVatLienQuan;
    }

    public Set<String> getCacThoiKyLienQuan() {
        return cacThoiKyLienQuan;
    }

    public void setCacThoiKyLienQuan(Set<String> cacThoiKyLienQuan) {
        this.cacThoiKyLienQuan = cacThoiKyLienQuan;
    }

    public List<String> getCacLeHoiLienQuan() {
        return cacLeHoiLienQuan;
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



        // Add the name as a heading
        // htmlBuilder.append("<h1>").append("NHÂN VẬT").append("</h1>");
        // htmlBuilder.append("<h1>").append(getName()).append("</h1>");

        // Add the code
        // htmlBuilder.append("<p><strong>Code:</strong> ").append(getCode()).append("</p>");



        // Add the description

        if (this.cacThoiKyLienQuan != null)
        {
            htmlBuilder.append("<h2>Thời kỳ liên quan</h2>");
            htmlBuilder.append("<ul>");

            for (String desc : this.cacThoiKyLienQuan) {
                htmlBuilder.append("<li>").append(desc).append("</li>");
            }

            htmlBuilder.append("</ul>");
        }

        if (this.cacLeHoiLienQuan != null)
        {
            htmlBuilder.append("<h2>Lễ hội liên quan</h2>");
            htmlBuilder.append("<ul>");

            for (String desc : this.cacLeHoiLienQuan) {
                htmlBuilder.append("<li>").append(desc).append("</li>");
            }

            htmlBuilder.append("</ul>");
        }

        {
            htmlBuilder.append("<h2>Mô tả</h2>");
            for (String desc : this.moTa) {
                htmlBuilder.append("<p>").append(desc).append("</p>");
            }
        }

//        // Add the related figures
//        if (this.cacNhanVatLienQuan != null)
//        {
//            htmlBuilder.append("<h2>Related Figures</h2>");
//            htmlBuilder.append("<ul>");
//            for (String figure : this.cacNhanVatLienQuan) {
//                htmlBuilder.append("<li>").append(figure).append("</li>");
//            }
//            htmlBuilder.append("</ul>");
//        }

        // Close the HTML structure
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }

    @Override
    public String toString() {
        return  "\n{ \"Id\":\"" + this.id + "\", "
                + "\n\"Địa danh\":\"" + this.tenModel + "\", "
                + "\n\"Code\":\"" + this.code + "\", "
                + "\n\"Miêu tả\":\"" + this.moTa + "\", "
                + "\n\"Nhân vật liên quan code\":\"" + this.cacNhanVatLienQuan + "\" }" + "\n";
    }
}
