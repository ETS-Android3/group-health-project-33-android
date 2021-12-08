package com.iot.covid.duantotnghiep.model.device;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TrackHistory implements Serializable {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("real_time")
    @Expose
    private String realTime;
    @SerializedName("_id")
    @Expose
    private String id;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRealTime() {
        return realTime;
    }

    public void setRealTime(String realTime) {
        this.realTime = realTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDateFormatted() {
        Date d2 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyy");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = sdf.format(d2);
        return formattedDate;
    }
}
