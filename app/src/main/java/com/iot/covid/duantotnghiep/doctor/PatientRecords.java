package com.iot.covid.duantotnghiep.doctor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.databinding.ActivityPatientRecordsBinding;
import com.iot.covid.duantotnghiep.doctor.adapter.TreatmentAdapter;
import com.iot.covid.duantotnghiep.model.device.DataDevice;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;
import com.iot.covid.duantotnghiep.utils.HeartbeatChart;
import com.iot.covid.duantotnghiep.utils.SpO2Chart;
import com.iot.covid.duantotnghiep.utils.TemperatureChart;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PatientRecords extends AppCompatActivity {
    private ActivityPatientRecordsBinding recordsBinding;
    private Retrofit retrofit;
    private CallService callService;
    private DataDevice dataDevice;
    private TreatmentAdapter treatmentAdapter;
    private Patient patient;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recordsBinding = ActivityPatientRecordsBinding.inflate(getLayoutInflater());
        setContentView(recordsBinding.getRoot());
        retrofit = RetrofitInstance.getInstance();
        callService = retrofit.create(CallService.class);
        dataDevice = new DataDevice();
        treatmentAdapter = new TreatmentAdapter();



    String id = getIntent().getStringExtra("intent");
        if (id.equals("patient") ){
            Log.e("abc",id);
            loadDataPatient();
        }else if (id.equals("notify")){
            String key_device = getIntent().getStringExtra("keyDevice");
            String name = getIntent().getStringExtra("name");
            Integer age = getIntent().getIntExtra("age",0);
            Log.e("abc",id);
            Log.e("data",key_device+name+""+age);
            loadDataNotify(key_device,name,age);
        }



        recordsBinding.cvHeartItem.setOnClickListener(view ->{
            Intent intent = new Intent(PatientRecords.this, HeartbeatChart.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("heart", (Serializable) dataDevice.getHeart());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        recordsBinding.cvSpo2Item.setOnClickListener(view ->{
            Intent intent = new Intent(PatientRecords.this, SpO2Chart.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("spo2", (Serializable) dataDevice.getSpO2());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        recordsBinding.cvTempItem.setOnClickListener(view ->{
            Intent intent = new Intent(PatientRecords.this, TemperatureChart.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("temp", (Serializable) dataDevice.getTemp());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        recordsBinding.btnAddHistoryTrack.setOnClickListener(view ->{
            String data = recordsBinding.edtSymptom.getText().toString();
            JsonObject json = new JsonObject();
            json.addProperty("key_device",patient.getKeyDevice());
            json.addProperty("data",data);

            Log.e("json",""+json);
            callService.updateTreatmentCourse(json).enqueue(new Callback<DataDevice>() {
                @Override
                public void onResponse(Call<DataDevice> call, Response<DataDevice> response) {
                    if (response.isSuccessful()){
                        if (response.body()!=null){
                            Toast.makeText(PatientRecords.this, "Update History Track Success !", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.e("RECORD-TAG","failed-body-non : "+ response.body());
                        }
                    }else{
                        Log.e("RECORD-TAG","failed-isSuccess : "+ response.message());
                    }
                }

                @Override
                public void onFailure(Call<DataDevice> call, Throwable t) {
                    Log.e("Update history","failed call: "+ t.getMessage());
                }
            });

        });
        recordsBinding.btnShowHistoryTrack.setOnClickListener(view -> {
            Intent i = new Intent(PatientRecords.this,ShowHistoryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("treatment", (Serializable) dataDevice.getTreatment_course());
            i.putExtras(bundle);
            startActivity(i);
        });
    }

    private void loadDataPatient(){
         patient = (Patient) getIntent().getSerializableExtra("patient");
        //Log.e("patient",patient.getName());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key_device",patient.getKeyDevice());
        //jsonObject.addProperty("key_device","device01");
        Log.e("RECORD-TAG","json :  "+jsonObject);

        recordsBinding.setPatient(patient);
        callService.getDataDevice(jsonObject).enqueue(new Callback<DataDevice>() {
            @Override
            public void onResponse(Call<DataDevice> call, Response<DataDevice> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Log.e("RECORD-TAG","success ");
                        dataDevice = response.body();
                        recordsBinding.setDevice(dataDevice);
                        Collections.reverse(dataDevice.getTreatment_course());
                        treatmentAdapter.setTreatmentCourseList(dataDevice.getTreatment_course());
                        recordsBinding.rcvListHistory.setAdapter(treatmentAdapter);
                        recordsBinding.rcvListHistory.setHasFixedSize(true);
                        recordsBinding.rcvListHistory.setLayoutManager(new LinearLayoutManager(PatientRecords.this, RecyclerView.VERTICAL, false));
                    }
                    else {
                        Log.e("RECORD-TAG","failed-body-non : "+ response.body());
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
    }

    private void loadDataNotify(String key_device,String name,Integer age){
    Patient patient = new Patient();
    patient.setName(name);
    patient.setAge(age);
    recordsBinding.setPatient(patient);
////////////////////////////////////////////////////
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("key_device",key_device);
        //jsonObject.addProperty("key_device","device01");
        Log.e("RECORD-TAG","json :  "+jsonObject);

        callService.getDataDevice(jsonObject).enqueue(new Callback<DataDevice>() {
            @Override
            public void onResponse(Call<DataDevice> call, Response<DataDevice> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Log.e("RECORD-TAG","success ");
                        dataDevice = response.body();
                        recordsBinding.setDevice(dataDevice);
                        treatmentAdapter.setTreatmentCourseList(dataDevice.getTreatment_course());
                        recordsBinding.rcvListHistory.setAdapter(treatmentAdapter);
                        recordsBinding.rcvListHistory.setHasFixedSize(true);
                        recordsBinding.rcvListHistory.setLayoutManager(new LinearLayoutManager(PatientRecords.this, RecyclerView.VERTICAL, false));
                    }
                    else {
                        Log.e("RECORD-TAG","failed-body-non : "+ response.body());
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
    }

}