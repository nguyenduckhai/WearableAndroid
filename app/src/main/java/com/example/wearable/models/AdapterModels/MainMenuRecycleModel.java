package com.example.wearable.models.AdapterModels;

import com.example.wearable.models.ChartModel;
import com.example.wearable.models.networkmodels.RequestDeviceModel;
import com.example.wearable.models.PredictionModel;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class MainMenuRecycleModel {
    public static final int FOOTER = 1;
    public static final int PREDICTION = 2;
    public static final int ACCELEROMTER = 3;
    public static final int GYROSCOPE = 4;
    public static final int LABEL = 5;

    private int type;
    private PredictionModel mPredictionModel;
    private ChartModel mChartModel;

    private List<RequestDeviceModel> mAccList = new ArrayList<>();
    private List<Entry> Xacc_values = new ArrayList<Entry>();
    private List<Entry> Yacc_values = new ArrayList<Entry>();
    private List<Entry> Zacc_values = new ArrayList<Entry>();
    private int time_acc;

    private List<RequestDeviceModel> mGyroList = new ArrayList<>();
    private List<Entry> Xgyro_values = new ArrayList<Entry>();
    private List<Entry> Ygyro_values = new ArrayList<Entry>();
    private List<Entry> Zgyro_values = new ArrayList<Entry>();
    private int time_gyro;

    /** getter and setter */
    public PredictionModel getPredictionModel() {
        return mPredictionModel;
    }

    public MainMenuRecycleModel setPredictionModel(PredictionModel predictionModel) {
        mPredictionModel = predictionModel;
        return this;
    }

    public List<Entry> getXacc_values() {
        return Xacc_values;
    }

    public List<Entry> getYacc_values() {
        return Yacc_values;
    }

    public List<Entry> getZacc_values() {
        return Zacc_values;
    }

    public List<Entry> getXgyro_values() {
        return Xgyro_values;
    }

    public List<Entry> getYgyro_values() {
        return Ygyro_values;
    }

    public List<Entry> getZgyro_values() {
        return Zgyro_values;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTime_acc() {
        return time_acc;
    }

    public void setTime_acc(int time_acc) {
        this.time_acc = time_acc;
    }

    public int getTime_gyro() {
        return time_gyro;
    }

    public void setTime_gyro(int time_gyro) {
        this.time_gyro = time_gyro;
    }

    public List<RequestDeviceModel> getAccList() {
        return mAccList;
    }

    public List<RequestDeviceModel> getGyroList() {
        return mGyroList;
    }

    public MainMenuRecycleModel setAccList(List<RequestDeviceModel> deviceList, List<Entry> X_values, List<Entry> Y_values,
                                           List<Entry> Z_values) {
        mAccList = deviceList;
        this.Xacc_values = X_values;
        this.Yacc_values = Y_values;
        this.Zacc_values = Z_values;
        return this;
    }

    public MainMenuRecycleModel setGyroList(List<RequestDeviceModel> deviceList,List<Entry> X_values,List<Entry> Y_values,
                                           List<Entry> Z_values) {
        mGyroList = deviceList;
        this.Xgyro_values = X_values;
        this.Ygyro_values = Y_values;
        this.Zgyro_values = Z_values;
        return this;
    }


    /** constructor */
    public MainMenuRecycleModel(int type) {
        this.type = type;
    }

    public void updateChartAccValue(){
        for (RequestDeviceModel acc: mAccList){
            Xacc_values.add(new Entry(time_acc,acc.getX_value()));
            Yacc_values.add(new Entry(time_acc,acc.getY_value()));
            Zacc_values.add(new Entry(time_acc,acc.getZ_value()));
            time_acc++;
        }
    }

    public void updateChartGyroValue(){
        for (RequestDeviceModel gyro: mGyroList){
            Xgyro_values.add(new Entry(time_gyro,gyro.getX_value()));
            Ygyro_values.add(new Entry(time_gyro,gyro.getY_value()));
            Zgyro_values.add(new Entry(time_gyro,gyro.getZ_value()));
            time_gyro++;
        }
    }

    public void checkAcc(int X_LIMIT){
        if (time_acc > X_LIMIT){
            time_acc = 0;
            Xacc_values.clear();
            Yacc_values.clear();
            Zacc_values.clear();
            updateChartAccValue();
        }else{
            updateChartAccValue();
        }
    }

    public void checkGyro(int X_LIMIT){
        if (time_gyro > X_LIMIT){
            time_gyro = 0;
            Xgyro_values.clear();
            Ygyro_values.clear();
            Zgyro_values.clear();
            updateChartGyroValue();
        }else{
            updateChartGyroValue();
        }
    }
}
