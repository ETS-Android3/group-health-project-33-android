package com.iot.covid.duantotnghiep.doctor.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iot.covid.duantotnghiep.DetailTreatActivity;
import com.iot.covid.duantotnghiep.databinding.ItemTreatmentBinding;
import com.iot.covid.duantotnghiep.doctor.PatientRecords;
import com.iot.covid.duantotnghiep.doctor.TreatmentDetailActivity;
import com.iot.covid.duantotnghiep.doctor.TreatmentHistory;
import com.iot.covid.duantotnghiep.model.device.TreatmentCourse;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {
    private List<TreatmentCourse> treatmentCourseList;

    public void setTreatmentCourseList(List<TreatmentCourse> treatmentCourseList) {
        this.treatmentCourseList = treatmentCourseList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemTreatmentBinding binding = ItemTreatmentBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.treatmentBinding.setTreatment(treatmentCourseList.get(position));
        holder.treatmentBinding.executePendingBindings();
        holder.treatmentBinding.treatmentItem.setOnClickListener(view -> {
            Intent i = new Intent( holder.treatmentBinding.getRoot().getContext(), DetailTreatActivity.class);
            Bundle bundle= new Bundle();
            bundle.putSerializable("treatment",treatmentCourseList.get(position));
            i.putExtras(bundle);
            holder.treatmentBinding.getRoot().getContext().startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        if (treatmentCourseList!=null)return treatmentCourseList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ItemTreatmentBinding treatmentBinding;
        public ViewHolder(@NonNull ItemTreatmentBinding treatmentBinding) {
            super(treatmentBinding.getRoot());
            this.treatmentBinding = treatmentBinding;
        }
    }
}
