package com.example.wearable.Holders;

import android.view.View;

import com.example.wearable.databinding.ItemMainMenuFooterBarBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenuFooterViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "MainMenuFooterViewHolder";

    private ItemMainMenuFooterBarBinding binding;
    public MainMenuFooterViewHolder(@NonNull ItemMainMenuFooterBarBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
