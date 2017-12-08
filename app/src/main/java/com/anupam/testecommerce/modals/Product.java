package com.anupam.testecommerce.modals;


import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    int id;
    String name;
    Tax tax;
    String date_added;

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
        return tax.toString();
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public String getDate_added() {
        return date_added;
    }

    public void setDate_added(String date_added) {
        this.date_added = date_added;
    }

    public ArrayList<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(ArrayList<ProductVariant> variants) {
        this.variants = variants;
    }

}
