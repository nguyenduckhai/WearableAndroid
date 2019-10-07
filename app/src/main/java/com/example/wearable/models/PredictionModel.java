package com.example.wearable.models;

public class PredictionModel {
    public static final int STAND = 1;
    public static final int SIT = 2;
    public static final int STAND_SIT = 3;
    private int action_id;

    public int getAction_id() {
        return action_id;
    }

    public PredictionModel setAction_id(int action_id) {
        this.action_id = action_id;
        return this;
    }

    public PredictionModel(int action_id) {
        this.action_id = action_id;
    }
}
