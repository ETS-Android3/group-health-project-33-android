package com.iot.covid.duantotnghiep.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.notification.NotificationDTO;
import com.iot.covid.duantotnghiep.model.patient.Patient;

public class UserLocalStore {
    public static final String USER_SP="user-doctor";
    private SharedPreferences sharedPreferences;

    public UserLocalStore(Context context) {
        this.sharedPreferences=context.getSharedPreferences(USER_SP,0);
    }

    public void saveUser(Doctor doctor){
        SharedPreferences.Editor userEditor = sharedPreferences.edit();
        userEditor.putString("username", doctor.getUsername());
        userEditor.putString("password", doctor.getPassword());
        userEditor.putString("name", doctor.getName());
        userEditor.putBoolean("state", doctor.getState());
        userEditor.putString("token", doctor.getTokenn());
        userEditor.commit();
    }
    public void savePatient(Patient patient){
        SharedPreferences.Editor userEditor = sharedPreferences.edit();
        userEditor.putString("_id", patient.get_id());
        userEditor.putString("username", patient.getUsername());
        userEditor.putString("password", patient.getPassword());
        userEditor.putString("name", patient.getName());
        userEditor.putInt("numberRoom", patient.getNumberRoom());
        userEditor.putInt("phone", patient.getPhone());
        userEditor.putString("dob", patient.getBirthDay());
        userEditor.putString("keyDevice", patient.getKeyDevice());
        userEditor.putString("homieName", patient.getHomiePatient());
        userEditor.putInt("homiePhone", patient.getHomiePhone());
        userEditor.putInt("age", patient.getAge());
        userEditor.putString("dateAdd", patient.getDateAdded());
        userEditor.commit();
    }
    public Patient getPatient(){
        String _id = sharedPreferences.getString("_id","");
        String username = sharedPreferences.getString("username","");
        String password = sharedPreferences.getString("password","");
        String name = sharedPreferences.getString("name","");
        Integer room = sharedPreferences.getInt("numberRoom",0);
        Integer phone = sharedPreferences.getInt("phone",0);
        String dob = sharedPreferences.getString("dob","");
        String keyDevice = sharedPreferences.getString("keyDevice","");
        String homiePatient = sharedPreferences.getString("homieName","");
        Integer homiePhone = sharedPreferences.getInt("homiePhone",0);
        Integer age = sharedPreferences.getInt("age",0);
        String dateAdd = sharedPreferences.getString("dateAdd","");
        Patient p = new Patient(_id,name,username,password,age,dob,phone,room,keyDevice,dateAdd,homiePatient,homiePhone);
        return p;
    }
    public Doctor getLoggedUser(){
        String username = sharedPreferences.getString("username","");
        String password = sharedPreferences.getString("password","");
        String name = sharedPreferences.getString("name","");
        Boolean state = sharedPreferences.getBoolean("state",false);
        String token = sharedPreferences.getString("token","");
        Doctor d = new Doctor(username,password,name,state,token);
        return d;
    }

    public void setUserLogged(boolean loggedIn){
        SharedPreferences.Editor userEditor = sharedPreferences.edit();
        userEditor.putBoolean("loggedIn",loggedIn);
        userEditor.commit();
    }

    public boolean getUserLogged(){
       if (sharedPreferences.getBoolean("loggedIn",false)==true){
           return true;
       }else {
           return false;
       }
    }

    public void clearUserData(){
        SharedPreferences.Editor userEditor = sharedPreferences.edit();
        userEditor.clear();
        userEditor.commit();
    }

    public void setTypeLogin(String typeLogin){
        SharedPreferences.Editor userEditor = sharedPreferences.edit();
        userEditor.putString("type",typeLogin);
        userEditor.commit();
    }

    public String getTypeLogin( ){
        return sharedPreferences.getString("type","");
    }
}
