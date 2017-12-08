package com.anupam.testecommerce;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements DownloadDataTask.TaskCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new DownloadDataTask(this).execute("");
    }

    @Override
    public void onResultReceived(JSONObject result) {
        
    }

    @Override
    public void onError(String errorDescription) {

    }
}
