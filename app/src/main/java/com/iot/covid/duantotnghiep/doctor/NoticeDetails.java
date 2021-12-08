package com.iot.covid.duantotnghiep.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityNoticeDetailsBinding;
import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.notification.NotificationDTO;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;
import com.iot.covid.duantotnghiep.utils.UserLocalStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class NoticeDetails extends AppCompatActivity {
    private ActivityNoticeDetailsBinding noticeDetailsBinding;
    private Retrofit retrofit;
    private CallService callService;
    private Patient patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noticeDetailsBinding = ActivityNoticeDetailsBinding.inflate(getLayoutInflater());
        setContentView(noticeDetailsBinding.getRoot());
        retrofit = RetrofitInstance.getInstance();
        callService = retrofit.create(CallService.class);
        NotificationDTO notify = (NotificationDTO) getIntent().getSerializableExtra("notify");
        noticeDetailsBinding.setNotify(notify);
        noticeDetailsBinding.executePendingBindings();

        patient = new Patient();
        JsonObject json = new JsonObject();
        json.addProperty("id",notify.getId());
        callService.findPatientById(json).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                       patient.setName(response.body().getName());
                        patient.setAge(response.body().getAge());
                        patient.setKeyDevice(response.body().getKeyDevice());
                        Log.e("RECORD-TAG","success "+ response.body());
                    }
                    else {
                        Log.e("RECORD-TAG","failed-body-non : "+ response.body());
                    }
                }else{
                    Log.e("RECORD-TAG","failed-isSuccess : "+ response.message());
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {

            }
        });


        Log.e("RECORD-TAG","patient "+ patient.getKeyDevice());
        noticeDetailsBinding.btnViewRecord.setOnClickListener(view->{
            Intent i = new Intent(NoticeDetails.this,PatientRecords.class);
            i.putExtra("keyDevice",patient.getKeyDevice());
            i.putExtra("age",patient.getAge());
            i.putExtra("name",patient.getName());
            i.putExtra("intent","notify");
            startActivity(i);
        });
        //call patient, intent qua


        SharedPreferences userEditor = getSharedPreferences("id",MODE_PRIVATE);
        SharedPreferences.Editor prefs = getSharedPreferences("User", Context.MODE_PRIVATE).edit();
        //update status doctor
        noticeDetailsBinding.btnSuccess.setOnClickListener(view->{
            Log.e("id",userEditor.getString("id",""));
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id",userEditor.getString("id",""));
            callService.updateStatusDoctor(jsonObject).enqueue(new Callback<Doctor>() {
                @Override
                public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                    Toast.makeText(NoticeDetails.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
//                    if (response.isSuccessful()){
//                        if (response.body()!=null){
//                            Log.e("RECORD-TAG","success ");
//                            Toast.makeText(NoticeDetails.this, "Update Success !", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Log.e("RECORD-TAG","failed-body-non : "+ response.body());
//                        }
//                    }else{
//                        Log.e("RECORD-TAG","failed-isSuccess : "+ response.message());
//                    }
                }
                @Override
                public void onFailure(Call<Doctor> call, Throwable t) {
                    Toast.makeText(NoticeDetails.this, "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                    Log.e("RECORD-TAG","failed call: "+ t.getMessage());
                }
            });
            prefs.clear();
            prefs.commit();
            Intent i = new Intent(NoticeDetails.this,UrgentNotice.class);
            startActivity(i);
        });
    }
}