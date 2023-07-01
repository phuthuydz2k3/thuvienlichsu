package com.app.thuvienlichsu.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ThoiKyModel extends Model
{

    private Set<String> cacNhanVatLienQuan;
    private Set<String> cacDiTichLienQuan;

    public Set<String> getCacNhanVatLienQuan() {
        return this.cacNhanVatLienQuan;
    }
    public List<String> getcacDiTichLienQuan() {
        return new ArrayList<>(this.cacDiTichLienQuan);
    }

    public Set<String> getCacDiTichLienQuan() {
        return cacDiTichLienQuan;
    }

    public ThoiKyModel(String name, List<String> moTa, String code, Set<String> cacNhanVatLienQuan
            , Set<String> cacDiTichLienQuan)
    {
        super(name, moTa);
        setCode(code);
        setcacNhanVatLienQuan(cacNhanVatLienQuan);
        setcacDiTichLienQuan(cacDiTichLienQuan);
    }
    
    public void setcacNhanVatLienQuan(Set<String> cacNhanVatLienQuan) {
        this.cacNhanVatLienQuan = cacNhanVatLienQuan;
    }

    public void setcacDiTichLienQuan(Set<String> cacDiTichLienQuan) {
        this.cacDiTichLienQuan = cacDiTichLienQuan;
    }

    @Override
    public String toHTML()
    {
        StringBuilder htmlBuilder = new StringBuilder();

        // Start the HTML structure
        htmlBuilder.append("<html>");
        htmlBuilder.append("<head>");
        htmlBuilder.append("</head>");
        htmlBuilder.append("<body contenteditable=\"true\">");
        htmlBuilder.append("<i>").append(this.tenModel).append("</i>");
        htmlBuilder.append("<meta charset=\"UTF-8\">");
        // htmlBuilder.append("<title>").append(getName()).append("</title>");
        htmlBuilder.append("<style>");
        htmlBuilder.append("body { background-color:#f2f3f5; font-family:'lucida grande', tahoma, verdana, arial, sans-serif;font-size:14px; }");
        htmlBuilder.append("</style>");

        // Add the name as a heading
        // htmlBuilder.append("<h1>").append("NHÂN VẬT").append("</h1>");
        // htmlBuilder.append("<h1>").append(getName()).append("</h1>");

        // Add the code
        // htmlBuilder.append("<p><strong>Code:</strong> ").append(getCode()).append("</p>");

        // Add the infobox
        htmlBuilder.append("<h2>Thông tin thời kỳ</h2>");

        // Add the description
        if (this.moTa != null) {
            for (String desc : this.moTa) {
                htmlBuilder.append("<p>").append(desc).append("</p>");
            }
        }


        // Add the related figures

        // Close the HTML structure
        htmlBuilder.append("</body>");
        htmlBuilder.append("</html>");

        return htmlBuilder.toString();
    }

    @Override
    public String toString() {
        return "\n{ \"tenModel\":\"" + this.tenModel + "\", "
                + "\n\"moTa\":\"" + this.moTa + "\", "
                + "\n\"cacNhanVatLienQuan\":\"" + this.cacNhanVatLienQuan + "\" }" + "\n";
    }
}