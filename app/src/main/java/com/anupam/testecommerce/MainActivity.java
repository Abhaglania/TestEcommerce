package com.anupam.testecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anupam.testecommerce.adapters.HorizontalCategoriesAdaptor;
import com.anupam.testecommerce.adapters.HorizontalProductsAdaptor;
import com.anupam.testecommerce.modals.Category;
import com.anupam.testecommerce.modals.Product;
import com.anupam.testecommerce.myutils.DownloadDataTask;
import com.anupam.testecommerce.myutils.MyDbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DownloadDataTask.TaskCallback {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadData();
    }

    private void downloadData() {
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        myDbHelper.resetDatabase();
        if (myDbHelper.getCategories().size() == 0) {
            Toast.makeText(getApplicationContext(), "Downloading data please wait", Toast.LENGTH_LONG).show();
            new DownloadDataTask(this).execute("");
        } else {
            addCatogoriesToView();
            addRankingsToView();
        }
    }


    @Override
    public void onResultReceived(JSONObject result) {
        try {
            MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
            JSONArray jsonArray = result.getJSONArray("categories");
            parseAndStoreCategories(jsonArray, myDbHelper);
            JSONArray rankings = result.getJSONArray("rankings");
            parseAndStoreRankings(rankings, myDbHelper);
            myDbHelper.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        addCatogoriesToView();
        addRankingsToView();
    }

    private void addCatogoriesToView() {
        LinearLayout ranking = (LinearLayout) findViewById(R.id.list);
        View itemView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.category_item, null, false);
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.categories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        HorizontalCategoriesAdaptor rAdapter = new HorizontalCategoriesAdaptor(getApplicationContext(), myDbHelper.getCategories());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rAdapter);
        ranking.addView(itemView);
    }

    private void addRankingsToView() {
        LinearLayout ranking = (LinearLayout) findViewById(R.id.list);
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        try {
            JSONArray jsonArray = myDbHelper.getRankings();
            for (int i = 0; i < jsonArray.length(); i++) {
                View itemView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.category_item, null, false);
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                JSONArray productList = jsonObject.getJSONArray("items");
                ArrayList<Product> products = new ArrayList<>();
                for (int j = 0; j < productList.length(); j++) {
                    JSONObject rankingItem = productList.getJSONObject(j);
                    int id = rankingItem.getInt("id");
                    products.add(myDbHelper.getProductById(id));
                }
                TextView textView = (TextView) itemView.findViewById(R.id.textViewName);
                textView.setText(name);
                RecyclerView recyclerView = (RecyclerView) itemView.findViewById(R.id.categories);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
                HorizontalProductsAdaptor rAdapter = new HorizontalProductsAdaptor(getApplicationContext(), products);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(rAdapter);
                ranking.addView(itemView);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseAndStoreRankings(JSONArray rankings, MyDbHelper myDbHelper) throws JSONException {

        for (int i = 0; i < rankings.length(); i++) {
            JSONObject jsonObject = rankings.getJSONObject(i);
            String name = jsonObject.getString("ranking");
            JSONArray rankingItems = jsonObject.getJSONArray("products");
            myDbHelper.insertRankings(name, rankingItems.toString());
        }

    }

    private void parseAndStoreCategories(JSONArray jsonArray, MyDbHelper myDbHelper) throws JSONException {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = jsonObject.optInt("id", 0);
            String name = jsonObject.optString("name", "");
            JSONArray productJsonArray = jsonObject.getJSONArray("products");
            Category category = new Category();
            category.setName(name);
            category.setId(id);
            category.setChild(jsonObject.getJSONArray("child_categories"));
            myDbHelper.insertCategory(category, productJsonArray);
        }
    }

    @Override
    public void onError(String errorDescription) {

    }
}
