package com.iot.covid.duantotnghiep.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iot.covid.duantotnghiep.databinding.ItemHeartbeatChartBinding;
import com.iot.covid.duantotnghiep.model.device.Heart;

import java.util.Collections;
import java.util.List;

public class HeartAdapter extends RecyclerView.Adapter<HeartAdapter.ViewHolder> {
    private List<Heart> heartList;

    public void setHeartList(List<Heart> heartList) {
        this.heartList = heartList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemHeartbeatChartBinding binding = ItemHeartbeatChartBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.reverse(heartList);
holder.heartbeatChartBinding.setHeart(heartList.get(position));
holder.heartbeatChartBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (heartList!=null)return heartList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemHeartbeatChartBinding heartbeatChartBinding;
        public ViewHolder(@NonNull ItemHeartbeatChartBinding heartbeatChartBinding) {
            super(heartbeatChartBinding.getRoot());
            this.heartbeatChartBinding=heartbeatChartBinding;
        }
    }
}
