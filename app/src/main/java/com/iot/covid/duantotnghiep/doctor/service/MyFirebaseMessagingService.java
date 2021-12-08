package com.iot.covid.duantotnghiep.doctor.service;


import static com.iot.covid.duantotnghiep.doctor.utils.MyApplication.CHANNEL_ID;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.R;
import com.iot.covid.duantotnghiep.databinding.ActivityUrgentNoticeBinding;
import com.iot.covid.duantotnghiep.doctor.MainActivity;
import com.iot.covid.duantotnghiep.doctor.UrgentNotice;
import com.iot.covid.duantotnghiep.doctor.adapter.ListNotifyAdapter;
import com.iot.covid.duantotnghiep.model.notification.NotificationDTO;
import com.iot.covid.duantotnghiep.service.CallService;
import com.iot.covid.duantotnghiep.service.RetrofitInstance;
import com.iot.covid.duantotnghiep.utils.ObjectSerializer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Retrofit;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> params = remoteMessage.getData();
        JSONObject object = new JSONObject(params);

       // Log.e("JSON_OBJECT", object.toString());
        Log.e("Notification Body", "Message Notification Body: " + remoteMessage.getNotification());
        Log.e("Notification Body", "Message Data Body: " + remoteMessage.getData());

        String id  = null;
        String title  = null;
        String name = null;
        String room = null;
        String age = null;
        String status = null;
        String medicine = null;
        String amountAndUse = null;
        try {
            id = object.getString("id");
            title = object.getString("title");
            room = object.getString("room");
            name = object.getString("name");
            status = object.getString("status");
             age = object.getString("age");
             medicine = object.getString("medicine");
             amountAndUse = object.getString("amountAndUse");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //dto
        ArrayList dtoList   = new ArrayList();
        dtoList.add(new NotificationDTO(id,title,name,room,age,status,medicine,amountAndUse));
        SharedPreferences prefs = getSharedPreferences("User", Context.MODE_PRIVATE);
        //save the user list to preference
        SharedPreferences.Editor editor = prefs.edit();

        try {
            editor.putString("UserList", ObjectSerializer.serialize(dtoList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        editor.commit();

        //Log.e("title-notify",""+title);
        sendNotification(title,room,age);
    }

    private void sendNotification(String title, String room,String age) {
        Intent intent = new Intent(this, UrgentNotice.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle(title)
                .setContentText("Phòng : "+room+"---"+age+" tuổi")
                .setSmallIcon(R.drawable.ic_logoapp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.logoapp))
                .setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager!=null){
            manager.notify(1,notification);
        }

    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e("token",token);
    }


}
