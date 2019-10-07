package com.example.wearable.Holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.example.wearable.models.AdapterModels.MainMenuRecycleModel;
import com.example.wearable.Interfaces.LabelMenuClickListener;
import com.example.wearable.models.PredictionModel;
import com.example.wearable.R;
import com.example.wearable.databinding.ItemMainMenuPredictionBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenuPredictionViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MainMenuPredictionViewHolder";

    private LabelMenuClickListener mMainMenuClickListener;
    private final ItemMainMenuPredictionBinding binding;

    final Animation in = new AlphaAnimation(0.0f, 1.0f);
    final Animation out = new AlphaAnimation(1.0f, 0.0f);

    public MainMenuPredictionViewHolder(@NonNull ItemMainMenuPredictionBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    @SuppressLint("LongLogTag")
    public void bind(Context context, final MainMenuRecycleModel mainMenuRecycleModel, final int position){
        if (mainMenuRecycleModel.getPredictionModel() != null){
            PredictionModel predictionModel = mainMenuRecycleModel.getPredictionModel();
            blink(String.valueOf(predictionModel.getAction_id()));
            if (predictionModel.getAction_id() == PredictionModel.STAND){
                binding.textViewActionName.setText(R.string.stand);
            }else if (predictionModel.getAction_id() == PredictionModel.SIT){
                binding.textViewActionName.setText(R.string.sit);
            }else if (predictionModel.getAction_id() == PredictionModel.STAND_SIT){
                binding.textViewActionName.setText(R.string.stand_sit);
            }
        }else{
            Log.d(TAG,"have no model");
        }
    }

    private void blink(final String Action_id){
        in.setDuration(1000);
        out.setDuration(1000);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.textViewActionId.setText(Action_id);
                binding.textViewActionId.startAnimation(in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        binding.textViewActionId.startAnimation(out);
    }

}
