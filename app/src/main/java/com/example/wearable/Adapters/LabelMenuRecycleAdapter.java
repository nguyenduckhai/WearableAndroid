package com.example.wearable.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.wearable.Holders.LabelMenuViewHolder;
import com.example.wearable.Interfaces.LabelMenuClickListener;
import com.example.wearable.models.networkmodels.RequestActionModel;
import com.example.wearable.R;
import com.example.wearable.databinding.ItemLabelMenuBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class LabelMenuRecycleAdapter extends RecyclerView.Adapter<LabelMenuViewHolder> {
    private LabelMenuClickListener mLabelMenuClickListener;
    private List<RequestActionModel> items;
    private Context mContext;
    private Activity mActivity;

    /** get and set */
    public List<RequestActionModel> getItems() {
        return items;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }


    public void setItems(List<RequestActionModel> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    public void setLabelMenuClickListener(LabelMenuClickListener labelMenuClickListener) {
        mLabelMenuClickListener = labelMenuClickListener;
    }
    /** constructor */
    public LabelMenuRecycleAdapter(List<RequestActionModel> items, Context context) {
        this.items = items;
        mContext = context;
    }

    @NonNull
    @Override
    public LabelMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemLabelMenuBinding itemLabelMenuBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.item_label_menu,parent,false);
        return new LabelMenuViewHolder(itemLabelMenuBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelMenuViewHolder holder, int position) {
        RequestActionModel item = items.get(position);
        holder.bind(item,position,mActivity);
        holder.setLabelMenuClickListener(new LabelMenuClickListener() {
            @Override
            public void onMenuClick(int postion) {
                if (mLabelMenuClickListener != null){
                    mLabelMenuClickListener.onMenuClick(postion);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
