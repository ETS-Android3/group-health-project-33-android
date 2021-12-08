package com.iot.covid.duantotnghiep.utils;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iot.covid.duantotnghiep.databinding.ItemTemperatureChartBinding;

import com.iot.covid.duantotnghiep.model.device.Temp;

import java.util.Collections;
import java.util.List;

public class TempAdapter extends RecyclerView.Adapter<TempAdapter.ViewHolder> {
    private List<Temp> tempList;

    public void setTempList(List<Temp> tempList) {
        this.tempList = tempList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTemperatureChartBinding binding = ItemTemperatureChartBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Collections.reverse(tempList);
        holder.itemTemperatureChartBinding.setTemp(tempList.get(position));
        holder.itemTemperatureChartBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (tempList !=null)return tempList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemTemperatureChartBinding itemTemperatureChartBinding;
        public ViewHolder(@NonNull ItemTemperatureChartBinding itemTemperatureChartBinding) {
            super(itemTemperatureChartBinding.getRoot());
            this.itemTemperatureChartBinding=itemTemperatureChartBinding;
        }
    }
}
