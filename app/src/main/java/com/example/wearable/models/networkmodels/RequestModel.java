package com.example.wearable.models.networkmodels;

public class RequestModel {
    public static final int GET = 1;
    public static final int POST = 2;
    public static final int UPDATE = 3;
    public static final int DELETE = 4;

    private String api_url;
    private int request_method;
    private RequestActionBody body_request;
    private String inet1 = "192.168.1.230";
    private String inet2 = "192.168.10.104";

    public String postWearableLabel() {
        return "http://"+inet2 +":3000/wearable_labels";
    }

    public String getActivity(){
        return "http://"+inet2+":3000/activities";
    }

    public String putLastWearable(){
        return "http://"+inet2+":3000/last_wearable/update";
    }

    public String getPrediction(){
        return "http://"+inet2+":3000/prediction";
    }

    /** getter and setter */

    public String getApi_url() {
        return api_url;
    }

    public void setApi_url(String api_url) {
        this.api_url = api_url;
    }

    public int getRequest_method() {
        return request_method;
    }

    public void setRequest_method(int request_method) {
        this.request_method = request_method;
    }

    public RequestActionBody getBody_request() {
        return body_request;
    }

    public void setBody_request(RequestActionBody body_request) {
        this.body_request = body_request;
    }

    public String getInet1() {
        return inet1;
    }

    public String getInet2() {
        return inet2;
    }
}
