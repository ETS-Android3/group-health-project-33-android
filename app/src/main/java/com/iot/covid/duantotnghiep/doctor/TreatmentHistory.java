package com.iot.covid.duantotnghiep.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityTreatmentHistoryBinding;
import com.iot.covid.duantotnghiep.model.device.TreatmentCourse;

import java.util.ArrayList;
import java.util.List;

public class TreatmentHistory extends AppCompatActivity {
private ActivityTreatmentHistoryBinding historyBinding;
private List<TreatmentCourse> courseList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        historyBinding = ActivityTreatmentHistoryBinding.inflate(getLayoutInflater());
        setContentView(historyBinding.getRoot());
        courseList = new ArrayList<>();




    }
}