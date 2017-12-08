package com.anupam.testecommerce.modals;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product {
    int id;
    String name;
    String tax;
    String Date;

    ArrayList<ProductVariant> variants = new ArrayList<>();

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

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public ArrayList<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<ProductVariant> variants) {
        this.variants = variants;
    }

}
