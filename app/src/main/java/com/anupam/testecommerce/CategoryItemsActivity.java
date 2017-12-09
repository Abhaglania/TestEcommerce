package com.anupam.testecommerce;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.anupam.testecommerce.adapters.CategoriesItemAdaptor;
import com.anupam.testecommerce.adapters.HorizontalCategoriesAdaptor;
import com.anupam.testecommerce.modals.Category;
import com.anupam.testecommerce.modals.Product;
import com.anupam.testecommerce.myutils.MyDbHelper;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class CategoryItemsActivity extends AppCompatActivity {
    int categoryId;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        categoryId = getIntent().getIntExtra("categoryId", 0);

        addChildCategories();

        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        GridView gridview = (GridView) findViewById(R.id.gridview);
        ArrayList<Product> products = myDbHelper.getProductsByCategoryId(categoryId);
        Log.e("sd", "" + products.size());
        gridview.setAdapter(new CategoriesItemAdaptor(getApplicationContext(), products));
    }

    private void addChildCategories() {
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        Category category = myDbHelper.getCategoryById(categoryId);
        JSONArray jsonArray = category.getChild();
        TextView textView = (TextView) findViewById(R.id.textViewName);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.categories);
        if (jsonArray == null) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            myDbHelper.close();
            return;
        }
        if (jsonArray.length() == 0) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            myDbHelper.close();
            return;
        }
        ArrayList<Category> childCategories = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                int id = (int) jsonArray.get(i);
                childCategories.add(myDbHelper.getCategoryById(id));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        HorizontalCategoriesAdaptor rAdapter = new HorizontalCategoriesAdaptor(getApplicationContext(), childCategories);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rAdapter);
    }
}