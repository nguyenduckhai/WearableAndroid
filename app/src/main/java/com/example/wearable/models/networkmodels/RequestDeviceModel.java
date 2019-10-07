package com.example.wearable.models.networkmodels;

public class RequestDeviceModel {
    private String id;
    private String wearable_label_id;
    private int x_value;
    private int y_value;
    private int z_value;
    private String timest;

    /** getter and setter */
    public int getX_value() {
        return x_value;
    }

    public int getY_value() {
        return y_value;
    }

    public int getZ_value() {
        return z_value;
    }

    /** constructor */
    public RequestDeviceModel(int x_value, int y_value, int z_value) {
        this.x_value = x_value;
        this.y_value = y_value;
        this.z_value = z_value;
    }
}
