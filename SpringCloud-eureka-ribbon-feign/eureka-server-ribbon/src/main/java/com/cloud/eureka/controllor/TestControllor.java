package com.cloud.eureka.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by shaobo on 2018/3/5.
 */

@RestController
public class TestControllor {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过服务名调用
     * @return
     */
    @RequestMapping("test")
    public String test() {
        return restTemplate.getForObject("http://hi-eureka/test",String.class);
    }
}
