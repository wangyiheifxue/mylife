package com.mylife.test;

import com.mylife.bean.qo.user.UserQO;
import com.mylife.service.user.ITUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JUnitTest {

    @Autowired
    private ApplicationContext applicationContext;//spring应用环境

    @Test
    public void test(){
        ITUserService userService = applicationContext.getBean(ITUserService.class);
        userService.a();
    }
}
