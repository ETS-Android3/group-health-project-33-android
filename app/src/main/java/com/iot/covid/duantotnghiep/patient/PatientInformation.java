package com.iot.covid.duantotnghiep.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityPatientInformationBinding;
import com.iot.covid.duantotnghiep.doctor.PatientRecords;
import com.iot.covid.duantotnghiep.doctor.adapter.TreatmentAdapter;
import com.iot.covid.duantotnghiep.model.device.DataDevice;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;
import com.iot.covid.duantotnghiep.utils.HeartbeatChart;
import com.iot.covid.duantotnghiep.utils.SpO2Chart;
import com.iot.covid.duantotnghiep.utils.TemperatureChart;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PatientInformation extends AppCompatActivity {
    private Retrofit retrofit;
    private CallService callService;
    private ActivityPatientInformationBinding informationBinding;
    private DataDevice dataDevice;
    private TreatmentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        informationBinding = ActivityPatientInformationBinding.inflate(getLayoutInflater());
        setContentView(informationBinding.getRoot());
        retrofit = RetrofitInstance.getInstance();
        callService = retrofit.create(CallService.class);
        dataDevice = new DataDevice();
        adapter =new TreatmentAdapter();

        Patient patient = (Patient) getIntent().getSerializableExtra("patient");
//        Log.e("device",dataDevice.getTreatment_course().toString());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key_device",patient.getKeyDevice());
        Log.e("RECORD-TAG","json :  "+jsonObject);



        informationBinding.setPatient(patient);
        callService.getDataDevice(jsonObject).enqueue(new Callback<DataDevice>() {
            @Override
            public void onResponse(Call<DataDevice> call, Response<DataDevice> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Log.e("RECORD-TAG","success ");
                        dataDevice = response.body();
                        informationBinding.setDevice(dataDevice);
                        adapter.setTreatmentCourseList(dataDevice.getTreatmentCourse());
                        informationBinding.rcvHistory.setAdapter(adapter);
                        informationBinding.rcvHistory.setHasFixedSize(true);
                        informationBinding.rcvHistory.setLayoutManager(new LinearLayoutManager(PatientInformation.this, RecyclerView.VERTICAL, false));
                    }
                    else {
                        Log.e("RECORD-TAG","failed-body-non : "+ response.message());
                    }
                }else{
                    Log.e("RECORD-TAG","failed-isSuccess : "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<DataDevice> call, Throwable t) {
                Log.e("RECORD-TAG","failed call: "+ t.getMessage());
            }
        });

        informationBinding.cvHeartItem.setOnClickListener(view ->{
            Intent intent = new Intent(PatientInformation.this, HeartbeatChart.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("heart", (Serializable) dataDevice.getHeart());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        informationBinding.cvSpo2Item.setOnClickListener(view ->{
            Intent intent = new Intent(PatientInformation.this, SpO2Chart.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("spo2", (Serializable) dataDevice.getSpO2());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        informationBinding.cvTempItem.setOnClickListener(view ->{
            Intent intent = new Intent(PatientInformation.this, TemperatureChart.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("temp", (Serializable) dataDevice.getTemp());
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}