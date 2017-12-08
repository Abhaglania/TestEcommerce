package com.anupam.testecommerce.modals;

import org.json.JSONArray;



public class Category {
    int id;
    String name;
    JSONArray child;

    public Category() {

    }

    public JSONArray getChild() {
        return child;
    }

    public void setChild(JSONArray child) {
        this.child = child;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
