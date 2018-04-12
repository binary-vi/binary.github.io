package com.aop.response;

/**
 * Created by shaobo on 2018/4/12.
 */
public class ResponseData {
    private String respCode;
    private String respDesc;

    public ResponseData(String respCode, String respDesc) {
        this.respCode = respCode;
        this.respDesc = respDesc;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "respCode='" + respCode + '\'' +
                ", respDesc='" + respDesc + '\'' +
                '}';
    }
}
