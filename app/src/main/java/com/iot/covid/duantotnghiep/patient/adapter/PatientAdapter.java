package com.iot.covid.duantotnghiep.patient.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot.covid.duantotnghiep.databinding.ItemListPatientBinding;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.patient.PatientInformation;

import java.lang.reflect.Type;
import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private List<Patient> patientList;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Context context;


    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
        notifyDataSetChanged();
    }

    public PatientAdapter(Context context,SharedPreferences sharedPreferences,Gson gson) {
        this.context=context;
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
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
        holder.patientBinding.setPatient(patientList.get(position));
        holder.patientBinding.patientItem.setOnClickListener(view ->{
            Intent  intent = new Intent(context, PatientInformation.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("patient",patientList.get(position));
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           context.startActivity(intent);
        });
        if (patientList.get(position).getState() == 0 ){
            holder.patientBinding.itemPatient.setBackgroundColor(Color.rgb(209,231,221));
        } else if (patientList.get(position).getState() == 1 ){
            holder.patientBinding.itemPatient.setBackgroundColor(Color.rgb(207,244,252));
        } else if (patientList.get(position).getState() == 2 ){
            holder.patientBinding.itemPatient.setBackgroundColor(Color.rgb(255,243,205));
        } else if (patientList.get(position).getState() == 3 ){
            holder.patientBinding.itemPatient.setBackgroundColor(Color.rgb(248,215,218));
        }
        holder.patientBinding.patientItem.setOnLongClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String json = sharedPreferences.getString("arrayId",null);
            Type type = new TypeToken<List<String>>() {}.getType();
            List<String> data = gson.fromJson(json,type);
            data.remove(String.valueOf(patientList.get(position).getId()));
            String jsonData = gson.toJson(data);
            editor.putString("arrayId",jsonData);
            editor.commit();
            patientList.remove(position);
            Toast.makeText(context, "Xoá thành công !", Toast.LENGTH_SHORT).show();
            notifyDataSetChanged();
            return true;
        });
        holder.patientBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        if (patientList!=null)return patientList.size();
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ItemListPatientBinding patientBinding;
        public ViewHolder(@NonNull ItemListPatientBinding patientBinding) {
            super(patientBinding.getRoot());
            this.patientBinding=patientBinding;
        }
    }
}
