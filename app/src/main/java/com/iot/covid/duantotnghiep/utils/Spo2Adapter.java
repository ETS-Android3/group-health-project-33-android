package com.iot.covid.duantotnghiep.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iot.covid.duantotnghiep.databinding.ItemSpo2ChartBinding;
import com.iot.covid.duantotnghiep.model.device.SpO2;

import java.util.Collections;
import java.util.List;

public class Spo2Adapter extends RecyclerView.Adapter<Spo2Adapter.ViewHolder> {
    private List<SpO2> spO2List;

    public void setSpO2List(List<SpO2> spO2List) {
        this.spO2List = spO2List;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSpo2ChartBinding binding = ItemSpo2ChartBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.reverse(spO2List);
        holder.spo2ChartBinding.setSpo2(spO2List.get(position));
        holder.spo2ChartBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (spO2List !=null)return spO2List.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemSpo2ChartBinding spo2ChartBinding;
        public ViewHolder(@NonNull ItemSpo2ChartBinding spo2ChartBinding) {
            super(spo2ChartBinding.getRoot());
            this.spo2ChartBinding=spo2ChartBinding;
        }
    }
}
