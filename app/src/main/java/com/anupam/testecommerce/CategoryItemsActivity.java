package com.anupam.testecommerce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.anupam.testecommerce.adapters.CategoriesItemAdaptor;
import com.anupam.testecommerce.modals.Product;
import com.anupam.testecommerce.myutils.MyDbHelper;

import java.util.ArrayList;

public class CategoryItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        int categoryId = getIntent().getIntExtra("categoryId", 0);
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        GridView gridview = (GridView) findViewById(R.id.gridview);
        ArrayList<Product> products=myDbHelper.getProductsByCategoryId(categoryId);
        Log.e("sd",""+products.size());
        gridview.setAdapter(new CategoriesItemAdaptor(getApplicationContext(), products));
    }
}