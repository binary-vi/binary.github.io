package com.aop.api;

import com.aop.request.RequestData;
import com.aop.response.ResponseData;

/**
 * Created by shaobo on 2018/4/12.
 */
public interface ApiInterface {

    ResponseData helloWorld(RequestData requestData);
}
