package com.example.wearable.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.wearable.models.AdapterModels.MainMenuRecycleModel;
import com.example.wearable.Adapters.MainMenuRecycleAdapter;
import com.example.wearable.models.networkmodels.RequestDeviceModel;
import com.example.wearable.models.PredictionModel;
import com.example.wearable.R;
import com.example.wearable.databinding.ActivityMainBinding;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "log_main";

    private List<RequestDeviceModel> mAccList = new ArrayList<>();
    private List<Entry> Xacc_values = new ArrayList<Entry>();
    private List<Entry> Yacc_values = new ArrayList<Entry>();
    private List<Entry> Zacc_values = new ArrayList<Entry>();
    private int time_acc = 0;

    private List<RequestDeviceModel> mGyroList = new ArrayList<>();
    private List<Entry> Xgyro_values = new ArrayList<Entry>();
    private List<Entry> Ygyro_values = new ArrayList<Entry>();
    private List<Entry> Zgyro_values = new ArrayList<Entry>();
    private int time_gyro = 0;

    private Activity activity;
    private MainMenuRecycleAdapter adapter;
    private ActivityMainBinding binding;
    private List<MainMenuRecycleModel> items = new ArrayList<>();
    private Gson mGson = new Gson();

    private boolean running_Acc = false;
    private boolean running_Gyro = false;
    private boolean running_Prediction = false;

