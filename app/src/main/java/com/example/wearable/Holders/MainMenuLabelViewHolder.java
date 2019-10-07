package com.example.wearable.Holders;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.wearable.Activities.LabelActivity;
import com.example.wearable.databinding.ItemMainMenuLabelButtonBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenuLabelViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MainMenuLabelViewHolder";

    private final ItemMainMenuLabelButtonBinding binding;

    public MainMenuLabelViewHolder(@NonNull ItemMainMenuLabelButtonBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(final Context context){
        if (context != null){
            binding.buttonLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = LabelActivity.newIntent(context);
                    context.startActivity(intent);
                }
            });
        }else {
            Log.d(TAG,"no activity");
        }
    }
}
