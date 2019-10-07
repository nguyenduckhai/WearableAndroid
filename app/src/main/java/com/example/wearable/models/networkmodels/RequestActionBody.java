package com.example.wearable.models.networkmodels;

public class RequestActionBody {
    private String device_id;
    private String activity_id;
    private String time_end;

    /** getter and setter*/
    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }
}
