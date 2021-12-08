package com.iot.covid.duantotnghiep.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.databinding.ActivityPatientInformationBinding;
import com.iot.covid.duantotnghiep.databinding.ActivityPatientPersonalInformationBinding;
import com.iot.covid.duantotnghiep.doctor.MainActivity;
import com.iot.covid.duantotnghiep.doctor.UrgentNotice;
import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;
import com.iot.covid.duantotnghiep.utils.UserLocalStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PatientPersonalInformation extends AppCompatActivity {
    private ActivityPatientPersonalInformationBinding patientInformationBinding;
    private UserLocalStore userLocalStore;
    private Retrofit retrofit;
    private CallService callService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        patientInformationBinding = ActivityPatientPersonalInformationBinding.inflate(getLayoutInflater());
        setContentView(patientInformationBinding.getRoot());
        userLocalStore = new UserLocalStore(this);
        retrofit = RetrofitInstance.getInstance();
        callService = retrofit.create(CallService.class);
        Patient p = (Patient) userLocalStore.getPatient();
        patientInformationBinding.setPatient(p);
        patientInformationBinding.btnSos.setOnClickListener(view->{
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id",p.get_id());
            Log.e("id",""+jsonObject);
            callService.updateSOS(jsonObject).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.e("qq",response.message());
                    Toast.makeText(PatientPersonalInformation.this, "Bạn vừa thông báo khẩn cấp thành công !", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("qq",t.getMessage());
                    Toast.makeText(PatientPersonalInformation.this, "Thông báo khẩn cấp thất bại !", Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
    public void ClickMenu(View view) {
        UserLocalStore userLocalStore = new UserLocalStore(this);
        Patient p = userLocalStore.getPatient();
        PatientMainActivity.openDrawerPatient(patientInformationBinding.patientDrawerLayout,p.getName());
    }

    public void ClickHome(View view) {
        Intent intent = new Intent(this, PatientMainActivity.class);
        startActivity(intent);
    }

    public void ClickUrgentNotice(View view) {
        Intent intent = new Intent(this, PatientPersonalInformation.class);
        startActivity(intent);
    }

    public void ClickPatientPersonalInformation(View view) {
    }

    public void ClickLogout(View view) {
        PatientMainActivity.logout(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(patientInformationBinding.patientDrawerLayout);
    }
}