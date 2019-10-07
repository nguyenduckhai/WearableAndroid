package com.example.wearable.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.wearable.Holders.MainMenuAccelerometerViewHolder;
import com.example.wearable.Holders.MainMenuFooterViewHolder;
import com.example.wearable.Holders.MainMenuGyroscopeViewHolder;
import com.example.wearable.Holders.MainMenuLabelViewHolder;
import com.example.wearable.databinding.ItemMainMenuFooterBarBinding;
import com.example.wearable.models.AdapterModels.*;
import com.example.wearable.Holders.MainMenuPredictionViewHolder;
import com.example.wearable.R;
import com.example.wearable.databinding.ItemMainMenuAccelerometerBinding;
import com.example.wearable.databinding.ItemMainMenuGyroscopeBinding;
import com.example.wearable.databinding.ItemMainMenuLabelButtonBinding;
import com.example.wearable.databinding.ItemMainMenuPredictionBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class MainMenuRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MainMenuRecycleModel> items;
    private Context mContext;
    private Activity mActivity;

    /** get and set */
    public List<MainMenuRecycleModel> getItems() {
        return items;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public void setItems(List<MainMenuRecycleModel> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    /** constructor */
    public MainMenuRecycleAdapter(List<MainMenuRecycleModel> items, Context context) {
        this.items = items;
        mContext = context;
    }

    public void UpdatePredictionItem(MainMenuRecycleModel item){
        this.notifyItemChanged(1,item);
    }

    public void UpdateAccItem(MainMenuRecycleModel item){
        this.notifyItemChanged(2,item);
    }

    public void UpdateGyroItem(MainMenuRecycleModel item){
        this.notifyItemChanged(3,item);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MainMenuRecycleModel.PREDICTION){
            ItemMainMenuPredictionBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_main_menu_prediction,parent,false);
            return new MainMenuPredictionViewHolder(binding);
        }else if (viewType == MainMenuRecycleModel.ACCELEROMTER){
            ItemMainMenuAccelerometerBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_main_menu_accelerometer,parent,false);
            return new MainMenuAccelerometerViewHolder(binding);
        }else if (viewType == MainMenuRecycleModel.GYROSCOPE){
            ItemMainMenuGyroscopeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_main_menu_gyroscope,parent,false);
            return new MainMenuGyroscopeViewHolder(binding);
        }else if (viewType == MainMenuRecycleModel.LABEL){
            ItemMainMenuLabelButtonBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_main_menu_label_button,parent,false);
            return new MainMenuLabelViewHolder(binding);
        }else if (viewType == MainMenuRecycleModel.FOOTER){
            ItemMainMenuFooterBarBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_main_menu_footer_bar,parent,false);
            return new MainMenuFooterViewHolder(binding);
        }else{
            return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MainMenuPredictionViewHolder){
            final MainMenuPredictionViewHolder mainMenuPredictionViewHolder = ((MainMenuPredictionViewHolder) holder);
            mainMenuPredictionViewHolder.bind(mContext, items.get(1), position);
        }else if (holder instanceof MainMenuAccelerometerViewHolder){
            final MainMenuAccelerometerViewHolder mainMenuAccelerometerViewHolder = ((MainMenuAccelerometerViewHolder) holder);
            mainMenuAccelerometerViewHolder.bind(mContext,items.get(2),position);
        }else if (holder instanceof MainMenuGyroscopeViewHolder){
            final MainMenuGyroscopeViewHolder mainMenuGyroscopeViewHolder = ((MainMenuGyroscopeViewHolder) holder);
            mainMenuGyroscopeViewHolder.bind(mContext, items.get(3),position);
        }else if (holder instanceof MainMenuLabelViewHolder){
            final MainMenuLabelViewHolder mainMenuLabelViewHolder = ((MainMenuLabelViewHolder) holder);
            mainMenuLabelViewHolder.bind(mContext);
        }else if (holder instanceof MainMenuFooterViewHolder){
            final MainMenuFooterViewHolder mainMenuFooterViewHolder = ((MainMenuFooterViewHolder)holder);
        }
    }

    @Override
    public int getItemCount() {
//        return items.size();
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }
}
