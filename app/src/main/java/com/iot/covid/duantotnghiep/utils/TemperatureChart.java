package com.iot.covid.duantotnghiep.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.iot.covid.duantotnghiep.databinding.ActivityTemperatureChartBinding;
import com.iot.covid.duantotnghiep.model.device.Temp;

import java.util.List;

public class TemperatureChart extends AppCompatActivity {
    private ActivityTemperatureChartBinding temperatureChartBinding;
    private TempAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        temperatureChartBinding = ActivityTemperatureChartBinding.inflate(getLayoutInflater());
        setContentView(temperatureChartBinding.getRoot());
        adapter = new TempAdapter();

        List<Temp> temps = (List<Temp>) getIntent().getSerializableExtra("temp");


        adapter.setTempList(temps);
        temperatureChartBinding.rcvTemperature.setAdapter(adapter);
        temperatureChartBinding.rcvTemperature.setHasFixedSize(false);
        temperatureChartBinding.rcvTemperature.setLayoutManager(new LinearLayoutManager(TemperatureChart.this, RecyclerView.VERTICAL, false));
    }
}