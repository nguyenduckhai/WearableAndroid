package com.example.wearable.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.wearable.Adapters.LabelMenuRecycleAdapter;
import com.example.wearable.Connections.Api_call;
import com.example.wearable.Connections.NetworkAsyncTask;
import com.example.wearable.Interfaces.LabelMenuClickListener;
import com.example.wearable.models.networkmodels.RequestActionModel;
import com.example.wearable.models.networkmodels.RequestModel;
import com.example.wearable.R;
import com.example.wearable.databinding.ActivityLabelBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LabelActivity extends AppCompatActivity {
    private String mJsondata;
    private Activity activity;
    private Gson mGson = new Gson();
    private LabelMenuRecycleAdapter adapter;
    private ActivityLabelBinding binding;
    private List<RequestActionModel> items = new ArrayList<>();

    public static Intent newIntent(Context context){
        return new Intent(context,LabelActivity.class);
    }

    private String getActivityJson() throws ExecutionException, InterruptedException {
        RequestModel requestModel = new RequestModel();
        requestModel.setRequest_method(RequestModel.GET);
        requestModel.setApi_url(requestModel.getActivity());
        NetworkAsyncTask networkAsyncTask = new NetworkAsyncTask();
        return networkAsyncTask.execute(requestModel).get();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = this;
        initColorStatusBar();
        initWhiteStatusBar();
        initRecycleView();
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

    private void initRecycleView(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_label);
        binding.recyclerLabelMenu.setLayoutManager(new GridLayoutManager(this,2));
        binding.recyclerLabelMenu.setHasFixedSize(true);
        Api_call mApicall = new Api_call();
        mApicall.setContext(LabelActivity.this);

        try {
            mJsondata = getActivityJson();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Type listType =  new TypeToken<List<RequestActionModel>>(){}.getType();
        items = mGson.fromJson(mJsondata,listType);

        if (adapter == null){
            adapter = new LabelMenuRecycleAdapter(items,activity);
            adapter.setActivity(activity);
            adapter.setLabelMenuClickListener(new LabelMenuClickListener() {
                @Override
                public void onMenuClick(int postion) {
                    Intent intent = TimerActivity.newIntent(LabelActivity.this,postion);
                    startActivity(intent);
                }
            });
            binding.recyclerLabelMenu.setAdapter(adapter);
        }
    }
}
