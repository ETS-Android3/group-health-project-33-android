package com.iot.covid.duantotnghiep.utils;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.R;

import com.iot.covid.duantotnghiep.doctor.MainActivity;
import com.iot.covid.duantotnghiep.login.Login;
import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.patient.PatientMainActivity;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashScreenActivity extends AppCompatActivity {

    private UserLocalStore userLocalStore;
    private CallService callService;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        userLocalStore = new UserLocalStore(SplashScreenActivity.this);

        Log.e("authenticate-splash",""+authenticate());
        Log.e("type-login-splash",userLocalStore.getTypeLogin());
        if (authenticate()){
            if (userLocalStore.getTypeLogin().equals("Doctor")){
                loginDoctor(userLocalStore.getLoggedUser().getUsername(),userLocalStore.getLoggedUser().getPassword());
            } else if (userLocalStore.getTypeLogin().equals("Patient")){
                loginPatient(userLocalStore.getLoggedUser().getUsername(),userLocalStore.getLoggedUser().getPassword());
            }
        }else {
            Intent intent = new Intent(SplashScreenActivity.this, Login.class);
            startActivity(intent);
        }
    }
    private boolean authenticate(){
        return userLocalStore.getUserLogged();
    }

    private void loginDoctor(String username,String password){

        Toast.makeText(getBaseContext(), "Bạn đang đăng nhập bằng bác sĩ !", Toast.LENGTH_SHORT).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username);
        jsonObject.addProperty("password",password);
        Log.e("RECORD-TAG","json :  "+jsonObject);

        retrofit = RetrofitInstance.getInstance();
        callService = retrofit.create(CallService.class);

        callService.loginDoctor(jsonObject).enqueue(new Callback<Doctor>() {
            @Override
            public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        userLocalStore.saveUser(response.body());
                        userLocalStore.setUserLogged(true);
                        bundle.putSerializable("doctor",response.body());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getBaseContext(), "account not found", Toast.LENGTH_SHORT).show();
                        Log.e("doctor-",response.message());
                    }
                }else {
                    Toast.makeText(getBaseContext(), "validated failed !", Toast.LENGTH_SHORT).show();
                    Log.e("doctor-not-success:",response.message());
                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Waiting...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginPatient(String username,String password){


        Toast.makeText(getBaseContext(), "Bạn đang đăng nhập bằng tài khoản khách !", Toast.LENGTH_SHORT).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("username",username);
        jsonObject.addProperty("password",password);
        Log.e("RECORD-TAG","json :  "+jsonObject);

        retrofit = RetrofitInstance.getInstance();
        callService = retrofit.create(CallService.class);


        callService.loginPatient(jsonObject).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Intent intent = new Intent(SplashScreenActivity.this, PatientMainActivity.class);
                        Bundle bundle = new Bundle();
                        //Log.e("patient",""+response.body().toString());
                        userLocalStore.savePatient(response.body());
                        userLocalStore.setUserLogged(true);
                        bundle.putSerializable("patient",response.body());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getBaseContext(), "account not found", Toast.LENGTH_SHORT).show();
                        //Log.e("patient-",response.message());
                    }
                }else {
                    Toast.makeText(getBaseContext(), "validated failed !", Toast.LENGTH_SHORT).show();
                    //Log.e("patient-not-success:",response.message());
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Waiting...", Toast.LENGTH_SHORT).show();
            }
        });
    }

}