package com.iot.covid.duantotnghiep.model.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class DataDevice implements Serializable {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("key_device")
    @Expose
    private String keyDevice;
    @SerializedName("heart")
    @Expose
    private List<Heart> heart = null;
    @SerializedName("spO2")
    @Expose
    private List<SpO2> spO2 = null;
    @SerializedName("temp")
    @Expose
    private List<Temp> temp = null;

    @SerializedName("treatment_course")
    @Expose
    private List<TreatmentCourse> treatment_course = null;

    @SerializedName("track_history")
    @Expose
    private List<TrackHistory> track_history = null;

    @SerializedName("state")
    @Expose
    private List<Status> status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeyDevice() {
        return keyDevice;
    }

    public void setKeyDevice(String keyDevice) {
        this.keyDevice = keyDevice;
    }

    public List<Heart> getHeart() {
        return heart;
    }

    public void setHeart(List<Heart> heart) {
        this.heart = heart;
    }

    public List<SpO2> getSpO2() {
        return spO2;
    }

    public void setSpO2(List<SpO2> spO2) {
        this.spO2 = spO2;
    }

    public List<Temp> getTemp() {
        return temp;
    }

    public void setTemp(List<Temp> temp) {
        this.temp = temp;
    }


    public List<TreatmentCourse> getTreatment_course() {
        return treatment_course;
    }

    public void setTreatment_course(List<TreatmentCourse> treatment_course) {
        this.treatment_course = treatment_course;
    }

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }

    public List<TrackHistory> getTrack_history() {
        return track_history;
    }

    public void setTrack_history(List<TrackHistory> track_history) {
        this.track_history = track_history;
    }
}