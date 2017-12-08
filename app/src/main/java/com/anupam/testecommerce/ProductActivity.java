package com.anupam.testecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anupam.testecommerce.modals.Product;
import com.anupam.testecommerce.modals.ProductVariant;
import com.anupam.testecommerce.myutils.MyDbHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductActivity extends AppCompatActivity {
    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        int productId = getIntent().getIntExtra("productId", 0);
        product = getProduct(productId);
        addProductToView();
    }

    private Product getProductDummy(int productId) {
        Product product = new Product();
        product.setDate("2016-12-09T11:16:11.000Z");
        product.setId(12);
        product.setName("Dummy");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("tax", "VAT");
            jsonObject.put("value", 12);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        product.setTax(jsonObject.toString());
        product.setVariants(SelectVariantActivity.getVariantDataDummy(1));
        return product;
    }

    private void addProductToView() {
        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        ProductVariant selectedVariant = product.getVariants().get(0);
        addSelectedVariant(selectedVariant);
        textViewName.setText(product.getName());
    }

    private void addVariantToView(ProductVariant variant) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.selectedVariant);
        linearLayout.removeAllViews();
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View view = li.inflate(R.layout.product_variant, null);
        TextView textViewVariantPrice = (TextView) view.findViewById(R.id.textViewVariantPrice);
        String price = "Price : " + variant.getPrice();
        textViewVariantPrice.setText(price);
        TextView textViewVariantSize = (TextView) view.findViewById(R.id.textViewVariantSize);
        if (variant.getSize() <= 0) {
            textViewVariantSize.setVisibility(View.GONE);
        } else {
            String size = "Size : " + variant.getSize();
            textViewVariantSize.setText(size);
            textViewVariantSize.setVisibility(View.VISIBLE);
        }
        TextView textViewVariantColor = (TextView) view.findViewById(R.id.textViewVariantColor);
        String color = "Color : " + variant.getColor();
        textViewVariantColor.setText(color);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectVariantActivity.class);
                intent.putExtra("productId", 1);
                startActivityForResult(intent, 101);
            }
        });
        linearLayout.addView(view);
    }

    private void addSelectedVariant(ProductVariant variant) {
        TextView textViewFinalPrice = (TextView) findViewById(R.id.textViewFinalPrice);
        TextView textViewPriceDescription = (TextView) findViewById(R.id.textViewPriceDescription);
        int price = variant.getPrice();
        try {
            JSONObject jsonObject = new JSONObject(product.getTax());
            String taxName = jsonObject.optString("name", "");
            double taxPercentage = jsonObject.optDouble("value", 0.0);
            double tax = (taxPercentage * price * .001);
            double finalPrice = price + tax;
            textViewFinalPrice.setText("" + finalPrice);
            String priceDescription = price + "  +  " + tax + " (" + taxName + "  " + taxPercentage + ")";
            textViewPriceDescription.setText(priceDescription);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addVariantToView(variant);
    }

    public Product getProduct(int productId) {
        MyDbHelper dbHelper = new MyDbHelper(getApplicationContext());
        Product product = dbHelper.getProductById(productId);
        dbHelper.close();
        return product;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            ProductVariant variant = (ProductVariant) data.getSerializableExtra("variant");
            addSelectedVariant(variant);
        }
    }
}