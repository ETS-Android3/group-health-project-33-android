package com.iot.covid.duantotnghiep.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityShowHistoryBinding;
import com.iot.covid.duantotnghiep.doctor.adapter.TreatmentAdapter;
import com.iot.covid.duantotnghiep.model.device.TreatmentCourse;

import java.util.List;

public class ShowHistoryActivity extends AppCompatActivity {
    private ActivityShowHistoryBinding historyBinding;
    private TreatmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding=ActivityShowHistoryBinding.inflate(getLayoutInflater());
        setContentView(historyBinding.getRoot());
        adapter = new TreatmentAdapter();
        List<TreatmentCourse> lists = (List<TreatmentCourse>) getIntent().getSerializableExtra("treatment");
        adapter.setTreatmentCourseList(lists);
        historyBinding.rcvHistoryTrack.setAdapter(adapter);
        historyBinding.rcvHistoryTrack.setHasFixedSize(true);
        historyBinding.rcvHistoryTrack.setLayoutManager(new LinearLayoutManager(ShowHistoryActivity.this, RecyclerView.VERTICAL, false));
    }
}