package com.iot.covid.duantotnghiep.model.patient;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class Patient implements Serializable {

    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("birth_day")
    @Expose
    private String birthDay;
    @SerializedName("phone")
    @Expose
    private Integer phone;
    @SerializedName("number_room")
    @Expose
    private Integer numberRoom;
    @SerializedName("key_device")
    @Expose
    private String keyDevice;
    @SerializedName("done")
    @Expose
    private String done;
    @SerializedName("state")
    @Expose
    private Integer state;
    @SerializedName("date_added")
    @Expose
    private String dateAdded;
    @SerializedName("homie_patient")
    @Expose
    private String homiePatient;
    @SerializedName("homie_phone")
    @Expose
    private Integer homiePhone;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Integer getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(Integer numberRoom) {
        this.numberRoom = numberRoom;
    }

    public String getKeyDevice() {
        return keyDevice;
    }

    public void setKeyDevice(String keyDevice) {
        this.keyDevice = keyDevice;
    }

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getHomiePatient() {
        return homiePatient;
    }

    public void setHomiePatient(String homiePatient) {
        this.homiePatient = homiePatient;
    }

    public Integer getHomiePhone() {
        return homiePhone;
    }

    public void setHomiePhone(Integer homiePhone) {
        this.homiePhone = homiePhone;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Patient(String _id, String name, String username, String password, Integer age, String birthDay, Integer phone, Integer numberRoom, String keyDevice, String dateAdded, String homiePatient, Integer homiePhone) {
        this._id=_id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.birthDay = birthDay;
        this.phone = phone;
        this.numberRoom = numberRoom;
        this.keyDevice = keyDevice;
        this.dateAdded = dateAdded;
        this.homiePatient = homiePatient;
        this.homiePhone = homiePhone;
    }

    public Patient() {
    }

    @Override
    public String toString() {
        return "Patient{" +
                "_id='" + _id + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", birthDay='" + birthDay + '\'' +
                ", phone=" + phone +
                ", numberRoom=" + numberRoom +
                ", keyDevice='" + keyDevice + '\'' +
                ", done='" + done + '\'' +
                ", state=" + state +
                ", dateAdded='" + dateAdded + '\'' +
                ", homiePatient='" + homiePatient + '\'' +
                ", homiePhone=" + homiePhone +
                ", v=" + v +
                '}';
    }
}
