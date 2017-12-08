package com.anupam.testecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anupam.testecommerce.adapters.HorizontalCategoriesAdaptor;
import com.anupam.testecommerce.myutils.DownloadDataTask;
import com.anupam.testecommerce.myutils.MyDbHelper;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DownloadDataTask.TaskCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadData();
    }

    private void downloadData() {
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        myDbHelper.resetDatabase();
        if (myDbHelper.getCategories().size() == 0)
            new DownloadDataTask(this).execute("");
        else {
            addCatogoriesToView();
        }
    }

    private void addCatogoriesToView() {
        MyDbHelper myDbHelper = new MyDbHelper(getApplicationContext());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        HorizontalCategoriesAdaptor rAdapter = new HorizontalCategoriesAdaptor(getApplicationContext(), myDbHelper.getCategories());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rAdapter);
    }

    @Override
    public void onResultReceived(JSONObject result) {

    }

    @Override
    public void onError(String errorDescription) {

    }
}
