package com.iot.covid.duantotnghiep.patient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.iot.covid.duantotnghiep.databinding.ActivityPatientMainBinding;
import com.iot.covid.duantotnghiep.doctor.MainActivity;
import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.patient.adapter.PatientAdapter;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;
import com.iot.covid.duantotnghiep.utils.UserLocalStore;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PatientMainActivity extends AppCompatActivity {
    private ActivityPatientMainBinding mainBinding;
    private Retrofit retrofit;
    private CallService callService;
    private PatientAdapter adapter;
    private List<Patient> patientList;
    private List<String> listAdd;
    private SharedPreferences sharedPreferences;
    private Gson gson = new Gson();
    public UserLocalStore userLocalStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityPatientMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        retrofit = RetrofitInstance.getInstance();
        callService = retrofit.create(CallService.class);
        sharedPreferences = getSharedPreferences("arrayId",Context.MODE_PRIVATE);
        adapter = new PatientAdapter(getBaseContext(),sharedPreferences,gson);
        patientList = new ArrayList<>();
        listAdd = new ArrayList<>();
        userLocalStore=new UserLocalStore(this);
        String json = sharedPreferences.getString("arrayId",null);
        Type type = new TypeToken<List<String>>() {}.getType();
        //Log.e("data json ",""+gson.fromJson(json,type));
        List<String> data = gson.fromJson(json,type);
//        if (data!=null){
//            for (String id : data){
//                Log.e("id",id);
//                findAndAdd(id);
//            }
//        }
        addPatient();
        mainBinding.rcvPatient.setAdapter(adapter);
        mainBinding.rcvPatient.setHasFixedSize(true);
        mainBinding.rcvPatient.setLayoutManager(new LinearLayoutManager(PatientMainActivity.this, RecyclerView.VERTICAL, false));
        //Log.e("main array Id ",""+sharedPreferences.getString("arrayId",null));

    }
    private void addPatient() {
        mainBinding.fltAdd.setOnClickListener(view -> {
            LayoutInflater inflater = getLayoutInflater();
            View addDialog = inflater.inflate(R.layout.dialog_add_patient, null);
            EditText edtCode = addDialog.findViewById(R.id.edtCode);
            Button btnAdd = addDialog.findViewById(R.id.btnAdd);
            Button btnCancel = addDialog.findViewById(R.id.btnCancel);
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setView(addDialog);
            AlertDialog dialog = alert.create();
            dialog.show();
            btnAdd.setOnClickListener(view1->{
                String code = edtCode.getText().toString();
                if (listAdd.contains(code)){
                    Toast.makeText(PatientMainActivity.this, "Đã tồn tại bệnh nhân này !", Toast.LENGTH_SHORT).show();
                }else {
                    findAndAdd(code);
                }

            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

        });
    }

    private void findAndAdd(String code) {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id",code);

        callService.findPatientById(jsonObject).enqueue(new Callback<Patient>() {
            @Override
            public void onResponse(Call<Patient> call, Response<Patient> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        Log.e("patient find ",""+response.body().toString());
                        patientList.add(response.body());
                        listAdd.add(code);
                        String json = gson.toJson(listAdd);
                        editor.putString("arrayId",json);
                        editor.commit();

                        adapter.setPatientList(patientList);
                        Toast.makeText(PatientMainActivity.this, "Thêm thành công ! ", Toast.LENGTH_SHORT).show();

                        //Log.e("arrayId - ",""+sharedPreferences.getString("arrayId",null));
                    }
                    else {
                        Toast.makeText(PatientMainActivity.this, "Không tìm thấy bệnh nhân !", Toast.LENGTH_SHORT).show();
                        Log.e("main-patient","- body none code : "+ response.errorBody());
                    }
                }
                else {
                    Log.e("main-patient","- not isSuccessful code : "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(PatientMainActivity.this, "Error...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ClickMenu(View view){
        Patient p = (Patient) userLocalStore.getPatient();
        openDrawerPatient(mainBinding.patientDrawerLayout,p.getName());
    }
    public static void openDrawerPatient(DrawerLayout drawerLayout,String name){
        Log.e("patient","hahah");
        TextView tv = drawerLayout.findViewById(R.id.tv_name);
        tv.setText(name);
        drawerLayout.openDrawer(GravityCompat.START);

    }
    public void ClickHome(View view) {
    }


    public void ClickPatientPersonalInformation(View view) {
        Intent intent = new Intent(this, PatientPersonalInformation.class);
        Bundle bundle = new Bundle();
        Patient p = (Patient) getIntent().getSerializableExtra("patient");
        bundle.putSerializable("patient",p);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void ClickLogout(View view) {
        MainActivity.logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("logout");
        builder.setMessage("Bạn có muốn thoát không ?");
        builder.setPositiveButton("ĐỒNG Ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserLocalStore userLocalStore = new UserLocalStore(activity);
                userLocalStore.clearUserData();
                activity.finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("KHÔNG", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(mainBinding.patientDrawerLayout);
    }

}