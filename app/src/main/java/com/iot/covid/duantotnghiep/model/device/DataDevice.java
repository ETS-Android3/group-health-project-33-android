package com.iot.covid.duantotnghiep.model.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class DataDevice implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("key_device")
    @Expose
    private String keyDevice;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("heart")
    @Expose
    private List<Heart> heart = null;
    @SerializedName("spO2")
    @Expose
    private List<SpO2> spO2 = null;
    @SerializedName("temp")
    @Expose
    private List<Temp> temp = null;
    @SerializedName("track_history")
    @Expose
    private List<TrackHistory> trackHistory = null;
    @SerializedName("treatment_course")
    @Expose
    private List<TreatmentCourse> treatmentCourse = null;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyDevice() {
        return keyDevice;
    }

    public void setKeyDevice(String keyDevice) {
        this.keyDevice = keyDevice;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public List<TrackHistory> getTrackHistory() {
        return trackHistory;
    }

    public void setTrackHistory(List<TrackHistory> trackHistory) {
        this.trackHistory = trackHistory;
    }

    public List<TreatmentCourse> getTreatmentCourse() {
        return treatmentCourse;
    }

    public void setTreatmentCourse(List<TreatmentCourse> treatmentCourse) {
        this.treatmentCourse = treatmentCourse;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

}