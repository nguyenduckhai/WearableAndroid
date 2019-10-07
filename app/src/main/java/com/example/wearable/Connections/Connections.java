package com.example.wearable.Connections;

import android.app.Activity;
import android.util.Log;

import com.example.wearable.models.networkmodels.RequestModel;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connections {
    private static Connections instance;
    private static final String TAG = "connections";
    public static final String ERROR_STATUS = "HTTP_ERROR";
    public static final String UNAUTHORIZATION_ERROR = "401";

    private Gson gson;
    private OkHttpClient client;
    private Activity activity;

    //----------Create Constructor---------------------------------------------------------
    public Connections(Activity activity) {
        this.activity = activity;
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    //--------------------------------------------------------------------------------------
    public static Connections getInstance(Activity activity) {
        if (instance == null) {
            instance = new Connections(activity);
        }
        return instance;
    }

    public String post_api(RequestModel req) throws Exception{
        if (req.getBody_request() != null){
            MediaType mediaType = MediaType.parse("application/json");

            String url = req.getApi_url();

            String json = gson.toJson(req.getBody_request());
            Log.d("log_post_api", url);
            Log.d("log_post_body", json);

            RequestBody body = RequestBody.create(mediaType, json);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    if(response.code() == 401) return UNAUTHORIZATION_ERROR;
                    else return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }else{
            MediaType mediaType = MediaType.parse("application/json");

            String url = req.getApi_url();
            String json ="";

            json  = gson.toJson(req.getBody_request());


            Log.d("log_post_api", url);
            Log.d("log_post_body", json);
            RequestBody body = RequestBody.create(mediaType, json);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("cache-control", "no-cache")
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    return response.body().string();
                } else {
                    if(response.code() == 401) return UNAUTHORIZATION_ERROR;
                    else return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public String get_api(RequestModel req) throws Exception{
        String url = req.getApi_url();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.d("","");
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                if (response.code() == 401) return UNAUTHORIZATION_ERROR;
                else return null;
            }
        } catch (IOException e) {
//            e.printStackTrace();
            Log.e("get",e.getMessage().toString());
            return null;
        }
    }

    public String delete_api(RequestModel req) throws Exception{
        MediaType mediaType = MediaType.parse("application/json");

        String url = req.getApi_url();

        String json = gson.toJson(req.getBody_request());
        Log.d("log_delete_api", url);
        Log.d("log_delete_body", json);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                if(response.code() == 401) return UNAUTHORIZATION_ERROR;
                else return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String update_api(RequestModel req)throws Exception{
        MediaType mediaType = MediaType.parse("application/json");

        String url = req.getApi_url();

        String json = gson.toJson(req.getBody_request());
        Log.d("log_update_api", url);
        Log.d("log_update_body", json);
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                if(response.code() == 401) return UNAUTHORIZATION_ERROR;
                else return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

