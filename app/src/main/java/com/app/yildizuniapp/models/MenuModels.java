package com.app.yildizuniapp.models;

public class MenuModels {
    int id;
    String menuitems;

    public MenuModels(int id, String menuitems) {
        this.id = id;
        this.menuitems = menuitems;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMenuitems() {
        return menuitems;
    }

    public void setMenuitems(String menuitems) {
        this.menuitems = menuitems;
    }
}
