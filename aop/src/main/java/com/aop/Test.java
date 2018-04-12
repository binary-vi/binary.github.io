package com.aop;

import com.aop.api.ApiInterface;
import com.aop.request.RequestData;
import com.aop.response.ResponseData;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by shaobo on 2018/4/12.
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ApiInterface apiInterface = context.getBean(ApiInterface.class);
        ResponseData responseData = apiInterface.helloWorld(new RequestData("请求参数：name=张三"));
        System.out.println("main end response:" + responseData);
    }
}
