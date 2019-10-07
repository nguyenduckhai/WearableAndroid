package com.example.wearable.models;

import android.graphics.Color;

import com.example.wearable.models.networkmodels.RequestDeviceModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class ChartModel {
    static final float X_LIMIT = 500;
    static final float X_DISTANCE_MINIMUM_WINDOW = 10f;
    static final float X_DISTANCE_MAXIMUM_WINDOW = 30f;
    static final String TAG = "time";

    private int mTime;
    private List<Entry> mX_values = new ArrayList<>();
    private List<Entry> mY_values = new ArrayList<>();
    private List<Entry> mZ_values = new ArrayList<>();
    private String message;

    private LineChart lineChart;
    private LineData data;
    private LargeValueFormatter mFormatter;

    /** getter and setter */
    public List<Entry> getX_value() {
        return mX_values;
    }

    public List<Entry> getY_value() {
        return mY_values;
    }

    public List<Entry> getZ_value() {
        return mZ_values;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTime() {
        return mTime;
    }

    /** constructor */
    public ChartModel(int time, List<Entry> x_values, List<Entry> y_values, List<Entry> z_values, LineChart lineChart) {
        mTime = time;
        mX_values = x_values;
        mY_values = y_values;
        mZ_values = z_values;
        this.lineChart = lineChart;
    }

    /** methods */
//    public void setChartValue(List<RequestDeviceModel> deviceObj){
//        for (RequestDeviceModel device:deviceObj){
//            mX_values.add(new Entry(mTime,device.getX_value()));
//            mY_values.add(new Entry(mTime,device.getY_value()));
//            mZ_values.add(new Entry(mTime,device.getZ_value()));
//            mTime++;
//            Log.d(TAG,String.valueOf(mTime));
//        }
//    }

    public List<ILineDataSet> createChartView(List<Entry> x_values, List<Entry> y_values, List<Entry> z_values){
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        LineDataSet set_Xvalues = new LineDataSet(x_values,"X_value");
        LineDataSet set_Yvalues = new LineDataSet(y_values,"Y_value");
        LineDataSet set_Zvalues = new LineDataSet(z_values,"Z_value");

        set_Xvalues.setColor(Color.RED);
        set_Yvalues.setColor(Color.BLUE);
        set_Zvalues.setColor(Color.GREEN);

        set_Xvalues.setDrawCircles(false);
        set_Yvalues.setDrawCircles(false);
        set_Zvalues.setDrawCircles(false);

        dataSets.add(set_Xvalues);
        dataSets.add(set_Yvalues);
        dataSets.add(set_Zvalues);
        return dataSets;
    }

    public void resetChartView(List<RequestDeviceModel> deviceObjs){
        mTime = 0;
        mX_values.clear();
        mY_values.clear();
        mZ_values.clear();

        List<ILineDataSet> dataSets = createChartView(mX_values,mY_values,mZ_values);
        data = new LineData(dataSets);
        lineChart.setData(data);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(mFormatter);

        lineChart.getDescription().setEnabled(false);
        lineChart.setVisibleXRangeMinimum(X_DISTANCE_MINIMUM_WINDOW);
        lineChart.setVisibleXRangeMaximum(X_DISTANCE_MAXIMUM_WINDOW);
        lineChart.moveViewToX(mTime - 30);
        lineChart.invalidate(); // refresh
    }

    public void addDynamicData(List<RequestDeviceModel> deviceObjs){
        List<ILineDataSet> dataSets = createChartView(mX_values,mY_values,mZ_values);
        data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.getDescription().setEnabled(false);
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(mFormatter);

        lineChart.setVisibleXRangeMinimum(X_DISTANCE_MINIMUM_WINDOW);
        lineChart.setVisibleXRangeMaximum(X_DISTANCE_MAXIMUM_WINDOW);
        lineChart.moveViewToX(mTime - 30);
        lineChart.invalidate(); // refresh
    }
}
