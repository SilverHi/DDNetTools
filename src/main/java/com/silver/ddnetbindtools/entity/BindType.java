package com.silver.ddnetbindtools.entity;

public class BindType {
    private String typeName;
    private String tips;
    private String bindstr;

    public BindType(String typeName, String tips, String bindstr) {
        this.typeName = typeName;
        this.tips = tips;
        this.bindstr = bindstr;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getBindstr() {
        return bindstr;
    }

    public void setBindstr(String bindstr) {
        this.bindstr = bindstr;
    }
}
