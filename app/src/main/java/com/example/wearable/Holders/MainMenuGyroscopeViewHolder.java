package com.example.wearable.Holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.wearable.Interfaces.LabelMenuClickListener;
import com.example.wearable.models.AdapterModels.MainMenuRecycleModel;
import com.example.wearable.models.ChartModel;
import com.example.wearable.databinding.ItemMainMenuGyroscopeBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenuGyroscopeViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MainMenuGyroscopeViewHolder";

    private LabelMenuClickListener mMainMenuClickListener;
    private final ItemMainMenuGyroscopeBinding binding;

    public MainMenuGyroscopeViewHolder(@NonNull ItemMainMenuGyroscopeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("LongLogTag")
    public void bind(Context context, final MainMenuRecycleModel mainMenuRecycleModel, final int position){
        if (mainMenuRecycleModel.getGyroList() != null && mainMenuRecycleModel.getTime_gyro() != -1){

            ChartModel chartModel = new ChartModel(mainMenuRecycleModel.getTime_gyro(), mainMenuRecycleModel.getXgyro_values(),
                    mainMenuRecycleModel.getYgyro_values(), mainMenuRecycleModel.getZgyro_values(),
                    binding.lineChartMainMenuGyro);
            chartModel.addDynamicData(mainMenuRecycleModel.getGyroList());
            Log.d("TIme_inHolder",String.valueOf(chartModel.getTime()));
        }else{
            Log.d(TAG,"Have no model");
        }
    }
}
