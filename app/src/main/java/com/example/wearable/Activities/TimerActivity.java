package com.example.wearable.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.wearable.Connections.NetworkAsyncTask;
import com.example.wearable.R;
import com.example.wearable.models.networkmodels.RequestActionBody;
import com.example.wearable.models.networkmodels.RequestModel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TimerActivity extends AppCompatActivity {
    private  static final String EXTRA_ACTIVITY_ID = "com.example.label.Timer.ActivityId";

    private int seconds = 0;
    private boolean running;
    private int mActivityId;
    private Activity activity;
    private Button mButtonStart;
    private TextView mTextReset;
    private RelativeLayout mRelativeLayout;
    private TextView mTextTimerAcitivy;

    public static Intent newIntent(Context context,int activityId){
        Intent intent =  new Intent(context, TimerActivity.class);
        intent.putExtra(EXTRA_ACTIVITY_ID,activityId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        this.activity = this;

        mTextReset = findViewById(R.id.text_reset);
        mTextReset.setVisibility(View.INVISIBLE);
        mTextTimerAcitivy = findViewById(R.id.text_timer_activity_name);
        getActivityId();
        runTimer();
        initColorStatusBar();
        initWhiteStatusBar();
        mRelativeLayout = findViewById(R.id.relative_activity_timer);
        if (mActivityId == 1){
            mTextTimerAcitivy.setBackgroundColor(activity.getResources().getColor(R.color.color_stand));
            mTextTimerAcitivy.setText(R.string.stand);
        }else if (mActivityId == 2){
            mTextTimerAcitivy.setBackgroundColor(activity.getResources().getColor(R.color.color_sit));
            mTextTimerAcitivy.setText(R.string.sit);
        }else if (mActivityId == 3){
            mTextTimerAcitivy.setBackgroundColor(activity.getResources().getColor(R.color.color_stand_sit));
            mTextTimerAcitivy.setText(R.string.stand_sit);
        }else{
            mTextTimerAcitivy.setBackgroundColor(activity.getResources().getColor(R.color.color_sit));
            mTextTimerAcitivy.setText(String.valueOf(mActivityId));
        }
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
            if (mActivityId == 1){
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_stand));
            }else if (mActivityId == 2){
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_sit));
            }else if (mActivityId == 3){
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_stand_sit));
            }else{
                window.setStatusBarColor(ContextCompat.getColor(activity, R.color.color_sit));
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    public void onClickStart(View view) throws ExecutionException, InterruptedException{
        mButtonStart = findViewById(R.id.button_start);
        mTextReset = findViewById(R.id.text_reset);
        if (!running){
            running = true;
            mButtonStart.setText(R.string.stop);
            mTextReset.setVisibility(View.INVISIBLE);
            mButtonStart.setBackgroundColor(activity.getResources().getColor(R.color.color_button_timer_stop_background));
            postWearableLabel("1",String.valueOf(mActivityId),"False");
        }else{
            running = false;
            YoYo.with(Techniques.FadeInDown).
                    duration(500)
                    .playOn(mTextReset);
            mButtonStart.setEnabled(false);
            mTextReset.setVisibility(View.VISIBLE);
            mTextReset.setEnabled(true);
            mTextReset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    seconds = 0;
                    mButtonStart.setEnabled(true);
                    mButtonStart.setText(R.string.start);
                    mButtonStart.setBackgroundColor(activity.getResources().getColor(R.color.color_button_timer_start_background));

                    YoYo.with(Techniques.FadeOutUp).
                            duration(500)
                            .playOn(mTextReset);
                    mTextReset.setEnabled(false);
                }
            });
            putLastWearable("True");
        }
    }



    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.text_activity_timer);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 0);
            }
        });
    }

    private void getActivityId(){
        mActivityId = (int) getIntent().getSerializableExtra(EXTRA_ACTIVITY_ID);
        mActivityId += 1;
    }

    private void postWearableLabel(String device_id,String activity_id,String time_end){
        RequestActionBody requestActionBody = new RequestActionBody();
        requestActionBody.setActivity_id(activity_id);
        requestActionBody.setDevice_id(device_id);
        requestActionBody.setTime_end(time_end);

        RequestModel requestModel = new RequestModel();
        requestModel.setRequest_method(RequestModel.POST);
        requestModel.setApi_url(requestModel.postWearableLabel());
        requestModel.setBody_request(requestActionBody);

        NetworkAsyncTask asyncTask = new NetworkAsyncTask();
        asyncTask.setActivity(TimerActivity.this);
        asyncTask.setProcessing_listener(new NetworkAsyncTask.MyAsyncTaskListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onPostExecute(String result) {
                if (result != null){
                    Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
                }
            }
        });
        asyncTask.execute(requestModel);
    }

    private void putLastWearable(String time_end){
        RequestActionBody requestActionBody = new RequestActionBody();
        requestActionBody.setTime_end(time_end);

        RequestModel req = new RequestModel();
        req.setRequest_method(RequestModel.UPDATE);
        req.setApi_url(req.putLastWearable());
        req.setBody_request(requestActionBody);

        NetworkAsyncTask asyncTask = new NetworkAsyncTask();
        asyncTask.setActivity(TimerActivity.this);
        asyncTask.setProcessing_listener(new NetworkAsyncTask.MyAsyncTaskListener() {
            @Override
            public void onPreExecute() {

            }

            @Override
            public void onPostExecute(String result) {
                if (result != null){
                    Toast.makeText(TimerActivity.this, result, Toast.LENGTH_SHORT).show();
                }
            }
        });
        asyncTask.execute(req);
    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//            running =false;
//            seconds = 0;
//            putLastWearable("True");
//    }

    @Override
    protected void onPause() {
        super.onPause();
        if (running){
            running =false;
            seconds = 0;
            putLastWearable("True");
        }
    }
}
