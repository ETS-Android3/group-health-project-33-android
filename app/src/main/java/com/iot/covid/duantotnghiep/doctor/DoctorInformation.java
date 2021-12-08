package com.iot.covid.duantotnghiep.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityDoctorInformationBinding;
import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.doctors.Token;
import com.iot.covid.duantotnghiep.utils.UserLocalStore;

public class DoctorInformation extends AppCompatActivity {
    private ActivityDoctorInformationBinding binding;
    private UserLocalStore userLocalStore;
    //private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDoctorInformationBinding.inflate(getLayoutInflater());

        //setContentView(R.layout.activity_doctor_information);
        setContentView(binding.getRoot());
        userLocalStore = new UserLocalStore(this);
//
        Doctor d = (Doctor) userLocalStore.getLoggedUser();
        binding.setDoctor(d);
        Token t = new Token();

        t.setToken(getIntent().getStringExtra("token"));

        binding.setT(t);

    }

    public void ClickMenu(View view) {
        UserLocalStore userLocalStore = new UserLocalStore(this);
        Doctor d = userLocalStore.getLoggedUser();
        MainActivity.openDrawer(binding.drawerLayout,d.getName());
    }

    public void ClickLogo(View view) {
    }

    public void ClickHome(View view) {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void ClickUrgentNotice(View view) {
        Intent i = new Intent(this,UrgentNotice.class);
        startActivity(i);
    }

    public void ClickDoctorInformation(View view) {
    }

    public void ClickLogout(View view) {
        MainActivity.logout(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(binding.drawerLayout);
    }
}