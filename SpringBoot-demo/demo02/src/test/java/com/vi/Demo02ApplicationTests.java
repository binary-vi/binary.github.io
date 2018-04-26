package com.vi;

import com.vi.entity.UserInfo;
import com.vi.service.UserInfoInterface;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo02ApplicationTests {

    @Autowired
    private UserInfoInterface userInfoInterface;

    @Before
    public void initData() {
        userInfoInterface.save(new UserInfo("张三", 10, "123456"));
        userInfoInterface.save(new UserInfo("李四", 15, "567890"));
    }

    @After
    public void deleteData() {
        userInfoInterface.deleteAll();
    }

    @Test
    public void test() {
        List<UserInfo> userInfoList = userInfoInterface.findAll();
        Assert.assertEquals(userInfoList.size(), 2);
        for (UserInfo userInfo : userInfoList) {
            System.out.println(userInfo);
            userInfo.setPwd("AAABBB");
        }
        userInfoInterface.save(userInfoList);
        for (UserInfo userInfo : userInfoInterface.findAll()) {
            Assert.assertEquals(userInfo.getPwd(), "AAABBB");
        }
    }

}
