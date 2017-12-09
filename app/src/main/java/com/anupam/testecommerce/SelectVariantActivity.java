package com.anupam.testecommerce;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anupam.testecommerce.modals.ProductVariant;
import com.anupam.testecommerce.myutils.MyDbHelper;

import java.util.ArrayList;

public class SelectVariantActivity extends AppCompatActivity {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_variant);
        int productId = getIntent().getIntExtra("productId", 0);
        if (productId == 0) {
            finish();
            return;
        }

        ArrayList<ProductVariant> variants = getVariantData(productId);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.variants);
        for (ProductVariant variant : variants) {
            try {
                linearLayout.addView(addVariantToView(variant));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<ProductVariant> getVariantData(int productId) {
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        ArrayList<ProductVariant> variants = myDbHelper.getVariantsByProductId(productId);
        myDbHelper.close();
        return variants;
    }

    private View addVariantToView(final ProductVariant variant) throws Exception {
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
                setActivityResult(variant);
                finish();
            }
        });
        return view;

    }

    private void setActivityResult(ProductVariant variant) {
        Intent intent = new Intent();
        intent.putExtra("variant", variant);
        setResult(200, intent);
    }
}
