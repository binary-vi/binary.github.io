package com.cloud.eureka.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by shaobo on 2018/3/6.
 */
@FeignClient(value = "hi-eureka")
public interface FeignApi {

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    String serviceHi();
}
