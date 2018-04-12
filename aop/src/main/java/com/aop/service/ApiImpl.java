package com.aop.service;

import com.aop.api.ApiInterface;
import com.aop.request.RequestData;
import com.aop.response.ResponseData;
import org.springframework.stereotype.Service;

/**
 * Created by shaobo on 2018/4/12.
 */
@Service
public class ApiImpl implements ApiInterface {

    @Override
    public ResponseData helloWorld(RequestData requestData) {
        ResponseData responseData = new ResponseData("SUCCESS","请求成功！");
        System.out.println("impl 请求参数:" + requestData.getData());
        return responseData;
    }
}
