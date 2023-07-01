package com.app.thuvienlichsu.base;

import java.util.List;

public class InfoLine {
    private String fieldName = "";
    private String sourceNKS = "";
    private String sourceWiki = "";
    InfoLine(List<String> tableData) {
        if (tableData.size() >= 1) this.fieldName = tableData.get(0);
        if (tableData.size() >= 2) this.sourceNKS = tableData.get(1);
        if (tableData.size() >= 3) this.sourceWiki = tableData.get(2);

    }

    public String getFieldName() {
        return fieldName;
    }

    public String getSourceNKS() {
        return sourceNKS;
    }

    public String getSourceWiki() {
        return sourceWiki;
    }

    @Override
    public String toString() {
        return fieldName + ", " + sourceNKS + ", " + sourceWiki;
    }
}
