package com.iot.covid.duantotnghiep.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.databinding.FragmentLoginBinding;
import com.iot.covid.duantotnghiep.doctor.MainActivity;
import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.patient.Patient;
import com.iot.covid.duantotnghiep.patient.PatientMainActivity;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;
import com.iot.covid.duantotnghiep.utils.UserLocalStore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button btnLogin;
    public LoginFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentLoginBinding loginBinding;
    private Retrofit retrofit;
    private CallService callService;
    private UserLocalStore userLocalStore;
    AlertDialog alertDialog ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userLocalStore = new UserLocalStore(getContext());
        loginBinding=FragmentLoginBinding.inflate(inflater,container,false);
        return loginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        alertDialog = new AlertDialog.Builder(getContext()).create();
        //new Handler().postDelayed(() -> {
                Toast.makeText(getContext(), "Xin mời đăng nhập !", Toast.LENGTH_SHORT).show();
                loginBinding.btnLogin.setOnClickListener(v -> {
                    if (loginBinding.cbTypeLogin.isChecked()){
                        userLocalStore.setTypeLogin("Doctor");
                        loginDoctor(loginBinding.edtUsername.getText().toString(),loginBinding.edtPassword.getText().toString());

                    }else{
                        userLocalStore.setTypeLogin("Patient");
                        loginPatient(loginBinding.edtUsername.getText().toString(),loginBinding.edtPassword.getText().toString());
                    }
                });
    }
    private void showDialog(){
        alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("Đăng nhập");
        alertDialog.setMessage("Đang đăng nhập...");
        alertDialog.show();
    }
    //sontcD
    private void loginDoctor(String username,String password){
        showDialog();
        Toast.makeText(getContext(), "Bạn đang đăng nhập bằng bác sĩ !", Toast.LENGTH_SHORT).show();
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
                        Intent intent = new Intent(getContext(),MainActivity.class);
                        Bundle bundle = new Bundle();
                        userLocalStore.saveUser(response.body());
                        userLocalStore.setUserLogged(true);
                        bundle.putSerializable("doctor",response.body());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "account not found", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        Log.e("doctor-",response.message());
                    }
                }else {
                    Toast.makeText(getContext(), "validated failed !", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    Log.e("doctor-not-success:",response.message());
                }
            }

            @Override
            public void onFailure(Call<Doctor> call, Throwable t) {
                Toast.makeText(getContext(), "Waiting...", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }

    private void loginPatient(String username,String password){
        showDialog();
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
                        Intent intent = new Intent(getContext(), PatientMainActivity.class);
                        Bundle bundle = new Bundle();
                        //Log.e("patient00000000",""+response.body().toString());
                        userLocalStore.savePatient(response.body());
                        userLocalStore.setUserLogged(true);
                        bundle.putSerializable("patient",response.body());
                        intent.putExtras(bundle);
                        startActivity(intent);
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "account not found", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                        Log.e("patient-",response.message());
                    }
                }else {
                    Toast.makeText(getContext(), "validated failed !", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    Log.e("patient-not-success:",response.message());
                }
            }

            @Override
            public void onFailure(Call<Patient> call, Throwable t) {
                Toast.makeText(getContext(), "Waiting...", Toast.LENGTH_SHORT).show();
                alertDialog.dismiss();
            }
        });
    }

}