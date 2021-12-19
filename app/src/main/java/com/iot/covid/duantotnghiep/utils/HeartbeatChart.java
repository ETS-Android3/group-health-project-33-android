package com.iot.covid.duantotnghiep.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.iot.covid.duantotnghiep.databinding.ActivityHeartbeatChartBinding;
import com.iot.covid.duantotnghiep.model.device.Heart;

import java.util.List;

public class HeartbeatChart extends AppCompatActivity {
    private ActivityHeartbeatChartBinding heartbeatChartBinding;
    private HeartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        heartbeatChartBinding = ActivityHeartbeatChartBinding.inflate(getLayoutInflater());
        setContentView(heartbeatChartBinding.getRoot());
        adapter = new HeartAdapter();

        List<Heart> heart = (List<Heart>) getIntent().getSerializableExtra("heart");

       // Log.e("real time heart ",""+heart.get(0).getRealTime());
        adapter.setHeartList(heart);
        heartbeatChartBinding.rcvHeartBeatChar.setAdapter(adapter);
        heartbeatChartBinding.rcvHeartBeatChar.setHasFixedSize(false);
        heartbeatChartBinding.rcvHeartBeatChar.setLayoutManager(new LinearLayoutManager(HeartbeatChart.this, RecyclerView.VERTICAL, false));
    }
}