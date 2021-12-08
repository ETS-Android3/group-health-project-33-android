package com.iot.covid.duantotnghiep.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityUrgentNoticeBinding;
import com.iot.covid.duantotnghiep.doctor.adapter.ListNotifyAdapter;
import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.notification.NotificationDTO;
import com.iot.covid.duantotnghiep.utils.ObjectSerializer;
import com.iot.covid.duantotnghiep.utils.UserLocalStore;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UrgentNotice extends AppCompatActivity {
    private ActivityUrgentNoticeBinding urgentNoticeBinding;
    private ListNotifyAdapter adapter;
   // private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        urgentNoticeBinding = ActivityUrgentNoticeBinding.inflate(getLayoutInflater());
        setContentView(urgentNoticeBinding.getRoot());
        adapter = new ListNotifyAdapter(getBaseContext());

        ArrayList userList   = new ArrayList();
        SharedPreferences prefs = getSharedPreferences("User", Context.MODE_PRIVATE);
        try {
            userList = (ArrayList) ObjectSerializer.deserialize(prefs.getString("UserList", ObjectSerializer.serialize(new ArrayList())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<NotificationDTO> dtoArrayList = new ArrayList<>();
        dtoArrayList.addAll(userList);
        adapter.setNotificationDTOList(dtoArrayList);
        urgentNoticeBinding.rcvUrgentNotice.setAdapter(adapter);
        urgentNoticeBinding.rcvUrgentNotice.setHasFixedSize(true);
        urgentNoticeBinding.rcvUrgentNotice.setLayoutManager(new LinearLayoutManager(UrgentNotice.this, RecyclerView.VERTICAL, false));
        Log.e("notify",""+dtoArrayList.size());



    }
    public void ClickLogo(View view){
        MainActivity.redirectActivity(this);
    }
    public void ClickMenu(View view) {
       UserLocalStore userLocalStore = new UserLocalStore(this);
       Doctor d = userLocalStore.getLoggedUser();
        MainActivity.openDrawer(urgentNoticeBinding.drawerLayout,d.getName());
    }

    public void ClickHome(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("token",""+getIntent().getStringExtra("token"));
        Bundle bundle = new Bundle();
        Doctor d = (Doctor) getIntent().getSerializableExtra("doctor");
        bundle.putSerializable("doctor",d);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void ClickUrgentNotice(View view) {
    }

    public void ClickDoctorInformation(View view) {

        Intent intent = new Intent(this,DoctorInformation.class);
        intent.putExtra("token",""+getIntent().getStringExtra("token"));
        Bundle bundle = new Bundle();
        Doctor d = (Doctor) getIntent().getSerializableExtra("doctor");
        bundle.putSerializable("doctor",d);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void ClickLogout(View view) {
        MainActivity.logout(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        MainActivity.closeDrawer(urgentNoticeBinding.drawerLayout);
    }
}