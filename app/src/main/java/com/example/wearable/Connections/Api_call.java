package com.example.wearable.Connections;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Api_call extends AsyncTask<String,Integer,String> {
    private String mJsonData;
    private int httpResponse;
    private Context mContext;


    public void setContext(Context context) {
        mContext = context;
    }


    @Override
    protected String doInBackground(String... params) {
        OkHttpClient mClient = new OkHttpClient();

        Request request = new Request.Builder()
                .url(params[0])
                .build();
        try {
            Response response = mClient.newCall(request).execute();
            mJsonData =  response.body().string();
            httpResponse = response.code();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJsonData;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(mContext, "Response code:" + httpResponse, Toast.LENGTH_SHORT).show();
    }
}