//------------------------URL of redis server---------------------------------
    private String URL_RISEPLUS = "http://192.168.10.104:6379/";

    Thread mThread_acc = new Thread(new Runnable() {
        @Override
        public void run() {
            if (running_Acc){
                final Jedis client = new Jedis(URL_RISEPLUS);
                JedisPubSub sub = new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        update_Acc_View(message);
                    }
                };
                client.subscribe(sub,"acc");
            }else{
                Log.d("THREAD_ACC:","STOP");
            }

        }
    });

    Thread mThread_gyro = new Thread(new Runnable() {
        @Override
        public void run() {
            if (running_Gyro){
                final Jedis client = new Jedis(URL_RISEPLUS);
                JedisPubSub sub = new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        update_Gyro_View(message);
                    }
                };
                client.subscribe(sub,"gyro");
            }else {
                Log.d("THREAD_GYRO","STOP");
            }
        }
    });

    Thread mThread_prediction = new Thread(new Runnable() {
        @Override
        public void run() {
            if (running_Prediction){
                Jedis client = new Jedis(URL_RISEPLUS);
                JedisPubSub sub = new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        update_predction_view(message);
                    }
                };
                client.subscribe(sub,"prediction");
            }else {
                Log.d("THREAD_PREDICTION","STOP");
            }

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initColorStatusBar();
        initWhiteStatusBar();
        initRecycleView();

        if (!mThread_acc.isAlive()){
            running_Acc = false;
        }else {
            running_Acc = true;
        }
        if (!mThread_gyro.isAlive()){
            running_Gyro = false;
        }else {
            running_Gyro = true;
        }
        if (!mThread_prediction.isAlive()){
            running_Prediction = false;
        }else {
            running_Prediction = true;
        }
        Log.d("THREAD_ACC",String.valueOf(items.size()));

    }

    private void update_predction_view(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                char[] mess = message.toCharArray();
                if (mess == null){
                    PredictionModel predictionModel = new PredictionModel(1);
                    items.set(1,getPredictionItem(predictionModel));
                }else{
                    int id  = (int) Integer.parseInt(String.valueOf(mess[1]));
                    PredictionModel predictionModel = new PredictionModel(id);
                    items.set(1,getPredictionItem(predictionModel));
                }
//                Toast.makeText(activity, "Changed_prediction", Toast.LENGTH_SHORT).show();

                adapter.UpdatePredictionItem(items.get(1));
            }
        });
    }

    private void update_Acc_View(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<List<RequestDeviceModel>>(){}.getType();
                mAccList  = mGson.fromJson(message,listType);

                items.set(2,getAccList(mAccList));
                items.get(2).checkAcc(500);

                adapter.UpdateAccItem(items.get(2));

                time_acc = items.get(2).getTime_acc();
                Xacc_values = items.get(2).getXacc_values();
                Yacc_values = items.get(2).getYacc_values();
                Zacc_values = items.get(2).getZacc_values();

                Log.d("UPDATE",String.valueOf(time_acc));
            }
        });
    }

    private void update_Gyro_View(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<List<RequestDeviceModel>>(){}.getType();
                mGyroList  = mGson.fromJson(message,listType);

                items.set(3,getGyroList(mGyroList));
                items.get(3).checkGyro(500);

                adapter.UpdateGyroItem(items.get(3));

                time_gyro = items.get(3).getTime_gyro();
                Xgyro_values = items.get(3).getXgyro_values();
                Ygyro_values = items.get(3).getYgyro_values();
                Zgyro_values = items.get(3).getZgyro_values();

                Log.d("UPDATE",String.valueOf(time_gyro));
            }
        });
    }

    private void initRecycleView(){
        running_Gyro = true;
        running_Prediction = true;
        running_Acc = true;

        binding.recyclerMainMenu.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        binding.recyclerMainMenu.setLayoutManager(linearLayoutManager);
        binding.recyclerMainMenu.smoothScrollToPosition(0);

        SimpleItemAnimator itemAnimator = (SimpleItemAnimator) binding.recyclerMainMenu.getItemAnimator();
        itemAnimator.setSupportsChangeAnimations(false);

        List<RequestDeviceModel> deviceModels = new ArrayList<>();
        deviceModels.add(new RequestDeviceModel(0,0,0));
        MainMenuRecycleModel item1 = new MainMenuRecycleModel(MainMenuRecycleModel.FOOTER);
        MainMenuRecycleModel item2 = getPredictionItem(new PredictionModel(1));
        MainMenuRecycleModel item3 = getAccList(deviceModels);
        MainMenuRecycleModel item4 = getGyroList(deviceModels);
        MainMenuRecycleModel item5 = new MainMenuRecycleModel(MainMenuRecycleModel.LABEL);

        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        items.add(item5);
        items.get(2).checkAcc(0);
        items.get(3).checkGyro(0);

        mThread_acc.start();
        mThread_gyro.start();
        mThread_prediction.start();

        setAdapter(items);
    }

    private void initColorStatusBar(){
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorActionBar));
        }
    }

    private void initWhiteStatusBar(){
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        }
    }

    private void setAdapter(List<MainMenuRecycleModel> items){
//        items = getMenuItem(predictionModel,mDeviceList);
        if (adapter == null){
            adapter = new MainMenuRecycleAdapter(items, activity);
            adapter.setActivity(activity);
            binding.recyclerMainMenu.setAdapter(adapter);
        }
        binding.recyclerMainMenu.smoothScrollToPosition(0);
    }

    private MainMenuRecycleModel getPredictionItem(PredictionModel predictionModel){
        MainMenuRecycleModel item = new MainMenuRecycleModel(MainMenuRecycleModel.PREDICTION);
        item.setPredictionModel(predictionModel);
        return item;
    }

    private MainMenuRecycleModel getAccList(List<RequestDeviceModel> deviceModels){
        MainMenuRecycleModel item = new MainMenuRecycleModel(MainMenuRecycleModel.ACCELEROMTER);
        item.setAccList(deviceModels,Xacc_values,Yacc_values,Zacc_values);
        item.setTime_acc(time_acc);
        return item;
    }

    private MainMenuRecycleModel getGyroList(List<RequestDeviceModel> deviceModels){
        MainMenuRecycleModel item = new MainMenuRecycleModel(MainMenuRecycleModel.GYROSCOPE);
        item.setGyroList(deviceModels,Xgyro_values,Ygyro_values,Zgyro_values);
        item.setTime_gyro(time_gyro);
        return item;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        running_Acc = false;
//        running_Gyro = false;
//        running_Prediction = false;
//
//
//        time_gyro = 0;
//        time_acc = 0;
//
//        mAccList.clear();
//        Xacc_values.clear();
//        Yacc_values.clear();
//        Zacc_values.clear();
//
//        mGyroList.clear();
//        Xgyro_values.clear();
//        Ygyro_values.clear();
//        Zgyro_values.clear();

        Log.d("OnStop","True");
    }

    @Override
    protected void onPause() {
        super.onPause();
        running_Acc = false;
        running_Gyro = false;
        running_Prediction = false;


        time_gyro = 0;
        time_acc = 0;

        mAccList.clear();
        Xacc_values.clear();
        Yacc_values.clear();
        Zacc_values.clear();

        mGyroList.clear();
        Xgyro_values.clear();
        Ygyro_values.clear();
        Zgyro_values.clear();

        Log.d("OnPause","True");
    }

}
