package com.iot.covid.duantotnghiep.service;

import com.google.gson.JsonObject;
import com.iot.covid.duantotnghiep.model.device.DataDevice;
import com.iot.covid.duantotnghiep.model.doctors.Doctor;
import com.iot.covid.duantotnghiep.model.patient.Patient;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CallService {
    //get patient
    @GET("/data/data-patient")
    Call<List<Patient>> getPatient();

    @POST("/data-a-device")
    Call<DataDevice> getDataDevice(@Body JsonObject key_device);

    @POST("/data-login-doctor")
    Call<Doctor> loginDoctor(@Body JsonObject login);

    @POST("/update-token")
    Call<Doctor> updateToken(@Body JsonObject doctor);

    //patient
    @POST("/data-login-patient")
    Call<Patient> loginPatient(@Body JsonObject patient);

    //find one patient
    @POST("/data-one-patient")
    Call<Patient> findPatientById(@Body JsonObject id);

    //update status doctor
    @POST("/updateStatus")
    Call<Doctor> updateStatusDoctor(@Body JsonObject _id);

    @POST("/update/track-history")
    Call<DataDevice> updateTrackHistory(@Body JsonObject json);

    @POST("/update/treatmentcourse")
    Call<DataDevice> updateTreatmentCourse(@Body JsonObject json);

    @POST("/updatestatuspt")
    Call<String> updateSOS(@Body JsonObject id);


}
