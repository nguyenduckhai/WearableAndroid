package com.example.wearable.Holders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.example.wearable.Interfaces.LabelMenuClickListener;
import com.example.wearable.models.networkmodels.RequestActionModel;
import com.example.wearable.R;
import com.example.wearable.databinding.ItemLabelMenuBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LabelMenuViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "LabelMenuViewHolder";

    private LabelMenuClickListener mLabelMenuClickListener;
    private final ItemLabelMenuBinding binding;

    public LabelMenuViewHolder(@NonNull ItemLabelMenuBinding binding) {
        super(binding.getRoot());
        this.binding = binding ;
    }

    @SuppressLint("ResourceAsColor")
    public void bind(RequestActionModel item, final int position, Context context){
        binding.realativeLabelMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLabelMenuClickListener != null){
                    mLabelMenuClickListener.onMenuClick(position);
                }
            }
        });

        if (item.getId().equals("1")){
            binding.realativeLabelMenu.setBackgroundColor(context.getResources().getColor(R.color.color_stand));
            binding.setAction(item);

        }else if (item.getId().equals("2")){
            binding.realativeLabelMenu.setBackgroundColor(context.getResources().getColor(R.color.color_sit));
            binding.setAction(item);
        }else if (item.getId().equals("3")){
            binding.realativeLabelMenu.setBackgroundColor(context.getResources().getColor(R.color.color_stand_sit));
            binding.setAction(item);
        }else{
            binding.realativeLabelMenu.setBackgroundColor(context.getResources().getColor(R.color.color_sit));
            binding.setAction(item);
        }
        Log.d(TAG,item.getId());
    }

    public void setLabelMenuClickListener(LabelMenuClickListener labelMenuClickListener) {
        mLabelMenuClickListener = labelMenuClickListener;
    }
}
