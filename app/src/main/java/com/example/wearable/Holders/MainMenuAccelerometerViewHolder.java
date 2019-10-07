package com.example.wearable.Holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.example.wearable.models.AdapterModels.MainMenuRecycleModel;
import com.example.wearable.Interfaces.LabelMenuClickListener;
import com.example.wearable.models.ChartModel;
import com.example.wearable.databinding.ItemMainMenuAccelerometerBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenuAccelerometerViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MainMenuAccelerometerViewHolder";

    private LabelMenuClickListener mMainMenuClickListener;
    private final ItemMainMenuAccelerometerBinding binding;

    public MainMenuAccelerometerViewHolder(@NonNull ItemMainMenuAccelerometerBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("LongLogTag")
    public void bind(Context context, final MainMenuRecycleModel mainMenuRecycleModel, final int position){
        if (mainMenuRecycleModel.getAccList() != null && mainMenuRecycleModel.getTime_acc() != -1){

            ChartModel chartModel = new ChartModel(mainMenuRecycleModel.getTime_acc(), mainMenuRecycleModel.getXacc_values(),
                    mainMenuRecycleModel.getYacc_values(), mainMenuRecycleModel.getZacc_values(),
                    binding.lineChartMainMenuAcc);
            chartModel.addDynamicData(mainMenuRecycleModel.getAccList());
            Log.d("TIme_inHolder",String.valueOf(chartModel.getTime()));
        }else{
            Log.d(TAG,"Have no model");
        }
    }
}
