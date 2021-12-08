package com.iot.covid.duantotnghiep.doctor.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iot.covid.duantotnghiep.databinding.ItemListPatientBinding;
import com.iot.covid.duantotnghiep.doctor.PatientRecords;
import com.iot.covid.duantotnghiep.model.patient.Patient;

import java.util.List;

public class ListPatientAdapter extends RecyclerView.Adapter<ListPatientAdapter.ViewHolder>{
    private List<Patient> patientList;

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemListPatientBinding binding = ItemListPatientBinding.inflate(inflater,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemListPatientBinding.setPatient(patientList.get(position));
        holder.itemListPatientBinding.executePendingBindings();
        holder.itemListPatientBinding.patientItem.setOnClickListener(v->{
            Intent i = new Intent( holder.itemListPatientBinding.getRoot().getContext(), PatientRecords.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("patient",patientList.get(position));
            i.putExtra("intent","patient");
            i.putExtras(bundle);
            holder.itemListPatientBinding.getRoot().getContext().startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        if (patientList != null) return patientList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemListPatientBinding itemListPatientBinding;
        public ViewHolder(ItemListPatientBinding itemListPatientBinding) {
            super(itemListPatientBinding.getRoot());
            this.itemListPatientBinding=itemListPatientBinding;
        }
    }
}
