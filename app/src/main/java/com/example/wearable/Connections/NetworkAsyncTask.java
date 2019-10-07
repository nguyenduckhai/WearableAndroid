package com.example.wearable.Connections;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.wearable.models.networkmodels.RequestModel;

public class NetworkAsyncTask extends AsyncTask<RequestModel,Void,String> {
    private Activity mActivity;
    private MyAsyncTaskListener processing_listener;

    public void setProcessing_listener() {
    }

    public interface MyAsyncTaskListener {
        void onPreExecute();
        void onPostExecute(String result);
    }

    public Activity getActivity(){
        return mActivity;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public MyAsyncTaskListener getProcessing_listener() {
        return processing_listener;
    }

    public void setProcessing_listener(MyAsyncTaskListener processing_listener) {
        this.processing_listener = processing_listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (processing_listener != null){
            processing_listener.onPreExecute();
        }
    }

    @Override
    protected String doInBackground(RequestModel... params) {
        RequestModel requestModel = params[0];

        try{
            if (requestModel.getRequest_method() == RequestModel.GET){
                return Connections.getInstance(mActivity).get_api(requestModel);
            } else if (requestModel.getRequest_method() == RequestModel.POST) {
                return Connections.getInstance(mActivity).post_api(requestModel);
            } else if (requestModel.getRequest_method() == RequestModel.UPDATE) {
                return Connections.getInstance(mActivity).update_api(requestModel);
            } else if (requestModel.getRequest_method() == RequestModel.DELETE) {
                return Connections.getInstance(mActivity).delete_api(requestModel);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (processing_listener != null) processing_listener.onPostExecute(s);
    }
}
