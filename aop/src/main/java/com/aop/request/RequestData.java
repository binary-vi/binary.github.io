package com.aop.request;

/**
 * Created by shaobo on 2018/4/12.
 */
public class RequestData {
    private String data;

    public RequestData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestData{" +
                "data='" + data + '\'' +
                '}';
    }
}
