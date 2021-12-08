package com.iot.covid.duantotnghiep.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityTreatmentDetailBinding;
import com.iot.covid.duantotnghiep.model.device.TreatmentCourse;

public class TreatmentDetailActivity extends AppCompatActivity {
private ActivityTreatmentDetailBinding detailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = ActivityTreatmentDetailBinding.inflate(getLayoutInflater());
        setContentView(detailBinding.getRoot());
        TreatmentCourse  course = (TreatmentCourse) getIntent().getSerializableExtra("treatment");

        detailBinding.setTreatment(course);
        detailBinding.executePendingBindings();

    }
}