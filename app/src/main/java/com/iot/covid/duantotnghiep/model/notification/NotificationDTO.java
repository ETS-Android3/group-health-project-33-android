package com.iot.covid.duantotnghiep.model.notification;


import java.io.Serializable;

public class NotificationDTO  implements Serializable {
    String id;
    String title;
    String name;
    String room;
    String age;
    String status;
    String medicine;
    String amountAndUse ;


    public NotificationDTO(String id, String title, String name, String room, String age, String status, String medicine, String amountAndUse) {
        this.id = id;
        this.title = title;
        this.name = name;
        this.room = room;
        this.age = age;
        this.status = status;
        this.medicine = medicine;
        this.amountAndUse = amountAndUse;
    }

    public NotificationDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getAmountAndUse() {
        return amountAndUse;
    }

    public void setAmountAndUse(String amountAndUse) {
        this.amountAndUse = amountAndUse;
    }

    @Override
    public String toString() {
        return "NotificationDTO{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", name='" + name + '\'' +
                ", room='" + room + '\'' +
                ", age='" + age + '\'' +
                ", status='" + status + '\'' +
                ", medicine='" + medicine + '\'' +
                ", amountAndUse='" + amountAndUse + '\'' +
                '}';
    }
}
