package com.anupam.testecommerce;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class DownloadDataTask extends AsyncTask {

    static final String TAG = DownloadDataTask.class.getName();

    private TaskCallback listener;


    DownloadDataTask(TaskCallback listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Object o) {
        String data = (String) o;
        Log.e("dsf",data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            listener.onResultReceived(jsonObject);
        } catch (JSONException e) {
            Log.e(TAG, "Error Parsing data");
            listener.onError("Error Parsing data");
            e.printStackTrace();
        }
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return downloadData();
    }

    private String downloadData() {

        HttpsURLConnection con = null;
        try {
            URL u = new URL("https://stark-spire-93433.herokuapp.com/json");
            con = (HttpsURLConnection) u.openConnection();
            con.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return "";


    }

    interface TaskCallback {
        void onResultReceived(JSONObject result);

        void onError(String errorDescription);
    }


}