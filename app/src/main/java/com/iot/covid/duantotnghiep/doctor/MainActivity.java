package com.iot.covid.duantotnghiep.doctor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityMainBinding;
import com.iot.covid.duantotnghiep.doctor.adapter.ListPatientAdapter;
import com.iot.covid.duantotnghiep.login.Login;
import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;
import com.iot.covid.duantotnghiep.utils.UserLocalStore;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding mainBinding;
    private ListPatientAdapter adapter;
    private Retrofit retrofit;
    private CallService callService;
    private List<Patient> patientList = new ArrayList<>();
    private String t ="";
    private static UserLocalStore userLocalStore;

    public static final String TAG =MainActivity.class.getName().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        adapter = new ListPatientAdapter();
        retrofit = RetrofitInstance.getInstance();
        callService = retrofit.create(CallService.class);
        Doctor d = (Doctor) getIntent().getSerializableExtra("doctor");
        SharedPreferences.Editor userEditor = getSharedPreferences("id",MODE_PRIVATE).edit();
        SharedPreferences idD = getSharedPreferences("id",MODE_PRIVATE);
        String idDoc = idD.getString("id",null);
        try {
            if (idDoc==null){
                userEditor.putString("id", d.get_id());
                userEditor.commit();
            }
        }catch (Exception e){}

        Log.e("idD",idDoc);

       getData();

        mainBinding.swipeData.setOnRefreshListener(() -> {
            getData();
            mainBinding.swipeData.setRefreshing(false);
            Toast.makeText(MainActivity.this, "Refresh Data Success !", Toast.LENGTH_SHORT).show();
        });


        try {
                FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()){
                        //Log.e(TAG,"Feching FCM regisration tolen failed",task.getException());
                        return;
                    }
                    String token = task.getResult();
                    t+=token;
                    JsonObject jsonObject = new JsonObject();
                   try {
                       d.setTokenn(""+t);
                       jsonObject.addProperty("key",d.get_id());
                       jsonObject.addProperty("tokennn",d.getTokenn());
                   }catch (Exception  e){}
                    Log.e("TOKEN-"+TAG,token);
                    callService.updateToken(jsonObject).enqueue(new Callback<Doctor>() {
                        @Override
                        public void onResponse(Call<Doctor> call, Response<Doctor> response) {
                            if (response.isSuccessful()){
                                if (response.body()!=null){
                                    //Log.e("update-token - "+TAG,"update success - "+jsonObject);
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Can't not update token !", Toast.LENGTH_SHORT).show();
                                Log.e("update-token - "+TAG,"update failed - isSuccess");
                            }
                        }

                        @Override
                        public void onFailure(Call<Doctor> call, @NonNull Throwable t) {
                            // Log.e("update-token-"+TAG,"update failed");
                        }
                    });

                });
        }catch (Exception e){

        }
        //Log.e("main doctor",""+(Doctor) getIntent().getSerializableExtra("doctor"));
    }

    private void getData() {
        callService.getPatient().enqueue(new Callback<List<Patient>>() {
            @Override
            public void onResponse(@NonNull Call<List<Patient>> call, @NonNull Response<List<Patient>> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        //Log.e("TAG","data: "+ response.body());
                        patientList =response.body();
                        adapter.setPatientList(patientList);
                        mainBinding.rcvListPatient.setAdapter(adapter);
                        mainBinding.rcvListPatient.setHasFixedSize(true);
                        mainBinding.rcvListPatient.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                        Toast.makeText(MainActivity.this, "Load data success !", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Can't not load patient !", Toast.LENGTH_SHORT).show();
                        //Log.e("TAG","failed body-non: "+ response.message());
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Can't not connected to server !", Toast.LENGTH_SHORT).show();
                    Log.e("TAG","failed isSuccessful: "+ response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Patient>> call, @NonNull Throwable t) {
                Log.e("TAG","failed call :"+t.getMessage());
            }
        });
    }

    public void ClickMenu(View view){
        UserLocalStore userLocalStore = new UserLocalStore(this);
        Doctor d = userLocalStore.getLoggedUser();
        openDrawer(mainBinding.drawerLayout,d.getName());
    }
    public static void openDrawer(DrawerLayout drawerLayout,String name){
        TextView tv = drawerLayout.findViewById(R.id.name_doctor);
        tv.setText(name);
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view) {
    }

    public static void redirectActivity(Activity activity) {
        //Intent intent = new Intent(activity,aclass);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //activity.startActivity(intent);
        activity.finish();
    }

    public void ClickUrgentNotice(View view) {
        Intent intent = new Intent(this,UrgentNotice.class);
        intent.putExtra("token",""+t);
        Bundle bundle = new Bundle();
        Doctor d = (Doctor) getIntent().getSerializableExtra("doctor");
        bundle.putSerializable("doctor",d);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void ClickDoctorInformation(View view) {
        Intent intent = new Intent(this, DoctorInformation.class);
        intent.putExtra("token",""+t);
        Bundle bundle = new Bundle();
        Doctor d = (Doctor) getIntent().getSerializableExtra("doctor");
        bundle.putSerializable("doctor",d);
        intent.putExtras(bundle);
        startActivity(intent);
    }


    public void ClickLogout(View view) {
        MainActivity.logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        userLocalStore=new UserLocalStore(activity);
        builder.setTitle("logout");
        builder.setMessage("Bạn có muốn thoát không ?");
        builder.setPositiveButton("ĐỒNG Ý", (dialog, which) -> {
            userLocalStore.clearUserData();
            Intent intent = new Intent(activity, Login.class);
            activity.startActivity(intent);

        });
        builder.setNegativeButton("KHÔNG", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(mainBinding.drawerLayout);
    }
}