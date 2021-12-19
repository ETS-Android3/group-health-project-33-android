package com.iot.covid.duantotnghiep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.iot.covid.duantotnghiep.databinding.ActivityDetailTreatBinding;
import com.iot.covid.duantotnghiep.model.device.TreatmentCourse;

public class DetailTreatActivity extends AppCompatActivity {
    private ActivityDetailTreatBinding detailTreatBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailTreatBinding = ActivityDetailTreatBinding.inflate(getLayoutInflater());
        setContentView(detailTreatBinding.getRoot());
        TreatmentCourse treat = (TreatmentCourse) getIntent().getSerializableExtra("treatment");
        detailTreatBinding.setTreat(treat);
        detailTreatBinding.executePendingBindings();
    }
}