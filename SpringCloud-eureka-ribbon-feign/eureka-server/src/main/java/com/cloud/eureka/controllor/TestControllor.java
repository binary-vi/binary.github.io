package com.cloud.eureka.controllor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestControllor {

    @Value("${server.port}")
    private String prot;
    @Value(("${spring.application.name}"))
    private String serverName;

    @RequestMapping("test")
    public String test() {
        String str = "服务名：" + serverName + ",端口：" + prot;
        System.out.println(str);
        return str;
    }
}
