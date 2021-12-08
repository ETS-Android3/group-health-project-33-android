package com.iot.covid.duantotnghiep.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.iot.covid.duantotnghiep.databinding.ActivitySpO2ChartBinding;
import com.iot.covid.duantotnghiep.model.device.SpO2;

import java.util.List;

public class SpO2Chart extends AppCompatActivity {
    private ActivitySpO2ChartBinding spO2ChartBinding;
    private Spo2Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spO2ChartBinding = ActivitySpO2ChartBinding.inflate(getLayoutInflater());
        setContentView(spO2ChartBinding.getRoot());
        adapter = new Spo2Adapter();

        List<SpO2> spo2 = (List<SpO2>) getIntent().getSerializableExtra("spo2");

        adapter.setSpO2List(spo2);
        spO2ChartBinding.rcvSPO2Char.setAdapter(adapter);
        spO2ChartBinding.rcvSPO2Char.setHasFixedSize(false);
        spO2ChartBinding.rcvSPO2Char.setLayoutManager(new LinearLayoutManager(SpO2Chart.this, RecyclerView.VERTICAL, false));

    }
}